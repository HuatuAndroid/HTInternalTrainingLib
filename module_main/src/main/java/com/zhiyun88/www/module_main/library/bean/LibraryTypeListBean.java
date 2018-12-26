package com.zhiyun88.www.module_main.library.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LibraryTypeListBean implements Parcelable {
    private String id;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String name) {
        this.title = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
    }

    public LibraryTypeListBean() {
    }

    protected LibraryTypeListBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
    }

    public static final Creator<LibraryTypeListBean> CREATOR = new Creator<LibraryTypeListBean>() {
        @Override
        public LibraryTypeListBean createFromParcel(Parcel source) {
            return new LibraryTypeListBean(source);
        }

        @Override
        public LibraryTypeListBean[] newArray(int size) {
            return new LibraryTypeListBean[size];
        }
    };
}
