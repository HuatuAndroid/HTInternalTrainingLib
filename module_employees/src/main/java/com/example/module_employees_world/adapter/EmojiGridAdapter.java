package com.example.module_employees_world.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.EmojiconBean;
import com.example.module_employees_world.common.config.EmojiConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/28
 *
 * 表情显示adapter
 */
public class EmojiGridAdapter  extends BaseAdapter {

    private List<EmojiconBean> datas;
    private final Context cxt;

    public EmojiGridAdapter(Context cxt, List<EmojiconBean> datas) {
        this.cxt = cxt;
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
    }

    public void refresh(List<EmojiconBean> datas) {
        if (datas == null) {
            datas = new ArrayList<>(0);
        }
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size()+1;
    }

    @Override
    public EmojiconBean getItem(int position) {
        return datas.size() > position ? datas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private static class ViewHolder {
        TextView tv;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            TextView tv= new TextView(cxt);
            tv.setGravity(Gravity.CENTER);
            //tv.setUseSystemDefault(true);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,23);
            //tv.setEmojiconSize((int) SpPxUtils.dp2px(cxt.getResources(),30));
            convertView = tv;
            int bound = (int) cxt.getResources().getDimension(R.dimen.space_49);
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(bound, bound);
            convertView.setLayoutParams(params);
            int padding = (int) cxt.getResources().getDimension(R.dimen.space_10);
            convertView.setPadding(padding, padding, padding, padding);
            holder.tv = (TextView) convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if(position == getCount()-1){
            holder.tv.setText("");
            holder.tv.setCompoundDrawablesWithIntrinsicBounds(EmojiConfig.RESID_DELETE_ICON,0,0,0);
        }else{
            holder.tv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,0);
            holder.tv.setText(getItem(position).emojiChart+"");
        }
//        holder.tv.setBackgroundResource(R.drawable.bg_select_rectangle);
        return convertView;
    }
}
