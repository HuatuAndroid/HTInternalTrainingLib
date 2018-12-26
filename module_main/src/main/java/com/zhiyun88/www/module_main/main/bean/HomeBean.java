package com.zhiyun88.www.module_main.main.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class HomeBean implements Parcelable {

    private List<BannerBean> banner;
    private List<HomeCourseBean> course;
    private List<HomeTransformerBean> transformer;
    private List<HomeInformationBean> information;

    public List<BannerBean> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerBean> banner) {
        this.banner = banner;
    }

    public List<HomeCourseBean> getCourse() {
        return course;
    }

    public void setCourse(List<HomeCourseBean> course) {
        this.course = course;
    }

    public List<HomeTransformerBean> getTransformer() {
        return transformer;
    }

    public void setTransformer(List<HomeTransformerBean> transformer) {
        this.transformer = transformer;
    }

    public List<HomeInformationBean> getInformation() {
        return information;
    }

    public void setInformation(List<HomeInformationBean> information) {
        this.information = information;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.banner);
        dest.writeTypedList(this.course);
        dest.writeTypedList(this.transformer);
        dest.writeTypedList(this.information);
    }

    public HomeBean() {
    }

    protected HomeBean(Parcel in) {
        this.banner = in.createTypedArrayList(BannerBean.CREATOR);
        this.course = in.createTypedArrayList(HomeCourseBean.CREATOR);
        this.transformer = in.createTypedArrayList(HomeTransformerBean.CREATOR);
        this.information = in.createTypedArrayList(HomeInformationBean.CREATOR);
    }

    public static final Creator<HomeBean> CREATOR = new Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel source) {
            return new HomeBean(source);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };
}
