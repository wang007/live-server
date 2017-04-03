package org.live.dictionary.service;


import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.dictionary.entity.DictType;

import javax.servlet.http.HttpServletRequest;

/**
 * 字典类型服务
 * @author KAM
 *
 */
public interface DictTypeService extends BaseService<DictType, String> {
    DataTableModel findPage(HttpServletRequest request);
}
