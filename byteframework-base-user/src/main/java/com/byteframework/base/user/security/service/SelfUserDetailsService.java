package com.byteframework.base.user.security.service;

import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * SpringSecurity用户的业务实现
 *
 * @Author Sans
 * @CreateTime 2019/10/1 17:21
 */
@Component
public class SelfUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    /**
     * 查询用户信息
     *
     * @Author Sans
     * @CreateTime 2019/9/13 17:23
     * @Param username  用户名
     * @Return UserDetails SpringSecurity用户信息
     */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.selectUserByName(username);
    }
}
