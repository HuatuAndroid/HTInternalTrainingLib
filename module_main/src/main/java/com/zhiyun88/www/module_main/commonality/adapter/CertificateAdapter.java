package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.wb.baselib.image.GlideManager;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.CertificateListBean;
import com.zhiyun88.www.module_main.commonality.ui.CertificateActivity;
import com.zhiyun88.www.module_main.main.adapter.ListViewAdapter;

import java.util.List;

public class CertificateAdapter extends BaseAdapter {
    private Context mContext;
    private List<CertificateListBean> certificateListBeans;

    public CertificateAdapter(Context context, List<CertificateListBean> certificateListBeans) {
        this.mContext = context;
        this.certificateListBeans = certificateListBeans;
    }

    @Override
    public int getCount() {
        return certificateListBeans.size();
    }

    @Override
    public CertificateListBean getItem(int position) {
        return certificateListBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        CertificateListBean certificateListBean = getItem(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_item_certificate, null);
            viewHolder.imageView = convertView.findViewById(R.id.certificate_image);
            viewHolder.title = convertView.findViewById(R.id.certificate_title);
            viewHolder.subtitle = convertView.findViewById(R.id.certificate_subtitle);
            viewHolder.time = convertView.findViewById(R.id.certificate_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GlideManager.getInstance().setRoundPhoto(viewHolder.imageView, R.drawable.course_image,mContext , certificateListBean.getCertificate_img(), 4);
        viewHolder.title.setText(certificateListBean.getTitle());
        viewHolder.subtitle.setText("获得条件: "+certificateListBean.getShop_title());
        viewHolder.time.setText("获得时间: "+certificateListBean.getCreated_at());
        return convertView;
    }
    class ViewHolder {
        ImageView imageView;
        TextView title,subtitle,time;
    }
}
