package com.example.module_employees_world.presenter;

import android.app.Activity;
import android.net.Uri;
import android.text.Html;

import com.example.module_employees_world.bean.NImageBean;
import com.example.module_employees_world.bean.NImageListsBean;
import com.example.module_employees_world.bean.TopicContentItem;
import com.example.module_employees_world.common.TutuPicInit;
import com.example.module_employees_world.contranct.TopicEditContranct;
import com.example.module_employees_world.model.TopicEditModel;
import com.example.module_employees_world.utils.EmojiUtils;
import com.thefinestartist.utils.log.LogUtil;
import com.wb.baselib.app.AppUtils;
import com.wb.baselib.bean.Result;
import com.wb.baselib.http.HttpConfig;
import com.wb.baselib.http.HttpManager;
import com.wb.baselib.http.exception.ApiException;
import com.wb.baselib.http.observer.BaseObserver;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author liuzhe
 * @date 2019/3/21
 */
public class TopicEditPresenter extends TopicEditContranct.Presenter {

    private Activity mActivity;
    public Disposable subscribe;

    public TopicEditPresenter(TopicEditContranct.View iView, Activity mActivity) {

        this.mActivity = mActivity;
        this.mView = iView;
        this.mModel = new TopicEditModel();

    }

    @Override
    public void sendUpdateTopic(String topicId, String title, String content, String is_anonymity) {
        HttpManager.newInstance().commonRequest(mModel.sendUpdateTopic(topicId, title, content, is_anonymity), new BaseObserver<Result>(AppUtils.getContext()) {
            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {
            }

            @Override
            public void onSuccess(Result result) {
                mView.updateTopicSuccess(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }
        });
    }

    @Override
    public void commitImage(Map<String, RequestBody> map) {

        HttpManager.newInstance().commonRequest(mModel.commitImage(map), new BaseObserver<Result<NImageListsBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<NImageListsBean> result) {
                if (result.getData() == null || result.getData().getList().size() == 0) {

                } else {
                    mView.SuccessData(result.getData().getList());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }

    /**
     * 提交帖子
     *
     * @param group_id
     * @param title
     * @param content
     * @param is_anonymity
     * @param type
     */
    @Override
    public void commitTopicData(String group_id, String title, String content, String is_anonymity, String type) {

        HttpManager.newInstance().commonRequest(mModel.commitTopicData(group_id, title, content, is_anonymity, type), new BaseObserver<Result>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result result) {
                mView.closeLoadV();
                mView.commitSuccess(result.getMsg());
            }

            @Override
            public void onFail(ApiException e) {
                mView.closeLoadV();
                mView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }

    public void processImage(TopicContentItem[] topicContentItems) {

        Map<String, File> map = new HashMap<>();
        for (int i = 0; i < topicContentItems.length; i++) {
            File file = new File(Uri.parse(topicContentItems[i].localUrl).getPath());
            map.put("file" + i, file);
        }

        Map<String, RequestBody> bodyMap = HttpManager.newInstance().getRequestBodyMap(map, MediaType.parse("image/*"));
        commitImages(bodyMap);

    }

    public void commitImages(Map<String, RequestBody> map) {

        HttpManager.newInstance().commonRequest(mModel.commitImage(map), new BaseObserver<Result<NImageListsBean>>(AppUtils.getContext()) {

            @Override
            public void onSuccess(Result<NImageListsBean> result) {
                if (result.getData() != null && result.getData().getList().size() != 0) {
                    processImage(result.getData().getList());
                }
            }

            @Override
            public void onFail(ApiException e) {
                mView.closeLoadV();
                mView.showErrorMsg(e.getMessage());
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscribe(d);
            }

            @Override
            public void onComplete() {

            }
        }, mView.binLifecycle());
    }

    public void processImage(List<NImageBean> nImageBeans) {

        mView.showLoadV("提交中....");

        List<TopicContentItem> topicContentItems = mView.getData();

        if (nImageBeans != null) {

            for (NImageBean nImageBean : nImageBeans) {
                for (int i = 0; i < topicContentItems.size(); i++) {
                    if (TopicContentItem.TYPE_IMG.equals(topicContentItems.get(i).type.toString())) {
                        if (topicContentItems.get(i).remoteUrl == null || "".equals(topicContentItems.get(i).remoteUrl)) {
                            topicContentItems.get(i).remoteUrl = nImageBean.getPath();
                            break;
                        }
                    }

                }

            }

        }

        processData(topicContentItems);

    }

    public void processData(List<TopicContentItem> topicContentItems) {

        mView.showLoadV("提交中....");

        List<TopicContentItem> newtopicContentItems = new ArrayList<>();
        for (TopicContentItem topicContentItem : topicContentItems) {

            List<String> strContainData = getStrContainData(topicContentItem.content);

            if (strContainData != null) {
                String content = Html.toHtml(topicContentItem.mEditable).replace("<p dir=\"ltr\">", "")
                        .replace("\n", "").replace("</p>", "");

                for (String string : strContainData) {
                    content = content.replaceFirst("<img src=\"null\">", string);
                }

                topicContentItem.content = content;
            }

            newtopicContentItems.add(topicContentItem);

        }

        String stringContent = "";

        for (TopicContentItem topicContentItem : newtopicContentItems) {

            if (TopicContentItem.TYPE_IMG.equals(topicContentItem.type.toString())) {

                stringContent += "<br><img src=\"" + HttpConfig.newInstance().getmBaseUrl() + "/" + topicContentItem.remoteUrl + "\"><br>";

            } else if (TopicContentItem.TYPE_TXT.equals(topicContentItem.type.toString())) {

                stringContent += topicContentItem.content;

            }

        }

        LogUtil.e("addTutuImg -- " + stringContent);

        mView.commitTopicData(stringContent);

    }


    /**
     *      * 描述：获取字符串中被两个字符（串）包含的所有数据
     *      * @param content 处理字符串
     *      * @return Set<String>
     *     
     */
    public List<String> getStrContainData(String content) {

        if (content == null || content.length() == 0) {
            return null;
        }

        List<String> result = new ArrayList<>();

        String regex = ".*?]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            String key = matcher.group();
            int start = key.indexOf("[");
            int end = key.indexOf("]") + 1;
            if (key.contains("[") && key.contains("]") && end > start) {

                String substring = key.substring(start, end);

                if (TutuPicInit.getToStringNames().contains(substring))
                    result.add(substring);
            }
        }
        return result;
    }
}
