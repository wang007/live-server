package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Log;
import org.live.sys.vo.LogVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Mr.wang on 2016/12/13.
 */
public interface LogRepository extends BaseRepository<Log, String> {

	/**
	 * 分页查询日志
	 * @param pageable
	 * @param log
	 * @return
	 */
	public Page<LogVo> findLogInfoAll(Pageable pageable, LogVo log);
}
