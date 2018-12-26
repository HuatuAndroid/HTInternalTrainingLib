package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MyTrainBean implements Parcelable {

    private int total;
    private int current_page;
    private List<MyTrainListBean> list;

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

    public List<MyTrainListBean> getList() {
        return list;
    }

    public void setList(List<MyTrainListBean> list) {
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

    public MyTrainBean() {
    }

    protected MyTrainBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyTrainListBean>();
        in.readList(this.list, MyTrainListBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<MyTrainBean> CREATOR = new Parcelable.Creator<MyTrainBean>() {
        @Override
        public MyTrainBean createFromParcel(Parcel source) {
            return new MyTrainBean(source);
        }

        @Override
        public MyTrainBean[] newArray(int size) {
            return new MyTrainBean[size];
        }
    };
}
