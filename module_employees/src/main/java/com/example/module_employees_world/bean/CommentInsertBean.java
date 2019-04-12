package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author:LIENLIN
 * date:2019/4/3
 * 添加
 */
public class CommentInsertBean implements Serializable {
    @SerializedName("first")
    public CommentListBean.ListBean first;
    @SerializedName("second")
    public ParentBean second;



}
