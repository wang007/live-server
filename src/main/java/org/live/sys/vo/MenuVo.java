package org.live.sys.vo;

/**
 * 菜单的数据载体
 *
 * Created by Mr.wang on 2016/12/6.
 */
public class MenuVo {

    /**
     *  菜单id
     */
    private String id ;

    /**
     * 序号
     */
    private Integer serialNo ;

    /**
     * 菜单名
     */
    private String menuName ;

    /**
     * 菜单url
     */
    private String menuUrl ;

    /**
     * 菜单图标
     */
    private String menuIcon ;

    /**
     * 权限值
     */
    private String permissionValue ;

    /**
     * 启用状态
     * 1.启用 0.未启用
     */
    private int isEnable ;

    /**
     * 保存方式，
     * 1.保存为同级菜单，
     * 2.保存为子级菜单
     */
    private int saveType ;


    /**
     * 参考的id。
     * 保存方式参考这个id

     */
    private String referenceId ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
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

    public int getSaveType() {
        return saveType;
    }

    public void setSaveType(int saveType) {
        this.saveType = saveType;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
