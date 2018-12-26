package com.zhiyun88.www.module_main.course.adapter;

import android.content.Context;
import android.print.PrinterId;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wb.baselib.adapter.ListBaseAdapter;
import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.bean.CommentListBean;
import com.zhiyun88.www.module_main.course.bean.CommentListData;
import com.zhiyun88.www.module_main.course.view.MyRatingBar;
import com.zhiyun88.www.module_main.utils.CircleTransform;

import java.util.List;

public class CommentListAdapter extends ListBaseAdapter<CommentListData> {
    private Context mContext;
    public CommentListAdapter(List<CommentListData> list, Context context) {
        super(list, context);
        this.mContext=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentListHolder holder=null;
        CommentListData commentListData=getItem(position);
        if(convertView==null){
            holder=new CommentListHolder();
            convertView= LayoutInflater.from(mContext).inflate(R.layout.main_commentlist_item,null);
            holder.pfxx_rb=convertView.findViewById(R.id.pfxx_rb);
            holder.user_img=convertView.findViewById(R.id.user_img);
            holder.user_nice_tv=convertView.findViewById(R.id.user_nice_tv);
            holder.time_tv=convertView.findViewById(R.id.time_tv);
            holder.comment_tv=convertView.findViewById(R.id.comment_tv);
            convertView.setTag(holder);
        }else {
            holder= (CommentListHolder) convertView.getTag();
        }
        holder.pfxx_rb.setClickable(false);
        holder.pfxx_rb.setStar(Float.parseFloat(commentListData.getGrade()));
        holder.comment_tv.setText(commentListData.getContent());
        holder.time_tv.setText(commentListData.getCreated_at());
        holder.user_nice_tv.setText(commentListData.getCreated_name());
        try {
            Picasso.with(mContext).load(commentListData.getAvatar()).error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.user_img);
        }catch (Exception e){
            Picasso.with(mContext).load("http://www.baidu.com").error(R.drawable.user_head).placeholder(R.drawable.user_head).transform(new CircleTransform()).into(holder.user_img);
        }

//        GlideManager.getInstance().setGlideRoundTransImage(holder.user_img,R.drawable.user_head,mContext,commentListData.getAvatar());
        return convertView;
    }
    class CommentListHolder{
        ImageView user_img;
        TextView user_nice_tv,time_tv,comment_tv;
        MyRatingBar pfxx_rb;
    }
}
