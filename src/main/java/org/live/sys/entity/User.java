package org.live.sys.entity;


import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;
import org.live.common.constants.UserTypeConstants;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体
 * @author wzc
 *
 */
@Entity
@Table(name="sys_user")
public class User extends BaseEntity {

	private static final long serialVersionUID = -257504323760154851L;
	
	/**
	 * 登录名（账号） 
	 */
	@Column(unique=true)	//唯一
	private String username ;
	
	/**
	 * 密码
	 */
	@Column
	private String password ;
	
	/**
	 * 用户类型：对应UserType类中预定义的常量.默认值为 "ordinaryUser"
	 */
	@Column
	private String userType = UserTypeConstants.ORDINARY_USER ;

	/**
	 *  盐，用于加密
	 */
	@Column
	private String salt ;
	
	/**
	 * 用户名称(昵称)
	 */
	@Column
	private String name ;
		
	/**
	 * 注册时间
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerTime ;
	
	/**
	 * 账号是否被锁定  1.锁定   0.未锁定(默认)
	 */
	@Column
	private int isLock = Constants.DIC_NO ;
	
	/**
	 * 用户是否被删除
	 * 1.是。 0.否(默认)
	 */
	private int isDelete = Constants.DIC_NO ;

	/**
	 * 最后登录时间
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginTime ;
	
	/**
	 * 最后登录的ip地址
	 */
	@Column
	private String lastLoginIp ;

	/**
	 *  用户所属组
	 */
	@ManyToOne(fetch = FetchType.LAZY)	//懒加载
	@JoinColumn(name="group_id")
	private Group group ;


	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public int getIsLock() {
		return isLock;
	}

	public void setIsLock(int isLock) {
		this.isLock = isLock;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

}
