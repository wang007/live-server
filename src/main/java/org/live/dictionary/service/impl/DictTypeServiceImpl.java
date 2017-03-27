package org.live.dictionary.service.impl;


import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.repository.DictTypeRepository;
import org.live.dictionary.service.DictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 数据字典服务实现
 * 
 * @author KAM
 *
 */
@Service
public class DictTypeServiceImpl extends BaseServiceImpl<DictType, String> implements DictTypeService {

	@Resource
	private DictTypeRepository dictTypeRepository;

	@Override
	protected BaseRepository<DictType, String> getRepository() {
		return this.dictTypeRepository;
	}

}
