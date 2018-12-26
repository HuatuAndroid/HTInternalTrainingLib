package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CountInfoData implements Parcelable {


    /**
     * id : 1667
     * report_time_long : 292163970
     * ques_count : 10
     * updated_at : 2018-11-16 18:08:11
     * wrong_count : 7
     * right_count : 0
     * point : 0
     * not_count : 9
     * task_id : 184
     * paper_id : 111
     * paper_name : 11.16测试三部分考试试卷；一二部分3题；三部分4题答题时间1分钟
     * correct_rate : 0
     * error_rate : 70
     */

    private String id;
    private String report_time_long;
    private String ques_count;
    private String updated_at;
    private String wrong_count;
    private String right_count;
    private String point;
    private String not_count;
    private String task_id;
    private String paper_id;
    private String paper_name;
    private String correct_rate;
    private String error_rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReport_time_long() {
        return report_time_long;
    }

    public void setReport_time_long(String report_time_long) {
        this.report_time_long = report_time_long;
    }

    public String getQues_count() {
        return ques_count;
    }

    public void setQues_count(String ques_count) {
        this.ques_count = ques_count;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getWrong_count() {
        return wrong_count;
    }

    public void setWrong_count(String wrong_count) {
        this.wrong_count = wrong_count;
    }

    public String getRight_count() {
        return right_count;
    }

    public void setRight_count(String right_count) {
        this.right_count = right_count;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getNot_count() {
        return not_count;
    }

    public void setNot_count(String not_count) {
        this.not_count = not_count;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getPaper_name() {
        return paper_name;
    }

    public void setPaper_name(String paper_name) {
        this.paper_name = paper_name;
    }

    public String getCorrect_rate() {
        return correct_rate;
    }

    public void setCorrect_rate(String correct_rate) {
        this.correct_rate = correct_rate;
    }

    public String getError_rate() {
        return error_rate;
    }

    public void setError_rate(String error_rate) {
        this.error_rate = error_rate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.report_time_long);
        dest.writeString(this.ques_count);
        dest.writeString(this.updated_at);
        dest.writeString(this.wrong_count);
        dest.writeString(this.right_count);
        dest.writeString(this.point);
        dest.writeString(this.not_count);
        dest.writeString(this.task_id);
        dest.writeString(this.paper_id);
        dest.writeString(this.paper_name);
        dest.writeString(this.correct_rate);
        dest.writeString(this.error_rate);
    }

    public CountInfoData() {
    }

    protected CountInfoData(Parcel in) {
        this.id = in.readString();
        this.report_time_long = in.readString();
        this.ques_count = in.readString();
        this.updated_at = in.readString();
        this.wrong_count = in.readString();
        this.right_count = in.readString();
        this.point = in.readString();
        this.not_count = in.readString();
        this.task_id = in.readString();
        this.paper_id = in.readString();
        this.paper_name = in.readString();
        this.correct_rate = in.readString();
        this.error_rate = in.readString();
    }

    public static final Creator<CountInfoData> CREATOR = new Creator<CountInfoData>() {
        @Override
        public CountInfoData createFromParcel(Parcel source) {
            return new CountInfoData(source);
        }

        @Override
        public CountInfoData[] newArray(int size) {
            return new CountInfoData[size];
        }
    };
}
