package org.live.live.service;

import org.live.common.response.DataTableModel;

import java.util.Map;

/**
 * 举报业务层
 * Created by wang on 2017/4/30.
 */
public interface ReportService {
    /**
     * 根据分页信息查询举报数据
     *
     * @param params
     * @return
     */
    DataTableModel findReportByPage(Map<String, Object> params, boolean handleType);

    /**
     * 更新举报数据
     */
    void updateReport(String id);
}
