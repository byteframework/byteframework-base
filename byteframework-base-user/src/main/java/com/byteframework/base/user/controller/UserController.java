package com.byteframework.base.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.domain.UserRole;
import com.byteframework.base.user.service.PermissionService;
import com.byteframework.base.user.service.UserRoleService;
import com.byteframework.base.user.service.UserService;
import com.byteframework.commons.web.BaseAction;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
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
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    PermissionService permissionService;

    // 默认密码
    private final String DEFAULT_PASSWORD = "000000";


    /**
     * 查询用户列表
     *
     * @param request
     * @param response
     * @param jsonObject
     */
    @RequestMapping(value = "/listUser")
    public void listUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        User user = jsonObject.toJavaObject(User.class);
        IPage<User> page = jsonObject.toJavaObject(Page.class);
        try {
//            IPage<User> list = userService.page(page, new QueryWrapper<>(user));
            IPage<User> list = userService.listUser(page, user);
            this.responseSuccess(list, request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("分页查询失败!", request, response);
        }
    }


    /**
     * 用户信息表 保存数据
     *
     * @param request
     * @param response
     * @param jsonObject
     */
    @RequestMapping(value = "/saveUser")
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        User user = jsonObject.toJavaObject(User.class);
        try {
            user.setCreateTime(LocalDateTime.now());
            user.setPassword(new BCryptPasswordEncoder().encode(DEFAULT_PASSWORD));
            userService.save(user);
            // 更新用户角色
            List<Long> userRoles = user.getUserRoles();
            if (CollectionUtils.isNotEmpty(userRoles)) {
                userRoles.forEach(role -> {
                    UserRole userRole = new UserRole();
                    userRole.setUserId(user.getId());
                    userRole.setRoleId(role);
                    userRoleService.save(userRole);
                });
            }

            this.responseSuccess("数据保存成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据保存失败!", request, response);
        }
    }


    /**
     * 用户信息表 修改数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public void updateRole(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        User user = jsonObject.toJavaObject(User.class);
        user.setUpdateTime(LocalDateTime.now());
        try {
            userService.updateById(user);
            // 更新用户角色
            List<Long> userRoles = user.getUserRoles();
            if (CollectionUtils.isNotEmpty(userRoles)) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRoleService.remove(new QueryWrapper<>(userRole));
                userRoles.forEach(role -> {
                    userRole.setRoleId(role);
                    userRoleService.save(userRole);
                });
            }
            this.responseSuccess("数据修改成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据修改失败!", request, response);
        }
    }


    /**
     * 用户信息表 删除数据
     *
     * @param request
     * @param response
     * @param jsonObject
     */
    @RequestMapping(value = "/removeUser")
    public void removeUser(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        User user = jsonObject.toJavaObject(User.class);
        try {
            userService.removeById(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRoleService.remove(new QueryWrapper<>(userRole));
            this.responseSuccess("数据删除成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据删除失败!", request, response);
        }
    }


    /**
     * 用户端信息   {"code":20000,"data":{"roles":["admin"],"introduction":"I am a super administrator","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif","name":"Super Admin"}}
     *
     * @Author Sans
     * @CreateTime 2019/10/2 14:52
     * @Return Map<String, Object> 返回数据MAP
     */
//    @PreAuthorize("hasAnyRole('USER','ADMIN')")
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


    /**
     * 用户信息表 修改数据
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public void updatePassword(HttpServletRequest request, HttpServletResponse response, @RequestBody JSONObject jsonObject) {
        try {
            Long id = jsonObject.getLong("id");
            String oldPassword = jsonObject.getString("oldPassword");
            String newPassword = jsonObject.getString("newPassword");
            if (null == id) {
                this.responseFailure("用户不存在!", request, response);
                return;
            }

            User user = new User();
            user.setId(id);
            user = userService.getOne(new QueryWrapper<>(user));
            if (null == user) {
                this.responseFailure("用户不存在!", request, response);
                return;
            }

            // 判断原密码是否输入正确
            boolean passwordIsRight = new BCryptPasswordEncoder().matches(oldPassword, user.getPassword());

            if (!passwordIsRight) {
                this.responseFailure("原密码不正确!", request, response);
                return;
            }

            if (StringUtils.isEmpty(newPassword)) {
                this.responseFailure("新密码不能为空!", request, response);
                return;
            }

            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            user.setUpdateTime(LocalDateTime.now());

            userService.updateById(user);

            this.responseSuccess("数据修改成功!", request, response);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.responseFailure("数据修改失败!", request, response);
        }
    }


}
