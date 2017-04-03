package org.live.dictionary.repository;


import org.live.common.base.BaseRepository;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.entity.DictionaryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;

/**
 * 字典持久层
 * 
 * @author KAM
 *
 */
public interface DictRepository extends BaseRepository<Dictionary, String> {
	Page<DictionaryVo> find(PageRequest pageRequest, Map<String, Object> filter);
}
