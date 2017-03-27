package org.live.sys.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.sys.entity.Log;
import org.live.sys.vo.LogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class LogRepositoryImpl extends BaseRepositoryImpl {
	
	public Page<Log> findLogInfoAll(Pageable pageable, LogVo logVo){
		final String xsql="select l from Log l "
				+ "where 1=1 "
				+ "/~ and l.ip like '%[ip]%' ~/"
				+ "/~ and l.name like '%[name]%' ~/"
				+ "/~ and l.username like '%[username]%'~/"
				+ "/~ and l.handleTime >= '[beginTime]'~/"
				+ "/~ and l.handle_time <= '[endTime]'~/"
				+ "/~ and l.description like '%[description]%'~/"
				+ "/~ and l.logLevel like '%[logLevel]%'~/"
				+ "/~ and l.operateType like '%[operateType]%'~/"
				+ "order by l.handleTime desc";
		
		Map<String, Object> filter = new HashMap<String, Object>();
		if(logVo != null){
			filter.put("ip", logVo.getIp());
			filter.put("name", logVo.getName());
			filter.put("username", logVo.getUsername());
			filter.put("handleTime", logVo.getHandleTime());
			filter.put("beginTime", logVo.getBeginTime());
			filter.put("endTime", logVo.getEndTime());
			filter.put("description", logVo.getDescription());
			filter.put("logLevel", logVo.getLogLevel());
			filter.put("operateType", logVo.getOperateType());
		}
		String hql = this.xsqlConvertSql(xsql, filter);
		return this.find(pageable,hql, null) ;
	}

}
