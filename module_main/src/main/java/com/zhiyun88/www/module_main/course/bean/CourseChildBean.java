package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseChildBean implements Parcelable {

    /**
     * id : 49
     * parent_id : 48
     * file_url : http://api.zhouhaiyang.com/uploads/files/20181010/e8ae4d33248df243d5a7c3b7cbf7d976.jpg
     * chapter_title : 第一章：算法与案例：线性回归与逻辑回归
     * periods_title : 第一节：
     * start_end_date : 2018-10-10 17:10-17:30
     * play_type : 1
     * video_id : 18101037550578
     * flag : true
     */

    private String id;
    private String parent_id;
    private String file_url;
    private String chapter_title;
    private String periods_title;
    private String start_end_date;
    private String play_type;
    private String video_id;
    private String course_type;

    public String getCourse_type() {
        return course_type;
    }

    public void setCourse_type(String course_type) {
        this.course_type = course_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public String getChapter_title() {
        return chapter_title;
    }

    public void setChapter_title(String chapter_title) {
        this.chapter_title = chapter_title;
    }

    public String getPeriods_title() {
        return periods_title;
    }

    public void setPeriods_title(String periods_title) {
        this.periods_title = periods_title;
    }

    public String getStart_end_date() {
        return start_end_date;
    }

    public void setStart_end_date(String start_end_date) {
        this.start_end_date = start_end_date;
    }

    public String getPlay_type() {
        return play_type;
    }

    public void setPlay_type(String play_type) {
        this.play_type = play_type;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public CourseChildBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.parent_id);
        dest.writeString(this.file_url);
        dest.writeString(this.chapter_title);
        dest.writeString(this.periods_title);
        dest.writeString(this.start_end_date);
        dest.writeString(this.play_type);
        dest.writeString(this.video_id);
        dest.writeString(this.course_type);
    }

    protected CourseChildBean(Parcel in) {
        this.id = in.readString();
        this.parent_id = in.readString();
        this.file_url = in.readString();
        this.chapter_title = in.readString();
        this.periods_title = in.readString();
        this.start_end_date = in.readString();
        this.play_type = in.readString();
        this.video_id = in.readString();
        this.course_type = in.readString();
    }

    public static final Creator<CourseChildBean> CREATOR = new Creator<CourseChildBean>() {
        @Override
        public CourseChildBean createFromParcel(Parcel source) {
            return new CourseChildBean(source);
        }

        @Override
        public CourseChildBean[] newArray(int size) {
            return new CourseChildBean[size];
        }
    };
}
