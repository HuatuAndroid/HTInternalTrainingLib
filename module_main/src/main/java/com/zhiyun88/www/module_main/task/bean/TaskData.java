package com.zhiyun88.www.module_main.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskData implements Parcelable {

    /**
     * id : 48
     * name : 录播课移动端开发不公开学分60分分分分分分分分分分分分分
     * count : 1
     * type : 4
     * complete : 0
     */
    private String parent_type;
    private String id;
    private String name;
    private String count;
    private String type;
    private String complete;
    private String video_states;
    private String task_states;
    private int again_number;
    private int report_id;
    private int states;
    private String publish_date;
    private int exam_count;

    public int getReport_id() {
        return report_id;
    }

    public void setReport_id(int report_id) {
        this.report_id = report_id;
    }

    public int getExam_count() {
        return exam_count;
    }

    public void setExam_count(int exam_count) {
        this.exam_count = exam_count;
    }

    public String getParent_type() {
        return parent_type;
    }

    public void setParent_type(String parent_type) {
        this.parent_type = parent_type;
    }

    public int getAgain_number() {
        return again_number;
    }

    public void setAgain_number(int again_number) {
        this.again_number = again_number;
    }


    public int getStates() {
        return states;
    }

    public void setStates(int states) {
        this.states = states;
    }

    public String getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public String getTask_states() {
        return task_states;
    }

    public void setTask_states(String task_states) {
        this.task_states = task_states;
    }

    public String getVideo_states() {
        return video_states;
    }

    public void setVideo_states(String video_states) {
        this.video_states = video_states;
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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComplete() {
        return complete;
    }

    public void setComplete(String complete) {
        this.complete = complete;
    }

    public TaskData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parent_type);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.count);
        dest.writeString(this.type);
        dest.writeString(this.complete);
        dest.writeString(this.video_states);
        dest.writeString(this.task_states);
        dest.writeInt(this.again_number);
        dest.writeInt(this.report_id);
        dest.writeInt(this.states);
        dest.writeString(this.publish_date);
        dest.writeInt(this.exam_count);
    }

    protected TaskData(Parcel in) {
        this.parent_type = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.count = in.readString();
        this.type = in.readString();
        this.complete = in.readString();
        this.video_states = in.readString();
        this.task_states = in.readString();
        this.again_number = in.readInt();
        this.report_id = in.readInt();
        this.states = in.readInt();
        this.publish_date = in.readString();
        this.exam_count = in.readInt();
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel source) {
            return new TaskData(source);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };
}
