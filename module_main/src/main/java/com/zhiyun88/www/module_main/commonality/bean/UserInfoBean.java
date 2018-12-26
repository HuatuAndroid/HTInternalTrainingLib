package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfoBean implements Parcelable {

    private int id;
    private String name;
    private String workEmail;
    private String birthday;
    private String sex;
    private String avatar;
    private String mobile;
    private String b_name;
    private String g_name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public String getG_name() {
        return g_name;
    }

    public void setG_name(String g_name) {
        this.g_name = g_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.workEmail);
        dest.writeString(this.birthday);
        dest.writeString(this.sex);
        dest.writeString(this.avatar);
        dest.writeString(this.mobile);
        dest.writeString(this.b_name);
        dest.writeString(this.g_name);
    }

    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.workEmail = in.readString();
        this.birthday = in.readString();
        this.sex = in.readString();
        this.avatar = in.readString();
        this.mobile = in.readString();
        this.b_name = in.readString();
        this.g_name = in.readString();
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel source) {
            return new UserInfoBean(source);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };
}
