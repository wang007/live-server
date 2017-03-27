package org.live.sys.entity;


import org.live.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 	功能操作实体  
 * 
 * 	理解：功能操作就是页面上的某些操作。 例如：查询用户信息。
 * 
 * 	           功能操作实体跟页面元素可以交集。  例：查询用户一般是点击按钮，即可以看做是页面元素实体（按钮）
 * 									也可以看做是功能操作，即查询用户功能。
 *
 *		所以如果系统看了页面元素时，就不用功能操作实体了。
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_operation")
public class Operation extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8496901417037774977L;
	
	/**
	 * 菜单实体
	 * 
	 *  用于判断该功能操作属于哪个页面的。
	 * 
	 */
	@ManyToOne
	@JoinColumn(name="menu_id")
	private Menu menu ;
	
	/**
	 * 权限实体
	 * 关联权限
	 * 
	 */
	@OneToOne
	@JoinColumn(name="permission_id")
	private Permission permission ;
	
	/**
	 * 功能操作名称
	 */
	@Column
	private String operationName ;
	
	/**
	 * 序号，用于排序
	 */
	@Column
	private Integer serialNo ;
	
	/**
	 * 描述
	 */
	@Column
	private String description ;

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	

}
