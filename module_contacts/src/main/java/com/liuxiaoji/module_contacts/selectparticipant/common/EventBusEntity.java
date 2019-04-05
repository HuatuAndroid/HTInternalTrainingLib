package com.liuxiaoji.module_contacts.selectparticipant.common;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class EventBusEntity {
    private String type = null;
    private Object object = null;
    private int sign = -1;
    private long signLong = -1;
    private String msgInfo;
    public EventBusEntity(String type, Object object) {
        this.type = type;
        this.object = object;
    }

    public EventBusEntity(String type, int sign) {
        this.type = type;
        this.sign = sign;
    }
    public EventBusEntity(String type, String msgInfo1) {
        this.type = type;
        this.msgInfo = msgInfo1;
    }
    public EventBusEntity(String type, long signLong) {
        this.type = type;
        this.signLong = signLong;
    }

    public int getSmgTypeSign() {
        return sign;
    }

    public <T extends Object> T getObject() {
        if (object == null) {
            return null;
        }
        return (T) object;
    }

    public String getTypeStr() {
        return type;
    }

    public int getSign() {
        return sign;
    }

    public void setSignLong(long signLong) {
        this.signLong = signLong;
    }

    public long getSignLong() {
        return signLong;
    }

    public String getMsgInfo() {
        return msgInfo;
    }
}
