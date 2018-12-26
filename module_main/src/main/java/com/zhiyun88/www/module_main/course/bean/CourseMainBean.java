package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CourseMainBean implements Parcelable {
    private List<CourseMainData> list;

    public List<CourseMainData> getList() {
        return list;
    }

    public void setList(List<CourseMainData> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public CourseMainBean() {
    }

    protected CourseMainBean(Parcel in) {
        this.list = in.createTypedArrayList(CourseMainData.CREATOR);
    }

    public static final Parcelable.Creator<CourseMainBean> CREATOR = new Parcelable.Creator<CourseMainBean>() {
        @Override
        public CourseMainBean createFromParcel(Parcel source) {
            return new CourseMainBean(source);
        }

        @Override
        public CourseMainBean[] newArray(int size) {
            return new CourseMainBean[size];
        }
    };
}
