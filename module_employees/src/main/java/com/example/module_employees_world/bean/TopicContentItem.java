package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.utils.Rgba;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class TopicContentItem implements Parcelable {

    public transient static final String TYPE_TXT = "text";
    public transient static final String TYPE_IMG = "image";

    public int index;
    public ContentType type;
    public String content;
    public String localUrl;
    public String remoteUrl;
    public String remoteThumb;
    public int w;
    public int h;
    public Rgba color;

    public enum ContentType{

        IMG(TYPE_IMG),
        TXT(TYPE_TXT);

        String tname;

        ContentType(String tname) {
            this.tname = tname;
        }

        @Override
        public String toString() {
            return tname;
        }
    }

    public TopicContentItem(String content) {
        this.type = ContentType.TXT;
        this.content = content;
    }

    public TopicContentItem(String localUrl, String remoteUrl) {
        this.type = ContentType.IMG;
        this.localUrl = localUrl;
        this.remoteUrl = remoteUrl;
    }

    public TopicContentItem(String localUrl, String remoteUrl,Rgba color) {
        this.type = ContentType.IMG;
        this.localUrl = localUrl;
        this.remoteUrl = remoteUrl;
        this.color = color;
    }

    public TopicContentItem(String localUrl, String remoteUrl,Rgba color,int w,int h) {
        this.type = ContentType.IMG;
        this.localUrl = localUrl;
        this.remoteUrl = remoteUrl;
        this.color = color;
        this.w = w;
        this.h = h;
    }

    @Override
    public String toString() {
        return "ContentItem{" +
                "type=" + type +
                ", content='" + content + '\'' +
                ", localUrl='" + localUrl + '\'' +
                ", remoteUrl='" + remoteUrl + '\'' +
                ", remoteThumb='" + remoteThumb + '\'' +
                ", w=" + w +
                ", h=" + h +
                ", color=" + color +
                '}';
    }

//
//    public TopicContentItem toContentItem(){
//        if(TopicContentItem.TYPE_TXT.equals(type.toString())){
//            return new TopicContentItem(content);
//        }else if(TopicContentItem.TYPE_IMG.equals(type.toString())){
//            return new TopicContentItem(remoteUrl,remoteThumb,w,h,color);
//        }else{
//            return null;
//        }
//    }

    @Override
    public boolean equals(Object o) {
        if(o == null){
            return false;
        }
        if(o instanceof TopicContentItem){
            TopicContentItem ci = (TopicContentItem) o;
            if(ci.type == this.type){
                if(ci.type == ContentType.IMG){
                    return CommonUtils.compareStr(ci.remoteUrl,this.remoteUrl) && CommonUtils.compareStr(ci.remoteThumb,this.remoteThumb);
                }else if(ci.type == ContentType.TXT){
                    return CommonUtils.compareStr(ci.content,this.content);
                }
            }
        }
        return super.equals(o);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeString(this.content);
        dest.writeString(this.localUrl);
        dest.writeString(this.remoteUrl);
        dest.writeString(this.remoteThumb);
        dest.writeInt(this.w);
        dest.writeInt(this.h);
        dest.writeParcelable(this.color, flags);
    }

    protected TopicContentItem(Parcel in) {
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : ContentType.values()[tmpType];
        this.content = in.readString();
        this.localUrl = in.readString();
        this.remoteUrl = in.readString();
        this.remoteThumb = in.readString();
        this.w = in.readInt();
        this.h = in.readInt();
        this.color = in.readParcelable(Rgba.class.getClassLoader());
    }

    public static final Parcelable.Creator<TopicContentItem> CREATOR = new Parcelable.Creator<TopicContentItem>() {
        @Override
        public TopicContentItem createFromParcel(Parcel source) {
            return new TopicContentItem(source);
        }

        @Override
        public TopicContentItem[] newArray(int size) {
            return new TopicContentItem[size];
        }
    };

}
