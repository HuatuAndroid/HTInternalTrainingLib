package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/25
 */
public class NImageListsBean implements Serializable {

    private List<NImageBean> list;

    public List<NImageBean> getList() {
        return list;
    }

    public void setList(List<NImageBean> list) {
        this.list = list;
    }

    public NImageListsBean() {
    }

}
