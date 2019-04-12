package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyItemBean implements Serializable {

    private int total;
    private int current_page;
    private List<MyItemListBean> list;

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

    public List<MyItemListBean> getList() {
        return list;
    }

    public void setList(List<MyItemListBean> list) {
        this.list = list;
    }


    public MyItemBean() {
    }

    protected MyItemBean(Parcel in) {
        this.total = in.readInt();
        this.current_page = in.readInt();
        this.list = new ArrayList<MyItemListBean>();
        in.readList(this.list, MyItemListBean.class.getClassLoader());
    }

}
