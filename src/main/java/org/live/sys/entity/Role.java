package org.live.sys.entity;


import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 角色实体
 * @author wzc
 *
 */
@Entity
@Table(name="sys_role")	//表名的命名: 模块名_实体名
public class Role extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2071995856035960246L ;
	
	/**
	 * 角色名称
	 */
	@Column
	private String roleName ;
	
	/**
	 * 序号,用于排序
	 */
	@Column
	private Integer serialNo ;
	
	/**
	 * 角色值.   对应shiro的角色权限控制.例如：admin,ordinary_user,teacher等。
	 * 
	 */
	@Column
	private String roleValue ;
	
	/**
	 *  角色描述，对角色进行简短地描述
	 */
	@Column
	private String description ;
	
	/**
	 * 是否启用   1.启用    0.未启用.
	 */
	@Column
	private int isEnable = Constants.DIC_YES ;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getRoleValue() {
		return roleValue;
	}

	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	

}
