package com.jungan.www.common_dotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class UserPostData implements Serializable, Parcelable {
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

    public String getUser_time() {
        return ques_time;
    }

    public void setUser_time(String user_time) {
        this.ques_time = user_time;
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

    public UserPostData() {
    }

    protected UserPostData(Parcel in) {
        this.ques_id = in.readString();
        this.user_answer = in.readString();
        this.ques_time = in.readString();
    }

    public static final Parcelable.Creator<UserPostData> CREATOR = new Parcelable.Creator<UserPostData>() {
        @Override
        public UserPostData createFromParcel(Parcel source) {
            return new UserPostData(source);
        }

        @Override
        public UserPostData[] newArray(int size) {
            return new UserPostData[size];
        }
    };

    @Override
    public String toString() {
        return "UserPostData{" +
                "ques_id='" + ques_id + '\'' +
                ", user_answer='" + user_answer + '\'' +
                ", user_time='" + ques_time + '\'' +
                '}';
    }
}
