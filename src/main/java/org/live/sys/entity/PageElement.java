package org.live.sys.entity;


import org.live.common.base.BaseEntity;

import javax.persistence.*;

/**
 * 页面元素实体
 * 
 * 前端页面中的元素，例如：超链接，按钮等等。
 * 
 * 页面元素跟菜单是多对一 关联，页面元素跟权限是一对一关联。
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_pageelement")
public class PageElement extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2345068771624989795L;
	
	/**
	 * 菜单
	 * 该页面元素属于哪个菜单页面下的
	 */
	@ManyToOne
	@JoinColumn(name="menu_id")
	private Menu menu ;
	
	/**
	 * 菜单
	 */
	@OneToOne
	@JoinColumn(name="permission_id")
	private Permission permission ;
	
	/**
	 * 页面元素名称
	 */
	@Column
	private String elementName ;
	
	/**
	 * 序号：用于排序
	 */
	@Column
	private Integer serialNo ;
	
	/**
	 * 描述，对该页面元素进行简短的描述。
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

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
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
