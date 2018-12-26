package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class WjBean implements Parcelable {
    private String point;
    private String  comment;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getV() {
        return comment;
    }

    public void setV(String v) {
        this.comment = v;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.point);
        dest.writeString(this.comment);
    }

    public WjBean() {
    }

    protected WjBean(Parcel in) {
        this.point = in.readString();
        this.comment = in.readString();
    }

    public static final Parcelable.Creator<WjBean> CREATOR = new Parcelable.Creator<WjBean>() {
        @Override
        public WjBean createFromParcel(Parcel source) {
            return new WjBean(source);
        }

        @Override
        public WjBean[] newArray(int size) {
            return new WjBean[size];
        }
    };
}
