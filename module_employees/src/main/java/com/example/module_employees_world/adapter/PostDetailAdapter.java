package com.example.module_employees_world.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.module_employees_world.R;
import com.wb.baselib.utils.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * author:LIENLIN
 * date:2019/3/20
 * 帖子详情列表
 */
public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder>{

    LayoutInflater inflater;
    Activity context;
    private ArrayList<String> imgList;

    public PostDetailAdapter(Activity context) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        imgList = new ArrayList<>();
        imgList.add("");
        imgList.add("");
        imgList.add("");
        imgList.add("");
        imgList.add("");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        ViewHolder viewHolder = null;
        /*switch (viewType){
            case 0:
                view = inflater.inflate(R.layout.post_detail_top_layout, parent, false);
                viewHolder = new ViewHolder(view,viewType);
                break;
            default:
                view = inflater.inflate(R.layout.post_comment_item, parent, false);
                viewHolder = new ViewHolder(view,viewType);
                break;
        }*/

        view = inflater.inflate(R.layout.post_comment_item, parent, false);
        viewHolder = new ViewHolder(view,viewType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        /*if (position==0){
            holder.tvTitle.setText("欢迎搭建来到这个瓶体多提宝贵意见");
            holder.tvDetailText.setText("        "+"halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见halsdnfasfnlk哈快递费哈利的法拉发哪里上课积分拉克丝剪短发哪里看法呢时间段内发了看不见"+"         ");
            holder.tvImg.setLayoutManager(new GridLayoutManager(context,4));
            holder.tvImg.setAdapter(new ImgAdapter(context,imgList,imgClickListener));
            //显示“展开全部”条件：文本等于五行或者有图片
            holder.tvDetailText.post(new Runnable() {
                @Override
                public void run() {
                    if (holder.tvDetailText.getLineCount()==5||imgList.size()>0){
                        holder.tvOpen.setVisibility(View.VISIBLE);
                        holder.rlOpen.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ToastUtils.showToast(context,"展开");
                                holder.llDev1.setVisibility(View.GONE);
                                holder.llDev2.setVisibility(View.VISIBLE);
                            }
                        });
                    }else {
                        holder.tvOpen.setVisibility(View.INVISIBLE);
                    }
                }
            });

            holder.tvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.llDev1.setVisibility(View.VISIBLE);
                    holder.llDev2.setVisibility(View.GONE);
                    ToastUtils.showToast(context,"收起");
                }
            });
            holder.tvHtml.setText("html");
            // TODO: 2019/3/21 数据暂写死
            setActivityContent("<br\\/><img src='http:\\/\\/peixun.huatu.com\\/uploads\\/images\\/20190304\\/1de6158aab21bf817d82da7c59c3f872.jpg' width='100%' _src='http:\\/\\/peixun.huatu.com\\/uploads\\/images\\/20190304\\/1de6158aab21bf817d82da7c59c3f872.jpg'\\/>",holder.tvHtml);


        }else {
            holder.tvName.setText("李恩林");
        }*/

        holder.tvName.setText("李恩林");

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RecyclerView tvImg;
        private RelativeLayout rlOpen;
        private TextView tvDetailText,tvName,tvOpen,tvHtml,tvClose,tvTitle;
        private LinearLayout llDev1,llDev2;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            /*if (viewType==0){
                tvDetailText = itemView.findViewById(R.id.tv_details_text);
                tvImg = itemView.findViewById(R.id.rv_img);
                rlOpen = itemView.findViewById(R.id.rl_details_open);
                tvOpen = itemView.findViewById(R.id.tv_details_open);
                llDev1 = itemView.findViewById(R.id.ll_dev_1);
                llDev2 = itemView.findViewById(R.id.ll_dev_2);
                tvHtml = itemView.findViewById(R.id.tv_detail_html);
                tvClose = itemView.findViewById(R.id.tv_details_close);
                tvTitle = itemView.findViewById(R.id.tv_detail_title);
            }else {
                tvName = itemView.findViewById(R.id.comment_name);
            }*/
            tvName = itemView.findViewById(R.id.comment_name);
        }
    }


    private static class MyHandler extends Handler{

        WeakReference<PostDetailAdapter> adapterWeakReference;
        public MyHandler(PostDetailAdapter adapter) {
            adapterWeakReference=new WeakReference<PostDetailAdapter>(adapter);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PostDetailAdapter postDetailAdapter = adapterWeakReference.get();
            if (postDetailAdapter!=null){
            }
        }
    }
}
