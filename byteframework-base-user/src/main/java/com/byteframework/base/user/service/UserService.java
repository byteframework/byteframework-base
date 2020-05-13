package com.byteframework.base.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
public interface UserService extends IService<User>, UserDetailsService {

    /**
     * 根据用户名查询实体
     *
     * @Author Sans
     * @CreateTime 2019/9/14 16:30
     * @Param username 用户名
     * @Return SysUserEntity 用户实体
     */
    User selectUserByName(String username);

    /**
     * 根据用户ID查询角色集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 根据用户ID查询权限集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<Permission> selectMenuByUserId(Long userId);



    User loadUserByUsername(String username) throws UsernameNotFoundException;

}
