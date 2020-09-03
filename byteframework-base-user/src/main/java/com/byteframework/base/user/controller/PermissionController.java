package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.commons.web.BaseAction;
import org.apache.commons.collections.list.TreeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
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
            this.responseSuccess(request, response, "数据保存成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure(request, response, "数据保存失败!");
        }
    }


    /**
     * 权限表 查询全部
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/listPermission")
    public void listPermission(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Permission> permissions = permissionService.list();
            permissions = this.findChildrenList(permissions, 0L);
            this.responseSuccess(permissions, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure(request, response, "查询失败!");
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
            this.responseSuccess(request, response, "数据修改成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure(request, response, "数据修改失败!");
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
            this.responseSuccess(request, response, "数据删除成功!");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure(request, response, "数据删除失败!");
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


    /**
     * 递归遍历节点
     *
     * @param permissionList
     * @param parentId
     * @return
     */
    private List<Permission> findChildrenList(List<Permission> permissionList, Long parentId) {
        List<Permission> treeList = new ArrayList<>();
        //获取到所有parentId的子节点
        for (Permission permission : permissionList) {
            if (parentId.equals(permission.getParentId())) {
                treeList.add(permission);
                //递归遍历该子节点的子节点列表
                permission.setChildren(this.findChildrenList(permissionList, permission.getId()));
            }
        }
        return treeList;
    }
}

