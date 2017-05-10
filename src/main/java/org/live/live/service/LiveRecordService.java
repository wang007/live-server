package org.live.live.service;

import org.live.common.response.DataTableModel;

import java.util.Map;

/**
 * 直播记录服务接口
 * Created by KAM on 2017/5/10.
 */
public interface LiveRecordService {
    /**
     * 根据分页信息查看直播记录
     *
     * @param param 查询相关参数
     * @return DataTable定制response
     */
    DataTableModel findLiveRecordByPage(Map<String, Object> params);
}
