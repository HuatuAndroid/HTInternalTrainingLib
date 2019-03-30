package com.example.module_employees_world.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author:LIENLIN
 * date:2019/3/28
 */
public class RxBusMessageBean implements Parcelable{

    public static class MessageType{
        public final static int POST_101=101;
        public final static int POST_102=102;
        public final static int POST_103=103;
        public final static int POST_104=104;
        public final static int POST_105=105;
        public final static int POST_106=106;
        public final static int POST_107=107;
        public final static int POST_108=108;
        public final static int POST_109=109;
        public final static int POST_110=110;
        public final static int POST_111=111;
        public final static int POST_112=112;
    }


    private int messageCode;
    private Object message;
    private Object message1;
    private Object message2;
    public RxBusMessageBean(int messageCode, Object message) {
        this.messageCode = messageCode;
        this.message = message;
    }

    public RxBusMessageBean(int messageCode, Object message, Object message1) {
        this.messageCode = messageCode;
        this.message = message;
        this.message1 = message1;
    }

    public RxBusMessageBean(int messageCode, Object message, Object message1, Object message2) {
        this.messageCode = messageCode;
        this.message = message;
        this.message1 = message1;
        this.message2 = message2;
    }

    public int getMessageCode() {
        return messageCode;
    }

    public Object getMessage() {
        return message;
    }

    public Object getMessage1() {
        return message1;
    }

    public Object getMessage2() {
        return message2;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageCode);
    }
}
