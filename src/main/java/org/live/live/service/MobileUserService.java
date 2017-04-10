package org.live.live.service;

import org.live.common.base.BaseService;
import org.live.common.response.DataTableModel;
import org.live.live.entity.MobileUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 移动端用户业务接口
 * Created by KAM on 2017/4/6.
 */
public interface MobileUserService extends BaseService<MobileUser, String> {
    public DataTableModel findPage(HttpServletRequest request);
    public void setOutDate(List<String> ids);
}
