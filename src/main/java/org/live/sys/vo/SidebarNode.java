package org.live.sys.vo;


import org.live.sys.support.Combinable;

import java.util.List;

/**
 *
 * 左侧菜单栏的菜单节点
 *
 * Created by Mr.wang on 2016/12/13.
 */
public class SidebarNode implements Combinable {

    private String id ;

    private String menuName ;

    private String menuUrl ;

    private String menuIcon ;

    private Integer serialNo ;

    private String parentId ;

    /**
     * 子菜单。
     */
    private List<SidebarNode> childSidebarNodes ;

    public SidebarNode(){}

    public SidebarNode(String id, String menuName, String menuUrl, String menuIcon, Integer serialNo, String parentId) {
        this.id = id;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.serialNo = serialNo;
        this.parentId = parentId;
    }

    @Override
    public boolean compare(Object compareObj) {

        SidebarNode node = (SidebarNode) compareObj;
        if(parentId == null) {
            return node == null ;
        } else {
            return node == null? false : parentId.equals(node.getId()) ;

        }
    }

    @Override
    public void setChilds(List<? extends Combinable> combinables) {

        this.setChildSidebarNodes((List<SidebarNode>) combinables) ;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<SidebarNode> getChildSidebarNodes() {
        return childSidebarNodes;
    }

    public void setChildSidebarNodes(List<SidebarNode> childSidebarNodes) {
        this.childSidebarNodes = childSidebarNodes;
    }

}
