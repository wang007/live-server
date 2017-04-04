package org.live.school.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.school.entity.Department;

import javax.servlet.http.HttpServletRequest;

/**
 * 系部业务接口
 * Created by KAM on 2017/4/4.
 */
public interface DepartmentService extends BaseService<Department, String> {
    /**
     * 查询分页信息
     *
     * @param request
     * @return
     */
    DataTableModel findPage(HttpServletRequest request);
}
