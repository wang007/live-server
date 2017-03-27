package org.live.common.page;


import org.live.common.constants.PageConstants;
import org.live.common.constants.RequestPageConstants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;

/**
 *  分页工具
 *  依赖spring-data
 *
 * @author  Mr.wang
 */
public class PageUtils {

    /**
     * 将jqgrid插件请求的参数封装到PageRequest中
     * PageRequest用于spring-data-jpa中进行分页查询

     * @param request
     * @param requiredOrder 是否需要排序. 排序将加到 查询语句中
     * @param orderPropertyPrefix 用于sql排序的属性前缀
     * @return
     */
    public static PageRequest getPage4JqGrid(HttpServletRequest request, boolean requiredOrder, String orderPropertyPrefix) {
        PageRequest page = null ;
        String strCurrentPage = request.getParameter(RequestPageConstants.REQUEST_JQGRID_CURRENT_PAGE) ; //当前页
        String strPageSize = request.getParameter(RequestPageConstants.REQUEST_JQGRID_PAGE_SIZE) ;   //每页记录数

        int currentPage = strCurrentPage == null ? PageConstants.DEFAULT_CURRENT_PAGE : Integer.valueOf(strCurrentPage) ;  //当前页
        int pageSize = strPageSize == null ? PageConstants.DEFAULT_PAGE_SIZE : Integer.valueOf(strPageSize) ; //每页记录数

        currentPage = currentPage <= 0 ? PageConstants.DEFAULT_CURRENT_PAGE :currentPage ;
        pageSize = pageSize < 1 ? PageConstants.DEFAULT_PAGE_SIZE : pageSize ;

        String sortName = request.getParameter(RequestPageConstants.REQUEST_JQGRID_SORT_NAME) ; //排序的属性名
        if(!requiredOrder || sortName == null) {  //没有排序
            page = new PageRequest(currentPage -1 ,pageSize) ; 	//
        } else  {      //有排序
        	String sortOrder = request.getParameter(RequestPageConstants.REQUEST_JQGRID_SORT_ORDER) ;    //排序顺序
            if(!"asc".equalsIgnoreCase(sortOrder) && !"desc".equalsIgnoreCase(sortOrder)) {
                sortOrder = "desc" ; //默认降序
            }
            Sort.Direction direction =  Sort.Direction.fromString(sortOrder) ;
            if(orderPropertyPrefix != null) {   //排序属性前面加前缀.
                sortName += orderPropertyPrefix ;
            }
            page = new PageRequest(currentPage - 1, pageSize, new Sort(direction, sortName));
        }
        return page ;
    }

    /**
     * 不排序的分页
     * @param request
     * @return
     */
    public static PageRequest getPage4JqGrid(HttpServletRequest request) {

        return getPage4JqGrid(request, false,null) ;
    }

    /**
     * 将repository层查询出来的Page分页数据转成jqGrid中
     * @param page 从数据库查询出来的page分页模型，实现是PageImpl
     * @param <T>
     * @return jqGrid插件的数据模型
     */
    public static <T> JqGridModel<T>  pageConvertJqGrid(Page<T> page) {
        JqGridModel<T> model = new JqGridModel<T>() ;
        model.setRecords(page.getTotalElements()) ;     //总记录数
        model.setTotal(page.getTotalPages());  //总页数
        model.setRows(page.getContent()) ;  // 数据
        model.setPage(page.getNumber()+1);    //当前页
        return model ;
    }

}
