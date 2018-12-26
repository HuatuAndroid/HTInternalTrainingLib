package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class InfoBean implements Parcelable {
    private int basis_id;
    private String basis_title;
    private int chapter_id;
    private String chapter_title;
    private String start_date;
    private String end_date;
    private String teacher;

    public int getBasis_id() {
        return basis_id;
    }

    public void setBasis_id(int basis_id) {
        this.basis_id = basis_id;
    }

    public String getBasis_title() {
        return basis_title;
    }

    public void setBasis_title(String basis_title) {
        this.basis_title = basis_title;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
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
        dest.writeInt(this.basis_id);
        dest.writeString(this.basis_title);
        dest.writeInt(this.chapter_id);
        dest.writeString(this.chapter_title);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeString(this.teacher);
    }

    public InfoBean() {
    }

    protected InfoBean(Parcel in) {
        this.basis_id = in.readInt();
        this.basis_title = in.readString();
        this.chapter_id = in.readInt();
        this.chapter_title = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.teacher = in.readString();
    }

    public static final Creator<InfoBean> CREATOR = new Creator<InfoBean>() {
        @Override
        public InfoBean createFromParcel(Parcel source) {
            return new InfoBean(source);
        }

        @Override
        public InfoBean[] newArray(int size) {
            return new InfoBean[size];
        }
    };
}
