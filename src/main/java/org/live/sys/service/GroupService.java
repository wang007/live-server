package org.live.sys.service;


import org.live.common.base.BaseService;
import org.live.sys.entity.Group;
import org.live.sys.vo.FindGroupVo;
import org.live.sys.vo.GroupInfo;
import org.live.sys.vo.GroupVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户组的service
 * Created by Mr.wang on 2016/11/29.
 */
public interface GroupService extends BaseService<Group,String> {

    /**
     * 查询用户组信息，包括角色
     * @return
     */
    public List<GroupInfo> findGroupInfos() ;
    
    /**
     * 通过serialNo查询用户组
     * @param serialNo
     * @return
     */
    public List<Group> findGroupBySerialNo(int serialNo) ;

    /**
     * 查询用户组信息，包括角色
     * @param groupId 用户组id
     * @return
     */
    public GroupInfo findGorupInfoByGroupId(String groupId) ;

    /**
     * 保存用户组信息，包括角色
     * @param group
     * @param roleIds
     */
    public void saveGroupInfo(Group group, String[] roleIds) ;

    /**
     * 修改用户组信息，包括角色
     * @param groupVo 用户组的数据载体
     */
    public void updateGroupInfo(GroupVo groupVo) ;

    /**
     * 用户组分页查询
     * @param pageable 分页信息载体
     * @param group 查询用户组的数据载体
     * @return
     */
    public Page<FindGroupVo> findGroups(Pageable pageable, Group group) ;
    
    /**
     * 生成serialNo
     * @return
     */
    public Integer createSerialNo();
    
    /**
     * 检查序号是否已经存在
     * @param serialNo
     * @return
     */
    public boolean isExistSerialNo(int serialNo);

}
