package com.liuxiaoji.module_contacts.selectparticipant.common;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class NetError {
    public static final int NET_ERROR_CODE = 20180410;
    public static final int SERVER_ERROR_CODE = 20180411;
    private int mErrorCode;
    private String mServerMessage;

    public int getmErrorCode() {
        return mErrorCode;
    }

    public void setmErrorCode(int mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public String getmServerMessage() {
        return mServerMessage;
    }

    public void setmServerMessage(String mServerMessage) {
        this.mServerMessage = mServerMessage;
    }

    @Override
    public String toString() {
        return "NetError{" +
                "mErrorCode=" + mErrorCode +
                ", mServerMessage='" + mServerMessage + '\'' +
                '}';
    }
}
