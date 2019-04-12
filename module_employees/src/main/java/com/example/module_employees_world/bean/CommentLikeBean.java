package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author:LIENLIN
 * date:2019/3/29
 * 评论点赞
 */
public class CommentLikeBean implements Serializable {

    /**
     * result : 1
     * result_type : 1
     */
    @SerializedName("result")
    public int result;
    @SerializedName("result_type")
    public int resultType;

    protected CommentLikeBean(Parcel in) {
        result = in.readInt();
        resultType = in.readInt();
    }

}
