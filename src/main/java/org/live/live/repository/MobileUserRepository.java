package org.live.live.repository;

import org.live.common.base.BaseRepository;
import org.live.live.entity.MobileUser;
import org.live.live.vo.MobileUserVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

/**
 * 移动端用户数据库访问接口
 * Created by KAM on 2017/4/6.
 */
public interface MobileUserRepository extends BaseRepository<MobileUser, String> {
    Page<MobileUserVo> find(PageRequest pageRequest, Map<String, Object> filter);
}
