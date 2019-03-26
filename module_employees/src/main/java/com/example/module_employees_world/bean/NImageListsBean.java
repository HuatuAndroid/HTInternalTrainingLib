package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/25
 */
public class NImageListsBean implements Parcelable{

    private List<NImageBean> list;

    public List<NImageBean> getList() {
        return list;
    }

    public void setList(List<NImageBean> list) {
        this.list = list;
    }

    public NImageListsBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected NImageListsBean(Parcel in) {
        this.list = in.createTypedArrayList(NImageBean.CREATOR);
    }

    public static final Parcelable.Creator<NImageListsBean> CREATOR = new Parcelable.Creator<NImageListsBean>() {
        @Override
        public NImageListsBean createFromParcel(Parcel source) {
            return new NImageListsBean(source);
        }

        @Override
        public NImageListsBean[] newArray(int size) {
            return new NImageListsBean[size];
        }
    };
}
