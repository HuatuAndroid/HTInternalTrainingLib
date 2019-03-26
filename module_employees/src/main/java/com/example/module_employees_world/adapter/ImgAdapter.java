package com.example.module_employees_world.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.module_employees_world.R;

import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/21
 * 帖子闲情预览图
 */
public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    List<String> imgList;
    LayoutInflater inflater;
    View.OnClickListener listener;

    public ImgAdapter(Context context, List<String> imgList, View.OnClickListener listener) {
        this.imgList=imgList;
        this.listener=listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.img_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivImg.setImageResource(R.drawable.morentouxiang);
        if (position==4){
            if (imgList.size()>4){
                holder.tvNumber.setVisibility(View.VISIBLE);
                holder.tvNumber.setText("+"+(imgList.size()-4));
            }else {
                holder.tvNumber.setVisibility(View.GONE);
            }
        }else {
            holder.tvNumber.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        int count=imgList.size()>=4?4:imgList.size();
        return count;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImg;
        private TextView tvNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = itemView.findViewById(R.id.iv_img);
            tvNumber = itemView.findViewById(R.id.tv_nummber);
        }
    }
}
