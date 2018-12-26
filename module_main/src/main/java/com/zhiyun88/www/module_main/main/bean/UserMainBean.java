package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class UserMainBean implements Parcelable {
    private int resId;
    private int resName;
    private Class resClass;
    private String path;

    public UserMainBean(int resId, int resName, Class resClass, String path) {
        this.resId = resId;
        this.resName = resName;
        this.resClass = resClass;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserMainBean(int resId, int resName, Class resClass) {
        this.resId = resId;
        this.resName = resName;
        this.resClass = resClass;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public Class getResClass() {
        return resClass;
    }

    public void setResClass(Class resClass) {
        this.resClass = resClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.resId);
        dest.writeInt(this.resName);
        dest.writeSerializable(this.resClass);
        dest.writeString(this.path);
    }

    protected UserMainBean(Parcel in) {
        this.resId = in.readInt();
        this.resName = in.readInt();
        this.resClass = (Class) in.readSerializable();
        this.path = in.readString();
    }

    public static final Creator<UserMainBean> CREATOR = new Creator<UserMainBean>() {
        @Override
        public UserMainBean createFromParcel(Parcel source) {
            return new UserMainBean(source);
        }

        @Override
        public UserMainBean[] newArray(int size) {
            return new UserMainBean[size];
        }
    };
}
