package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class PaperListBean implements Parcelable {
    private String id;
    private String name;
    private String year;
    private String paper_score;
    private String paper_classify;
    private String paper_ques_classify;
    private String created_id;
    private String created_at;
    private String updated_id;
    private String updated_at;
    private String sort;
    private String question_pass;
    private String question_time;
    private String is_permanent;
    private String start_time;
    private String end_time;
    private String exam_explain;
    private String states;
    private String deleted_at;
    private String ques_count;
    private String ques_analysis;

    public String getQues_analysis() {
        return ques_analysis;
    }

    public void setQues_analysis(String ques_analysis) {
        this.ques_analysis = ques_analysis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPaper_score() {
        return paper_score;
    }

    public void setPaper_score(String paper_score) {
        this.paper_score = paper_score;
    }

    public String getPaper_classify() {
        return paper_classify;
    }

    public void setPaper_classify(String paper_classify) {
        this.paper_classify = paper_classify;
    }

    public String getPaper_ques_classify() {
        return paper_ques_classify;
    }

    public void setPaper_ques_classify(String paper_ques_classify) {
        this.paper_ques_classify = paper_ques_classify;
    }

    public String getCreated_id() {
        return created_id;
    }

    public void setCreated_id(String created_id) {
        this.created_id = created_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_id() {
        return updated_id;
    }

    public void setUpdated_id(String updated_id) {
        this.updated_id = updated_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getQuestion_pass() {
        return question_pass;
    }

    public void setQuestion_pass(String question_pass) {
        this.question_pass = question_pass;
    }

    public String getQuestion_time() {
        return question_time;
    }

    public void setQuestion_time(String question_time) {
        this.question_time = question_time;
    }

    public String getIs_permanent() {
        return is_permanent;
    }

    public void setIs_permanent(String is_permanent) {
        this.is_permanent = is_permanent;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getExam_explain() {
        return exam_explain;
    }

    public void setExam_explain(String exam_explain) {
        this.exam_explain = exam_explain;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getQues_count() {
        return ques_count;
    }

    public void setQues_count(String ques_count) {
        this.ques_count = ques_count;
    }

    public PaperListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.year);
        dest.writeString(this.paper_score);
        dest.writeString(this.paper_classify);
        dest.writeString(this.paper_ques_classify);
        dest.writeString(this.created_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_id);
        dest.writeString(this.updated_at);
        dest.writeString(this.sort);
        dest.writeString(this.question_pass);
        dest.writeString(this.question_time);
        dest.writeString(this.is_permanent);
        dest.writeString(this.start_time);
        dest.writeString(this.end_time);
        dest.writeString(this.exam_explain);
        dest.writeString(this.states);
        dest.writeString(this.deleted_at);
        dest.writeString(this.ques_count);
        dest.writeString(this.ques_analysis);
    }

    protected PaperListBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.year = in.readString();
        this.paper_score = in.readString();
        this.paper_classify = in.readString();
        this.paper_ques_classify = in.readString();
        this.created_id = in.readString();
        this.created_at = in.readString();
        this.updated_id = in.readString();
        this.updated_at = in.readString();
        this.sort = in.readString();
        this.question_pass = in.readString();
        this.question_time = in.readString();
        this.is_permanent = in.readString();
        this.start_time = in.readString();
        this.end_time = in.readString();
        this.exam_explain = in.readString();
        this.states = in.readString();
        this.deleted_at = in.readString();
        this.ques_count = in.readString();
        this.ques_analysis = in.readString();
    }

    public static final Creator<PaperListBean> CREATOR = new Creator<PaperListBean>() {
        @Override
        public PaperListBean createFromParcel(Parcel source) {
            return new PaperListBean(source);
        }

        @Override
        public PaperListBean[] newArray(int size) {
            return new PaperListBean[size];
        }
    };
}
