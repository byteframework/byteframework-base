package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.domain.RolePermission;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.base.user.service.RolePermissionService;
import com.byteframework.base.user.service.RoleService;
import com.byteframework.commons.web.BaseAction;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author sa
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @Autowired
    RolePermissionService rolePermissionService;


    /**
     * 角色表 保存数据
     *
     * @param request
     * @param response
     * @param jsonObject
     */
    @RequestMapping(value = "/saveRole")
    public void saveRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        role.setCreateTime(LocalDateTime.now());
        try {
            roleService.save(role);
            // 更新权限
            List<Permission> permissions = role.getPermissions();
            if(CollectionUtils.isNotEmpty(permissions)){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                permissions.forEach(permission -> {
                    rolePermission.setPermissionId(permission.getId());
                    rolePermissionService.save(rolePermission);
                });
            }
            this.responseSuccess("数据保存成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据保存失败!", request, response);
        }
    }


    /**
     * 角色表 分页查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/listRole")
    public void listRole(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Role> list = roleService.list();
            this.responseSuccess(list, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("查询失败!", request, response);
        }
    }


    /**
     * 角色表 修改数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.PUT)
    public void updateRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        role.setUpdateTime(LocalDateTime.now());
        try {
            roleService.updateById(role);
            // 更新权限
            List<Permission> permissions = role.getPermissions();
            if(CollectionUtils.isNotEmpty(permissions)){
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(role.getId());
                rolePermissionService.remove(new QueryWrapper<>(rolePermission));
                permissions.forEach(permission -> {
                    rolePermission.setPermissionId(permission.getId());
                    rolePermissionService.save(rolePermission);
                });
            }
            this.responseSuccess("数据修改成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据修改失败!", request, response);
        }
    }


    /**
     * 角色表 删除数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/removeRole", method = RequestMethod.DELETE)
    public void removeRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        try {
            roleService.removeById(role);
            // 删除权限信息
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermissionService.remove(new QueryWrapper<>(rolePermission));
            this.responseSuccess("数据删除成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据删除失败!", request, response);
        }
    }


    /**
     * 查询角色拥有的权限信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/rolePermissions")
    public void rolePermissions(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        try {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            List<RolePermission> datas = rolePermissionService.list(new QueryWrapper<>(rolePermission));
            List<Long> ids = new ArrayList<>();
            datas.forEach(data -> ids.add(data.getPermissionId()));
            this.responseSuccess(ids, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("查询角色拥有的权限信息失败!", request, response);
        }
    }

}

