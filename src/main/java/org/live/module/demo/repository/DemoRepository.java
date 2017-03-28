package org.live.module.demo.repository;

import org.live.common.base.BaseRepository;
import org.live.module.demo.entity.Demo;
import org.live.module.demo.entity.DemoVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * Created by KAM on 2017/3/28.
 */
public interface DemoRepository extends BaseRepository<Demo, String>{

    public Page<DemoVo> find(PageRequest pageRequest, Map<String, Object> filter);
}
