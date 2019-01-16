package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyCourseListBean implements Parcelable {

    private String id;
    private String title;
    private String course_type;
    private String cover;
    private int hour_num;
    private String start_date;
    private String end_date;
    private String teacher;
    private int study_chapter_count;
    private float rate_progress;
    private int is_comment;

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

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getHour_num() {
        return hour_num;
    }

    public void setHour_num(int hour_num) {
        this.hour_num = hour_num;
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

    public int getStudy_chapter_count() {
        return study_chapter_count;
    }

    public void setStudy_chapter_count(int study_chapter_count) {
        this.study_chapter_count = study_chapter_count;
    }

    public float getRate_progress() {
        return rate_progress;
    }

    public void setRate_progress(float rate_progress) {
        this.rate_progress = rate_progress;
    }

    public int getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(int is_comment) {
        this.is_comment = is_comment;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.course_type);
        dest.writeString(this.cover);
        dest.writeInt(this.hour_num);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeString(this.teacher);
        dest.writeInt(this.study_chapter_count);
        dest.writeFloat(this.rate_progress);
        dest.writeInt(this.is_comment);
    }

    public MyCourseListBean() {
    }

    protected MyCourseListBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.course_type = in.readString();
        this.cover = in.readString();
        this.hour_num = in.readInt();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.teacher = in.readString();
        this.study_chapter_count = in.readInt();
        this.rate_progress = in.readInt();
        this.is_comment = in.readInt();
    }

    public static final Parcelable.Creator<MyCourseListBean> CREATOR = new Parcelable.Creator<MyCourseListBean>() {
        @Override
        public MyCourseListBean createFromParcel(Parcel source) {
            return new MyCourseListBean(source);
        }

        @Override
        public MyCourseListBean[] newArray(int size) {
            return new MyCourseListBean[size];
        }
    };
}
