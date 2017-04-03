package org.live.dictionary.repository;


import org.live.common.base.BaseRepository;
import org.live.dictionary.entity.DictType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 字典类型持久层
 * 
 * @author KAM
 *
 */
public interface DictTypeRepository extends BaseRepository<DictType, String> {
    public Page<DictType> find(PageRequest pageRequest, Map<String, Object> filter);
}
