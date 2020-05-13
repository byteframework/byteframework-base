package com.byteframework.base.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.byteframework.base.user.domain.Permission;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 将权限列表数据转换成tree
     *
     * @param permissions
     */
    List<Object> getPermissionTree(List<Permission> permissions);

    /**
     * 根据角色ID查询权限列表
     *
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Long roleId);
}
