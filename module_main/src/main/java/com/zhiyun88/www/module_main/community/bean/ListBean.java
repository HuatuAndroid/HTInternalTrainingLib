package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ListBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.introduce);
        dest.writeString(this.notice);
        dest.writeString(this.user_count);
        dest.writeString(this.question_count);
        dest.writeString(this.created_at);
        dest.writeString(this.relation);
        dest.writeString(this.created_id);
        dest.writeString(this.updated_at);
        dest.writeString(this.states);
        dest.writeString(this.sort);
        dest.writeString(this.is_group);
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

    public static final Creator<ListBean> CREATOR = new Creator<ListBean>() {
        @Override
        public ListBean createFromParcel(Parcel source) {
            return new ListBean(source);
        }

        @Override
        public ListBean[] newArray(int size) {
            return new ListBean[size];
        }
    };
}
