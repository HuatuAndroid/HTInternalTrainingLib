package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.course.ui.CourseInfoActivity;
import com.zhiyun88.www.module_main.main.bean.HomeTransformerBean;

import java.util.List;


public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder>{
    private List<HomeTransformerBean> transformerBeanList;
    private Context mContext;

    public RecycleAdapter(Context context, List<HomeTransformerBean> transformerBeanList) {
        this.mContext = context;
        this.transformerBeanList = transformerBeanList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_item_home_recycle, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {
            GlideManager.getInstance().setRoundPhoto(holder.imageView, R.drawable.course_image ,mContext , transformerBeanList.get(position).getCover(), 4);
        }catch (Exception e){
            GlideManager.getInstance().setRoundPhoto(holder.imageView, R.drawable.course_image ,mContext , "http://www.baidu.com", 4);
        }

        holder.title.setText(transformerBeanList.get(position).getTitle());
        holder.num.setText(transformerBeanList.get(position).getStudy_count()+"人报名");
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CourseInfoActivity.class);
                intent.putExtra("isCourseTaskInfo",true);
                intent.putExtra("courseId", transformerBeanList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transformerBeanList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView title,num;
        private CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_image);
            title = itemView.findViewById(R.id.home_title);
            num = itemView.findViewById(R.id.home_num);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }
}
