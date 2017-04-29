package org.live.live.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.live.entity.Report;
import org.live.live.vo.ReportVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wang on 2017/4/29.
 */
public class ReportRepositoryImpl extends BaseRepositoryImpl {


    private static final String FIND_REPORT_XHQL = "select re from Report re where re.handleType=?0 and ("
            + "/~ re.user.account like '%[account]%'~/"
            + "/~ or re.user.nickname like '%[nickname]%'~/"
            + "/~ or re.liveRoom.roomNum like '%[roomNum]%'~/"
            + "/~ or re.liveRoom.roomName like '%[roomName]%'~/"
            +") order by re.reportNum DESC, re.createTime DESC" ;

    /**
     * 获取举报的数据
     * @param page
     * @param searchStr 搜索条件
     * @param handleType 获取是否已处理的举报表
     * @return
     */
    public Page<ReportVo> findReports(PageRequest page, String searchStr, boolean handleType) {

        Map<String, Object> filter = new HashMap<>() ;
        filter.put("account", searchStr) ;
        filter.put("nickname", searchStr) ;
        filter.put("roomNum", searchStr) ;
        filter.put("roomName", searchStr) ;

        String hql = this.xsqlConvertHql(FIND_REPORT_XHQL, filter) ;
        hql = hql.replace("()", "") ;
        return this.find(page, hql, new Object[]{handleType}) ;
    }

}
