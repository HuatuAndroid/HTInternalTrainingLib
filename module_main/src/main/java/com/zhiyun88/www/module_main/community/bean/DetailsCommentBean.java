package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class DetailsCommentBean implements Parcelable {

    private int total;
    private int current_page;
    private List<DetailsCommentListBean> list;

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

    public List<DetailsCommentListBean> getList() {
        return list;
    }

    public void setList(List<DetailsCommentListBean> list) {
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

    public DetailsCommentBean() {
    }

    protected DetailsCommentBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<DetailsCommentListBean>();
        in.readList(this.list, DetailsCommentListBean.class.getClassLoader());
    }

    public static final Creator<DetailsCommentBean> CREATOR = new Creator<DetailsCommentBean>() {
        @Override
        public DetailsCommentBean createFromParcel(Parcel source) {
            return new DetailsCommentBean(source);
        }

        @Override
        public DetailsCommentBean[] newArray(int size) {
            return new DetailsCommentBean[size];
        }
    };
}
