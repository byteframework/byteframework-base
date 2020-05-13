package com.byteframework.base.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byteframework.base.user.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author generation
 * @date 2020-04-17
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色id查询权限信息
     * @param id
     * @return
     */
    List<Permission> findPermissionByRoleId(Long id);
}
