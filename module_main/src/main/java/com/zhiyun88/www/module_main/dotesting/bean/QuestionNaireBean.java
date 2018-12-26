package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestionNaireBean implements Parcelable {
    private String id;
    private String name;
    private String describe;
    private String question_time;
    private String integral;
    private String deleted_at;
    private String created_at;
    private String updated_at;
    private String created_id;
    private String updated_id;
    private String comment;
    private String ques_count;

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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getQuestion_time() {
        return question_time;
    }

    public void setQuestion_time(String question_time) {
        this.question_time = question_time;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_id() {
        return created_id;
    }

    public void setCreated_id(String created_id) {
        this.created_id = created_id;
    }

    public String getUpdated_id() {
        return updated_id;
    }

    public void setUpdated_id(String updated_id) {
        this.updated_id = updated_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getQues_count() {
        return ques_count;
    }

    public void setQues_count(String ques_count) {
        this.ques_count = ques_count;
    }

    public QuestionNaireBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.describe);
        dest.writeString(this.question_time);
        dest.writeString(this.integral);
        dest.writeString(this.deleted_at);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.created_id);
        dest.writeString(this.updated_id);
        dest.writeString(this.comment);
        dest.writeString(this.ques_count);
    }

    protected QuestionNaireBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.describe = in.readString();
        this.question_time = in.readString();
        this.integral = in.readString();
        this.deleted_at = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.created_id = in.readString();
        this.updated_id = in.readString();
        this.comment = in.readString();
        this.ques_count = in.readString();
    }

    public static final Creator<QuestionNaireBean> CREATOR = new Creator<QuestionNaireBean>() {
        @Override
        public QuestionNaireBean createFromParcel(Parcel source) {
            return new QuestionNaireBean(source);
        }

        @Override
        public QuestionNaireBean[] newArray(int size) {
            return new QuestionNaireBean[size];
        }
    };
}
