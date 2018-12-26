package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.UserInfoBean;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

public class UserInfoAdapter extends BaseAdapter {
    private  UserInfoBean userInfoBean;
    private Context mContext;

    public UserInfoAdapter(Context context, UserInfoBean userInfoBean) {
        this.mContext = context;
        this.userInfoBean = userInfoBean;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_userinfo, null);
            viewHolder.title = convertView.findViewById(R.id.userinfo_title);
            viewHolder.imageView = convertView.findViewById(R.id.userinfo_image);
            viewHolder.text = convertView.findViewById(R.id.userinfo_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            viewHolder.text.setVisibility(View.GONE);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.title.setText(R.string.main_head_portrait);
            Picasso.with(mContext).load(userInfoBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.imageView);
//            GlideManager.getInstance().setGlideRoundTransImage(viewHolder.imageView,R.drawable.user_head ,mContext ,userInfoBean.getAvatar() );
        }else {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.text.setVisibility(View.VISIBLE);
        }
        if (position == 1) {
            viewHolder.title.setText(R.string.main_name);
            viewHolder.text.setText(userInfoBean.getName());
        }
        if (position == 2) {
            viewHolder.title.setText(R.string.main_gender);
            if ("1".equals(userInfoBean.getSex())) {
                viewHolder.text.setText("男");
            }else if ("2".equals(userInfoBean.getSex())) {
                viewHolder.text.setText("女");
            }
        }
        if (position == 3) {
            viewHolder.title.setText(R.string.main_date_of_birth);
            viewHolder.text.setText(userInfoBean.getBirthday().substring(0,10));
        }
        if (position == 4) {
            viewHolder.title.setText(R.string.main_cell_phone_number);
            String substring = userInfoBean.getMobile().substring(3, 7);
            viewHolder.text.setText(userInfoBean.getMobile().replaceFirst(substring,"****"));
        }
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView title,text;
    }
}

