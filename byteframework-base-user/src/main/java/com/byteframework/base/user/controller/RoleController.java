package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.base.user.service.RoleService;
import com.byteframework.commons.web.BaseAction;
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


    /**
     * 分页查询角色
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<Role> datas = roleService.list();

        for (Role role : datas) {
            List<Permission> permissions = permissionService.findPermissionByRoleId(role.getId());
            List<Object> permissionTree = permissionService.getPermissionTree(permissions);


            if (role.getId() == 1) {
                role.setKey("admin");
            } else {
                role.setKey("editor");
            }
            role.setRoutes(permissionTree);
        }

        this.responseSuccess(datas, request, response);
    }


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
        try {
            roleService.save(role);
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
    public void listRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        IPage<Role> page = jsonObject.toJavaObject(Page.class);
        Wrapper<Role> queryWrapper = new QueryWrapper<>(role);
        try {
            IPage<Role> list = roleService.page(page, queryWrapper);
            this.responseSuccess(list, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("分页查询失败!", request, response);
        }
    }


    /**
     * 角色表 修改数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateRole")
    public void updateRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        try {
            roleService.updateById(role);
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
    @RequestMapping(value = "/removeRole")
    public void removeRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        Role role = jsonObject.toJavaObject(Role.class);
        try {
            roleService.removeById(role);
            this.responseSuccess("数据删除成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据删除失败!", request, response);
        }
    }

}

