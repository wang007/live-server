package org.live.dictionary.repository;


import org.live.common.base.BaseRepository;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;

import java.util.List;

/**
 * 字典持久层
 * 
 * @author KAM
 *
 */
public interface DictRepository extends BaseRepository<Dictionary, String> {
	/**
	 * 根据字典类型查询所有字典
	 * 
	 * @param dictType
	 * @return
	 */
	public List<Dictionary> findByDictType(DictType dictType);
}
