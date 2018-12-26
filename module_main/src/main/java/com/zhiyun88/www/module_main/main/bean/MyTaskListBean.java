package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class MyTaskListBean implements Parcelable {

    private String id;
    private String name;
    private String start_date;
    private String end_date;
    private int type;
    private int integral;
    private int progress;

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeInt(this.type);
        dest.writeInt(this.integral);
        dest.writeInt(this.progress);
    }

    public MyTaskListBean() {
    }

    protected MyTaskListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.type = in.readInt();
        this.integral = in.readInt();
        this.progress = in.readInt();
    }

    public static final Parcelable.Creator<MyTaskListBean> CREATOR = new Parcelable.Creator<MyTaskListBean>() {
        @Override
        public MyTaskListBean createFromParcel(Parcel source) {
            return new MyTaskListBean(source);
        }

        @Override
        public MyTaskListBean[] newArray(int size) {
            return new MyTaskListBean[size];
        }
    };
}
