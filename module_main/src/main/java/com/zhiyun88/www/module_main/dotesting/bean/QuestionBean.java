package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionBean implements Parcelable {

    private String id;
    private String ques_type;
    private String ques_stem;
    private String right_answer;
    private String score;
    private String sort;
    private String report_id;
    private String ques_number;
    private List<QuesOptionBean> ques_option;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQues_type() {
        return ques_type;
    }

    public void setQues_type(String ques_type) {
        this.ques_type = ques_type;
    }

    public String getQues_stem() {
        return ques_stem;
    }

    public void setQues_stem(String ques_stem) {
        this.ques_stem = ques_stem;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getQues_number() {
        return ques_number;
    }

    public void setQues_number(String ques_number) {
        this.ques_number = ques_number;
    }

    public List<QuesOptionBean> getQues_option() {
        return ques_option;
    }

    public void setQues_option(List<QuesOptionBean> ques_option) {
        this.ques_option = ques_option;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.ques_type);
        dest.writeString(this.ques_stem);
        dest.writeString(this.right_answer);
        dest.writeString(this.score);
        dest.writeString(this.sort);
        dest.writeString(this.report_id);
        dest.writeString(this.ques_number);
        dest.writeTypedList(this.ques_option);
    }

    public QuestionBean() {
    }

    protected QuestionBean(Parcel in) {
        this.id = in.readString();
        this.ques_type = in.readString();
        this.ques_stem = in.readString();
        this.right_answer = in.readString();
        this.score = in.readString();
        this.sort = in.readString();
        this.report_id = in.readString();
        this.ques_number = in.readString();
        this.ques_option = in.createTypedArrayList(QuesOptionBean.CREATOR);
    }

    public static final Creator<QuestionBean> CREATOR = new Creator<QuestionBean>() {
        @Override
        public QuestionBean createFromParcel(Parcel source) {
            return new QuestionBean(source);
        }

        @Override
        public QuestionBean[] newArray(int size) {
            return new QuestionBean[size];
        }
    };
}
