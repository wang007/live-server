package org.live.module.demo.service.impl;

import org.live.common.response.DataTableModel;
import org.live.common.utils.DataTableUtils;
import org.live.module.demo.entity.DemoVo;
import org.live.module.demo.repository.DemoRepository;
import org.live.module.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by KAM on 2017/3/28.
 */
@Service(value = "demoService")
public class DemoServiceImpl implements DemoService {
    @Autowired
    private DemoRepository demoRepository;

    @Override
    public DataTableModel findPage(HttpServletRequest request) {
        Long recordsTotal = demoRepository.count();
        Map<String, Object> params = DataTableUtils.parseMap(request);
        Page<DemoVo> page = demoRepository.find((PageRequest) params.get("pageRequest"), (Map<String, Object>) params.get("filter"));
        DataTableModel model = DataTableUtils.parseDataTableModel(page, (Integer) params.get("draw"), recordsTotal);
        return model;
    }
}
