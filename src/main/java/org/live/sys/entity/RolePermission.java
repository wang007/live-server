package org.live.sys.entity;


import org.live.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 角色与权限的关联实体
 * 
 * 角色与权限是一对一关联
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_role_permission")
public class RolePermission extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5488938678512230396L;
	
	/**
	 *  角色实体
	 */
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role  ;
	
	/**
	 * 权限实体
	 */
	@ManyToOne
	@JoinColumn(name="permission_id")
	private Permission permission ;

	public RolePermission(){}

	public RolePermission(Role role, Permission permission) {
		this.role = role ;
		this.permission = permission ;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
	
	
	
	
	
	

}
