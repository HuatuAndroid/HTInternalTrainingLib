package com.zhiyun88.www.module_main.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class TaskInfoListBean implements Parcelable {
    private TaskInfoBean task_info;
    private List<TaskData> task_data;

    public TaskInfoBean getTask_info() {
        return task_info;
    }

    public void setTask_info(TaskInfoBean task_info) {
        this.task_info = task_info;
    }

    public List<TaskData> getTask_data() {
        return task_data;
    }

    public void setTask_data(List<TaskData> task_data) {
        this.task_data = task_data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.task_info, flags);
        dest.writeTypedList(this.task_data);
    }

    public TaskInfoListBean() {
    }

    protected TaskInfoListBean(Parcel in) {
        this.task_info = in.readParcelable(TaskInfoBean.class.getClassLoader());
        this.task_data = in.createTypedArrayList(TaskData.CREATOR);
    }

    public static final Parcelable.Creator<TaskInfoListBean> CREATOR = new Parcelable.Creator<TaskInfoListBean>() {
        @Override
        public TaskInfoListBean createFromParcel(Parcel source) {
            return new TaskInfoListBean(source);
        }

        @Override
        public TaskInfoListBean[] newArray(int size) {
            return new TaskInfoListBean[size];
        }
    };
}
