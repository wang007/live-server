package org.live.school.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.school.entity.Major;

import javax.servlet.http.HttpServletRequest;

/**
 * 专业业务接口
 * Created by KAM on 2017/4/4.
 */
public interface MajorService extends BaseService<Major, String> {
    public DataTableModel findPage(HttpServletRequest request);
}
