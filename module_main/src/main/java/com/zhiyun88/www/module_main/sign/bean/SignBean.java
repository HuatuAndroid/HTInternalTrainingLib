package com.zhiyun88.www.module_main.sign.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class SignBean implements Parcelable {
    private SignlistData list;

    public SignlistData getList() {
        return list;
    }

    public void setList(SignlistData list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.list, flags);
    }

    public SignBean() {
    }

    protected SignBean(Parcel in) {
        this.list = in.readParcelable(SignlistData.class.getClassLoader());
    }

    public static final Creator<SignBean> CREATOR = new Creator<SignBean>() {
        @Override
        public SignBean createFromParcel(Parcel source) {
            return new SignBean(source);
        }

        @Override
        public SignBean[] newArray(int size) {
            return new SignBean[size];
        }
    };
}
