package org.live.dictionary.service.impl;


import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.dictionary.cache.CacheDict;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.repository.DictTypeRepository;
import org.live.dictionary.vo.DictionaryVo;
import org.live.dictionary.repository.DictRepository;
import org.live.dictionary.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典服务实现
 *
 * @author KAM
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<Dictionary, String> implements DictService {
    @Autowired
    private DictRepository dictRepository;
    @Autowired
    private DictTypeRepository dictTypeRepository;

    @Override
    protected BaseRepository<Dictionary, String> getRepository() {
        return dictRepository;
    }

    /**
     * 查询分页数据
     *
     * @param request
     * @return DataTableModel dataTable插件定制模型
     */
    @Override
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = dictRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<DictionaryVo> page = dictRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }

    /**
     * 缓存字典
     */
    @Override
    public void cacheDic() {
        List<DictType> dictTypes = dictTypeRepository.findAll();
        for (DictType dictType : dictTypes) {
            CacheDict.allTypes.put(dictType.getDictTypeMark(), dictType); // 缓存所有字典类型

            List<Dictionary> dictionaries = dictRepository.findByDictTypeId(dictType.getId());
            CacheDict.dictList.put(dictType.getDictTypeMark(), dictionaries); // 缓存所有字典类型及其对应的一组字典
            Map<String, String> map = new HashMap<String, String>();
            for (Dictionary dictionary : dictionaries) {
                map.put(dictionary.getDictMark(), dictionary.getDictValue());
            }
            CacheDict.dictMap.put(dictType.getDictTypeMark(), map); // 缓存所有字典类型及其对应的一组字典键值对
        }
        List<Dictionary> dictionaries = dictRepository.findAll();
        for (Dictionary dictionary : dictionaries) {
            CacheDict.allDictMap.put(dictionary.getDictMark(), dictionary.getDictValue()); // 缓存所有字典数据键值对
        }
    }

    /**
     * 根据字典key取得value
     *
     * @param dicKey
     * @return
     */
    @Override
    public String getDicVal(String dicKey) {
        return CacheDict.allDictMap.get(dicKey);
    }

    /**
     * 根据字典类型key取得一组字典数据
     *
     * @param dictTypeKey
     * @return
     */
    @Override
    public List<Dictionary> getDictList(String dictTypeKey) {
        return CacheDict.dictList.get(dictTypeKey);
    }

    /**
     * 根据字典类型key取得一组字典数据键值对
     *
     * @param dictTypeKey
     * @return
     */
    @Override
    public Map<String, String> getDictMap(String dictTypeKey) {
        return CacheDict.dictMap.get(dictTypeKey);
    }

    /**
     * 根据字典value取得对应key
     *
     * @param dicVal
     * @return
     */
    @Override
    public String getDictKey(String dicVal) {
        for (Map.Entry<String, String> entry : CacheDict.allDictMap.entrySet()) {
            if (dicVal.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}