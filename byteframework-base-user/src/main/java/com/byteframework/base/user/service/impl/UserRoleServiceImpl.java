package com.byteframework.base.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteframework.base.user.domain.UserRole;
import com.byteframework.base.user.mapper.UserRoleMapper;
import com.byteframework.base.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
