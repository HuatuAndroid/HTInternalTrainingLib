package com.zhiyun88.www.module_main.commonality.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class CertificateListBean implements Parcelable {

        private int id;
        private String title;
        private String created_at;
        private String certificate_img;
        private String shop_title;

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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getCertificate_img() {
            return certificate_img;
        }

        public void setCertificate_img(String certificate_img) {
            this.certificate_img = certificate_img;
        }

        public String getShop_title() {
            return shop_title;
        }

        public void setShop_title(String shop_title) {
            this.shop_title = shop_title;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.created_at);
        dest.writeString(this.certificate_img);
        dest.writeString(this.shop_title);
    }

    public CertificateListBean() {
    }

    protected CertificateListBean(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.created_at = in.readString();
        this.certificate_img = in.readString();
        this.shop_title = in.readString();
    }

    public static final Creator<CertificateListBean> CREATOR = new Creator<CertificateListBean>() {
        @Override
        public CertificateListBean createFromParcel(Parcel source) {
            return new CertificateListBean(source);
        }

        @Override
        public CertificateListBean[] newArray(int size) {
            return new CertificateListBean[size];
        }
    };
}
