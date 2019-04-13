package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyPartBean implements Serializable {

    private int total;
    private int current_page;
    private List<MyPartListBean> list;

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

    public List<MyPartListBean> getList() {
        return list;
    }

    public void setList(List<MyPartListBean> list) {
        this.list = list;
    }

    public MyPartBean() {
    }

    protected MyPartBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyPartListBean>();
        in.readList(this.list, MyPartListBean.class.getClassLoader());
    }

}
