package com.zhiyun88.www.module_main.main.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class BannerBean implements Parcelable {

    private int id;
    private String title;
    private String link;
    private String url_banner_img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getUrl_banner_img() {
        return url_banner_img;
    }

    public void setUrl_banner_img(String url_banner_img) {
        this.url_banner_img = url_banner_img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.url_banner_img);
    }

    public BannerBean() {
    }

    protected BannerBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.link = in.readString();
        this.url_banner_img = in.readString();
    }

    public static final Parcelable.Creator<BannerBean> CREATOR = new Parcelable.Creator<BannerBean>() {
        @Override
        public BannerBean createFromParcel(Parcel source) {
            return new BannerBean(source);
        }

        @Override
        public BannerBean[] newArray(int size) {
            return new BannerBean[size];
        }
    };
}
