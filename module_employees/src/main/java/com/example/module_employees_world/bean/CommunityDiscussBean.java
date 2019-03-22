package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class CommunityDiscussBean implements Parcelable {

    private int total;
    private int current_page;
    private List<DiscussListBean> list;

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

    public List<DiscussListBean> getList() {
        return list;
    }

    public void setList(List<DiscussListBean> list) {
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

    public CommunityDiscussBean() {
    }

    protected CommunityDiscussBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<DiscussListBean>();
        in.readList(this.list, DiscussListBean.class.getClassLoader());
    }

    public static final Creator<CommunityDiscussBean> CREATOR = new Creator<CommunityDiscussBean>() {
        @Override
        public CommunityDiscussBean createFromParcel(Parcel source) {
            return new CommunityDiscussBean(source);
        }

        @Override
        public CommunityDiscussBean[] newArray(int size) {
            return new CommunityDiscussBean[size];
        }
    };
}
