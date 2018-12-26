package com.zhiyun88.www.module_main.information.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class InformationTypeBean implements Parcelable {

    private List<InformationTypeListBean> list;

    public List<InformationTypeListBean> getList() {
        return list;
    }

    public void setList(List<InformationTypeListBean> list) {
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

    public InformationTypeBean() {
    }

    protected InformationTypeBean(Parcel in) {
        this.list = new ArrayList<InformationTypeListBean>();
        in.readList(this.list, InformationTypeListBean.class.getClassLoader());
    }

    public static final Creator<InformationTypeBean> CREATOR = new Creator<InformationTypeBean>() {
        @Override
        public InformationTypeBean createFromParcel(Parcel source) {
            return new InformationTypeBean(source);
        }

        @Override
        public InformationTypeBean[] newArray(int size) {
            return new InformationTypeBean[size];
        }
    };
}
