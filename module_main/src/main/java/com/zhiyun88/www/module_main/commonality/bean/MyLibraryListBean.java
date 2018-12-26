package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MyLibraryListBean implements Parcelable {

    private String id;
    private String classify_id;
    private String name;
    @SerializedName("abstract")
    private String abstractX;
    private String img;
    private String browse_num;
    private String created_at;
    private String file_type;
    private String is_collection;
    private String ext;
    private String h5_detail;

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

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getH5_detail() {
        return h5_detail;
    }

    public void setH5_detail(String h5_detail) {
        this.h5_detail = h5_detail;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public MyLibraryListBean() {
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
        dest.writeString(this.browse_num);
        dest.writeString(this.created_at);
        dest.writeString(this.file_type);
        dest.writeString(this.is_collection);
        dest.writeString(this.ext);
        dest.writeString(this.h5_detail);
    }

    protected MyLibraryListBean(Parcel in) {
        this.id = in.readString();
        this.classify_id = in.readString();
        this.name = in.readString();
        this.abstractX = in.readString();
        this.img = in.readString();
        this.browse_num = in.readString();
        this.created_at = in.readString();
        this.file_type = in.readString();
        this.is_collection = in.readString();
        this.ext = in.readString();
        this.h5_detail = in.readString();
    }

    public static final Creator<MyLibraryListBean> CREATOR = new Creator<MyLibraryListBean>() {
        @Override
        public MyLibraryListBean createFromParcel(Parcel source) {
            return new MyLibraryListBean(source);
        }

        @Override
        public MyLibraryListBean[] newArray(int size) {
            return new MyLibraryListBean[size];
        }
    };
}
