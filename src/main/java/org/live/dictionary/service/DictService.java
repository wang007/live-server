package org.live.dictionary.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.dictionary.entity.DictType;
import org.live.dictionary.entity.Dictionary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 字典服务
 * 
 * @author KAM
 *
 */
public interface DictService extends BaseService<Dictionary, String> {
    DataTableModel findPage(HttpServletRequest request);
}
