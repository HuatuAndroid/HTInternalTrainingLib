package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RecordListBean implements Parcelable {

    private int id;
    private String name;
    private int is_increase;
    private int integral;
    private String created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIs_increase() {
        return is_increase;
    }

    public void setIs_increase(int is_increase) {
        this.is_increase = is_increase;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.is_increase);
        dest.writeInt(this.integral);
        dest.writeString(this.created_at);
    }

    public RecordListBean() {
    }

    protected RecordListBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.is_increase = in.readInt();
        this.integral = in.readInt();
        this.created_at = in.readString();
    }

    public static final Creator<RecordListBean> CREATOR = new Creator<RecordListBean>() {
        @Override
        public RecordListBean createFromParcel(Parcel source) {
            return new RecordListBean(source);
        }

        @Override
        public RecordListBean[] newArray(int size) {
            return new RecordListBean[size];
        }
    };
}
