package com.zhiyun88.www.module_main.library.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class LibraryTypeBean implements Parcelable {

    private List<LibraryTypeListBean> list;

    public List<LibraryTypeListBean> getList() {
        return list;
    }

    public void setList(List<LibraryTypeListBean> list) {
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

    public LibraryTypeBean() {
    }

    protected LibraryTypeBean(Parcel in) {
        this.list = in.createTypedArrayList(LibraryTypeListBean.CREATOR);
    }

    public static final Creator<LibraryTypeBean> CREATOR = new Creator<LibraryTypeBean>() {
        @Override
        public LibraryTypeBean createFromParcel(Parcel source) {
            return new LibraryTypeBean(source);
        }

        @Override
        public LibraryTypeBean[] newArray(int size) {
            return new LibraryTypeBean[size];
        }
    };
}
