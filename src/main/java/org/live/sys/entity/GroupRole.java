package org.live.sys.entity;


import org.live.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 用户组与角色的关联实体
 * 
 * 用户组与角色是多对多关联
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_group_role")
public class GroupRole extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3208390315485885126L;
	
	/**
	 * 角色实体
	 */
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role ;
	
	/**
	 * 用户组实体
	 */
	@ManyToOne
	@JoinColumn(name="group_id")
	private Group group ;

	public GroupRole(){}

	public GroupRole(Role role, Group group) {
		this.role = role ;
		this.group = group ;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}
	

}
