package com.example.module_employees_world.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.example.module_employees_world.bean.SearchCommenBean;
import com.example.module_employees_world.bean.SearchPostBean;
import com.example.module_employees_world.utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;

import java.util.List;

/**
 * 员工天地-搜索结果-评价列表
 */
public class SearchCommentAdapter extends ListBaseAdapter {

    public SearchCommentAdapter(Context context, List<SearchCommenBean> searchCommenBeans) {
        super(searchCommenBeans, context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchCommenBean searchCommenBean = (SearchCommenBean) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_comment, null);
            viewHolder.tvPostTitle = convertView.findViewById(R.id.tvPostTitle);
            viewHolder.tvTime = convertView.findViewById(R.id.tvTime);
            viewHolder.ivHead = convertView.findViewById(R.id.ivHead);
            viewHolder.tvPostName = convertView.findViewById(R.id.tvPostName);
            viewHolder.tvComment = convertView.findViewById(R.id.tvComment);
            viewHolder.tvPart = convertView.findViewById(R.id.tvPart);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (searchCommenBean.getIs_anonymity() == 1) {
            viewHolder.tvPostName.setText("匿名");
        } else {
            viewHolder.tvPostName.setText(searchCommenBean.getUser_name()+"");
        }
        viewHolder.tvPostTitle.setText(searchCommenBean.getQuestion_title());
        viewHolder.tvComment.setText(searchCommenBean.getContent());
        //头像统一由服务器获取
        if (!TextUtils.isEmpty(searchCommenBean.getAvatar()))
            Picasso.with(context).load(searchCommenBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.ivHead);
        viewHolder.tvTime.setText(searchCommenBean.getCreated_at());
        viewHolder.tvPart.setText(searchCommenBean.getDepartment_name());
        viewHolder.tvComment.setText(searchCommenBean.getContent());
        return convertView;
    }

    class ViewHolder {
        ImageView ivHead;
        TextView tvPostName, tvPart, tvTime, tvComment, tvPostTitle;
    }
}
