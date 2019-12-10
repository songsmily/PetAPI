package com.songsmily.petapi.entity;

import org.apache.shiro.authz.Permission;

import java.util.List;

public class Roles {
    private Integer id;

    private String name;

    private List<Permission> permissionList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }


    @Override
    public String toString() {
        return "Roles [id=" + id + ", name=" + name + ", permissionList=" + permissionList + "]";
    }

}
