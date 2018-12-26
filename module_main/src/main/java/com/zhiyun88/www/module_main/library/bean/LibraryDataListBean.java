package com.zhiyun88.www.module_main.library.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LibraryDataListBean implements Parcelable {
    private String id;
    private String classify_id;
    private String name;
    @SerializedName("abstract")
    private String abstractX;
    private String img;
    private String ext;
    private String browse_num;
    private String created_at;
    private String file_type;
    private String is_download;
    private String is_collection;
    private String app_created_at;
    private String app_created_time;
    private String h5_detail;

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(String classify_id) {
        this.classify_id = classify_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(String browse_num) {
        this.browse_num = browse_num;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getIs_download() {
        return is_download;
    }

    public void setIs_download(String is_download) {
        this.is_download = is_download;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
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

    public LibraryDataListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.classify_id);
        dest.writeString(this.name);
        dest.writeString(this.abstractX);
        dest.writeString(this.img);
        dest.writeString(this.ext);
        dest.writeString(this.browse_num);
        dest.writeString(this.created_at);
        dest.writeString(this.file_type);
        dest.writeString(this.is_download);
        dest.writeString(this.is_collection);
        dest.writeString(this.app_created_at);
        dest.writeString(this.app_created_time);
        dest.writeString(this.h5_detail);
    }

    protected LibraryDataListBean(Parcel in) {
        this.id = in.readString();
        this.classify_id = in.readString();
        this.name = in.readString();
        this.abstractX = in.readString();
        this.img = in.readString();
        this.ext = in.readString();
        this.browse_num = in.readString();
        this.created_at = in.readString();
        this.file_type = in.readString();
        this.is_download = in.readString();
        this.is_collection = in.readString();
        this.app_created_at = in.readString();
        this.app_created_time = in.readString();
        this.h5_detail = in.readString();
    }

    public static final Creator<LibraryDataListBean> CREATOR = new Creator<LibraryDataListBean>() {
        @Override
        public LibraryDataListBean createFromParcel(Parcel source) {
            return new LibraryDataListBean(source);
        }

        @Override
        public LibraryDataListBean[] newArray(int size) {
            return new LibraryDataListBean[size];
        }
    };
}
