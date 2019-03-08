package com.baijiayun.videoplayer.ui.component;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.baijiayun.constant.MediaPlayerDebugInfo;
import com.baijiayun.playback.util.LPRxUtils;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.event.UIEventKey;
import com.baijiayun.videoplayer.ui.listener.PlayerStateGetter;
import com.baijiayun.videoplayer.ui.utils.Utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by yongjiaming on 2018/9/20 18:36
 * 视频实时信息调试面板，仅供开发参考，release版本请勿添加
 */
public class MediaPlayerDebugInfoComponent extends BaseComponent {

    private TableLayout mTableLayout;
    private Disposable disposable;
    private SparseArray<View> mRowMap = new SparseArray<>();

    public MediaPlayerDebugInfoComponent(Context context) {
        super(context);

    }

    @Override
    protected View onCreateComponentView(Context context) {
        return View.inflate(context, R.layout.table_media_info, null);
    }

    @Override
    protected void onInitView() {
        mTableLayout = findViewById(R.id.table);
    }

    @Override
    protected void setKey() {
        key = UIEventKey.KEY_VIDEO_INFO_COMPONENT;
    }

    @Override
    public void bindStateGetter(PlayerStateGetter stateGetter) {
        super.bindStateGetter(stateGetter);
        disposable = Observable.interval(0, 500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    MediaPlayerDebugInfo debugInfo = getStateGetter().getMediaPlayerDebugInfo();
                    setRowValue(R.string.vdec, debugInfo.vdec);
                    setRowValue(R.string.fps, String.format(Locale.US, "%.2f / %.2f", debugInfo.fpsDecode, debugInfo.fpsOutput));
                    setRowValue(R.string.v_cache, String.format(Locale.US, "%s, %s", Utils.formatedDurationMilli(debugInfo.videoCachedDuration), Utils.formatedSize(debugInfo.videoCachedBytes)));
                    setRowValue(R.string.a_cache, String.format(Locale.US, "%s, %s", Utils.formatedDurationMilli(debugInfo.audioCachedDuration), Utils.formatedSize(debugInfo.audioCachedBytes)));
                    setRowValue(R.string.seek_load_cost, String.format(Locale.US, "%d ms", debugInfo.seekLoadDuration));
                    setRowValue(R.string.tcp_speed, String.format(Locale.US, "%s", Utils.formatedSpeed(debugInfo.tcpSpeed, 1000)));
                    setRowValue(R.string.bit_rate, String.format(Locale.US, "%.2f kbs", debugInfo.bitRate / 1000f));
                });
    }

    private void setRowValue(int id, String value) {
        View rowView = mRowMap.get(id);
        if (rowView == null) {
            rowView = appendRow(getContext().getString(id), value);
            mRowMap.put(id, rowView);
        } else {
            setValueText(rowView, value);
        }
    }

    private View appendRow(String name, String value) {
        ViewGroup rowView = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.table_media_info_row1, mTableLayout, false);
        setNameValueText(rowView, name, value);

        mTableLayout.addView(rowView);
        return rowView;
    }

    private void setValueText(View rowView, String value) {
        ViewHolder viewHolder = obtainViewHolder(rowView);
        viewHolder.setValue(value);
    }

    private void setNameValueText(View rowView, String name, String value) {
        ViewHolder viewHolder = obtainViewHolder(rowView);
        viewHolder.setName(name);
        viewHolder.setValue(value);
    }

    private ViewHolder obtainViewHolder(View rowView) {
        ViewHolder viewHolder = (ViewHolder) rowView.getTag();
        if (viewHolder == null) {
            viewHolder = new ViewHolder();
            viewHolder.mNameTextView = rowView.findViewById(R.id.name);
            viewHolder.mValueTextView = rowView.findViewById(R.id.value);
            rowView.setTag(viewHolder);
        }
        return viewHolder;
    }

    private static class ViewHolder {
        private TextView mNameTextView;
        private TextView mValueTextView;

        public void setName(String name) {
            if (mNameTextView != null) {
                mNameTextView.setText(name);
            }
        }

        public void setValue(String value) {
            if (mValueTextView != null) {
                mValueTextView.setText(value);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        LPRxUtils.dispose(disposable);
        mRowMap.clear();
    }
}
