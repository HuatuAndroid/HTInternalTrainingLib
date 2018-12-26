package com.zhiyun88.www.module_main.commonality.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.commonality.bean.RankingBean;
import com.zhiyun88.www.module_main.commonality.bean.RankingListBean;

import java.util.ArrayList;

public class RankingAdapter extends BaseAdapter {
    private ArrayList<RankingListBean> rankingListBeans;
    private Context mContext;

    public RankingAdapter(Context context, ArrayList<RankingListBean> rankingListBeans) {
        this.mContext = context;
        this.rankingListBeans = rankingListBeans;
    }

    @Override
    public int getCount() {
        return rankingListBeans.size() + 1;
    }

    @Override
    public RankingListBean getItem(int position) {
        if (position == 0) return null;
        return rankingListBeans.get(position-1);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RankingListBean listBean = getItem(position);
        RankingHolder rankingHolder;
        if (convertView == null) {
            rankingHolder = new RankingHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_integral_rank, null);
            rankingHolder.num = convertView.findViewById(R.id.ranking_num);
            rankingHolder.user = convertView.findViewById(R.id.ranking_user);
            rankingHolder.intrgral = convertView.findViewById(R.id.ranking_intrgral);
            convertView.setTag(rankingHolder);
        } else {
            rankingHolder = (RankingHolder) convertView.getTag();
        }
        if (position == 0) {
            rankingHolder.num.setText(R.string.main_ranking);
            rankingHolder.num.setTextColor(mContext.getResources().getColor(R.color.main_text_66));
            rankingHolder.num.setTextSize(13);
            rankingHolder.user.setText(R.string.main_username);
            rankingHolder.user.setTextColor(mContext.getResources().getColor(R.color.main_text_66));
            rankingHolder.user.setTextSize(13);
            rankingHolder.intrgral.setText(R.string.main_The_current_integral);
            rankingHolder.intrgral.setTextColor(mContext.getResources().getColor(R.color.main_text_66));
            rankingHolder.intrgral.setTextSize(13);
        } else {
            rankingHolder.num.setTextSize(18);
            rankingHolder.user.setTextSize(15);
            rankingHolder.intrgral.setTextSize(15);
            if (listBean.getNumber() == 1) {
                rankingHolder.num.setTextColor(mContext.getResources().getColor(R.color.main_text_red_1));
            } else if (listBean.getNumber() == 2) {
                rankingHolder.num.setTextColor(mContext.getResources().getColor(R.color.main_text_red_2));
            } else if (listBean.getNumber() == 3) {
                rankingHolder.num.setTextColor(mContext.getResources().getColor(R.color.main_text_red_3));
            } else {
                rankingHolder.num.setTextColor(mContext.getResources().getColor(R.color.main_title_grey_99));
            }
            rankingHolder.num.setText(listBean.getNumber()+"");
            rankingHolder.user.setText(listBean.getName());
            rankingHolder.intrgral.setText(listBean.getIntegral()+"");
        }
        return convertView;
    }

    class RankingHolder {
        TextView num, intrgral, user;
    }
}
