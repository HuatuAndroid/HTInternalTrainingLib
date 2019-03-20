package com.example.module_employees_world.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.module_employees_world.R;

/**
 * author:LIENLIN
 * date:2019/3/20
 * 帖子详情列表
 */
public class PostDetailAdapter extends RecyclerView.Adapter<PostDetailAdapter.ViewHolder>{

    LayoutInflater inflater;

    public PostDetailAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        ViewHolder viewHolder = null;
        switch (viewType){
            case 0:
                view = inflater.inflate(R.layout.post_detail_top_layout, parent, false);
                viewHolder = new ViewHolder(view);
                break;
            default:
                view = inflater.inflate(R.layout.post_comment_item, parent, false);
                viewHolder = new ViewHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

        public ViewHolder(View itemView) {
            super(itemView);

        }

    }
}
