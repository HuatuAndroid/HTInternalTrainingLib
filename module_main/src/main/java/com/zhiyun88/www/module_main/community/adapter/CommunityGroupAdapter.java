package com.zhiyun88.www.module_main.community.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.bean.ListBean;
import com.zhiyun88.www.module_main.community.config.CommunityConfig;

import java.util.List;


public class CommunityGroupAdapter extends BaseAdapter {
    private List<ListBean> listBeans;
    private Context mContext;
    private CommunityConfig.OnItemJoinListener onItemJoinListener;

    public CommunityGroupAdapter(Context context, List<ListBean> listBeans) {
        this.mContext = context;
        this.listBeans = listBeans;
    }

    @Override
    public int getCount() {
        return listBeans.size();
    }

    @Override
    public ListBean getItem(int position) {
        return listBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ListBean listBean = getItem(position);
        final ViewHolder viewHolder;
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
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image ,mContext ,listBean.getImg() ,4 );
        viewHolder.title.setText(listBean.getName());
        Log.d("kaelli", "getUser_count:"+listBean.getUser_count());
        viewHolder.subtitle.setText("成员: "+listBean.getUser_count()+"人");
        viewHolder.context.setText(listBean.getIntroduce());
        if (listBean.getIs_group().equals("0")) {
            viewHolder.join.setSelected(false);
            viewHolder.join.setText("加入小组");
        }else {
            viewHolder.join.setSelected(true);
            viewHolder.join.setText("退出小组");
        }
        viewHolder.join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemJoinListener == null) return;
                onItemJoinListener.setJoinInfo(listBean.getId(),listBean.getIs_group(),position,viewHolder.join);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title, subtitle, context,join;
    }

    public void setOnItemJoinListener(CommunityConfig.OnItemJoinListener listener) {
        this.onItemJoinListener =  listener;
    }

    public void updataItem(int index, ListView listView, String state) {
        int position = listView.getFirstVisiblePosition();
        if (index - position >= 0) {
            View childAt = listView.getChildAt(index - position);
            ViewHolder viewHolder = (ViewHolder) childAt.getTag();
            if (state.equals("0")) {
                viewHolder.join.setSelected(true);
                viewHolder.join.setText("退出小组");
                listBeans.get(index).setIs_group("1");
                int userCount = Integer.parseInt(listBeans.get(index).getUser_count()) + 1;
                listBeans.get(index).setUser_count(userCount+"");
            }else {
                viewHolder.join.setSelected(false);
                viewHolder.join.setText("加入小组");
                listBeans.get(index).setIs_group("0");
                int userCount = Integer.parseInt(listBeans.get(index).getUser_count()) - 1;
                listBeans.get(index).setUser_count(userCount+"");
            }
            viewHolder.join.setEnabled(true);
            notifyDataSetChanged();
        }
    }
}
