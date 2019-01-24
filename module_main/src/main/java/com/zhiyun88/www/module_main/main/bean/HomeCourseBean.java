package com.zhiyun88.www.module_main.main.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class HomeCourseBean implements Parcelable {

    private String id;
    private String title;
    private String teacher;
    private String cover;
    private int study_count;

    protected HomeCourseBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        teacher = in.readString();
        cover = in.readString();
        study_count = in.readInt();
    }

    public static final Creator<HomeCourseBean> CREATOR = new Creator<HomeCourseBean>() {
        @Override
        public HomeCourseBean createFromParcel(Parcel in) {
            return new HomeCourseBean(in);
        }

        @Override
        public HomeCourseBean[] newArray(int size) {
            return new HomeCourseBean[size];
        }
    };

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

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getTeacher() {

        return teacher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(teacher);
        dest.writeString(cover);
        dest.writeInt(study_count);
    }
}

