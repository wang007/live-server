package org.live.school.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.school.entity.Major;
import org.live.school.repository.MajorRepository;
import org.live.school.service.MajorService;
import org.live.school.vo.MajorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 专业业务接口实现
 * Created by KAM on 2017/4/4.
 */
@Service(value = "majorService")
public class MajorServiceImpl extends BaseServiceImpl<Major, String> implements MajorService {

    @Autowired
    private MajorRepository majorRepository;

    @Override
    protected BaseRepository<Major, String> getRepository() {
        return majorRepository;
    }

    /**
     * 查询专业分页信息
     *
     * @return DataTableModel dataTable定制模型
     */
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = majorRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<MajorVo> page = majorRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }
}
