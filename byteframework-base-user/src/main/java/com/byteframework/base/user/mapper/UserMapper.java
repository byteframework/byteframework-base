package com.byteframework.base.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户ID查询角色集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysRoleEntity> 角色名集合
     */
    List<Role> selectRoleByUserId(Long userId);

    /**
     * 通过用户ID查询权限集合
     *
     * @Author Sans
     * @CreateTime 2019/9/18 18:01
     * @Param userId 用户ID
     * @Return List<SysMenuEntity> 角色名集合
     */
    List<Permission> selectMenuByUserId(Long userId);

}
