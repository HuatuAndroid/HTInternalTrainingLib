package com.zhiyun88.www.module_main.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TaskInfoBean implements Parcelable {

    /**
     * id : 4
     * name : 培训任务1231
     * abstract : 任务简介:培训任务123456789
     * type : 2
     * start_date : 2018-09-27 14:37:08
     * end_date : 2018-09-27 14:37:08
     * complete : 20
     * task_start : 3
     */

    private String id;
    private String name;
    @SerializedName("abstract")
    private String abstractX;
    private String type;
    private String start_date;
    private String end_date;
    private float complete;
    private String task_states;

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

    public String getAbstractX() {
        return abstractX;
    }

    public void setAbstractX(String abstractX) {
        this.abstractX = abstractX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public float getComplete() {
        return complete;
    }

    public void setComplete(float complete) {
        this.complete = complete;
    }

    public String getTask_states() {
        return task_states;
    }

    public void setTask_states(String task_states) {
        this.task_states = task_states;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.abstractX);
        dest.writeString(this.type);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeFloat(this.complete);
        dest.writeString(this.task_states);
    }

    public TaskInfoBean() {
    }

    protected TaskInfoBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.abstractX = in.readString();
        this.type = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.complete = in.readFloat();
        this.task_states = in.readString();
    }

    public static final Parcelable.Creator<TaskInfoBean> CREATOR = new Parcelable.Creator<TaskInfoBean>() {
        @Override
        public TaskInfoBean createFromParcel(Parcel source) {
            return new TaskInfoBean(source);
        }

        @Override
        public TaskInfoBean[] newArray(int size) {
            return new TaskInfoBean[size];
        }
    };
}
