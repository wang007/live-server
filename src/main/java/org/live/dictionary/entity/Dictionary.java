package org.live.dictionary.entity;


import org.live.common.base.BaseEntity;
import org.live.common.constants.Constants;

import javax.persistence.*;

/**
 * 数据字典实体，持久化用于页面显示的数据常量，页面中通过字典的key值即mark标识来显示value值
 * 
 * @author KAM
 *
 */
@Entity
@Table(name = "sys_dict")
public class Dictionary extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 字典编码
	 */
	@Column
	private String serialNo;

	/**
	 * 字典类型
	 */
	@ManyToOne
	@JoinColumn(name = "dict_type_id")
	private DictType dictType;

	/**
	 * 字典检索标识
	 */
	@Column(name = "dict_mark")
	private String dictMark;

	/**
	 * 字典值
	 */
	@Column(name = "dict_value")
	private String dictValue;

	/**
	 * 备注
	 */
	@Column
	private String remarks;

	/** 删除标识，可选{Constants.DIC_NO：0, Constans.DIC_YES：1}，默认为Constants.DIC_NO **/
	@Column(name = "del_flag")
	private int delFlag = Constants.DIC_NO;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public DictType getDictType() {
		return dictType;
	}

	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}

	public String getDictMark() {
		return dictMark;
	}

	public void setDictMark(String dictMark) {
		this.dictMark = dictMark;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

}
