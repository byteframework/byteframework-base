package com.byteframework.base.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.byteframework.base.user.domain.Role;
import com.byteframework.base.user.mapper.RoleMapper;
import com.byteframework.base.user.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author sa
 * @date 2020-04-17
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
