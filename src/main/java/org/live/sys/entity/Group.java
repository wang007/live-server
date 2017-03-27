package org.live.sys.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户组实体
 * @author wzc
 *
 */
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Entity
@Table(name="sys_group")
public class Group extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1230541146575580441L;
	
	/**
	 * 用户组名
	 */
	@Column
	private String groupName ;
	
	/**
	 * 序号 (用于排序)
	 */
	@Column
	private Integer serialNo ;
	
	/**
	 * 用户组描述(对用户组进行简短的描述)
	 */
	@Column
	private String description ; 
	
	/**
	 * 是否启用  	1.启用  0.未启用
	 */
	@Column
	private int isEnable = Constants.DIC_YES ;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

	public int getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(int isEnable) {
		this.isEnable = isEnable;
	}
	
	

}
