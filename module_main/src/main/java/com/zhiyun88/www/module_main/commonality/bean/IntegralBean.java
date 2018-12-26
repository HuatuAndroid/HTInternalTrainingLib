package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class IntegralBean implements Parcelable {

    private String integral;
    private String n_get_integral;
    private String y_get_integral;
    private String ranking;

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getN_get_integral() {
        return n_get_integral;
    }

    public void setN_get_integral(String n_get_integral) {
        this.n_get_integral = n_get_integral;
    }

    public String getY_get_integral() {
        return y_get_integral;
    }

    public void setY_get_integral(String y_get_integral) {
        this.y_get_integral = y_get_integral;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.integral);
        dest.writeString(this.n_get_integral);
        dest.writeString(this.y_get_integral);
        dest.writeString(this.ranking);
    }

    public IntegralBean() {
    }

    protected IntegralBean(Parcel in) {
        this.integral = in.readString();
        this.n_get_integral = in.readString();
        this.y_get_integral = in.readString();
        this.ranking = in.readString();
    }

    public static final Creator<IntegralBean> CREATOR = new Creator<IntegralBean>() {
        @Override
        public IntegralBean createFromParcel(Parcel source) {
            return new IntegralBean(source);
        }

        @Override
        public IntegralBean[] newArray(int size) {
            return new IntegralBean[size];
        }
    };
}
