package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MyCourseBean implements Parcelable {

    private int total;
    private int current_page;
    private List<MyCourseListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<MyCourseListBean> getList() {
        return list;
    }

    public void setList(List<MyCourseListBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.current_page);
        dest.writeList(this.list);
    }

    public MyCourseBean() {
    }

    protected MyCourseBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyCourseListBean>();
        in.readList(this.list, MyCourseListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyCourseBean> CREATOR = new Parcelable.Creator<MyCourseBean>() {
        @Override
        public MyCourseBean createFromParcel(Parcel source) {
            return new MyCourseBean(source);
        }

        @Override
        public MyCourseBean[] newArray(int size) {
            return new MyCourseBean[size];
        }
    };
}
