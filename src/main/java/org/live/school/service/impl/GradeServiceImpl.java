package org.live.school.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseService;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.school.entity.Grade;
import org.live.school.repository.GradeRepository;
import org.live.school.service.GradeService;
import org.live.school.vo.GradeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 班级业务接口实现类
 * Created by KAM on 2017/4/5.
 */
@Service(value = "gradeService")
public class GradeServiceImpl extends BaseServiceImpl<Grade, String> implements GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Override
    protected BaseRepository<Grade, String> getRepository() {
        return gradeRepository;
    }

    /**
     * 查询班级分页信息
     *
     * @return DataTableModel dataTable定制模型
     */
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = gradeRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<GradeVo> page = gradeRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }

    /**
     * 查询所有启用班级
     *
     * @return
     */
    @Override
    public List<Grade> findAll() {
        return gradeRepository.findByEnableFlag(true);
    }
}
