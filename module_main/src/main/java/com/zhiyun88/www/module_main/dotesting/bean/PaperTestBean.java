package com.zhiyun88.www.module_main.dotesting.bean;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaperTestBean implements Parcelable {

    private String paper_id;
    private String task_id;
    private PaperListBean paper_list;
    private List<PaperModuleBean> module;

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public PaperListBean getPaper_list() {
        return paper_list;
    }

    public void setPaper_list(PaperListBean paper_list) {
        this.paper_list = paper_list;
    }

    public List<PaperModuleBean> getModule() {
        return module;
    }

    public void setModule(List<PaperModuleBean> module) {
        this.module = module;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.paper_id);
        dest.writeString(this.task_id);
        dest.writeParcelable(this.paper_list, flags);
        dest.writeTypedList(this.module);
    }

    public PaperTestBean() {
    }

    protected PaperTestBean(Parcel in) {
        this.paper_id = in.readString();
        this.task_id = in.readString();
        this.paper_list = in.readParcelable(PaperListBean.class.getClassLoader());
        this.module = in.createTypedArrayList(PaperModuleBean.CREATOR);
    }

    public static final Creator<PaperTestBean> CREATOR = new Creator<PaperTestBean>() {
        @Override
        public PaperTestBean createFromParcel(Parcel source) {
            return new PaperTestBean(source);
        }

        @Override
        public PaperTestBean[] newArray(int size) {
            return new PaperTestBean[size];
        }
    };
}
