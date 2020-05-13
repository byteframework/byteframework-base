package com.byteframework.base.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byteframework.base.user.domain.Role;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
