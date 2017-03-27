package org.live.sys.utils;


import org.live.common.constants.SysConstants;
import org.live.sys.vo.MenuNode;
import org.live.sys.vo.SidebarNode;
import org.live.sys.vo.TreeViewNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单树工具
 * Created by Mr.wang on 2016/12/4.
 */
public class MenuTreeUtils {

    /**
     * MenuNode转成treeview插件所需的节点.
     * 该转换只能转一层的菜单，深层的菜单将被忽略
     * @param menuList
     */
    public static List<TreeViewNode> toTreeViewLayout(List<MenuNode> menuList) {

        int nodeLength = menuList == null ? 0: menuList.size() ;    //节点的长度
        List<TreeViewNode> treeViewNodes = null ;   //返回的treeview的节点
        if(nodeLength > 0 ) {
            treeViewNodes = new ArrayList<TreeViewNode>(nodeLength) ;
            for(MenuNode menuNode: menuList) {
                int childNodeLength = menuNode.getChildMenuNodes() == null ? 0: menuNode.getChildMenuNodes().size() ;   //节点内子节点的个数
                TreeViewNode treeviewNode = new TreeViewNode() ;
                treeviewNode.setText(menuNode.getMenuName()) ;  //菜单名
                treeviewNode.setType( childNodeLength > 0 ? SysConstants.MENU_NODE_TYPE_FOLDER: SysConstants.MENU_NODE_TYPE_ITEM) ; //节点类型
                Map<String,Object> additionalParameters = new HashMap<String, Object>() ;
                additionalParameters.put("id",menuNode.getId()) ;   //节点id
                if(childNodeLength > 0) {
                    additionalParameters.put("children",true) ; //存在子节点
                }
                treeviewNode.setAdditionalParameters(additionalParameters) ;
                treeViewNodes.add(treeviewNode) ;
            }
        }
        return treeViewNodes ;
    }

    /**
     * 生成页面左侧的菜单
     * @param sidebarNodes
     * @return
     */
    public static String appendSidebarBySidebarNodes(List<SidebarNode> sidebarNodes) {
        if(sidebarNodes != null) {
            StringBuilder menuSb = new StringBuilder() ;
            for(SidebarNode node: sidebarNodes) {
                List<SidebarNode> childNodes = node.getChildSidebarNodes(); //子菜单
                boolean haveChildFlag = childNodes != null;    //是否存在子菜单的标记。true:有，false：无
                if (haveChildFlag) {
                    menuSb.append("<li class=''> <a href='#' class='dropdown-toggle'><i class='menu-icon fa ");
                    menuSb.append(node.getMenuIcon());
                    menuSb.append("'></i>");
                    menuSb.append("<span class='menu-text'> ");
                    menuSb.append(node.getMenuName());
                    menuSb.append("</span><b class='arrow fa fa-angle-down'></b></a><b class='arrow'></b>");
                    menuSb.append( appendChildSidebarNodes(childNodes) ) ;
                    menuSb.append("</li>") ;
                    menuSb.append("") ;
                } else {
                    menuSb.append( appendNoChildSidebarNode(node) ) ;
                }
            }
            return menuSb.toString();
        } else{
            return "" ;
        }

    }

    private static String appendChildSidebarNodes(List<SidebarNode> childSidebarNodes) {
        if(childSidebarNodes != null) {
            StringBuilder childNodesSb = new StringBuilder() ;
            childNodesSb.append("<ul class='submenu'>") ;
            for(SidebarNode childNode: childSidebarNodes) {
                List<SidebarNode> childChildNodes = childNode.getChildSidebarNodes() ;  //子子菜单
                boolean haveChildFlag = childChildNodes != null ;   //是否存在子子菜单的标记。true:有，false：无
                if(haveChildFlag) {
                    childNodesSb.append("<li class=''> <a href='#' class='dropdown-toggle'><i class='menu-icon fa fa-caret-right") ;
                   // childNodesSb.append(childNode.getMenuIcon()) ;
                    childNodesSb.append("'></i>") ;
                    childNodesSb.append("<span class='menu-text'> ") ;
                    childNodesSb.append( childNode.getMenuName()) ;
                    childNodesSb.append("</span><b class='arrow fa fa-angle-down'></b></a><b class='arrow'></b>") ;
                    childNodesSb.append( appendChildSidebarNodes(childChildNodes) ) ;   //递归拼接子子菜单
                    childNodesSb.append("</li>") ;
                } else {
                    childNodesSb.append(appendNoChildSidebarNode(childNode))  ;

                }
            }
            childNodesSb.append("</ul>") ;
            return childNodesSb.toString() ;
        } else {
            return "" ;
        }

    }

    private static String appendNoChildSidebarNode(SidebarNode node) {
        if(node == null) {
            return "" ;
        }
        StringBuilder menuSb = new StringBuilder() ;
        boolean haveUrlFlag = node.getMenuUrl() != null && !"".equals(node.getMenuUrl()) ;   //是否存在url的标记。 true：存在， false：：不存在
        menuSb.append("<li class=''>") ;
        if(haveUrlFlag) {
            menuSb.append("<a href='#") ;
            menuSb.append(node.getMenuUrl()) ;
            menuSb.append("' data-url='") ;
            menuSb.append(node.getMenuUrl()) ;
            menuSb.append("'>") ;
        } else {
            menuSb.append("<a href='#' data-url=''>") ;
        }
        menuSb.append("<i class='menu-icon fa fa-caret-right") ;
       // menuSb.append( node.getMenuIcon()) ;
        menuSb.append("'></i><span class='menu-text'>") ;
        menuSb.append(node.getMenuName()) ;
        menuSb.append("</span></a><b class='arrow'></b></li>") ;
        return menuSb.toString() ;
    }




}
