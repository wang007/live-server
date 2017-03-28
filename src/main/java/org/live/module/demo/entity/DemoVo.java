package org.live.module.demo.entity;

/**
 * Created by KAM on 2017/3/28.
 */
public class DemoVo {

    private String id;
    private String name;
    private String age;
    private String username;
    private String address;
    private String other;

    public DemoVo(String id, String name, String age, String username, String address, String other) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.username = username;
        this.address = address;
        this.other = other;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
