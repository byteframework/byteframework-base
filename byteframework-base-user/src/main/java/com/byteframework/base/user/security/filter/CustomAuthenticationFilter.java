package com.byteframework.base.user.security.filter;

import com.byteframework.base.user.config.JWTConfig;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.util.JWTTokenUtil;
import com.byteframework.base.user.util.ResultUtil;
import com.byteframework.commons.constant.ErrorCodeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录拦截器，接收前端传递的json数据 Account and password are incorrect
 *
 * @author sa
 * @date 2020-04-18
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * 重写验证方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //当Content-Type为json时尝试身份验证
        if (request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {

            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()) {
                User user = mapper.readValue(is, User.class);
                authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
                new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }

        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        try {
            User user = (User) authResult.getPrincipal();
            String token = JWTConfig.tokenPrefix + JWTTokenUtil.createAccessToken(user);

            Map<String, Object> map = new HashMap<>();
            map.put("Authorization", token);
            Map<String, Object> codeMap = new HashMap<>();
            codeMap.put("code", ErrorCodeEnum.SUCCESS_00000.getCode());
            codeMap.put("data", map);

            ResultUtil.responseJson(response, codeMap);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


}
