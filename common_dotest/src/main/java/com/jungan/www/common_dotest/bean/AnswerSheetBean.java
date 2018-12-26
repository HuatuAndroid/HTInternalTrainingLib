package com.jungan.www.common_dotest.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class AnswerSheetBean implements Parcelable {
    private String group;
    private List<QuestionBankBean> questionBankBeanList;

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<QuestionBankBean> getQuestionBankBeanList() {
        return questionBankBeanList;
    }

    public void setQuestionBankBeanList(List<QuestionBankBean> questionBankBeanList) {
        this.questionBankBeanList = questionBankBeanList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.group);
        dest.writeTypedList(this.questionBankBeanList);
    }

    public AnswerSheetBean() {
    }

    protected AnswerSheetBean(Parcel in) {
        this.group = in.readString();
        this.questionBankBeanList = in.createTypedArrayList(QuestionBankBean.CREATOR);
    }

    public static final Creator<AnswerSheetBean> CREATOR = new Creator<AnswerSheetBean>() {
        @Override
        public AnswerSheetBean createFromParcel(Parcel source) {
            return new AnswerSheetBean(source);
        }

        @Override
        public AnswerSheetBean[] newArray(int size) {
            return new AnswerSheetBean[size];
        }
    };
}
