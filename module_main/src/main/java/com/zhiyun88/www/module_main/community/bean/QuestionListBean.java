package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionListBean implements Parcelable {
    private int yesterday_count;
    private int today_count;
    private int all_count;

    public int getYesterday_count() {
        return yesterday_count;
    }

    public void setYesterday_count(int yesterday_count) {
        this.yesterday_count = yesterday_count;
    }

    public int getToday_count() {
        return today_count;
    }

    public void setToday_count(int today_count) {
        this.today_count = today_count;
    }

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.yesterday_count);
        dest.writeInt(this.today_count);
        dest.writeInt(this.all_count);
    }

    public QuestionListBean() {
    }

    protected QuestionListBean(Parcel in) {
        this.yesterday_count = in.readInt();
        this.today_count = in.readInt();
        this.all_count = in.readInt();
    }

    public static final Creator<QuestionListBean> CREATOR = new Creator<QuestionListBean>() {
        @Override
        public QuestionListBean createFromParcel(Parcel source) {
            return new QuestionListBean(source);
        }

        @Override
        public QuestionListBean[] newArray(int size) {
            return new QuestionListBean[size];
        }
    };
}
