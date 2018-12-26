package com.zhiyun88.www.module_main.api;

import android.os.Parcel;
import android.os.Parcelable;

public class AppBean implements Parcelable {

    /**
     * id : 1
     * remember_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC90ZXN0LXB4Lmh1YXR1LmNvbVwvYXBpXC9hcHBcL2dldEFwcFRva2VuIiwiaWF0IjoxNTQwNDU3MTEyLCJleHAiOjE1NDA3NTk1MTIsIm5iZiI6MTU0MDQ1NzExMiwianRpIjoiaUlNQklsbGRIVE5XSk5ReCIsInN1YiI6MSwicHJ2IjoiYjkxMjc5OTc4ZjExYWE3YmM1NjcwNDg3ZmZmMDFlMjI4MjUzZmU0OCJ9.8GHJXQT-LhM4DFUIQQUOi7PEQFqubjfmB7Zsiv7TRhY
     */

    private String id;
    private String remember_token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemember_token() {
        return remember_token;
    }

    public void setRemember_token(String remember_token) {
        this.remember_token = remember_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.remember_token);
    }

    public AppBean() {
    }

    protected AppBean(Parcel in) {
        this.id = in.readString();
        this.remember_token = in.readString();
    }

    public static final Parcelable.Creator<AppBean> CREATOR = new Parcelable.Creator<AppBean>() {
        @Override
        public AppBean createFromParcel(Parcel source) {
            return new AppBean(source);
        }

        @Override
        public AppBean[] newArray(int size) {
            return new AppBean[size];
        }
    };
}
