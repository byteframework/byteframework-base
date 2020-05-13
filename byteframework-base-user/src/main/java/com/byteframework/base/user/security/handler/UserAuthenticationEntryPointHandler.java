package com.byteframework.base.user.security.handler;

import com.byteframework.commons.constant.ErrorCodeEnum;
import com.byteframework.commons.web.BaseAction;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户未登录处理类
 *
 * @Author Sans
 * @CreateTime 2019/10/3 8:55
 */
@Component
public class UserAuthenticationEntryPointHandler extends BaseAction implements AuthenticationEntryPoint {

    /**
     * 用户未登录返回结果
     *
     * @Author Sans
     * @CreateTime 2019/10/3 9:01
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        this.responseFailure(ErrorCodeEnum.ERROR_A0220.getCode(), ErrorCodeEnum.ERROR_A0220.getMessage(), request, response);
    }
}
