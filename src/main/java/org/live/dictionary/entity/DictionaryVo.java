package org.live.dictionary.entity;

/**
 * Created by KAM on 2017/4/3.
 */
public class DictionaryVo {
    private String id;
    private String typeName;
    private String dictMark;
    private String dictValue;
    private String remarks;

    public DictionaryVo(String id, String typeName, String dictMark, String dictValue, String remarks){
        this.id = id;
        this.typeName = typeName;
        this.dictMark = dictMark;
        this.dictValue = dictValue;
        this.remarks = remarks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
}
