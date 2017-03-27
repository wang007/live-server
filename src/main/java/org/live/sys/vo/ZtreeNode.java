package org.live.sys.vo;

/**
 * Created by mr.wang on 2016/12/11.
 */
public class ZtreeNode {

    /**
     * 菜单id
     */
    private String id ;

    /**
     * 父菜单id
     */
    private String pId ;

    /**
     * 菜单名称
     */
    private String name ;

    /**
     * 菜单是否展开
     */
    private boolean open ;

    /**
     * 菜单是否选中
     */
    private boolean checked ;

    public ZtreeNode(){
        open = true ;
    }

    public ZtreeNode(String id, String pId, String name) {
        this.id = id ;
        this.pId = pId ;
        this.name = name ;
        open = true ;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
