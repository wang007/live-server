package org.live.sys.repository;


import org.live.common.base.BaseRepositoryImpl;
import org.live.common.constants.Constants;
import org.live.sys.vo.LogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;

public class LogRepositoryImpl extends BaseRepositoryImpl {
	
	public Page<LogVo> findLogInfoAll(Pageable pageable, LogVo logVo){
		final String xsql="select l.id as id,l.ip as ip,l.name as name,l.username as username,l.handle_time as handleTime,"
				+ "l.description as description,l.log_level as logLevel,l.operate_type as operateType "
				+ "from sys_log l "
				+ "where 1=?0 "
				+ "/~ and l.ip like '%[ip]%' ~/"
				+ "/~ and l.name like '%[name]%' ~/"
				+ "/~ and l.username like '%[username]%'~/"
				+ "/~ and l.handle_time >= '[beginTime]'~/"
				+ "/~ and l.handle_time <= '[endTime]'~/"
				//+ "/~ and l.handle_time between '[beginTime]' and '[endTime]'~/"
				+ "/~ and l.description like '%[description]%'~/"
				+ "/~ and l.log_level like '%[logLevel]%'~/"
				+ "/~ and l.operate_type like '%[operateType]%'~/"
				+ "order by l.handle_time desc";
		
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
		String sql = this.xsqlConvertSql(xsql, filter);
		return this.findBySql(pageable,sql, new Object[] { Constants.DIC_YES }, LogVo.class);
	}

}
