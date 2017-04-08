package org.live.dictionary.service.impl;


import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.dictionary.entity.Dictionary;
import org.live.dictionary.vo.DictionaryVo;
import org.live.dictionary.repository.DictRepository;
import org.live.dictionary.service.DictService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 字典服务实现
 *
 * @author KAM
 */
@Service("dictService")
public class DictServiceImpl extends BaseServiceImpl<Dictionary, String> implements DictService {
    @Resource
    private DictRepository dictRepository;

    @Override
    protected BaseRepository<Dictionary, String> getRepository() {
        return dictRepository;
    }

    /**
     * 查询分页数据
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
}