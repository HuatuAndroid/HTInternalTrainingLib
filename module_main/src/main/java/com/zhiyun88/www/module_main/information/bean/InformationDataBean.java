package com.zhiyun88.www.module_main.information.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class InformationDataBean implements Parcelable {

    private int total;
    private int current_page;
    private List<InformationDataListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public List<InformationDataListBean> getList() {
        return list;
    }

    public void setList(List<InformationDataListBean> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.total);
        dest.writeInt(this.current_page);
        dest.writeList(this.list);
    }

    public InformationDataBean() {
    }

    protected InformationDataBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<InformationDataListBean>();
        in.readList(this.list, InformationDataListBean.class.getClassLoader());
    }

    public static final Creator<InformationDataBean> CREATOR = new Creator<InformationDataBean>() {
        @Override
        public InformationDataBean createFromParcel(Parcel source) {
            return new InformationDataBean(source);
        }

        @Override
        public InformationDataBean[] newArray(int size) {
            return new InformationDataBean[size];
        }
    };
}
