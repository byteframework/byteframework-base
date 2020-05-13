package com.byteframework.base.user.security.handler;

import com.byteframework.commons.constant.ErrorCodeEnum;
import com.byteframework.commons.web.BaseAction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 暂无权限处理类
 * @Author Sans
 * @CreateTime 2019/10/3 8:39
 */
@Component
public class UserAuthAccessDeniedHandler extends BaseAction implements AccessDeniedHandler {

    /**
     * 暂无权限返回结果
     *
     * @Author Sans
     * @CreateTime 2019/10/3 8:41
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) {
        this.responseFailure(ErrorCodeEnum.ERROR_A0301.getCode(), ErrorCodeEnum.ERROR_A0301.getMessage(), request, response);
    }
}
