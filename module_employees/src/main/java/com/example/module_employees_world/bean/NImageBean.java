package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author liuzhe
 * @date 2019/3/25
 */
public class NImageBean implements Parcelable

{

    private String path;
    private String ext;
    private String type;
    private String size;
    private String originalName;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public NImageBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path);
        dest.writeString(this.ext);
        dest.writeString(this.type);
        dest.writeString(this.size);
        dest.writeString(this.originalName);
    }

    protected NImageBean(Parcel in) {
        this.path = in.readString();
        this.ext = in.readString();
        this.type = in.readString();
        this.size = in.readString();
        this.originalName = in.readString();
    }

    public static final Parcelable.Creator<NImageBean> CREATOR = new Parcelable.Creator<NImageBean>() {
        @Override
        public NImageBean createFromParcel(Parcel source) {
            return new NImageBean(source);
        }

        @Override
        public NImageBean[] newArray(int size) {
            return new NImageBean[size];
        }
    };
}
