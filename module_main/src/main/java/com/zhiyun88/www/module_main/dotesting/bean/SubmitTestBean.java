package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.jungan.www.common_dotest.bean.UserPostData;

import java.util.List;

public class SubmitTestBean implements Parcelable {
    private String report_id;
    private String type;
    private String answer_time;
    private List<UserPostData> answer_data;

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer_time() {
        return answer_time;
    }

    public void setAnswer_time(String answer_time) {
        this.answer_time = answer_time;
    }

    public List<UserPostData> getAnswer_data() {
        return answer_data;
    }

    public void setAnswer_data(List<UserPostData> answer_data) {
        this.answer_data = answer_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.report_id);
        dest.writeString(this.type);
        dest.writeString(this.answer_time);
        dest.writeTypedList(this.answer_data);
    }

    public SubmitTestBean() {
    }

    protected SubmitTestBean(Parcel in) {
        this.report_id = in.readString();
        this.type = in.readString();
        this.answer_time = in.readString();
        this.answer_data = in.createTypedArrayList(UserPostData.CREATOR);
    }

    public static final Creator<SubmitTestBean> CREATOR = new Creator<SubmitTestBean>() {
        @Override
        public SubmitTestBean createFromParcel(Parcel source) {
            return new SubmitTestBean(source);
        }

        @Override
        public SubmitTestBean[] newArray(int size) {
            return new SubmitTestBean[size];
        }
    };
}
