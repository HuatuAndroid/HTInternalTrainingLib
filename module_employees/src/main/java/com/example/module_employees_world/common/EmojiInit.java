package com.example.module_employees_world.common;

import com.example.module_employees_world.bean.EmojiconBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wb.baselib.app.AppUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class EmojiInit {

    public static final List<EmojiconBean> EMOJICONS = new ArrayList<>();

    static {

        try {
            InputStream open = AppUtils.getContext().getAssets().open("SysEmoji.json");
            List<String> emojis = new Gson().fromJson(new JsonReader(new InputStreamReader(open, Charset.forName("utf-8"))),new TypeToken<List<String>>(){}.getType());
            for (int i = 0; i < emojis.size(); i++) {
                EMOJICONS.add(new EmojiconBean(emojis.get(i),i/27));
            }
        } catch (IOException e) {
            int unicode = 0x1F601;
            for (int i = 0; i < 58; i++) {
                EMOJICONS.add(new EmojiconBean(new String(Character.toChars(unicode++)),0));
            }
            unicode = 0x1F680;
            for (int i = 0; i < 34; i++) {
                EMOJICONS.add(new EmojiconBean(new String(Character.toChars(unicode++)),1));
            }
        }
    }

    public static List<EmojiconBean> getByType(final int type){

        final List<EmojiconBean> emojicons = new ArrayList<>();

        Observable.from(EMOJICONS)
                .filter(emojicon -> type == emojicon.type).subscribe(emojicon -> emojicons.add(emojicon));

        return emojicons;
    }
}
