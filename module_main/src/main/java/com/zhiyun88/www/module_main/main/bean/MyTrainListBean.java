package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTrainListBean implements Parcelable {
    private String id;
    private String title;
    private String cover;
    private String start_date;
    private String end_date;
    private String teacher;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
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
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeString(this.teacher);
    }

    public MyTrainListBean() {
    }

    protected MyTrainListBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.cover = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.teacher = in.readString();
    }

    public static final Parcelable.Creator<MyTrainListBean> CREATOR = new Parcelable.Creator<MyTrainListBean>() {
        @Override
        public MyTrainListBean createFromParcel(Parcel source) {
            return new MyTrainListBean(source);
        }

        @Override
        public MyTrainListBean[] newArray(int size) {
            return new MyTrainListBean[size];
        }
    };
}
