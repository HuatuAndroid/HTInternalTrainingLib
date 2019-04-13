package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class ListBean implements Serializable {
    private String id;
    private String name;
    private String img;
    private String introduce;
    private String notice;
    private String user_count;
    private String question_count;
    private String created_at;
    private String relation;
    private String created_id;
    private String updated_at;
    private String states;
    private String sort;
    private String is_group;

    public String getIs_group() {
        return is_group;
    }

    public void setIs_group(String is_group) {
        this.is_group = is_group;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

    public String getUser_count() {
        return user_count;
    }

    public void setUser_count(String user_count) {
        this.user_count = user_count;
    }

    public String getQuestion_count() {
        return question_count;
    }

    public void setQuestion_count(String question_count) {
        this.question_count = question_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRelation() {
        return relation;
    }

    public ListBean() {
    }

    protected ListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.img = in.readString();
        this.introduce = in.readString();
        this.notice = in.readString();
        this.user_count = in.readString();
        this.question_count = in.readString();
        this.created_at = in.readString();
        this.relation = in.readString();
        this.created_id = in.readString();
        this.updated_at = in.readString();
        this.states = in.readString();
        this.sort = in.readString();
        this.is_group = in.readString();
    }

}
