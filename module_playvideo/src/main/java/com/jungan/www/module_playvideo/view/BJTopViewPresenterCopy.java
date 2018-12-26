package com.jungan.www.module_playvideo.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import com.baijiahulian.player.BJPlayerView;
import com.baijiahulian.player.playerview.IPlayerTopContact;
import com.baijiahulian.player.utils.c;
import com.jungan.www.module_playvideo.R;

/**
 * Created by yanglei on 2016/11/4.
 */
public class BJTopViewPresenterCopy implements IPlayerTopContact.TopView {

    private QueryCopy $;
    private IPlayerTopContact.IPlayer mPlayer;

    public BJTopViewPresenterCopy(View var1,final Context var2) {
        $ = QueryCopy.with(var1);
//        $.id(com.baijiahulian.player.R.id.bjplayer_top_back_horizontal_btn).a(new View.OnClickListener() {
//            public void onClick(View var1) {
//                if(BJTopViewPresenterCopy.this.mPlayer != null) {
//                    ((Activity)var2).finish();
//                }
//
//            }
//        });
        $.id(R.id.bjplayer_top_back_horizontal_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BJTopViewPresenterCopy.this.mPlayer != null) {
                    ((Activity)var2).finish();
                }
            }
        });
    }

    /**
     * 返回拦截 强制关闭
     */
    public void setOnBackClickInterceptor(final Context context) {
        $.id(R.id.bjplayer_top_back_horizontal_btn).clicked(null);
        $.id(R.id.bjplayer_top_back_horizontal_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }


    @Override
    public void onBind(IPlayerTopContact.IPlayer player) {
        mPlayer = player;
        setOrientation(mPlayer.getOrientation());
    }

    @Override
    public void setTitle(String title) {
        $.id(R.id.bjplayer_top_title_tv).text(title);
    }

    @Override
    public void setOrientation(int orientation) {
        if (orientation == BJPlayerView.VIDEO_ORIENTATION_LANDSCAPE) {
            $.id(R.id.bjplayer_top_back_vertical_btn).gone();
            $.id(R.id.bjplayer_top_back_horizontal_btn).visible();
            $.id(R.id.bjplayer_top_title_tv).visible();
            $.contentView().setBackgroundColor(ContextCompat.getColor($.view().getContext(), com.baijiahulian.player.R.color.bjplayer_controller_bg));
        } else {
            $.id(R.id.bjplayer_top_back_vertical_btn).visible();
            $.id(R.id.bjplayer_top_back_horizontal_btn).gone();
            $.id(R.id.bjplayer_top_title_tv).gone();
            $.contentView().setBackgroundColor(ContextCompat.getColor($.view().getContext(), android.R.color.transparent));
        }
    }

    @Override
    public void setOnBackClickListener(final View.OnClickListener listener) {
        $.id(R.id.bjplayer_top_back_vertical_btn).clicked(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (!mPlayer.onBackPressed()) {
                        listener.onClick(v);
                    }
                } else {
                    listener.onClick(v);
                }
            }
        });
    }
        public void finsnA(Context var2){
            if(BJTopViewPresenterCopy.this.mPlayer != null) {
                ((Activity)var2).finish();
            }
        }
}
