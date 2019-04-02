package com.example.module_employees_world.common;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.TutuIconBean;
import com.thefinestartist.utils.log.LogUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * @author liuzhe
 * @date 2019/3/28
 */
public class TutuPicInit {

    public static final List<TutuIconBean> EMOJICONS = new ArrayList<>();

    public static int pics[] = {R.drawable.pic1_01
                                ,R.drawable.pic1_02
                                ,R.drawable.pic1_03
                                ,R.drawable.pic1_04
                                ,R.drawable.pic1_05
                                ,R.drawable.pic1_06
                                ,R.drawable.pic1_07
                                ,R.drawable.pic1_08
                                ,R.drawable.pic1_09
                                ,R.drawable.pic1_10
                                ,R.drawable.pic1_11
                                ,R.drawable.pic1_12
                                ,R.drawable.pic1_13
                                ,R.drawable.pic1_14
                                ,R.drawable.pic1_15
                                ,R.drawable.pic1_16};

     static {

        try {

            for (int i = 0; i < pics.length; i++){
                String key = "[pic1_0" + i + "]";
                TutuIconBean tutuIconBean = new TutuIconBean(pics[i], key, i/10);
                EMOJICONS.add(tutuIconBean);
            }

        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }

    public static List<TutuIconBean> getData(int type){

        final List<TutuIconBean> tutuIconBeans = new ArrayList<>();

        Observable.from(EMOJICONS)
                .filter(emojicon -> type == emojicon.type).subscribe(emojicon -> tutuIconBeans.add(emojicon));

        return tutuIconBeans;
    }
}
