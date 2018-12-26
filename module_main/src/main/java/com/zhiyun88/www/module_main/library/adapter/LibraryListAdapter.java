package com.zhiyun88.www.module_main.library.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.library.bean.LibraryDataListBean;
import com.zhiyun88.www.module_main.library.config.LibraryConfig;

import java.util.List;

public class LibraryListAdapter extends BaseAdapter {
    private Context mContext;
    private List<LibraryDataListBean> dataListBeans;
    private LibraryConfig.OnClickCollected mOnItemClickListener;

    public LibraryListAdapter(Context context, List<LibraryDataListBean> dataListBeans) {
        this.mContext = context;
        this.dataListBeans = dataListBeans;
    }

    @Override
    public int getCount() {
        return dataListBeans.size();
    }

    @Override
    public LibraryDataListBean getItem(int position) {
        return dataListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LibraryDataListBean dataListBean = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_library, null);
            viewHolder.type = convertView.findViewById(R.id.library_type);
            viewHolder.imageView = convertView.findViewById(R.id.library_image);
            viewHolder.title = convertView.findViewById(R.id.library_title);
            viewHolder.collect = convertView.findViewById(R.id.library_collected);
            viewHolder.subtitle = convertView.findViewById(R.id.library_subtitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image, mContext, dataListBean.getImg(), 4);
        viewHolder.title.setText(dataListBean.getName());
        viewHolder.subtitle.setText(dataListBean.getAbstractX());
        if (dataListBean.getIs_collection().equals("1")) {
            viewHolder.collect.setSelected(true);
        } else {
            viewHolder.collect.setSelected(false);
        }
        if (dataListBean.getExt().equals("pdf")) {
            viewHolder.type.setText("[PDF]");
            viewHolder.type.setTextColor(Color.parseColor("#e60303"));
        } else if (dataListBean.getExt().equals("doc")|| dataListBean.getExt().equals("docx")) {
            viewHolder.type.setText("[WORD]");
            viewHolder.type.setTextColor(Color.parseColor("#005aff"));
        } else if (dataListBean.getExt().equals("xlsx") || dataListBean.getExt().equals("xls")){
            viewHolder.type.setText("[EXEL]");
            viewHolder.type.setTextColor(Color.parseColor("#00cb21"));
        }else if (dataListBean.getExt().equals("ppt")){
            viewHolder.type.setText("[PPT]");
            viewHolder.type.setTextColor(Color.parseColor("#ff7800"));
        }
        viewHolder.collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener == null) return;
                mOnItemClickListener.setCollection(dataListBean.getId(), "30860", dataListBean.getIs_collection(), position);
                viewHolder.collect.setEnabled(false);
            }
        });
        return convertView;
    }

    class ViewHolder {
        ImageView imageView, collect;
        TextView title, subtitle, type;
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
                dataListBeans.get(index).setIs_collection("0");
            } else {
                holder.collect.setSelected(true);
                dataListBeans.get(index).setIs_collection("1");
            }
            holder.collect.setEnabled(true);
            notifyDataSetChanged();
        }
    }

}
