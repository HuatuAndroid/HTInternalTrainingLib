package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MyPartBean implements Parcelable {

    private int total;
    private int current_page;
    private List<MyPartListBean> list;

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

    public List<MyPartListBean> getList() {
        return list;
    }

    public void setList(List<MyPartListBean> list) {
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

    public MyPartBean() {
    }

    protected MyPartBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyPartListBean>();
        in.readList(this.list, MyPartListBean.class.getClassLoader());
    }

    public static final Creator<MyPartBean> CREATOR = new Creator<MyPartBean>() {
        @Override
        public MyPartBean createFromParcel(Parcel source) {
            return new MyPartBean(source);
        }

        @Override
        public MyPartBean[] newArray(int size) {
            return new MyPartBean[size];
        }
    };
}
