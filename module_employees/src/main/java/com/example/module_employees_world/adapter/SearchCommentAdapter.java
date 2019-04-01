package com.example.module_employees_world.adapter;

import android.content.Context;
import android.text.Html;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 员工天地-搜索结果-评价列表
 */
public class SearchCommentAdapter extends ListBaseAdapter {
    private String keyword;

    public SearchCommentAdapter(Context context, String keyword, List<SearchCommenBean> searchCommenBeans) {
        super(searchCommenBeans, context);
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
            viewHolder.tvPostName.setText(searchCommenBean.getUser_name() + "");
        }
        //头像统一由服务器获取
        if (!TextUtils.isEmpty(searchCommenBean.getAvatar()))
            Picasso.with(context).load(searchCommenBean.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(viewHolder.ivHead);
        viewHolder.tvPart.setText(searchCommenBean.getDepartment_name());
        viewHolder.tvTime.setText(searchCommenBean.getCreated_at());

        String comment = searchCommenBean.getContent();
        if (comment.indexOf(keyword)==-1){
            viewHolder.tvComment.setText(comment);
        }else {
            int lengthKeyword = keyword.length();
            String htmlTitle = "";
            String start = "<font color='#007AFF'>";
            String end = "</font>";
            int lastPosition = 0;
            int lengthTitle = comment.length();
            while (lastPosition < lengthTitle){
                int subPosition = comment.indexOf(keyword, lastPosition);
                if (subPosition < 0) {
                    if (lastPosition < lengthTitle){
                        htmlTitle = htmlTitle+comment.substring(lastPosition);
                    }
                    break;
                } else {
                    htmlTitle = htmlTitle + comment.substring(lastPosition,subPosition) + start + keyword + end;
                    lastPosition = subPosition + lengthKeyword;
                }
            }
            viewHolder.tvComment.setText(Html.fromHtml(htmlTitle));
        }
        viewHolder.tvPostTitle.setText(searchCommenBean.getQuestion_title());
        return convertView;
    }

    class ViewHolder {
        ImageView ivHead;
        TextView tvPostName, tvPart, tvTime, tvComment, tvPostTitle;
    }
}
