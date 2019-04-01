package com.example.module_employees_world.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baijiayun.glide.Glide;
import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.bean.TutuIconBean;
import com.example.module_employees_world.common.config.EmojiConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * 表情显示adapter
 */
public class TutuGridAdapter extends BaseAdapter {

    private List<TutuIconBean> datas;
    private final Context cxt;

    public TutuGridAdapter(Context cxt, List<TutuIconBean> datas) {
        this.cxt = cxt;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
    }

    public void refresh(List<TutuIconBean> datas) {
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public TutuIconBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        ImageView iv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            ImageView tv= new ImageView(cxt);
            convertView = tv;
            int bound = (int) cxt.getResources().getDimension(R.dimen.space_60);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(bound, bound);
            convertView.setLayoutParams(params);
            int padding = (int) cxt.getResources().getDimension(R.dimen.space_10);
            convertView.setPadding(padding, padding, padding, padding);
            holder.iv = (ImageView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        if(getItem(position).TutuId != (int)holder.iv.getTag()) {//解决图片加载不闪烁的问题,可以在加载时候，对于已经加载过的item,  采用比对tag方式判断是否需要重新计算高度
//            holder.iv.setTag(null);//需要清空tag，否则报错
//            Glide.with(cxt)
//                    .asBitmap()
//                    .load(getItem(position).TutuId)
//                    .into(holder.iv);
//            holder.iv.setTag(getItem(position).TutuId);
//        }
        holder.iv.setImageResource(getItem(position).TutuId);

        return convertView;
    }
}
