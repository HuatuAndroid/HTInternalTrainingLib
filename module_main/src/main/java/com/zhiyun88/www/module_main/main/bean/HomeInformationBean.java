package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeInformationBean implements Parcelable {

    private String id;
    private String title;
    private String cover;
    private String created_at;
    private String click_rate;
    private String comment_count;
    private String url;

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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getClick_rate() {
        return click_rate;
    }

    public void setClick_rate(String click_rate) {
        this.click_rate = click_rate;
    }

    public String getComment_count() {
        return comment_count;
    }

    public void setComment_count(String comment_count) {
        this.comment_count = comment_count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HomeInformationBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.cover);
        dest.writeString(this.created_at);
        dest.writeString(this.click_rate);
        dest.writeString(this.comment_count);
        dest.writeString(this.url);
    }

    protected HomeInformationBean(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.cover = in.readString();
        this.created_at = in.readString();
        this.click_rate = in.readString();
        this.comment_count = in.readString();
        this.url = in.readString();
    }

    public static final Creator<HomeInformationBean> CREATOR = new Creator<HomeInformationBean>() {
        @Override
        public HomeInformationBean createFromParcel(Parcel source) {
            return new HomeInformationBean(source);
        }

        @Override
        public HomeInformationBean[] newArray(int size) {
            return new HomeInformationBean[size];
        }
    };
}
