package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AnswerDataBean implements Parcelable {

    private String ques_id;
    private String user_answer;
    private String ques_time;

    public String getQues_id() {
        return ques_id;
    }

    public void setQues_id(String ques_id) {
        this.ques_id = ques_id;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getQues_time() {
        return ques_time;
    }

    public void setQues_time(String ques_time) {
        this.ques_time = ques_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ques_id);
        dest.writeString(this.user_answer);
        dest.writeString(this.ques_time);
    }

    public AnswerDataBean() {
    }

    protected AnswerDataBean(Parcel in) {
        this.ques_id = in.readString();
        this.user_answer = in.readString();
        this.ques_time = in.readString();
    }

    public static final Creator<AnswerDataBean> CREATOR = new Creator<AnswerDataBean>() {
        @Override
        public AnswerDataBean createFromParcel(Parcel source) {
            return new AnswerDataBean(source);
        }

        @Override
        public AnswerDataBean[] newArray(int size) {
            return new AnswerDataBean[size];
        }
    };
}
