package org.live.sys.repository;


import org.live.common.base.BaseRepository;
import org.live.sys.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户组的Repository
 *
 * Created by Mr.wang on 2016/11/29.
 */
public interface GroupRepository extends BaseRepository<Group, String> {

    /**
     * 查询一组用户组
     * @param ids 用户组ids
     * @return
     */
    public List<Group> findByIdIn(String[] ids) ;
    
    /**
     * 通过serialNo查询
     * @param serialNo
     * @return
     */
    @Query("from Group g where g.serialNo=:serialNo")
	public List<Group> findGroupByGroupSerialNo(@Param("serialNo") int serialNo);

    /**
     *
     * @param pageable
     * @param group
     * @return
     */
    public Page<Group> findGroups(Pageable pageable, Group group) ;
    
    /**
     * 获得serialNo最大值
     * @return
     */
    @Query("select max(g.serialNo) from Group g ")
    public Integer getMaxSerialNo();

}
