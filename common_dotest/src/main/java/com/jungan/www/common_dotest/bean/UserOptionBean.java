package com.jungan.www.common_dotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserOptionBean implements Parcelable {
    private String optionName;
    private String optionContext;

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getOptionContext() {
        return optionContext;
    }

    public void setOptionContext(String optionContext) {
        this.optionContext = optionContext;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.optionName);
        dest.writeString(this.optionContext);
    }

    public UserOptionBean() {
    }

    protected UserOptionBean(Parcel in) {
        this.optionName = in.readString();
        this.optionContext = in.readString();
    }

    public static final Creator<UserOptionBean> CREATOR = new Creator<UserOptionBean>() {
        @Override
        public UserOptionBean createFromParcel(Parcel source) {
            return new UserOptionBean(source);
        }

        @Override
        public UserOptionBean[] newArray(int size) {
            return new UserOptionBean[size];
        }
    };
}
