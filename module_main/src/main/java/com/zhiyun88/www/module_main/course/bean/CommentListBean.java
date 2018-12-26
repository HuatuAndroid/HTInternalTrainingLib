package com.zhiyun88.www.module_main.course.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommentListBean implements Parcelable {
    private List<CommentListData> list;

    public List<CommentListData> getList() {
        return list;
    }

    public void setList(List<CommentListData> list) {
        this.list = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.list);
    }

    public CommentListBean() {
    }

    protected CommentListBean(Parcel in) {
        this.list = in.createTypedArrayList(CommentListData.CREATOR);
    }

    public static final Creator<CommentListBean> CREATOR = new Creator<CommentListBean>() {
        @Override
        public CommentListBean createFromParcel(Parcel source) {
            return new CommentListBean(source);
        }

        @Override
        public CommentListBean[] newArray(int size) {
            return new CommentListBean[size];
        }
    };
}
