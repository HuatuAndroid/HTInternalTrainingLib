package com.zhiyun88.www.module_main.commonality.bean;


import java.util.List;

public class MyLibraryBean {

    private int total;
    private int current_page;
    private List<MyLibraryListBean> list;

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

    public List<MyLibraryListBean> getList() {
        return list;
    }

    public void setList(List<MyLibraryListBean> list) {
        this.list = list;
    }
}
