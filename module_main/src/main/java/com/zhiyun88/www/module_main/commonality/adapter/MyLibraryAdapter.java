package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.MyLibraryListBean;
import com.zhiyun88.www.module_main.library.adapter.LibraryListAdapter;
import com.zhiyun88.www.module_main.library.config.LibraryConfig;

import java.util.List;

public class MyLibraryAdapter extends BaseAdapter{
    private Context mContext;
    private List<MyLibraryListBean> libraryListBeans;
    private LibraryConfig.OnClickCollected mOnItemClickListener;

    public MyLibraryAdapter(Context context, List<MyLibraryListBean> libraryListBeans) {
        this.mContext = context;
        this.libraryListBeans = libraryListBeans;
    }

    @Override
    public int getCount() {
        return libraryListBeans.size();
    }

    @Override
    public MyLibraryListBean getItem(int position) {
        return libraryListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        final MyLibraryListBean myLibraryListBean = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_library, null);
            viewHolder.type = convertView.findViewById(R.id.library_type);
            viewHolder.imageView = convertView.findViewById(R.id.library_image);
            viewHolder.title = convertView.findViewById(R.id.library_title);
            viewHolder.collect = convertView.findViewById(R.id.library_collected);
            viewHolder.subtitle = convertView.findViewById(R.id.library_subtitle);
            viewHolder.library_rl = convertView.findViewById(R.id.library_rl);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image, mContext, myLibraryListBean.getImg(), 4);
        viewHolder.title.setText(myLibraryListBean.getName());
        viewHolder.subtitle.setText(myLibraryListBean.getAbstractX());
        if (myLibraryListBean.getIs_collection().equals("1")) {
            viewHolder.collect.setSelected(true);
        } else {
            viewHolder.collect.setSelected(false);
        }
        if (myLibraryListBean.getExt().equals("pdf")) {
            viewHolder.type.setText("[PDF]");
            viewHolder.type.setTextColor(Color.parseColor("#e60303"));
        } else if (myLibraryListBean.getExt().equals("doc")|| myLibraryListBean.getExt().equals("docx")) {
            viewHolder.type.setText("[WORD]");
            viewHolder.type.setTextColor(Color.parseColor("#005aff"));
        } else if (myLibraryListBean.getExt().equals("xlsx") || myLibraryListBean.getExt().equals("xls")){
            viewHolder.type.setText("[EXEL]");
            viewHolder.type.setTextColor(Color.parseColor("#00cb21"));
        }else if (myLibraryListBean.getExt().equals("ppt")){
            viewHolder.type.setText("[PPT]");
            viewHolder.type.setTextColor(Color.parseColor("#ff7800"));
        }
        viewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener == null) {
                    return;
                }
                mOnItemClickListener.setCollection(myLibraryListBean.getId(), "30860", myLibraryListBean.getIs_collection(), position);
//                viewHolder.collect.setEnabled(false);
            }
        });
        return convertView;
    }
    class ViewHolder {
        ImageView imageView, collect;
        TextView title, subtitle, type;
        RelativeLayout library_rl;
    }

    public void setOnClickCollection(LibraryConfig.OnClickCollected listener) {
        mOnItemClickListener = listener;
    }

    public void updateItem(int index, ListView mListView, String isCollected) {
        int position = mListView.getFirstVisiblePosition();
        if (index - position >= 0) {
            View mView = mListView.getChildAt(index - position);
           ViewHolder holder = (ViewHolder) mView.getTag();
            if (isCollected.equals("1")) {
                holder.collect.setSelected(false);
                libraryListBeans.get(index).setIs_collection("0");
            } else {
                holder.collect.setSelected(true);
                libraryListBeans.get(index).setIs_collection("1");
            }
            holder.collect.setEnabled(true);
            notifyDataSetChanged();
        }
    }
}
