package com.example.module_employees_world.bean;


import java.io.Serializable;

public class GroupDetailsBean implements Serializable {

    private GroupInfoBean group_info;

    public GroupInfoBean getGroup_info() {
        return group_info;
    }

    public void setGroup_info(GroupInfoBean group_info) {
        this.group_info = group_info;
    }

}
