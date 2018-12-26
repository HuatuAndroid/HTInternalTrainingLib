package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MyTaskBean implements Parcelable {

    private int total;
    private int current_page;
    private CountNumberBean count_number;
    private List<MyTaskListBean> list;

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

    public CountNumberBean getCount_number() {
        return count_number;
    }

    public void setCount_number(CountNumberBean count_number) {
        this.count_number = count_number;
    }

    public List<MyTaskListBean> getList() {
        return list;
    }

    public void setList(List<MyTaskListBean> list) {
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
        dest.writeParcelable(this.count_number, flags);
        dest.writeList(this.list);
    }

    public MyTaskBean() {
    }

    protected MyTaskBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.count_number = in.readParcelable(CountNumberBean.class.getClassLoader());
        this.list = new ArrayList<MyTaskListBean>();
        in.readList(this.list, MyTaskListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyTaskBean> CREATOR = new Parcelable.Creator<MyTaskBean>() {
        @Override
        public MyTaskBean createFromParcel(Parcel source) {
            return new MyTaskBean(source);
        }

        @Override
        public MyTaskBean[] newArray(int size) {
            return new MyTaskBean[size];
        }
    };
}
