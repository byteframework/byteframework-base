package com.byteframework.base.user.controller;

import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.base.user.service.RoleService;
import com.byteframework.base.user.service.UserService;
import com.byteframework.base.user.util.SecurityUtil;
import com.byteframework.commons.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理端
 *
 * @Author Sans
 * @CreateTime 2019/10/2 14:16
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends BaseAction {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    /**
     * 管理端信息
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public void userLogin(HttpServletRequest request, HttpServletResponse response) {
        User user = SecurityUtil.getUserInfo();
        this.responseSuccess(user, request, response);
    }

    /**
     * 拥有ADMIN或者USER角色可以访问
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public void list(HttpServletRequest request, HttpServletResponse response) {
        List<User> sysUserEntityList = userService.list();
        this.responseSuccess(sysUserEntityList, request, response);
    }

    /**
     * 拥有ADMIN和USER角色可以访问
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasRole('USER')")
    @RequestMapping(value = "/menuList", method = RequestMethod.GET)
    public void menuList(HttpServletRequest request, HttpServletResponse response) {
        List<Permission> sysMenuEntityList = permissionService.list();
        this.responseSuccess(sysMenuEntityList, request, response);
    }


    /**
     * 拥有sys:user:info权限可以访问
     * hasPermission 第一个参数是请求路径 第二个参数是权限表达式
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasPermission('/admin/userList','sys:user:info')")
    @RequestMapping(value = "/userList", method = RequestMethod.GET)
    public void userList(HttpServletRequest request, HttpServletResponse response) {
        List<User> sysUserEntityList = userService.list();
        this.responseSuccess(sysUserEntityList, request, response);
    }


    /**
     * 拥有ADMIN角色和sys:role:info权限可以访问
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('ADMIN') and hasPermission('/admin/adminRoleList','sys:role:info')")
    @RequestMapping(value = "/adminRoleList", method = RequestMethod.GET)
    public void adminRoleList(HttpServletRequest request, HttpServletResponse response) {
        List<Role> sysRoleEntityList = roleService.list();
        this.responseSuccess(sysRoleEntityList, request, response);
    }
}
