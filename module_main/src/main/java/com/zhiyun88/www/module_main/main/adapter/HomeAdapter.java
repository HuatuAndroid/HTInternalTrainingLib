package com.zhiyun88.www.module_main.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;
import com.wb.baselib.phone.PhoneUtils;
import com.wb.baselib.utils.ToActivityUtil;
import com.wb.baselib.view.MyListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.zhiyun88.www.module_main.R;
import com.zhiyun88.www.module_main.community.ui.CommunityActivity;
import com.zhiyun88.www.module_main.course.ui.CourseMainActivity;
import com.zhiyun88.www.module_main.information.bean.InformationDataListBean;
import com.zhiyun88.www.module_main.information.ui.InformationActivity;
import com.zhiyun88.www.module_main.library.ui.LibraryActivity;
import com.zhiyun88.www.module_main.main.bean.HomeBean;
import com.zhiyun88.www.module_main.main.bean.HomeCourseBean;
import com.zhiyun88.www.module_main.main.bean.HomeInformationBean;
import com.zhiyun88.www.module_main.main.bean.HomeTransformerBean;
import com.zhiyun88.www.module_main.main.GlideImageLoader;
import com.zhiyun88.www.module_main.train.ui.TrainListActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends BaseAdapter {
    private static final int TYPE_BANNER = 0;
    private static final int TYPE_OFFLINE = 1;
    private static final int TYPE_ONLINE = 2;
    private static final int TYPE_TRADE = 3;
    private static final int BEAN_COUNT = 4;
    private List<HomeBean> homeBeanList;
    private Context mContext;

    public HomeAdapter(Context context, List<HomeBean> homeBeanList) {
        this.mContext = context;
        this.homeBeanList = homeBeanList;
    }

    @Override
    public int getCount() {
        if (homeBeanList.size() == 0)return 0;
        return BEAN_COUNT;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }else if (position == 1) {
            return TYPE_OFFLINE;
        }else if (position == 2) {
            return TYPE_ONLINE;
        }
        return TYPE_TRADE;
    }

    @Override
    public int getViewTypeCount() {
        return BEAN_COUNT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        HomeBean homeBean = homeBeanList.get(0);
        if (type == TYPE_BANNER) {
            return showBanner(convertView,homeBean);
        }else if (type == TYPE_OFFLINE) {
            return showOffLine(convertView,homeBean.getTransformer());
        }else if (type == TYPE_ONLINE) {
            return showOnLine(convertView,homeBean,TYPE_ONLINE);
        }else if (type == TYPE_TRADE) {
            return showOnLine(convertView,homeBean,TYPE_TRADE);
        }
        return null;
    }

    private View showOnLine(View convertView, HomeBean homeBean, int type) {
        OnLineHolder onLineHolder;
        if (convertView == null) {
            onLineHolder = new OnLineHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_home_online, null);
            onLineHolder.mylistView = convertView.findViewById(R.id.online_listview);
            onLineHolder.online_more = convertView.findViewById(R.id.online_more);
            onLineHolder.online_head = convertView.findViewById(R.id.online_head);
            onLineHolder.online_line = convertView.findViewById(R.id.online_line);
            convertView.setTag(onLineHolder);
        } else {
            onLineHolder = (OnLineHolder) convertView.getTag();
        }
        if (type == TYPE_ONLINE) {
            List<HomeCourseBean> homeCourseBeanList = homeBean.getCourse();
            onLineHolder.online_line.setVisibility(View.VISIBLE);
            onLineHolder.online_head.setText("在线好课");
            onLineHolder.online_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToActivityUtil.newInsance().toNextActivity(mContext, CourseMainActivity.class);
                }
            });
            ListViewAdapter listViewAdapter = new ListViewAdapter(mContext,homeCourseBeanList);
            onLineHolder.mylistView.setAdapter(listViewAdapter);
        }else {
            final List<HomeInformationBean> homeInformationBeanList = homeBean.getInformation();
            onLineHolder.online_line.setVisibility(View.GONE);
            onLineHolder.online_head.setText("行业动态");
            onLineHolder.online_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToActivityUtil.newInsance().toNextActivity(mContext, InformationActivity.class);
                }
            });
            final ListViewsAdapter listViewsAdapter = new ListViewsAdapter(mContext,homeInformationBeanList);
            onLineHolder.mylistView.setAdapter(listViewsAdapter);
            onLineHolder.mylistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    HomeInformationBean informationBean = homeInformationBeanList.get(position);
                    int clickCount = Integer.parseInt(informationBean.getClick_rate())+1;
                    informationBean.setClick_rate(clickCount+"");
                    listViewsAdapter.notifyDataSetChanged();
                    new FinestWebView.Builder(mContext)
                            .titleDefault("资讯详情")
                            .updateTitleFromHtml(false)
                            .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                            .iconDefaultColorRes(R.color.main_live_3c)
                            .showIconMenu(false)
                            .titleSizeRes(R.dimen.title2)
                            .webViewJavaScriptEnabled(true)
                            .progressBarHeight(PhoneUtils.newInstance().dip2px(mContext, 3))
                            .progressBarColorRes(R.color.main_live_3c)
                            .titleColorRes(R.color.main_live_3c)
                            .toolbarColorRes(R.color.statusbar_color)
                            .statusBarColorRes(R.color.statusbar_color)
                            .backPressToClose(false)
                            .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                            .showUrl(false)
                            .show(homeInformationBeanList.get(position).getUrl());
                }
            });
        }

        return convertView;
    }

    private View showOffLine(View convertView, List<HomeTransformerBean> transformerBeanList) {
        OffLineHolder offLineHolder;
        if (convertView == null) {
            offLineHolder = new OffLineHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_home_offline, null);
            offLineHolder.recyclerView = convertView.findViewById(R.id.offline_recycle);
            offLineHolder.offline_more = convertView.findViewById(R.id.offline_more);
            convertView.setTag(offLineHolder);
        } else {
            offLineHolder = (OffLineHolder) convertView.getTag();
        }
        offLineHolder.offline_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToActivityUtil.newInsance().toNextActivity(mContext, TrainListActivity.class);
            }
        });
        offLineHolder.recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL,false));
        RecycleAdapter recycleAdapter = new RecycleAdapter(mContext,transformerBeanList);
        offLineHolder.recyclerView.setAdapter(recycleAdapter);
        return convertView;
    }

    private View showBanner(View convertView, final HomeBean bean) {
        BannerHolder bannerHolder;
        if (convertView == null) {
            bannerHolder = new BannerHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_fragment_home_banner, null);
            bannerHolder.banner = convertView.findViewById(R.id.home_banner);
            bannerHolder.course = convertView.findViewById(R.id.home_course);
            bannerHolder.community = convertView.findViewById(R.id.home_community);
            bannerHolder.library = convertView.findViewById(R.id.home_library);
//            bannerHolder.shop = convertView.findViewById(R.id.home_shop);
            convertView.setTag(bannerHolder);
        } else {
            bannerHolder = (BannerHolder) convertView.getTag();
        }
        if(bean==null||bean.getBanner()==null||bean.getBanner().size()==0){
            bannerHolder.banner.setVisibility(View.GONE);
        }else {
            bannerHolder.banner.setVisibility(View.VISIBLE);
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < bean.getBanner().size(); i++) {
                list.add(bean.getBanner().get(i).getUrl_banner_img());
            }
            //设置图片加载器
            bannerHolder.banner.setImageLoader(new GlideImageLoader());
            //设置图片集合
            bannerHolder.banner.setImages(list);
            //设置banner动画效果
            bannerHolder.banner.setBannerAnimation(Transformer.DepthPage);
            //设置自动轮播，默认为true
            bannerHolder.banner.isAutoPlay(true);
            //设置轮播时间
            bannerHolder.banner.setDelayTime(5000);
            //设置指示器位置（当banner模式中有指示器时）
            bannerHolder.banner.setIndicatorGravity(BannerConfig.CENTER);
            //banner设置方法全部调用完毕时最后调用
            bannerHolder.banner.start();

            bannerHolder.banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (bean.getBanner() != null && bean.getBanner().size() >= position
                            && bean.getBanner().get(position-1) != null && !TextUtils.isEmpty(bean.getBanner().get(position-1).getLink())
                            && !"###".equals(bean.getBanner().get(position-1).getLink())) {
                        new FinestWebView.Builder(mContext)
                                .titleDefault("正在加载...")
                                .updateTitleFromHtml(true)
                                .toolbarScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS)
                                .iconDefaultColorRes(R.color.main_live_3c)
                                .showIconMenu(false)
                                .titleSizeRes(R.dimen.title2)
                                .webViewJavaScriptEnabled(true)
                                .progressBarHeight(PhoneUtils.newInstance().dip2px(mContext, 3))
                                .progressBarColorRes(R.color.main_live_3c)
                                .titleColorRes(R.color.main_live_3c)
                                .toolbarColorRes(R.color.statusbar_color)
                                .statusBarColorRes(R.color.statusbar_color)
                                .backPressToClose(false)
                                .setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out, R.anim.slide_left_in, R.anim.slide_right_out)
                                .showUrl(false)
                                .show(bean.getBanner().get(position - 1).getLink());
                    }
                }
            });
            bannerHolder.course.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,CourseMainActivity.class));
                }
            });
            bannerHolder.community.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,CommunityActivity.class));
                }
            });
            bannerHolder.library.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext,LibraryActivity.class));
                }
            });
            /*bannerHolder.shop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mContext.startActivity(new Intent(mContext,ShoppingActivity.class));
                }
            });*/
        }

        return convertView;
    }

    class OnLineHolder {
        TextView online_more,online_head;
        MyListView mylistView;
        View online_line;
    }

    class OffLineHolder {
        TextView offline_more;
        RecyclerView recyclerView;
    }

    class BannerHolder {
        Banner banner;
        TextView course,community,library,shop;
    }
}
