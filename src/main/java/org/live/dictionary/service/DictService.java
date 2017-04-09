package org.live.dictionary.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.dictionary.entity.Dictionary;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 字典服务
 *
 * @author KAM
 */
public interface DictService extends BaseService<Dictionary, String> {
    DataTableModel findPage(HttpServletRequest request);

    void cacheDic();

    String getDicVal(String dicKey);

    List<Dictionary> getDictList(String dictTypeKey);

    String getDictKey(String dicVal);
    
    Map<String, String> getDictMap(String dictTypeKey);

}
