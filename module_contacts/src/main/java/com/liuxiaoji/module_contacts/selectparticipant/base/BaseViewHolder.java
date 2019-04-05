package com.liuxiaoji.module_contacts.selectparticipant.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liuxiaoji.module_contacts.R;

/**
 * @author liuzhe
 * @date 2019/4/4
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    protected View rootView = null;
    public RelativeLayout netWorkRl;

    public TextView reUploadDataTv;

    public FrameLayout netErrorFragment, contentFragment;



    protected BaseViewHolder(View view) {
        super(view);
        this.rootView = view;
        initNoNetView();
    }

    private void initNoNetView() {
        try {
            netWorkRl = $$(R.id.approval_net_error_layout_rl);
            reUploadDataTv = $$(R.id.reload_data_tv);
//            netErrorFragment = $$(R.id.no_net_fragment);
//            contentFragment = $$(R.id.content_fragment);
        } catch (Exception ex) {

        }
    }

    protected <T extends View> T $$(int viewId) {
        return (T) this.rootView.findViewById(viewId);
    }

    public View getRootView() {
        return rootView;
    }



}
