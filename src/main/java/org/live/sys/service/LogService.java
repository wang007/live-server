package org.live.sys.service;


import org.live.common.base.BaseService;
import org.live.sys.entity.Log;
import org.live.sys.vo.LogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Mr.wang on 2016/12/13.
 */
public interface LogService extends BaseService<Log, String> {
	
	
	/**
	 * 分页查询日志
	 * @param pageable
	 * @param log
	 * @return
	 */
	public Page<LogVo> findLogs(Pageable pageable, LogVo logVo) ;
	
	/**
	 * 删除一组日志信息
	 * @param ids
	 */
	public void deleteLogInfo(String ids[]);
}
