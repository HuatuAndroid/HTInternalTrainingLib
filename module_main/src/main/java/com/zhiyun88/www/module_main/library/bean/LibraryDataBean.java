package com.zhiyun88.www.module_main.library.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class LibraryDataBean implements Parcelable {

    private int total;
    private int current_page;
    private List<LibraryDataListBean> list;

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

    public List<LibraryDataListBean> getList() {
        return list;
    }

    public void setList(List<LibraryDataListBean> list) {
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
        dest.writeTypedList(this.list);
    }

    public LibraryDataBean() {
    }

    protected LibraryDataBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = in.createTypedArrayList(LibraryDataListBean.CREATOR);
    }

    public static final Creator<LibraryDataBean> CREATOR = new Creator<LibraryDataBean>() {
        @Override
        public LibraryDataBean createFromParcel(Parcel source) {
            return new LibraryDataBean(source);
        }

        @Override
        public LibraryDataBean[] newArray(int size) {
            return new LibraryDataBean[size];
        }
    };
}
