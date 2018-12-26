package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CountNumberBean implements Parcelable {

    private int dwc_number;
    private int ywc_number;
    private int rwyq_number;

    public int getDwc_number() {
        return dwc_number;
    }

    public void setDwc_number(int dwc_number) {
        this.dwc_number = dwc_number;
    }

    public int getYwc_number() {
        return ywc_number;
    }

    public void setYwc_number(int ywc_number) {
        this.ywc_number = ywc_number;
    }

    public int getRwyq_number() {
        return rwyq_number;
    }

    public void setRwyq_number(int rwyq_number) {
        this.rwyq_number = rwyq_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.dwc_number);
        dest.writeInt(this.ywc_number);
        dest.writeInt(this.rwyq_number);
    }

    public CountNumberBean() {
    }

    protected CountNumberBean(Parcel in) {
        this.dwc_number = in.readInt();
        this.ywc_number = in.readInt();
        this.rwyq_number = in.readInt();
    }

    public static final Parcelable.Creator<CountNumberBean> CREATOR = new Parcelable.Creator<CountNumberBean>() {
        @Override
        public CountNumberBean createFromParcel(Parcel source) {
            return new CountNumberBean(source);
        }

        @Override
        public CountNumberBean[] newArray(int size) {
            return new CountNumberBean[size];
        }
    };
}
