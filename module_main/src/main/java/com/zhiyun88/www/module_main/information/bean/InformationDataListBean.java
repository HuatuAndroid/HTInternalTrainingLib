package com.zhiyun88.www.module_main.information.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InformationDataListBean implements Parcelable {
    private String id;
    private String information_title;
    private String picture;
    @SerializedName("abstract")
    private String abstractX;
    private String created_at;
    private String click_rate;
    private String app_created_at;
    private String app_created_time;
    private String h5_detail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInformation_title() {
        return information_title;
    }

    public void setInformation_title(String information_title) {
        this.information_title = information_title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getClick_rate() {
        return click_rate;
    }

    public void setClick_rate(String click_rate) {
        this.click_rate = click_rate;
    }

    public String getApp_created_at() {
        return app_created_at;
    }

    public void setApp_created_at(String app_created_at) {
        this.app_created_at = app_created_at;
    }

    public String getApp_created_time() {
        return app_created_time;
    }

    public void setApp_created_time(String app_created_time) {
        this.app_created_time = app_created_time;
    }

    public String getH5_detail() {
        return h5_detail;
    }

    public void setH5_detail(String h5_detail) {
        this.h5_detail = h5_detail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.information_title);
        dest.writeString(this.picture);
        dest.writeString(this.abstractX);
        dest.writeString(this.created_at);
        dest.writeString(this.click_rate);
        dest.writeString(this.app_created_at);
        dest.writeString(this.app_created_time);
        dest.writeString(this.h5_detail);
    }

    public InformationDataListBean() {
    }

    protected InformationDataListBean(Parcel in) {
        this.id = in.readString();
        this.information_title = in.readString();
        this.picture = in.readString();
        this.abstractX = in.readString();
        this.created_at = in.readString();
        this.click_rate = in.readString();
        this.app_created_at = in.readString();
        this.app_created_time = in.readString();
        this.h5_detail = in.readString();
    }

    public static final Creator<InformationDataListBean> CREATOR = new Creator<InformationDataListBean>() {
        @Override
        public InformationDataListBean createFromParcel(Parcel source) {
            return new InformationDataListBean(source);
        }

        @Override
        public InformationDataListBean[] newArray(int size) {
            return new InformationDataListBean[size];
        }
    };
}
