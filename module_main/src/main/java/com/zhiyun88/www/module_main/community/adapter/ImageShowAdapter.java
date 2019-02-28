package com.zhiyun88.www.module_main.community.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baijiahulian.livecore.models.imodels.IForbidChatModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.wb.baselib.permissions.PerMissionsManager;
import com.wb.baselib.permissions.interfaces.PerMissionCall;
import com.wb.baselib.utils.ToastUtils;
import com.wngbo.www.common_postphoto.ISNav;
import com.wngbo.www.common_postphoto.common.Constant;
import com.wngbo.www.common_postphoto.config.ISCameraConfig;
import com.wngbo.www.common_postphoto.config.ISListConfig;
import com.wngbo.www.common_postphoto.ui.ISListActivity;
import com.zhiyun88.www.module_main.R;

import org.junit.Ignore;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

public class ImageShowAdapter extends RecyclerView.Adapter<ImageShowAdapter.ViewHolder>{
    private Context mContext;
    private List<String> pathList;

    public ImageShowAdapter(Context context, List<String> pathList) {
        this.mContext = context;
        this.pathList= pathList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_item_image, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (position == pathList.size()) {
            holder.photo_del.setVisibility(View.GONE);
            holder.imageView.setImageResource(R.drawable.release_topic_add_pic_icon);
        }else {
            holder.photo_del.setVisibility(View.VISIBLE);

            Picasso.with(mContext).load("file://"+pathList.get(position)).placeholder(R.drawable.course_image)
                    .error(R.drawable.course_image)
//                    .resize(96,96)
//                    .centerCrop()
                    .transform(transformation)
                    .into(holder.imageView);
        }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathList.size() < 9 && position == pathList.size()) {
                    PerMissionsManager.newInstance().getUserPerMissions((Activity) mContext, new PerMissionCall() {
                        @Override
                        public void userPerMissionStatus(boolean b) {
                            if (b) {
                                ISListConfig config = new ISListConfig.Builder()
                                        .multiSelect(true)
                                        .rememberSelected(true)
                                        .maxNum(9)
                                        .needCamera(true)
                                        // .backResId()
                                        .build();
                                ISNav.getInstance().toListActivity(mContext, config, 666);
                            }else {
                                ToastUtils.showToast(mContext,"无相应权限" );
                            }
                        }
                    },new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE});

                }
            }
        });
        holder.photo_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Constant.imageList == null || Constant.imageList.size() == 0)return;
                Constant.imageList.remove(position);
                pathList.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pathList.size() < 9?pathList.size()+1 :pathList.size();
    }

    public void setData(List<String> data) {
        this.pathList = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView,photo_del;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.show_image);
            photo_del = itemView.findViewById(R.id.show_image_del);
        }
    }

    Transformation transformation=new Transformation() {
        @Override
        public Bitmap transform(Bitmap source) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            source.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
            source.recycle();
            return bitmap;
        }

        @Override
        public String key() {
            return "aaa";
        }
    };

}
