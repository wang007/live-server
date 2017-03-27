package org.live.sys.entity;


import org.live.common.base.BaseEntity;
import org.live.sys.support.Combinable;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单实体
 * 
 * 权限与菜单是一对一关联
 * 菜单与父菜单是一对一关联
 * 
 * @author wzc
 *
 */
@Entity
@Table(name="sys_menu")
public class Menu extends BaseEntity implements Combinable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2185164319804570601L;
	
	/**
	 * 权限
	 */
	@OneToOne
	@JoinColumn(name="permission_id")
	private Permission permission ;
	
	/**
	 * 父菜单
	 * 
	 * 每个菜单只有一个父菜单，所以是一对一关联
	 */
	@OneToOne
	@JoinColumn(name="parent_id")
	private Menu parent ;
	
	/**
	 * 菜单名称
	 */
	@Column
	private String menuName ;
	
	/**
	 * 菜单url
	 */
	@Column
	private String menuUrl ;
	
	/**
	 * 菜单图标
	 */
	@Column
	private String menuIcon ;
	
	/**
	 * 菜单打开图标
	 */
	@Column
	private String menuOpenIcon ;
	
	/**
	 * 序号：用于排序
	 */
	@Column
	private Integer serialNo ;
	
	/**
	 * 描述，对菜单进行简短的描述
	 */
	@Column
	private String description ;

	/**
	 * 菜单类型，
	 * item。 即：具体的菜单，不包含子菜单。
	 * folder。 即：菜单集合，包括子菜单.
	 *
	 */
	private String menuType ;

	/**
	 * 子菜单。
	 */
	@Transient //不参与持久化
	private List<Menu> childMenus ;

	public Menu(){}

	@Override
	public boolean compare(Object compareObj) {
		return this.getParent() == compareObj ;
	}

	@Override
	public void setChilds(List<? extends Combinable> combinables) {
		this.setChildMenus((List<Menu>)combinables);
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Menu getParent() {
		return parent;
	}



	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuOpenIcon() {
		return menuOpenIcon;
	}

	public void setMenuOpenIcon(String menuOpenIcon) {
		this.menuOpenIcon = menuOpenIcon;
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

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public List<Menu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(List<Menu> childMenus) {
		this.childMenus = childMenus;
	}


}
