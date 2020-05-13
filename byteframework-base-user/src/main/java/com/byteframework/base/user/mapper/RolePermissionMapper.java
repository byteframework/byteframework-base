package com.byteframework.base.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byteframework.base.user.domain.RolePermission;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色权限关联表 Mapper 接口
 * </p>
 *
 * @author generation
 * @date 2020-04-17
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}
