package org.live.live.service.impl;

import org.live.common.base.BaseRepository;
import org.live.common.base.BaseServiceImpl;
import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.live.entity.MobileUser;
import org.live.live.repository.MobileUserRepository;
import org.live.live.service.MobileUserService;
import org.live.live.vo.MobileUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 移动端用户业务接口实现
 * Created by KAM on 2017/4/6.
 */
@Service(value = "mobileUserService")
public class MobileUserServiceImpl extends BaseServiceImpl<MobileUser, String> implements MobileUserService {
    @Autowired
    private MobileUserRepository mobileUserRepository;

    @Override
    protected BaseRepository<MobileUser, String> getRepository() {
        return mobileUserRepository;
    }

    /**
     * 查询移动端用户分页信息
     *
     * @return DataTableModel dataTable定制模型
     */
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = mobileUserRepository.count(); // 查询总记录数
        Map<String, Object> params = DataTableUtils.parseMap(request); // 映射请求参数
        Page<MobileUserVo> page = mobileUserRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter")); // 查询分页数据
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal); // 映射成定制模型
        return model;
    }

    /**
     * 设置移动用户账户过期
     *
     * @param ids
     */
    public void setOutDate(List<String> ids) {
        for (String id : ids) {
            MobileUser record = mobileUserRepository.getOne(id);
            record.setOutDateFlag(true);
            mobileUserRepository.save(record);
        }

    }

    @Override
    public MobileUser findMobileUserByAccount(String account) {
        return mobileUserRepository.findMobileUserByAccount(account) ;
    }
}
