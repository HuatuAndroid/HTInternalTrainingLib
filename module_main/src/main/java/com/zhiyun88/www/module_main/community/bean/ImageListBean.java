package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ImageListBean implements Parcelable {
    private List<ImageBean> list;

    public List<ImageBean> getList() {
        return list;
    }

    public void setList(List<ImageBean> list) {
        this.list = list;
    }

    public ImageListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    protected ImageListBean(Parcel in) {
        this.list = in.createTypedArrayList(ImageBean.CREATOR);
    }

    public static final Creator<ImageListBean> CREATOR = new Creator<ImageListBean>() {
        @Override
        public ImageListBean createFromParcel(Parcel source) {
            return new ImageListBean(source);
        }

        @Override
        public ImageListBean[] newArray(int size) {
            return new ImageListBean[size];
        }
    };
}
