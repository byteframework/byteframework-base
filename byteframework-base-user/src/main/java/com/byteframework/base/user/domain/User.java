package com.byteframework.base.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author generation
 * @since 2020-04-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID=1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 真实姓名
	 */
	private String truename;

	/**
	 * 性别
	 */
	private Integer sex;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 头像
	 */
	private String avatar;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	private LocalDateTime updateTime;




	/**
	 * 用户角色
	 */
	@TableField(exist = false)
	private Collection<GrantedAuthority> authorities;
	@TableField(exist = false)
	private Collection<String> roles;
	/**
	 * 账户是否过期
	 */
	@TableField(exist = false)
	private boolean isAccountNonExpired = false;
	/**
	 * 账户是否被锁定
	 */
	@TableField(exist = false)
	private boolean isAccountNonLocked = false;
	/**
	 * 证书是否过期
	 */
	@TableField(exist = false)
	private boolean isCredentialsNonExpired = false;
	/**
	 * 账户是否有效
	 */
	@TableField(exist = false)
	private boolean isEnabled = true;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}
