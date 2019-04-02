package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyItemListBean implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.group_id);
        dest.writeString(this.name);
        dest.writeString(this.img);
        dest.writeString(this.question_count);
        dest.writeString(this.user_count);
        dest.writeString(this.introduce);
        dest.writeString(this.notice);
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

    public static final Creator<MyItemListBean> CREATOR = new Creator<MyItemListBean>() {
        @Override
        public MyItemListBean createFromParcel(Parcel source) {
            return new MyItemListBean(source);
        }

        @Override
        public MyItemListBean[] newArray(int size) {
            return new MyItemListBean[size];
        }
    };
}
