package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:LIENLIN
 * date:2019/1/16
 * 话题详情点赞结果
 */
public class DetailsLikeBean implements Parcelable{
    public int result;
    public int result_type;

    protected DetailsLikeBean(Parcel in) {
        result = in.readInt();
        result_type = in.readInt();
    }

    public static final Creator<DetailsLikeBean> CREATOR = new Creator<DetailsLikeBean>() {
        @Override
        public DetailsLikeBean createFromParcel(Parcel in) {
            return new DetailsLikeBean(in);
        }

        @Override
        public DetailsLikeBean[] newArray(int size) {
            return new DetailsLikeBean[size];
        }
    };

    public void setResult(int result) {
        this.result = result;
    }

    public void setResult_type(int result_type) {
        this.result_type = result_type;
    }

    public int getResult() {

        return result;
    }

    public int getResult_type() {
        return result_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(result);
        dest.writeInt(result_type);
    }
}
