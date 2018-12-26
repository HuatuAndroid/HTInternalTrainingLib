package com.zhiyun88.www.module_main.commonality.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class RankingListBean implements Parcelable {

    private int id;
    private String name;
    private String mobile;
    private String workEmail;
    private int integral;
    private int number;
    private int states;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.workEmail);
        dest.writeInt(this.integral);
        dest.writeInt(this.number);
        dest.writeInt(this.states);
    }

    public RankingListBean() {
    }

    protected RankingListBean(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.mobile = in.readString();
        this.workEmail = in.readString();
        this.integral = in.readInt();
        this.number = in.readInt();
        this.states = in.readInt();
    }

    public static final Creator<RankingListBean> CREATOR = new Creator<RankingListBean>() {
        @Override
        public RankingListBean createFromParcel(Parcel source) {
            return new RankingListBean(source);
        }

        @Override
        public RankingListBean[] newArray(int size) {
            return new RankingListBean[size];
        }
    };
}
