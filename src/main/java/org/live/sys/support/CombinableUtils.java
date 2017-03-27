package org.live.sys.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 拼装的菜单相关类的工具
 * Created by Mr.wang on 2016/12/4.
 */
public class CombinableUtils {


    /**
     * 执行拼装方法
     * @param root 父
     * @param allCombinables 所有可拼接的对象
     * @return
     */
    public static List<? extends Combinable> executeCombination(Object root, List<? extends Combinable> allCombinables) {
        if(allCombinables != null) {
            return getChild(root,allCombinables) ;
        }
        return null;
    }

    public static List<? extends Combinable> getChild(Object root, List<? extends Combinable> allCombinables) {
        List<Combinable> childs = new ArrayList<Combinable>() ;   //子菜单
        for(Combinable combinable : allCombinables) {
            if(combinable.compare(root)) {      //判断子菜单的父菜单是否等于root,如果加到子菜单的列表中
                childs.add(combinable) ;
            }
        }
        for(Combinable childCom: childs) {      //循环遍历子菜单，并为子菜单设置子菜单.
            childCom.setChilds(getChild(childCom,allCombinables));
        }
        if(childs.size() == 0) {    //子菜单的长度等于0，退出递归.
            return null ;
        }
        return childs ;
    }


}
