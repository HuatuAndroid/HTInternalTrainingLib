package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchListBean implements Parcelable {

    private String id;
    private String title;
    private String type;
    private int classify_id;
    private String cover;
    private int hour_num;
    private int study_num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getHour_num() {
        return hour_num;
    }

    public void setHour_num(int hour_num) {
        this.hour_num = hour_num;
    }

    public int getStudy_num() {
        return study_num;
    }

    public void setStudy_num(int study_num) {
        this.study_num = study_num;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.type);
        dest.writeInt(this.classify_id);
        dest.writeString(this.cover);
        dest.writeInt(this.hour_num);
        dest.writeInt(this.study_num);
    }

    public SearchListBean() {
    }

    protected SearchListBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.type = in.readString();
        this.classify_id = in.readInt();
        this.cover = in.readString();
        this.hour_num = in.readInt();
        this.study_num = in.readInt();
    }

    public static final Parcelable.Creator<SearchListBean> CREATOR = new Parcelable.Creator<SearchListBean>() {
        @Override
        public SearchListBean createFromParcel(Parcel source) {
            return new SearchListBean(source);
        }

        @Override
        public SearchListBean[] newArray(int size) {
            return new SearchListBean[size];
        }
    };
}
