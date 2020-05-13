package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.commons.web.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 普通用户
 *
 * @Author sa
 * @CreateTime 2019/10/2 14:43
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseAction {

    @Autowired
    private PermissionService permissionService;

    /**
     * 用户端信息   {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:52
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public void userLogin(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JSONObject jsonObject = JSONObject.parseObject("{\"roles\":[\"admin\"],\"introduction\":\"I am a super administrator\",\"avatar\":\"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif\",\"name\":\"Super Admin\"}");

        this.responseSuccess(user, request, response);
    }

    /**
     * 拥有USER角色和sys:user:info权限可以访问
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:22
     * @Return Map<String, Object> 返回数据MAP
     */
    @PreAuthorize("hasRole('USER') and hasPermission('/user/menuList','sys:user:info')")
    @RequestMapping(value = "/menuList", method = RequestMethod.GET)
    public void sysMenuEntity(HttpServletRequest request, HttpServletResponse response) {
        List<Permission> sysMenuEntityList = permissionService.list();
        this.responseSuccess(sysMenuEntityList, request, response);
    }

}
