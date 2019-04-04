package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author:LIENLIN
 * date:2019/4/3
 * 添加
 */
public class CommentInsertBean implements Parcelable{
    @SerializedName("first")
    public CommentListBean.ListBean first;
    @SerializedName("second")
    public ParentBean second;

    protected CommentInsertBean(Parcel in) {
        first = in.readParcelable(CommentListBean.ListBean.class.getClassLoader());
        second = in.readParcelable(ParentBean.class.getClassLoader());
    }

    public static final Creator<CommentInsertBean> CREATOR = new Creator<CommentInsertBean>() {
        @Override
        public CommentInsertBean createFromParcel(Parcel in) {
            return new CommentInsertBean(in);
        }

        @Override
        public CommentInsertBean[] newArray(int size) {
            return new CommentInsertBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(first, flags);
        dest.writeParcelable(second, flags);
    }
}
