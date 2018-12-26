package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RecordBean implements Parcelable {

    private int total;
    private int current_page;
    private List<RecordListBean> list;

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

    public List<RecordListBean> getList() {
        return list;
    }

    public void setList(List<RecordListBean> list) {
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

    public RecordBean() {
    }

    protected RecordBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<RecordListBean>();
        in.readList(this.list, RecordListBean.class.getClassLoader());
    }

    public static final Creator<RecordBean> CREATOR = new Creator<RecordBean>() {
        @Override
        public RecordBean createFromParcel(Parcel source) {
            return new RecordBean(source);
        }

        @Override
        public RecordBean[] newArray(int size) {
            return new RecordBean[size];
        }
    };
}
