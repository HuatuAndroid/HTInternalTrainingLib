package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CuntQuesData implements Parcelable {


    /**
     * ques_id : 162
     * is_right : 2
     * ques_type : 1
     * ques_number : 1
     */

    private String ques_id;
    private String is_right;
    private String ques_type;//试题类型:1:单选题 2:多选题  5:问答题
    private String ques_number;

    public String getQues_id() {
        return ques_id;
    }

    public void setQues_id(String ques_id) {
        this.ques_id = ques_id;
    }

    public String getIs_right() {
        return is_right;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public String getQues_type() {
        return ques_type;
    }

    public void setQues_type(String ques_type) {
        this.ques_type = ques_type;
    }

    public String getQues_number() {
        return ques_number;
    }

    public void setQues_number(String ques_number) {
        this.ques_number = ques_number;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ques_id);
        dest.writeString(this.is_right);
        dest.writeString(this.ques_type);
        dest.writeString(this.ques_number);
    }

    public CuntQuesData() {
    }

    protected CuntQuesData(Parcel in) {
        this.ques_id = in.readString();
        this.is_right = in.readString();
        this.ques_type = in.readString();
        this.ques_number = in.readString();
    }

    public static final Creator<CuntQuesData> CREATOR = new Creator<CuntQuesData>() {
        @Override
        public CuntQuesData createFromParcel(Parcel source) {
            return new CuntQuesData(source);
        }

        @Override
        public CuntQuesData[] newArray(int size) {
            return new CuntQuesData[size];
        }
    };
}
