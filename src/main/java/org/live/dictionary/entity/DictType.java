package org.live.dictionary.entity;


import org.hibernate.annotations.Cascade;
import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;
import org.live.common.utils.UUIDGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 字典类型
 * 
 * @author KAM
 *
 */
@Entity
@Table(name = "sys_dict_type")
public class DictType extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 字典类型编码
	 */
	@Column
	private String serialNo = UUIDGenerator.getUUID(); // 暂用uuid替代

	/**
	 * 字典类型名称
	 */
	@Column(name = "type_name")
	private String typeName;

	/**
	 * 字典集合
	 */
	@OneToMany(mappedBy = "dictType",orphanRemoval=true)
    @Cascade(value = {org.hibernate.annotations.CascadeType.DELETE})
	private Set<Dictionary> dictionarys;

	/**
	 * 字典类型标识
	 */
	@Column(name = "dict_type_mark")
	private String dictTypeMark;

	/**
	 * 字典类型描述
	 */
	@Column
	private String description;

	@Column(name = "del_flag")
	/** 删除标识，可选{Constants.DIC_NO：0, Constans.DIC_YES：1}，默认为Constants.DIC_NO **/
	private int delFlag = Constants.DIC_NO;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}


	public String getDictTypeMark() {
		return dictTypeMark;
	}

	public void setDictTypeMark(String dictTypeMark) {
		this.dictTypeMark = dictTypeMark;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
