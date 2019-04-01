package com.example.module_employees_world.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:LIENLIN
 * date:2019/3/28
 */
public class RxBusMessageBean implements Parcelable {

    public static class MessageType {
        public final static int POST_101 = 101;
        public final static int POST_102 = 102;
        public final static int POST_103 = 103;
        //搜索结果 帖子 调到详情页 详情页做评价 需要更新搜索列表评论个数
        public final static int SEARCH_POST_COMMENT = 201;
        //搜索结果 帖子 调到详情页 详情页点赞 需要更新搜索列表点赞个数
        public final static int SEARCH_POST_LIKE = 202;
        //搜索结果 帖子 进入详情后 帖子删除了
        public final static int SEARCH_POST_DELETE = 203;
        //搜索 改变关键字
        public final static int SEARCH_CHANGE_KEYWORD = 204;
    }


    private int messageCode;
    private Object message;

    public RxBusMessageBean(int messageCode, Object message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    protected RxBusMessageBean(Parcel in) {
        messageCode = in.readInt();
    }

    public static final Creator<RxBusMessageBean> CREATOR = new Creator<RxBusMessageBean>() {
        @Override
        public RxBusMessageBean createFromParcel(Parcel in) {
            return new RxBusMessageBean(in);
        }

        @Override
        public RxBusMessageBean[] newArray(int size) {
            return new RxBusMessageBean[size];
        }
    };

    public void setMessageType(int messageType) {
        this.messageCode = messageType;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public int getMessageType() {

        return messageCode;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageCode);
    }
}
