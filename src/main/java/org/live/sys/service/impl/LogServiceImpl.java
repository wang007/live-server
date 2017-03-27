package org.live.sys.service.impl;


import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.sys.entity.Log;
import org.live.sys.repository.LogRepository;
import org.live.sys.service.LogService;
import org.live.sys.vo.LogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Mr.wang on 2016/12/13.
 */
@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<Log, String> implements LogService {

    @Resource
    private LogRepository logRepo ;

    @Override
    protected BaseRepository<Log, String> getRepository() {
        return this.logRepo ;
    }

	/**
     * 分页查询
     */
	@Override
	public Page<LogVo> findLogs(Pageable pageable, LogVo logVo) {
		// TODO Auto-generated method stub
		return this.logRepo.findLogInfoAll(pageable, logVo);
	}

	/**
	 * 删除日志信息
	 */
	@Override
	public void deleteLogInfo(String[] ids) {
		// TODO Auto-generated method stub
		for(String id : ids){
			Log log=this.findOne(id);
			if(log !=null){
				this.delete(log);
			}
		}
	}


}
