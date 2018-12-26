package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PaperQuesOptionBean implements Parcelable {
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

    public PaperQuesOptionBean() {
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

    protected PaperQuesOptionBean(Parcel in) {
        this.answer = in.readString();
        this.content = in.readString();
        this.ques_id = in.readString();
    }

    public static final Creator<PaperQuesOptionBean> CREATOR = new Creator<PaperQuesOptionBean>() {
        @Override
        public PaperQuesOptionBean createFromParcel(Parcel source) {
            return new PaperQuesOptionBean(source);
        }

        @Override
        public PaperQuesOptionBean[] newArray(int size) {
            return new PaperQuesOptionBean[size];
        }
    };
}
