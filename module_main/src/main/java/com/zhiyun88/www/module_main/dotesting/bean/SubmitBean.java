package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baijia.player.playback.mocklive.PBRoomImpl;

public class SubmitBean implements Parcelable {
    private String type;
    private String report_id;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public SubmitBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.report_id);
        dest.writeString(this.msg);
    }

    protected SubmitBean(Parcel in) {
        this.type = in.readString();
        this.report_id = in.readString();
        this.msg = in.readString();
    }

    public static final Creator<SubmitBean> CREATOR = new Creator<SubmitBean>() {
        @Override
        public SubmitBean createFromParcel(Parcel source) {
            return new SubmitBean(source);
        }

        @Override
        public SubmitBean[] newArray(int size) {
            return new SubmitBean[size];
        }
    };
}
