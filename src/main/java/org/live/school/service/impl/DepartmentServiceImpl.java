package org.live.school.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.dictionary.entity.DictType;
import org.live.school.entity.Department;
import org.live.school.repository.DepartmentRepository;
import org.live.school.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系部业务接口实现类
 * Created by KAM on 2017/4/4.
 */
@Service(value = "departmentService")
public class DepartmentServiceImpl extends BaseServiceImpl<Department, String> implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    protected BaseRepository<Department, String> getRepository() {
        return this.departmentRepository;
    }

    /**
     * 查询分页信息
     *
     * @param request
     * @return DataTableModel dataTable定制模型
     */
    @Override
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = departmentRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<Department> page = departmentRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }
}
