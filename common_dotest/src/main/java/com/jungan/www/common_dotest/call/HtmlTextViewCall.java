package com.jungan.www.common_dotest.call;

import java.util.List;

public interface HtmlTextViewCall {
    //超链接回调
    void linkFixCall(String s);
    //点击图片回调
    void imageClicked(List<String> strings, int option);
    //加载完图片后回调
    void done(boolean is);
}
