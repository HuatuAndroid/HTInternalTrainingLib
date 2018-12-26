package com.zhiyun88.www.module_main.information.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class InformationTypeListBean implements Parcelable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
    }

    public InformationTypeListBean() {
    }

    protected InformationTypeListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
    }

    public static final Creator<InformationTypeListBean> CREATOR = new Creator<InformationTypeListBean>() {
        @Override
        public InformationTypeListBean createFromParcel(Parcel source) {
            return new InformationTypeListBean(source);
        }

        @Override
        public InformationTypeListBean[] newArray(int size) {
            return new InformationTypeListBean[size];
        }
    };
}
