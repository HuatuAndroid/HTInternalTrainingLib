package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CountBean implements Parcelable {
    private CountInfoData info;
    private List<CuntQuesData> ques_data;

    public CountInfoData getInfo() {
        return info;
    }

    public void setInfo(CountInfoData info) {
        this.info = info;
    }

    public List<CuntQuesData> getQues_data() {
        return ques_data;
    }

    public void setQues_data(List<CuntQuesData> ques_data) {
        this.ques_data = ques_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.info, flags);
        dest.writeTypedList(this.ques_data);
    }

    public CountBean() {
    }

    protected CountBean(Parcel in) {
        this.info = in.readParcelable(CountInfoData.class.getClassLoader());
        this.ques_data = in.createTypedArrayList(CuntQuesData.CREATOR);
    }

    public static final Parcelable.Creator<CountBean> CREATOR = new Parcelable.Creator<CountBean>() {
        @Override
        public CountBean createFromParcel(Parcel source) {
            return new CountBean(source);
        }

        @Override
        public CountBean[] newArray(int size) {
            return new CountBean[size];
        }
    };
}
