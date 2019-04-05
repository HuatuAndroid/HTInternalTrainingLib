package com.liuxiaoji.module_contacts.selectparticipant.service;

import com.google.gson.annotations.SerializedName;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class ErrorResponse {
    @SerializedName("msg")
    public String error;
    @SerializedName("code")
    public int code;
}