package org.live.common.constants;

/**
 * sys模块中常量
 * Created by Mr.wang on 2016/12/3.
 */
public class SysConstants {

    /**
     * 数据字典：权限表中的权限类型-菜单 类型
     */
    public static final int DIC_PERMISSIONTYPE_MENU = 1;

    /**
     * 数据字典：权限表中的权限类型-页面元素 类型
     */
    public static final int DIC_PERMISSIONTYPE_ELEMENT = 2 ;

    /**
     * 数据字典：权限表中的权限类型-功能操作 类型
     */
    public static final int DIC_PERMISSIONTYPE_OPERATION = 3 ;

    /**
     * 菜单类型，item。 即：具体的菜单，不包含子菜单
     */
    public static final String MENU_NODE_TYPE_ITEM = "item" ;

    /**
     * 菜单类型，folder。 即：菜单集合，包括子菜单.
     */
    public static final String MENU_NODE_TYPE_FOLDER = "folder" ;


    /**
     * 菜单节点：菜单父节点id
     */
    public static final String MENU_NODE_PARENT_ID = "parentId";

    /**
     * 菜单节点名称的key
     */
    public static final String MENU_NODE_TEXT_KEY = "text";

    /**
     * 菜单节点类型的key
     */
    public static final String MENU_NODE_TYPE_KEY = "type" ;

    /**
     * 菜单节点的附加参数
     */
    public static final String MENU_NODE_ADDITIONALPARAMETERS = "additionalParameters";

    /**
     * 根节点的值
     */
    public static final String MENU_NODE_ROOT = "root" ;




}
