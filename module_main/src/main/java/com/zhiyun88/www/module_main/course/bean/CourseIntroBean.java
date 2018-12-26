package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CourseIntroBean implements Parcelable {
    /**
     * id : 18
     * title : 直播课编程基础公开提前10分钟学分10分
     * subtitle : 直播课编程基础公开提前10分钟学分10分直播课编程基础公开提前10分钟学分10分
     * course_type : 1
     * cover : http://api.zhouhaiyang.com/uploads/images/20181010/f25b2e693a7d2a3f4d30059b24a1127c.jpg
     * is_public : 1
     * page_view : 8
     * details : <p>华盛顿发</p><p><img src="http://api.zhouhaiyang.com//uploads/ueditor/image/20181010/1539159809445930.png" title="1539159809445930.png" alt="课程详情.png"/></p>
     * teacher_name : 江军,张兵,黄瑞
     * is_buy : 1
     * apply_num : 1
     * advance_time : 10
     */

    private String id;
    private String title;
    private String subtitle;
    private String course_type;
    private String cover;
    private String is_public;
    private String page_view;
    private String details;
    private String teacher_name;
    private String is_buy;
    private String apply_num;
    private String advance_time;
    private String start_end_date;
    private String address;
    private String department;
    private String surplus_num;
private String details_url;

    public String getDetails_url() {
        return details_url;
    }

    public void setDetails_url(String details_url) {
        this.details_url = details_url;
    }

    public String getStart_end_date() {
        return start_end_date;
    }

    public void setStart_end_date(String start_end_date) {
        this.start_end_date = start_end_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSurplus_num() {
        return surplus_num;
    }

    public void setSurplus_num(String surplus_num) {
        this.surplus_num = surplus_num;
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

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
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

    public String getIs_public() {
        return is_public;
    }

    public void setIs_public(String is_public) {
        this.is_public = is_public;
    }

    public String getPage_view() {
        return page_view;
    }

    public void setPage_view(String page_view) {
        this.page_view = page_view;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getApply_num() {
        return apply_num;
    }

    public void setApply_num(String apply_num) {
        this.apply_num = apply_num;
    }

    public String getAdvance_time() {
        return advance_time;
    }

    public void setAdvance_time(String advance_time) {
        this.advance_time = advance_time;
    }

    public CourseIntroBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.subtitle);
        dest.writeString(this.course_type);
        dest.writeString(this.cover);
        dest.writeString(this.is_public);
        dest.writeString(this.page_view);
        dest.writeString(this.details);
        dest.writeString(this.teacher_name);
        dest.writeString(this.is_buy);
        dest.writeString(this.apply_num);
        dest.writeString(this.advance_time);
        dest.writeString(this.start_end_date);
        dest.writeString(this.address);
        dest.writeString(this.department);
        dest.writeString(this.surplus_num);
        dest.writeString(this.details_url);
    }

    protected CourseIntroBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.subtitle = in.readString();
        this.course_type = in.readString();
        this.cover = in.readString();
        this.is_public = in.readString();
        this.page_view = in.readString();
        this.details = in.readString();
        this.teacher_name = in.readString();
        this.is_buy = in.readString();
        this.apply_num = in.readString();
        this.advance_time = in.readString();
        this.start_end_date = in.readString();
        this.address = in.readString();
        this.department = in.readString();
        this.surplus_num = in.readString();
        this.details_url = in.readString();
    }

    public static final Creator<CourseIntroBean> CREATOR = new Creator<CourseIntroBean>() {
        @Override
        public CourseIntroBean createFromParcel(Parcel source) {
            return new CourseIntroBean(source);
        }

        @Override
        public CourseIntroBean[] newArray(int size) {
            return new CourseIntroBean[size];
        }
    };
}
