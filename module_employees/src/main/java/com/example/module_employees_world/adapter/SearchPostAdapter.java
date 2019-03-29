package com.example.module_employees_world.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.DiscussListBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;

import java.util.List;

/**
 * 员工天地-搜索结果-帖子列表
 */
public class SearchPostAdapter extends ListBaseAdapter {

    public SearchPostAdapter(Context context, List<SearchPostBean> searchPostBeans) {
        super(searchPostBeans, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchPostBean searchPostBean = (SearchPostBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_post, null);
            viewHolder.tvPostTitle = convertView.findViewById(R.id.tvPostTitle);
            viewHolder.tvRead = convertView.findViewById(R.id.tvRead);
            viewHolder.tvime = convertView.findViewById(R.id.tvime);
            viewHolder.tvPostGroup = convertView.findViewById(R.id.tvPostGroup);
            viewHolder.ivHead = convertView.findViewById(R.id.ivHead);
            viewHolder.tvPostName = convertView.findViewById(R.id.tvPostName);
            viewHolder.tvLike = convertView.findViewById(R.id.tvLike);
            viewHolder.tvComment = convertView.findViewById(R.id.tvComment);
            viewHolder.tvPart = convertView.findViewById(R.id.tvPart);
            viewHolder.tvPostContent = convertView.findViewById(R.id.tvPostContent);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (searchPostBean.getIs_anonymity() == 1) {
            viewHolder.tvPostName.setText("匿名");
        } else {
            viewHolder.tvPostName.setText(searchPostBean.getUser_name()+"");
        }
        viewHolder.tvPostTitle.setText(searchPostBean.getTitle());
        viewHolder.tvPostContent.setText(searchPostBean.getContent());
        //头像统一由服务器获取
        if (!TextUtils.isEmpty(searchPostBean.getAvatar()))
            Picasso.with(context).load(searchPostBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.ivHead);
        viewHolder.tvRead.setText(searchPostBean.getRead_count() + "");
        viewHolder.tvLike.setText(searchPostBean.getLike_count() + "");
        viewHolder.tvComment.setText(searchPostBean.getComment_count() + "");
        String status = "已采纳";
        if (searchPostBean.getSolve_status() == 0) {
            status = "未采纳";
        }
        viewHolder.tvime.setText(searchPostBean.getCreated_at() + " | " + status);
        viewHolder.tvPostGroup.setText("「" + searchPostBean.getGroup_name() + "」");
        viewHolder.tvPart.setText(searchPostBean.getDepartment_name());
        return convertView;
    }

    class ViewHolder {
        ImageView ivHead;
        TextView tvRead, tvComment, tvLike, tvime, tvPostName, tvPart, tvPostGroup, tvPostTitle, tvPostContent;
    }
}
