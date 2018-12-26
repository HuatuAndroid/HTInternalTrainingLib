package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.view.MyListView;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.main.bean.UserMainBean;

import java.util.List;

public class UserListAdapter extends BaseAdapter{
    private Context mContext;
    private List<UserMainBean> userMainBeanList;

    public UserListAdapter(Context mContext, List<UserMainBean> userMainBeanList) {
        this.mContext = mContext;
        this.userMainBeanList = userMainBeanList;
    }

    @Override
    public int getCount() {
        if (userMainBeanList.isEmpty()) {
            return 0;
        }
        return userMainBeanList.size();
    }

    @Override
    public Object getItem(int i) {
        return userMainBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        UserMainBean userMainBean= (UserMainBean) getItem(i);
        if (view == null) {
            viewHolder = new ViewHolder();
             view = LayoutInflater.from(mContext).inflate(R.layout.main_usermain_item, null);
            viewHolder.imageView = view.findViewById(R.id.iv_attr_icon);
            viewHolder.textView = view.findViewById(R.id.tv_attr_text);
            viewHolder.newmessgae_img=view.findViewById(R.id.newmessgae_img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(userMainBean.getResId());
        viewHolder.textView.setText(userMainBean.getResName());
        return view;
    }
    class ViewHolder{
        ImageView imageView,newmessgae_img;
        TextView textView;
    }
    public void updateItem(MyListView myListView,boolean isNew){
        int postion=myListView.getFirstVisiblePosition();
        if(0-postion>=0){
            View view=myListView.getChildAt(0-postion);
            ViewHolder holder= (ViewHolder) view.getTag();
            if(isNew){
                holder.newmessgae_img.setVisibility(View.VISIBLE);
            }else {
                holder.newmessgae_img.setVisibility(View.GONE);
            }
        }
    }
}
