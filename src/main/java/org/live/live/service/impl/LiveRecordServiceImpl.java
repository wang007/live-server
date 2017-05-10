package org.live.live.service.impl;

import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.live.repository.LiveRecordRepository;
import org.live.live.service.LiveRecordService;
import org.live.live.vo.LiveRecordVo;
import org.live.live.vo.MobileUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 直播记录服务接口实现
 * Created by KAM on 2017/5/10.
 */
@Service("liveRecordService")
public class LiveRecordServiceImpl implements LiveRecordService {
    @Autowired
    private LiveRecordRepository liveRecordRepository;

    /**
     * 根据分页信息查看直播记录
     *
     * @param params
     * @return 插件定制响应模型
     */
    @Override
    public DataTableModel findLiveRecordByPage(Map<String, Object> params) {
        Long recordsTotal = liveRecordRepository.count(); // 查询总记录数
        Page<LiveRecordVo> page = liveRecordRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }
}
