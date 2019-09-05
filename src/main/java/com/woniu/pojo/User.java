package com.woniu.pojo;

public class User {
    private String name;
    private Integer uid;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", uid=" + uid +
                '}';
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
