package com.zhiyun88.www.module_main.train.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TrainListBean implements Parcelable {
    private List<TrainListData> list;

    public List<TrainListData> getList() {
        return list;
    }

    public void setList(List<TrainListData> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public TrainListBean() {
    }

    protected TrainListBean(Parcel in) {
        this.list = in.createTypedArrayList(TrainListData.CREATOR);
    }

    public static final Parcelable.Creator<TrainListBean> CREATOR = new Parcelable.Creator<TrainListBean>() {
        @Override
        public TrainListBean createFromParcel(Parcel source) {
            return new TrainListBean(source);
        }

        @Override
        public TrainListBean[] newArray(int size) {
            return new TrainListBean[size];
        }
    };
}
