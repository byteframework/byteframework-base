package com.byteframework.base.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteframework.base.user.domain.Permission;
import com.byteframework.base.user.mapper.PermissionMapper;
import com.byteframework.base.user.service.PermissionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Service("permissionService")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public List<Permission> findPermissionByRoleId(Long roleId) {
        return permissionMapper.findPermissionByRoleId(roleId);
    }


    /**
     * 将权限列表数据转换成tree
     *
     * @param permissions
     */
    public List<Object> getPermissionTree(List<Permission> permissions) {
        List<Object> list = new ArrayList<>();
        for (Permission p : permissions) {
            Map<String, Object> map = new LinkedHashMap<>();
            if (p.getParentId() == 0) {
                map.put("id", p.getId());
                map.put("title", p.getTitle());
                map.put("parentId", p.getParentId());
                map.put("path", p.getPath());
                map.put("component", p.getPath());

                Map<String, Object> metaMap = new LinkedHashMap<>();
                metaMap.put("title", p.getTitle());
                metaMap.put("icon", p.getIcon());
                metaMap.put("noCache", true);
                map.put("meta", metaMap);

                map.put("children", childPermission(p.getId(), permissions));
                list.add(map);
            }
        }
        return list;
    }


    private List<Object> childPermission(Long id, List<Permission> permissions) {
        List<Object> list = new ArrayList<>();
        for (Permission p : permissions) {
            Map<String, Object> map = new LinkedHashMap<>();
            if (p.getParentId() == id) {
                map.put("id", p.getId());
                map.put("title", p.getTitle());
                map.put("parentId", p.getParentId());
                map.put("path", p.getPath());
                map.put("component", p.getPath());

                Map<String, Object> metaMap = new LinkedHashMap<>();
                metaMap.put("title", p.getTitle());
                metaMap.put("icon", p.getIcon());
                metaMap.put("noCache", true);
                map.put("meta", metaMap);

                List<Object> subChild = childPermission(p.getId(), permissions);
                if(CollectionUtils.isNotEmpty(subChild)) {
                    map.put("children", subChild);
                }
                list.add(map);
            }
        }
        return list;
    }
}
