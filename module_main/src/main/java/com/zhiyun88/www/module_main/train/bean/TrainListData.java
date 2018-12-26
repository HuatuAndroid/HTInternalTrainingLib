package com.zhiyun88.www.module_main.train.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainListData implements Parcelable {

    /**
     * id : 30
     * title : 线下培训1
     * subtitle : 线下培训1
     * cover :
     * teacher : 马志芳,我是讲师
     * department :
     * details : 测试直播0927测试直播0927测试直播0927测试直播0927测试直播0927
     * study_count : 0
     * address : 北京市东城区上地1
     * is_buy : 2
     * start_end_date : 2018-09-29 10:30-18:30
     */

    private String id;
    private String title;
    private String subtitle;
    private String cover;
    private String teacher;
    private String department;
    private String details;
    private String study_count;
    private String address;
    private String is_buy;
    private String start_end_date;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStudy_count() {
        return study_count;
    }

    public void setStudy_count(String study_count) {
        this.study_count = study_count;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIs_buy() {
        return is_buy;
    }

    public void setIs_buy(String is_buy) {
        this.is_buy = is_buy;
    }

    public String getStart_end_date() {
        return start_end_date;
    }

    public void setStart_end_date(String start_end_date) {
        this.start_end_date = start_end_date;
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
        dest.writeString(this.cover);
        dest.writeString(this.teacher);
        dest.writeString(this.department);
        dest.writeString(this.details);
        dest.writeString(this.study_count);
        dest.writeString(this.address);
        dest.writeString(this.is_buy);
        dest.writeString(this.start_end_date);
    }

    public TrainListData() {
    }

    protected TrainListData(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.subtitle = in.readString();
        this.cover = in.readString();
        this.teacher = in.readString();
        this.department = in.readString();
        this.details = in.readString();
        this.study_count = in.readString();
        this.address = in.readString();
        this.is_buy = in.readString();
        this.start_end_date = in.readString();
    }

    public static final Parcelable.Creator<TrainListData> CREATOR = new Parcelable.Creator<TrainListData>() {
        @Override
        public TrainListData createFromParcel(Parcel source) {
            return new TrainListData(source);
        }

        @Override
        public TrainListData[] newArray(int size) {
            return new TrainListData[size];
        }
    };
}
