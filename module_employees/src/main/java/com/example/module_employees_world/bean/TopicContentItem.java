package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;

import com.example.module_employees_world.common.CommonUtils;
import com.example.module_employees_world.utils.Rgba;

import java.io.Serializable;

/**
 * @author liuzhe
 * @date 2019/3/22
 */
public class TopicContentItem implements Serializable {

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

    public Editable mEditable;

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

}
