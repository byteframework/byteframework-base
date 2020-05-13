package com.byteframework.base.user.util;

import com.byteframework.base.user.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Security工具类
 * @Author Sans
 * @CreateTime 2019/10/2 13:16
 */
public class SecurityUtil {

    /**
     * 私有化构造器
     */
    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static User getUserInfo(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
        return user;
    }
    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getId();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserName(){
        return getUserInfo().getUsername();
    }
}
