package org.live.sys.vo;


import org.live.sys.entity.Group;
import org.live.sys.entity.Role;

import java.util.List;

/**
 *
 * 用户组的数据载体
 * Created by Mr.wang on 2016/12/2.
 */

public class GroupInfo {

    private Group group ;

    private List<Role> roles ;

    public GroupInfo(){}

    public GroupInfo(Group group, List<Role> roles) {
        this.group = group ;
        this.roles = roles ;
    }


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
