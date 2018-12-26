package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageDetailsBean implements Parcelable {

    private String app_message_desc;
    private int app_url;
    private String id;
    private String message_desc;
    private int message_type;
    private String created_at;
    private String updated_at;
    private int extend_id;
    private int is_read;
    private String app_time_created_at;
    private String app_time_updated_at;
    private String user_id;

    public String getApp_message_desc() {
        return app_message_desc;
    }

    public void setApp_message_desc(String app_message_desc) {
        this.app_message_desc = app_message_desc;
    }

    public int getApp_url() {
        return app_url;
    }

    public void setApp_url(int app_url) {
        this.app_url = app_url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage_desc() {
        return message_desc;
    }

    public void setMessage_desc(String message_desc) {
        this.message_desc = message_desc;
    }

    public int getMessage_type() {
        return message_type;
    }

    public void setMessage_type(int message_type) {
        this.message_type = message_type;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getExtend_id() {
        return extend_id;
    }

    public void setExtend_id(int extend_id) {
        this.extend_id = extend_id;
    }

    public int getIs_read() {
        return is_read;
    }

    public void setIs_read(int is_read) {
        this.is_read = is_read;
    }

    public String getApp_time_created_at() {
        return app_time_created_at;
    }

    public void setApp_time_created_at(String app_time_created_at) {
        this.app_time_created_at = app_time_created_at;
    }

    public String getApp_time_updated_at() {
        return app_time_updated_at;
    }

    public void setApp_time_updated_at(String app_time_updated_at) {
        this.app_time_updated_at = app_time_updated_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.app_message_desc);
        dest.writeInt(this.app_url);
        dest.writeString(this.id);
        dest.writeString(this.message_desc);
        dest.writeInt(this.message_type);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.extend_id);
        dest.writeInt(this.is_read);
        dest.writeString(this.app_time_created_at);
        dest.writeString(this.app_time_updated_at);
        dest.writeString(this.user_id);
    }

    public MessageDetailsBean() {
    }

    protected MessageDetailsBean(Parcel in) {
        this.app_message_desc = in.readString();
        this.app_url = in.readInt();
        this.id = in.readString();
        this.message_desc = in.readString();
        this.message_type = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.extend_id = in.readInt();
        this.is_read = in.readInt();
        this.app_time_created_at = in.readString();
        this.app_time_updated_at = in.readString();
        this.user_id = in.readString();
    }

    public static final Creator<MessageDetailsBean> CREATOR = new Creator<MessageDetailsBean>() {
        @Override
        public MessageDetailsBean createFromParcel(Parcel source) {
            return new MessageDetailsBean(source);
        }

        @Override
        public MessageDetailsBean[] newArray(int size) {
            return new MessageDetailsBean[size];
        }
    };
}
