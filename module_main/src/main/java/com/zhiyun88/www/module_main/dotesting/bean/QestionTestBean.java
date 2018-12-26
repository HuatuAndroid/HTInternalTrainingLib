package com.zhiyun88.www.module_main.dotesting.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class QestionTestBean implements Parcelable {

    private QuestionNaireBean question_naire;
    private List<QuestionBean> question;

    public QuestionNaireBean getQuestion_naire() {
        return question_naire;
    }

    public void setQuestion_naire(QuestionNaireBean question_naire) {
        this.question_naire = question_naire;
    }

    public List<QuestionBean> getQuestion() {
        return question;
    }

    public void setQuestion(List<QuestionBean> question) {
        this.question = question;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.question_naire, flags);
        dest.writeTypedList(this.question);
    }

    public QestionTestBean() {
    }

    protected QestionTestBean(Parcel in) {
        this.question_naire = in.readParcelable(QuestionNaireBean.class.getClassLoader());
        this.question = in.createTypedArrayList(QuestionBean.CREATOR);
    }

    public static final Creator<QestionTestBean> CREATOR = new Creator<QestionTestBean>() {
        @Override
        public QestionTestBean createFromParcel(Parcel source) {
            return new QestionTestBean(source);
        }

        @Override
        public QestionTestBean[] newArray(int size) {
            return new QestionTestBean[size];
        }
    };
}
