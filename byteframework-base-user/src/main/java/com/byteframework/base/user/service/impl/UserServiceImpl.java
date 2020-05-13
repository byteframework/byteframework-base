package com.byteframework.base.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.mapper.UserMapper;
import com.byteframework.base.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    /**
     * 根据用户名查询实体
     * @Author Sans
     * @CreateTime 2019/9/14 16:30
     * @Param  username 用户名
     * @Return SysUserEntity 用户实体
     */
    @Override
    public User selectUserByName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername,username);
        return this.baseMapper.selectOne(queryWrapper);
    }


    /**
     * 通过用户ID查询角色集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param  userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    @Override
    public List<Role> selectRoleByUserId(Long userId) {
        return this.baseMapper.selectRoleByUserId(userId);
    }


    /**
     * 根据用户ID查询权限集合
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    @Override
    public List<Permission> selectMenuByUserId(Long userId) {
        return this.baseMapper.selectMenuByUserId(userId);
    }



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
        // 查询用户信息
        User user = this.selectUserByName(username);
        return user;
    }
}
