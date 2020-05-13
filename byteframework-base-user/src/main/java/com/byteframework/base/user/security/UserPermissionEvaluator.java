package com.byteframework.base.user.security;

import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.domain.User;
import com.byteframework.base.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限注解验证
 *
 * @Author Sans
 * @CreateTime 2019/10/6 13:31
 */
@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private UserService userService;

    /**
     * hasPermission鉴权方法
     * 这里仅仅判断PreAuthorize注解中的权限表达式
     * 实际中可以根据业务需求设计数据库通过targetUrl和permission做更复杂鉴权
     * 当然targetUrl不一定是URL可以是数据Id还可以是管理员标识等,这里根据需求自行设计
     *
     * @Author Sans
     * @CreateTime 2019/10/6 18:25
     * @Param authentication  用户身份(在使用hasPermission表达式时Authentication参数默认会自动带上)
     * @Param targetUrl  请求路径
     * @Param permission 请求路径权限
     * @Return boolean 是否通过
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object targetUrl, Object permission) {
        // 获取用户信息
        User user = (User) authentication.getPrincipal();
        // 查询用户权限(这里可以将权限放入缓存中提升效率)
        Set<String> permissions = new HashSet<>();
        List<Permission> sysMenuEntityList = userService.selectMenuByUserId(user.getId());
        for (Permission per : sysMenuEntityList) {
            permissions.add(per.getPerms());
        }
        // 权限对比
        if (permissions.contains(permission.toString())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }


    public static void main(String[] args) {
        Set<String> permissions = new HashSet<>();
        String permission = "sys:user:info";

        permissions.add(null);
        permissions.add("sys:user:info");

        // 权限对比
        if (permissions.contains(permission.toString())) {
            System.out.println("wow");
        }
    }
}
