package com.zhiyun88.www.module_main.main.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeCourseBean implements Parcelable {

    private String id;
    private String title;
    private String cover;
    private int study_count;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getStudy_count() {
        return study_count;
    }

    public void setStudy_count(int study_count) {
        this.study_count = study_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.cover);
        dest.writeInt(this.study_count);
    }

    public HomeCourseBean() {
    }

    protected HomeCourseBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.cover = in.readString();
        this.study_count = in.readInt();
    }

    public static final Parcelable.Creator<HomeCourseBean> CREATOR = new Parcelable.Creator<HomeCourseBean>() {
        @Override
        public HomeCourseBean createFromParcel(Parcel source) {
            return new HomeCourseBean(source);
        }

        @Override
        public HomeCourseBean[] newArray(int size) {
            return new HomeCourseBean[size];
        }
    };
}

