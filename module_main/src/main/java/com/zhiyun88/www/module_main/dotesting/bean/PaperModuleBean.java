package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaperModuleBean implements Parcelable {
    private String id;
    private String paper_id;
    private String name;
    private String paper_part_info;
    private String serial_number;
    private List<PaperModuleQuesBean> module_question;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(String paper_id) {
        this.paper_id = paper_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPaper_part_info() {
        return paper_part_info;
    }

    public void setPaper_part_info(String paper_part_info) {
        this.paper_part_info = paper_part_info;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public List<PaperModuleQuesBean> getModule_question() {
        return module_question;
    }

    public void setModule_question(List<PaperModuleQuesBean> module_question) {
        this.module_question = module_question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.paper_id);
        dest.writeString(this.name);
        dest.writeString(this.paper_part_info);
        dest.writeString(this.serial_number);
        dest.writeTypedList(this.module_question);
    }

    public PaperModuleBean() {
    }

    protected PaperModuleBean(Parcel in) {
        this.id = in.readString();
        this.paper_id = in.readString();
        this.name = in.readString();
        this.paper_part_info = in.readString();
        this.serial_number = in.readString();
        this.module_question = in.createTypedArrayList(PaperModuleQuesBean.CREATOR);
    }

    public static final Creator<PaperModuleBean> CREATOR = new Creator<PaperModuleBean>() {
        @Override
        public PaperModuleBean createFromParcel(Parcel source) {
            return new PaperModuleBean(source);
        }

        @Override
        public PaperModuleBean[] newArray(int size) {
            return new PaperModuleBean[size];
        }
    };
}
