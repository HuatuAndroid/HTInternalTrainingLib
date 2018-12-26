package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ChapterBean implements Parcelable {

    /**
     * id : 48
     * title : 第一章：算法与案例：线性回归与逻辑回归
     * flag : true
     */

    private String id;
    private String title;
    private List<CourseChildBean> child;

    public List<CourseChildBean> getChild() {
        return child;
    }

    public void setChild(List<CourseChildBean> child) {
        this.child = child;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeTypedList(this.child);
    }

    public ChapterBean() {
    }

    protected ChapterBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.child = in.createTypedArrayList(CourseChildBean.CREATOR);
    }

    public static final Creator<ChapterBean> CREATOR = new Creator<ChapterBean>() {
        @Override
        public ChapterBean createFromParcel(Parcel source) {
            return new ChapterBean(source);
        }

        @Override
        public ChapterBean[] newArray(int size) {
            return new ChapterBean[size];
        }
    };
}
