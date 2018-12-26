package com.zhiyun88.www.module_main.community.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CommunityDetailsBean implements Parcelable {

    private QuestionInfoBean question_info;
    private List<RecommendListBean> recommend_list;

    public QuestionInfoBean getQuestion_info() {
        return question_info;
    }

    public void setQuestion_info(QuestionInfoBean question_info) {
        this.question_info = question_info;
    }

    public List<RecommendListBean> getRecommend_list() {
        return recommend_list;
    }

    public void setRecommend_list(List<RecommendListBean> recommend_list) {
        this.recommend_list = recommend_list;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.question_info, flags);
        dest.writeTypedList(this.recommend_list);
    }

    public CommunityDetailsBean() {
    }

    protected CommunityDetailsBean(Parcel in) {
        this.question_info = in.readParcelable(QuestionInfoBean.class.getClassLoader());
        this.recommend_list = in.createTypedArrayList(RecommendListBean.CREATOR);
    }

    public static final Creator<CommunityDetailsBean> CREATOR = new Creator<CommunityDetailsBean>() {
        @Override
        public CommunityDetailsBean createFromParcel(Parcel source) {
            return new CommunityDetailsBean(source);
        }

        @Override
        public CommunityDetailsBean[] newArray(int size) {
            return new CommunityDetailsBean[size];
        }
    };
}
