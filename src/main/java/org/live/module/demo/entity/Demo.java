package org.live.module.demo.entity;

import org.live.common.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 单标测试用例
 * Created by KAM on 2017/3/28.
 */
@Entity
@Table(name = "demo")
public class Demo extends BaseEntity {

    @Column
    private String name;
    @Column
    private String age;
    @Column
    private String username;
    @Column
    private String address;
    @Column
    private String other;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
