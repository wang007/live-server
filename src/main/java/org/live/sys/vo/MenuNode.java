package org.live.sys.vo;

import org.live.sys.support.Combinable;

import java.util.List;

;

/**
 * 菜单节点，响应到页面的菜单信息
 *
 * Created by Mr.wang on 2016/12/4.
 */
public class MenuNode implements Combinable {


    /**
     * 菜单id
     */
    private String id ;

    /**
     *  菜单名称
     */
    private String menuName ;

    /**
     * 节点的类型："item":具体菜单  "folder":目录
     */
    private String menuType ;

    /**
     * 父菜单节点id
     *
     */
    private String parentId ;

    /**

     * 子菜单节点
     */
    private List<MenuNode> childMenuNodes ;

    /**
     * 菜单序号
     */
    private Integer serialNo ;


    @Override
    public boolean compare(Object compareObj) {
        MenuNode node = (MenuNode) compareObj ;
        if(parentId == null) {  //没有父节点
            return node == null ;
        } else {
            return node == null? false : parentId.equals(node.getId()) ;
        }
    }

    @Override
    public void setChilds(List<? extends Combinable> combinables) {
        this.setChildMenuNodes((List<MenuNode>) combinables);
    }


    public MenuNode(){}


    public MenuNode(String id, String menuName, String parentId, Integer serialNo) {
        this.setId(id) ;
        this.setMenuName(menuName) ;
        this.setParentId(parentId) ;
        this.setSerialNo(serialNo) ;
    }

    public MenuNode(String id, String menuName, String parentId, Integer serialNo, String menuType) {
        this.setId(id) ;
        this.setMenuName(menuName) ;
        this.setParentId(parentId) ;
        this.setSerialNo(serialNo) ;
        this.setMenuType(menuType) ;
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

    public List<MenuNode> getChildMenuNodes() {
        return childMenuNodes;
    }

    public void setChildMenuNodes(List<MenuNode> childMenuNodes) {
        this.childMenuNodes = childMenuNodes;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }


    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
