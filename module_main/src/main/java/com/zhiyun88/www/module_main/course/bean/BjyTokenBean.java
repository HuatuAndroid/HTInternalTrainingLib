package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BjyTokenBean implements Parcelable {

    /**
     * video_id : 18101545634139
     * token : acbrdejBVriwCRjhQyTd4LFENj2L7x8SFe3PORqxK_NpgztVWYY9PnwYXKhKTHWiY6r7Lv5V4Fc
     * type : 4
     * user_id : 11
     * name : 张三
     * avatar :
     */

    private String video_id;
    private String token;
    private String type;
    private String user_id;
    private String name;
    private String avatar;

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.video_id);
        dest.writeString(this.token);
        dest.writeString(this.type);
        dest.writeString(this.user_id);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
    }

    public BjyTokenBean() {
    }

    protected BjyTokenBean(Parcel in) {
        this.video_id = in.readString();
        this.token = in.readString();
        this.type = in.readString();
        this.user_id = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
    }

    public static final Creator<BjyTokenBean> CREATOR = new Creator<BjyTokenBean>() {
        @Override
        public BjyTokenBean createFromParcel(Parcel source) {
            return new BjyTokenBean(source);
        }

        @Override
        public BjyTokenBean[] newArray(int size) {
            return new BjyTokenBean[size];
        }
    };
}
