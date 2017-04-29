package org.live.live.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.live.controller.ReportController;
import org.live.live.entity.Report;
import org.live.live.vo.ReportVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * Created by wang on 2017/4/29.
 */
public class ReportRepositoryImpl extends BaseRepositoryImpl {


    private static final Logger LOGGER = LoggerFactory.getLogger(ReportRepositoryImpl.class);

    private static final String FIND_REPORT_XHQL = "select new org.live.live.vo.ReportVo(re.id, re.reportNum, re.user.account, re.user.nickname, re.liveRoom.roomNum, re.liveRoom.roomName, re.createTime, re.handleType) from Report re where re.handleType=?0";
    private static final String STATEMENT_BLOCK = "/~ re.user.account like '%[account]%'~/"
            + "/~ or re.user.nickname like '%[nickname]%'~/"
            + "/~ or re.liveRoom.roomNum like '%[roomNum]%'~/"
            + "/~ or re.liveRoom.roomName like '%[roomName]%' ~/";

    /**
     * 获取举报的数据
     *
     * @param pageRequest
     * @param filter      过滤条件
     * @param handleType  获取是否已处理的举报表
     * @return
     */
    public Page<ReportVo> findReports(PageRequest pageRequest, Map<String, Object> filter, boolean handleType) {
        StringBuilder xSqlBuilder = new StringBuilder();
        xSqlBuilder.append(FIND_REPORT_XHQL);
        if (filter.size() > 0) {
            xSqlBuilder.append(" and (");
            xSqlBuilder.append(STATEMENT_BLOCK);
            xSqlBuilder.append(")");
        }
        String hql = this.xsqlConvertHql(xSqlBuilder.toString(), filter);
        LOGGER.debug(hql);
        return this.find(pageRequest, hql, new Object[]{handleType});
    }

}
