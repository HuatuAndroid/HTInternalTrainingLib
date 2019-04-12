package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class MyItemListBean implements Serializable {
    private String group_id;
    private String name;
    private String img;
    private String question_count;
    private String user_count;
    private String introduce;
    private String notice;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(String question_count) {
        this.question_count = question_count;
    }

    public String getUser_count() {
        return user_count;
    }

    public void setUser_count(String user_count) {
        this.user_count = user_count;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public MyItemListBean() {
    }

    protected MyItemListBean(Parcel in) {
        this.group_id = in.readString();
        this.name = in.readString();
        this.img = in.readString();
        this.question_count = in.readString();
        this.user_count = in.readString();
        this.introduce = in.readString();
        this.notice = in.readString();
    }

}
