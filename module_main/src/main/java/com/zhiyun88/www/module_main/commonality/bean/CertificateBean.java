package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CertificateBean implements Parcelable {

    private int total;
    private int current_page;
    private List<CertificateListBean> list;

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

    public List<CertificateListBean> getList() {
        return list;
    }

    public void setList(List<CertificateListBean> list) {
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

    public CertificateBean() {
    }

    protected CertificateBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<CertificateListBean>();
        in.readList(this.list, CertificateListBean.class.getClassLoader());
    }

    public static final Creator<CertificateBean> CREATOR = new Creator<CertificateBean>() {
        @Override
        public CertificateBean createFromParcel(Parcel source) {
            return new CertificateBean(source);
        }

        @Override
        public CertificateBean[] newArray(int size) {
            return new CertificateBean[size];
        }
    };
}
