package org.live.live.service.impl;

import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.live.repository.ReportRepository;
import org.live.live.service.ReportService;
import org.live.live.vo.MobileUserVo;
import org.live.live.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 举报业务实现层
 * Created by wang on 2017/4/30.
 */
@Service("reportService")
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportRepository reportRepository;

    /**
     * 根据分页信息查询举报记录
     *
     * @param params
     * @return
     */
    @Override
    public DataTableModel findReportByPage(Map<String, Object> params, boolean handleType) {
        Long recordsTotal = reportRepository.count(); // 查询总记录数
        Page<ReportVo> page = reportRepository.findReports((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter"), handleType); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }
}
