package com.baijiayun.videoplayer.ui.component;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baijiayun.constant.VideoDefinition;
import com.baijiayun.videoplayer.bean.BJYVideoInfo;
import com.baijiayun.videoplayer.event.BundlePool;
import com.baijiayun.videoplayer.event.EventKey;
import com.baijiayun.videoplayer.event.OnPlayerEventListener;
import com.baijiayun.videoplayer.player.PlayerStatus;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.utils.Utils;
import com.baijiayun.videoplayer.ui.bean.Rate;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.OnTouchGestureListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by yongjiaming on 2018/8/10 17:34
 */
public class MenuComponent extends BaseComponent implements OnTouchGestureListener {

    private LinearLayout menuLl;
    private TextView definitionTv;
    private RecyclerView recyclerView;
    private RelativeLayout recyclerViewLl;
    //默认横屏显示菜单项
    private boolean isLandscape;
    private List<VideoDefinition> definitionItemList = new ArrayList<>();
    private List<Rate> rateList = new ArrayList<>();
    private DefinitionAdapter definitionAdapter;
    private RateAdapter rateAdapter;

    private static final int HIDE_MENU = 1001;
    private Handler handler = new Handler(msg -> {
        if (msg.what == HIDE_MENU) {
            getView().setVisibility(View.GONE);
        }
        return false;
    });

    public MenuComponent(Context context) {
        super(context);
        updateUI();
        initRateList();
        initAdapter();
    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.layout_menu_component, null);
    }

    @Override
    protected void onInitView() {
        menuLl = findViewById(R.id.bjplayer_center_video_functions_ll);
        TextView rateTv = findViewById(R.id.bjplayer_center_video_functions_rate_tv);
        definitionTv = findViewById(R.id.bjplayer_center_video_functions_frame_tv);
        recyclerView = findViewById(R.id.rv_bjplayer);
        recyclerViewLl = findViewById(R.id.bjplayer_rv_ll);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        rateTv.setOnClickListener(v -> {
            recyclerView.setAdapter(rateAdapter);
            recyclerViewLl.setVisibility(View.VISIBLE);
            menuLl.setVisibility(View.GONE);
        });
        definitionTv.setOnClickListener(v -> {
            recyclerView.setAdapter(definitionAdapter);
            recyclerViewLl.setVisibility(View.VISIBLE);
            menuLl.setVisibility(View.GONE);
        });
    }

    @Override
    protected void setKey() {
        key = UIEventKey.KEY_MENU_COMPONENT;
    }

    @Override
    public void onCustomEvent(int eventCode, Bundle bundle) {
        super.onCustomEvent(eventCode, bundle);
        switch (eventCode) {
            case UIEventKey.CUSTOM_CODE_REQUEST_TOGGLE_SCREEN:
                //横屏才显示菜单项
                isLandscape = bundle.getBoolean(EventKey.BOOL_DATA);
                updateUI();
                autoHideMenu();
                break;
            case UIEventKey.CUSTOM_CODE_TAP_PPT:
                doSingleTapUp();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        super.onPlayerEvent(eventCode, bundle);
        switch (eventCode) {
            //重新设置视频源
            case OnPlayerEventListener.PLAYER_EVENT_ON_STATUS_CHANGE:
                PlayerStatus playerStatus = (PlayerStatus) bundle.getSerializable(EventKey.SERIALIZABLE_DATA);
                if (playerStatus != null && playerStatus == PlayerStatus.STATE_INITIALIZED) {
                    BJYVideoInfo videoInfo = getStateGetter().getVideoInfo();
                    if (videoInfo != null && videoInfo.getSupportedDefinitionList() != null) {
                        definitionItemList = videoInfo.getSupportedDefinitionList();
                        definitionTv.setText(Utils.getDefinitionInString(getContext(), videoInfo.getDefinition()));
                        definitionAdapter.notifyDataSetChanged();
                    } else {
                        //离线播放隐藏
                        definitionTv.setVisibility(View.GONE);
                    }
                }
                if (playerStatus != null && playerStatus == PlayerStatus.STATE_STARTED) {
                    autoHideMenu();
                }
                break;
        }
    }

    private void updateUI() {
        setComponentVisibility(isLandscape ? View.VISIBLE : View.GONE);
        menuLl.setVisibility(isLandscape ? View.VISIBLE : View.GONE);
    }

    private void initAdapter() {
        definitionAdapter = new DefinitionAdapter(getContext());
        definitionAdapter.setOnRvItemClickListener((view, index) -> {
            notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_SET_DEFINITION, BundlePool.obtain(definitionItemList.get(index)));
            definitionTv.setText(Utils.getDefinitionInString(getContext(), definitionItemList.get(index)));
            recyclerViewLl.setVisibility(View.GONE);
            menuLl.setVisibility(View.VISIBLE);
        });
        rateAdapter = new RateAdapter();
        rateAdapter.setOnRvItemClickListener((view, index) -> {
            notifyComponentEvent(UIEventKey.CUSTOM_CODE_REQUEST_SET_RATE, BundlePool.obtain(rateList.get(index).rate));
            recyclerViewLl.setVisibility(View.GONE);
            menuLl.setVisibility(View.VISIBLE);
        });
    }

    private void initRateList() {
        rateList.add(new Rate("0.7x", 0.7f));
        rateList.add(new Rate("1.0x", 1.0f));
        rateList.add(new Rate("1.2x", 1.2f));
        rateList.add(new Rate("1.5x", 1.5f));
        rateList.add(new Rate("2.0x", 2.0f));
    }

    @Override
    public void onSingleTapUp(MotionEvent event) {
        doSingleTapUp();
    }

    @Override
    public void onDoubleTap(MotionEvent event) {

    }

    @Override
    public void onDown(MotionEvent event) {

    }

    @Override
    public void onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

    }

    @Override
    public void onEndGesture() {

    }

    private void doSingleTapUp(){
        if (recyclerViewLl.getVisibility() == View.VISIBLE) {
            recyclerViewLl.setVisibility(View.GONE);
            menuLl.setVisibility(View.VISIBLE);
        }
        setComponentVisibility(getView().getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
        autoHideMenu();
    }

    private void autoHideMenu() {
        handler.removeMessages(HIDE_MENU);
        handler.sendEmptyMessageDelayed(HIDE_MENU, 5000);
    }

    interface OnRvItemClickListener {
        void onItemClick(View view, int index);
    }

    class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionViewHolder> implements View.OnClickListener {
        Context context;
        OnRvItemClickListener onRvItemClickListener;

        DefinitionAdapter(Context context) {
            this.context = context;
        }

        void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
            this.onRvItemClickListener = onRvItemClickListener;
        }

        @Override
        public DefinitionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View definitionView = LayoutInflater.from(context).inflate(R.layout.bjplayer_item_center_definition, parent, false);
            definitionView.setOnClickListener(this);
            return new DefinitionViewHolder(definitionView);
        }

        @Override
        public void onBindViewHolder(DefinitionViewHolder holder, int position) {
            holder.itemView.setTag(position);
            holder.tvDefinition.setText(Utils.getDefinitionInString(getContext(), definitionItemList.get(position)));
            BJYVideoInfo videoInfo = getStateGetter().getVideoInfo();
            if (videoInfo != null && videoInfo.getDefinition() == definitionItemList.get(position)) {
                holder.tvDefinition.setTextColor(context.getResources().getColor(R.color.bjplayer_color_primary));
                holder.tvDefinition.setBackgroundResource(R.drawable.bjplayer_bg_primary_radius_12);
            } else {
                holder.tvDefinition.setTextColor(context.getResources().getColor(android.R.color.white));
                holder.tvDefinition.setBackgroundResource(R.drawable.bjplayer_bg_radius_12);
            }

            if (definitionItemList.size() == 0 || position == definitionItemList.size() - 1) {
                holder.dividerView.setVisibility(View.GONE);
            } else {
                holder.dividerView.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return definitionItemList.size();
        }

        @Override
        public void onClick(View v) {
            if (onRvItemClickListener != null) {
                if (v.getTag() != null) {
                    int position = (int) v.getTag();
                    onRvItemClickListener.onItemClick(v, position);
                }
            }
        }

        class DefinitionViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            TextView tvDefinition;
            View dividerView;

            DefinitionViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                tvDefinition = itemView.findViewById(R.id.tv_bjplayer_item_center_definition);
                dividerView = itemView.findViewById(R.id.v_bjplayer_item_divider);
            }
        }
    }

    class RateAdapter extends RecyclerView.Adapter<RateAdapter.RateViewHolder> {

        OnRvItemClickListener onRvItemClickListener;

        void setOnRvItemClickListener(OnRvItemClickListener onRvItemClickListener) {
            this.onRvItemClickListener = onRvItemClickListener;
        }

        @NonNull
        @Override
        public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View rateItem = LayoutInflater.from(getContext()).inflate(R.layout.bjplayer_itme_rate, parent, false);
            return new RateViewHolder(rateItem);
        }

        @Override
        public void onBindViewHolder(@NonNull final RateViewHolder holder, final int position) {
            holder.rateTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRvItemClickListener != null) {
                        onRvItemClickListener.onItemClick(v, position);
                    }
                    holder.rateTv.setBackgroundResource(R.drawable.bjplayer_bg_primary_radius_12);
                }
            });
            if (getStateGetter().getPlayRate() != rateList.get(position).rate) {
                holder.rateTv.setBackgroundResource(R.drawable.bjplayer_bg_radius_12);
            } else {
                holder.rateTv.setBackgroundResource(R.drawable.bjplayer_bg_primary_radius_12);
            }
            holder.rateTv.setText(rateList.get(position).rateDescription);
        }

        @Override
        public int getItemCount() {
            return rateList.size();
        }

        class RateViewHolder extends RecyclerView.ViewHolder {

            private TextView rateTv;

            RateViewHolder(View itemView) {
                super(itemView);
                rateTv = itemView.findViewById(R.id.bjplayer_layout_rate_btn);
            }
        }
    }
}
