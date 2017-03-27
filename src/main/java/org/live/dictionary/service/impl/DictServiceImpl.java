package org.live.dictionary.service.impl;


import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.dictionary.cache.CacheDict;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.repository.DictRepository;
import org.live.dictionary.repository.DictTypeRepository;
import org.live.dictionary.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典服务实现
 * 
 * @author KAM
 *
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<Dictionary, String> implements DictService {
	@Resource
	private DictRepository dictRepository;
	@Resource
	private DictTypeRepository dictTypeRepository;

	@Override
	protected BaseRepository<Dictionary, String> getRepository() {
		return dictRepository;
	}

	/**
	 * 缓存数据字典
	 */
	@Override
	public void getCacheDic() {
		/** 清空缓存 **/
		CacheDict.allTypes.clear();
		CacheDict.dictList.clear();

		List<DictType> globalTypes = dictTypeRepository.findAll(); // 查询字典中的所有类型

		for (DictType dictType : globalTypes) {
			CacheDict.allTypes.put(dictType.getDictTypeMark(), dictType); // 字符类型标识作为key,字典类型实体作为value缓存至map

			/**
			 * 查询字典类型对应的所有字典记录，字典类型标识作为key,字典记录集合作为value缓存至map
			 */
			List<Dictionary> dictList = dictRepository.findByDictType(dictType);
			CacheDict.dictList.put(dictType.getDictTypeMark(), dictList);

			/**
			 * 将字典记录集合转成map集合,字典标识作为key,字典值做为 value
			 */

			Map<String, String> map = new HashMap<String, String>();
			for (Dictionary dict : dictList) {
				map.put(dict.getDictMark(), dict.getDictValue());
			}

			CacheDict.dictMap.put(dictType.getDictTypeMark(), map);
			// 字典类型标识作为key,字典map集合作为value缓存至map集合
			for (Dictionary dict : dictList) {
				CacheDict.allDictMap.put(dict.getDictMark(), dict.getDictValue());
			}
		}

	}

	/**
	 * 根据字典类型查询所有字典
	 */
	@Override
	public List<Dictionary> getByDictType(DictType dictType) {
		return this.dictRepository.findByDictType(dictType);
	}

	/**
	 * 根据字典标识取值
	 * 
	 * @param mark
	 * @return
	 */
	public String dict(String mark) {
		return CacheDict.allDictMap.get(mark);
	}

	/**
	 * 根据字典类型标识取一组同类型的字典
	 * 
	 * @param typeMark
	 * @return
	 */
	public List<Dictionary> dictList(String typeMark) {
		return CacheDict.dictList.get(typeMark);
	}

	/**
	 * 根据字典类型标识取一组同类型的字典
	 * 
	 * @param typeMark
	 * @return
	 */
	public Map<String, String> dictMap(String typeMark) {
		return CacheDict.dictMap.get(typeMark);
	}
}