package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.MyItemListBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;

import java.util.List;

public class CommunityMyGroupAdapter extends BaseAdapter {
    private List<MyItemListBean> myItemListBeans;
    private Context mContext;
    private CommunityConfig.OnItemOutListener onItemOutListener;

    public CommunityMyGroupAdapter(Context context, List<MyItemListBean> myItemListBeans) {
        this.mContext = context;
        this.myItemListBeans = myItemListBeans;
    }

    @Override
    public int getCount() {
        return myItemListBeans.size();
    }

    @Override
    public MyItemListBean getItem(int position) {
        return myItemListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyItemListBean myItemListBean = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_community, null);
            viewHolder.imageView = convertView.findViewById(R.id.community_image);
            viewHolder.title = convertView.findViewById(R.id.community_title);
            viewHolder.subtitle = convertView.findViewById(R.id.community_subtitle);
            viewHolder.join = convertView.findViewById(R.id.community_join);
            viewHolder.context = convertView.findViewById(R.id.community_context);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image, mContext, myItemListBean.getImg(), 4);
        viewHolder.title.setText(myItemListBean.getName());
        viewHolder.subtitle.setText("成员: " + myItemListBean.getUser_count() + "人");
        viewHolder.context.setText(myItemListBean.getIntroduce());
        viewHolder.join.setSelected(true);
        viewHolder.join.setText("退出小组");

        viewHolder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemOutListener == null) return;
                onItemOutListener.setOutItem(myItemListBean.getGroup_id(),position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title, subtitle, context, join;
    }

    public void setOnItemOutListener(CommunityConfig.OnItemOutListener listener) {
        this.onItemOutListener = listener;
    }
}
