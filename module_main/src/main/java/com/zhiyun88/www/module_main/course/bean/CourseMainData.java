package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseMainData implements Parcelable {

    /**
     * id : 2
     * title : 啊啊
     * type : 1
     * classify_id : 1
     * cover : http://localhost/images/32c589aaffc05ae565ffd1ddde01e832.jpg
     * hour_num : 0
     * study_num : 0
     */

    private String id;
    private String title;
    private String type;
    private String classify_id;
    private String cover;
    private String hour_num;
    private String study_num;
    private String teacher;

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(String classify_id) {
        this.classify_id = classify_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getHour_num() {
        return hour_num;
    }

    public void setHour_num(String hour_num) {
        this.hour_num = hour_num;
    }

    public String getStudy_num() {
        return study_num;
    }

    public void setStudy_num(String study_num) {
        this.study_num = study_num;
    }

    public CourseMainData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeString(this.classify_id);
        dest.writeString(this.cover);
        dest.writeString(this.hour_num);
        dest.writeString(this.study_num);
        dest.writeString(this.teacher);
    }

    protected CourseMainData(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.classify_id = in.readString();
        this.cover = in.readString();
        this.hour_num = in.readString();
        this.study_num = in.readString();
        this.teacher = in.readString();
    }

    public static final Creator<CourseMainData> CREATOR = new Creator<CourseMainData>() {
        @Override
        public CourseMainData createFromParcel(Parcel source) {
            return new CourseMainData(source);
        }

        @Override
        public CourseMainData[] newArray(int size) {
            return new CourseMainData[size];
        }
    };
}
