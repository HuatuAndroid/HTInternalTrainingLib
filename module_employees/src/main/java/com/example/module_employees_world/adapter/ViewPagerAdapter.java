package com.example.module_employees_world.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baijiayun.glide.Glide;
import com.example.module_employees_world.R;
import com.example.module_employees_world.common.LocalImageHelper;
import com.wb.baselib.app.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuzhe
 * @date 2019/3/27
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<LocalImageHelper.LocalFile> paths;//大图地址 如果为网络图片 则为大图url
    private Context mContext;
    ArrayList<Integer> size ;
    LocalImageHelper.LocalFile path;
    public ViewPagerAdapter(Context mContext, List<LocalImageHelper.LocalFile> paths, ArrayList<Integer> size){
        this.paths =paths;
        this.size = size ;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (size != null){
            return size.size();
        }
        return paths.size();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {

        View imageLayout = LayoutInflater.from(AppUtils.getContext()).inflate(R.layout.item_album_pager, null);
        viewGroup.addView(imageLayout);
        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.image);

        if (size!=null) {
            path = paths.get(size.get(position));
        }
        else{
            path = paths.get(position);
        }

        Glide.with(mContext)
                .asBitmap()
                .load(path.getOriginalUri())
                .into(imageView);

        return imageLayout;
    }



    @Override
    public int getItemPosition(Object object) {
        //在notifyDataSetChanged时返回None，重新绘制
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int arg1, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }
}