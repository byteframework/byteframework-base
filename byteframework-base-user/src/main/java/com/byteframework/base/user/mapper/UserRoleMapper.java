package com.byteframework.base.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.byteframework.base.user.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户角色关联表 Mapper 接口
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}
