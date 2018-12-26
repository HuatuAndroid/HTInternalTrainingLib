package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QuesOptionBean implements Parcelable {
    private String answer;
    private String content;
    private String ques_id;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQues_id() {
        return ques_id;
    }

    public void setQues_id(String ques_id) {
        this.ques_id = ques_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.answer);
        dest.writeString(this.content);
        dest.writeString(this.ques_id);
    }

    public QuesOptionBean() {
    }

    protected QuesOptionBean(Parcel in) {
        this.answer = in.readString();
        this.content = in.readString();
        this.ques_id = in.readString();
    }

    public static final Creator<QuesOptionBean> CREATOR = new Creator<QuesOptionBean>() {
        @Override
        public QuesOptionBean createFromParcel(Parcel source) {
            return new QuesOptionBean(source);
        }

        @Override
        public QuesOptionBean[] newArray(int size) {
            return new QuesOptionBean[size];
        }
    };
}
