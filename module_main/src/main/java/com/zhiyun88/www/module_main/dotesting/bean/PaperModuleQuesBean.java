package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaperModuleQuesBean implements Parcelable {
    private String id;
    private String ques_type;
    private String ques_stem;
    private String right_answer;
    private String ques_difficulty;
    private String question_score;
    private String report_id;
    private String ques_number;
    private String ques_analysis;
    private String user_answer;

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getQues_analysis() {
        return ques_analysis;
    }

    public void setQues_analysis(String ques_analysis) {
        this.ques_analysis = ques_analysis;
    }

    private List<PaperQuesOptionBean> ques_option;

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

    public String getQues_difficulty() {
        return ques_difficulty;
    }

    public void setQues_difficulty(String ques_difficulty) {
        this.ques_difficulty = ques_difficulty;
    }

    public String getQuestion_score() {
        return question_score;
    }

    public void setQuestion_score(String question_score) {
        this.question_score = question_score;
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

    public List<PaperQuesOptionBean> getQues_option() {
        return ques_option;
    }

    public void setQues_option(List<PaperQuesOptionBean> ques_option) {
        this.ques_option = ques_option;
    }

    public PaperModuleQuesBean() {
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
        dest.writeString(this.ques_difficulty);
        dest.writeString(this.question_score);
        dest.writeString(this.report_id);
        dest.writeString(this.ques_number);
        dest.writeString(this.ques_analysis);
        dest.writeString(this.user_answer);
        dest.writeTypedList(this.ques_option);
    }

    protected PaperModuleQuesBean(Parcel in) {
        this.id = in.readString();
        this.ques_type = in.readString();
        this.ques_stem = in.readString();
        this.right_answer = in.readString();
        this.ques_difficulty = in.readString();
        this.question_score = in.readString();
        this.report_id = in.readString();
        this.ques_number = in.readString();
        this.ques_analysis = in.readString();
        this.user_answer = in.readString();
        this.ques_option = in.createTypedArrayList(PaperQuesOptionBean.CREATOR);
    }

    public static final Creator<PaperModuleQuesBean> CREATOR = new Creator<PaperModuleQuesBean>() {
        @Override
        public PaperModuleQuesBean createFromParcel(Parcel source) {
            return new PaperModuleQuesBean(source);
        }

        @Override
        public PaperModuleQuesBean[] newArray(int size) {
            return new PaperModuleQuesBean[size];
        }
    };
}
