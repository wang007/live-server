package org.live.sys.entity;


import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 权限实体
 * 
 * 
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_permission")
public class Permission extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6286197563545334250L;
	
	/**
	 * 权限类型
	 *    
	 * 取值：对应cn.school.common.constants.CommonConstants类中定义的常量
	 * 		即：1,2,3。分别对应 菜单类型，页面元素类型，功能操作类型
	 * 
	 * 用于取不同权限时做判断
	 */
	@Column
	private int permissionType ;
	
	/**
	 * 权限值---对应shiro的权限标识符，即    资源标识符：操作
	 * 		例如：添加用户：user:create. 查询用户：user:view或user:find
	 * 			 更新用户：user:update 等
	 */
	@Column
	private String permissionValue ;
	
	/**
	 * 是否启用  	1.启用  0.未启用
	 */
	@Column
	private int isEnable = Constants.DIC_YES ;
	

	public int getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(int permissionType) {
		this.permissionType = permissionType;
	}

	public String getPermissionValue() {
		return permissionValue;
	}

	public void setPermissionValue(String permissionValue) {
		this.permissionValue = permissionValue;
	}

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	
	
	
	
	
	
	
	

}
