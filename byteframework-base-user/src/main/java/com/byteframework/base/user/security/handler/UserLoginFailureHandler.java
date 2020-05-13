package com.byteframework.base.user.security.handler;

import com.byteframework.commons.constant.ErrorCodeEnum;
import com.byteframework.commons.web.BaseAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 登录失败处理类
 * @Author Sans
 * @CreateTime 2019/10/3 9:06
 */
@Slf4j
@Component
public class UserLoginFailureHandler extends BaseAction implements AuthenticationFailureHandler {

    /**
     * 登录失败返回结果
     *
     * @Author Sans
     * @CreateTime 2019/10/3 9:12
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        // 这些对于操作的处理类可以根据不同异常进行不同处理
        if (exception instanceof UsernameNotFoundException) {
            log.info("【登录失败】" + exception.getMessage());
            this.responseFailure(ErrorCodeEnum.ERROR_A0201.getCode(), ErrorCodeEnum.ERROR_A0201.getMessage(), request, response);
        }
        if (exception instanceof LockedException) {
            log.info("【登录失败】" + exception.getMessage());
            this.responseFailure(ErrorCodeEnum.ERROR_A0202.getCode(), ErrorCodeEnum.ERROR_A0202.getMessage(), request, response);
        }
        if (exception instanceof BadCredentialsException) {
            log.info("【登录失败】" + exception.getMessage());
            this.responseFailure(ErrorCodeEnum.ERROR_A0210.getCode(), ErrorCodeEnum.ERROR_A0210.getMessage(), request, response);
        }
        this.responseFailure(ErrorCodeEnum.ERROR_A0200.getCode(), ErrorCodeEnum.ERROR_A0200.getMessage(), request, response);
    }
}
