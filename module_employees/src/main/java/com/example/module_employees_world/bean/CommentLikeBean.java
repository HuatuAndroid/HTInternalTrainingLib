package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * author:LIENLIN
 * date:2019/3/29
 * 评论点赞
 */
public class CommentLikeBean implements Parcelable{

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

    public static final Creator<CommentLikeBean> CREATOR = new Creator<CommentLikeBean>() {
        @Override
        public CommentLikeBean createFromParcel(Parcel in) {
            return new CommentLikeBean(in);
        }

        @Override
        public CommentLikeBean[] newArray(int size) {
            return new CommentLikeBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(result);
        dest.writeInt(resultType);
    }
}
