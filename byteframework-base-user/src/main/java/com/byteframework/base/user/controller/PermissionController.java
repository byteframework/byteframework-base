package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteframework.commons.web.BaseAction;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author sa
 * @since 2020-04-18
 */
@RestController
@RequestMapping("/permission")
public class PermissionController extends BaseAction {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    PermissionService permissionService;


    /**
    * 权限表 保存数据
    *
    * @param request
    * @param response
    * @param jsonObject
    */
    @RequestMapping(value = "/savePermission")
    public void savePermission(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Permission permission = jsonObject.toJavaObject(Permission.class);
        try {
            permissionService.save(permission);
            this.responseSuccess("数据保存成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据保存失败!", request, response);
        }
    }


    /**
     * 权限表 分页查询
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/listPermission")
    public void listPermission(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Permission permission = jsonObject.toJavaObject(Permission.class);
        IPage<Permission> page = jsonObject.toJavaObject(Page.class);
        Wrapper<Permission> queryWrapper = new QueryWrapper<>(permission);
        try {
            IPage<Permission> list = permissionService.page(page, queryWrapper);
            this.responseSuccess(list, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("分页查询失败!", request, response);
        }
    }


    /**
     * 权限表 修改数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updatePermission")
    public void updatePermission(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Permission permission = jsonObject.toJavaObject(Permission.class);
        try {
            permissionService.updateById(permission);
            this.responseSuccess("数据修改成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据修改失败!", request, response);
        }
    }


    /**
     * 权限表 删除数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/removePermission")
    public void removePermission(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Permission permission = jsonObject.toJavaObject(Permission.class);
        try {
            permissionService.removeById(permission);
            this.responseSuccess("数据删除成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据删除失败!", request, response);
        }
    }


    /**
     * 获取权限树
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/allPermission")
    public void allPermission(HttpServletRequest request, HttpServletResponse response) {
        List<Permission> permissions = permissionService.list();
        List<Object> permissionTree = permissionService.getPermissionTree(permissions);
        this.responseSuccess(permissionTree, request, response);
    }


    @RequestMapping(value = "/rolePermission")
    public void rolePermission(HttpServletRequest request, HttpServletResponse response) {
        List<Permission> permissions = permissionService.findPermissionByRoleId(1L);
        List<Object> permissionTree = permissionService.getPermissionTree(permissions);
        this.responseSuccess(permissionTree, request, response);
    }

}

