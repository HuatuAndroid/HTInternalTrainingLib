package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserMessageCount implements Parcelable {
    private String user_message_count;

    public String getUser_message_count() {
        return user_message_count;
    }

    public void setUser_message_count(String user_message_count) {
        this.user_message_count = user_message_count;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.user_message_count);
    }

    public UserMessageCount() {
    }

    protected UserMessageCount(Parcel in) {
        this.user_message_count = in.readString();
    }

    public static final Parcelable.Creator<UserMessageCount> CREATOR = new Parcelable.Creator<UserMessageCount>() {
        @Override
        public UserMessageCount createFromParcel(Parcel source) {
            return new UserMessageCount(source);
        }

        @Override
        public UserMessageCount[] newArray(int size) {
            return new UserMessageCount[size];
        }
    };
}
