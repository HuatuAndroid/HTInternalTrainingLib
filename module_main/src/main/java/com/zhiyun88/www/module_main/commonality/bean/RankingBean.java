package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class RankingBean implements Parcelable {
    private List<RankingListBean> list;

    public List<RankingListBean> getList() {
        return list;
    }

    public void setList(List<RankingListBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.list);
    }

    public RankingBean() {
    }

    protected RankingBean(Parcel in) {
        this.list = new ArrayList<RankingListBean>();
        in.readList(this.list, RankingListBean.class.getClassLoader());
    }

    public static final Creator<RankingBean> CREATOR = new Creator<RankingBean>() {
        @Override
        public RankingBean createFromParcel(Parcel source) {
            return new RankingBean(source);
        }

        @Override
        public RankingBean[] newArray(int size) {
            return new RankingBean[size];
        }
    };
}
