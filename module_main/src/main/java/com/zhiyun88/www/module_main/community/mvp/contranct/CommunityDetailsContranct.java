package com.zhiyun88.www.module_main.community.mvp.contranct;

import com.wb.baselib.base.mvp.BaseModel;
import com.wb.baselib.base.mvp.BasePreaenter;
import com.wb.baselib.base.mvp.MvpView;
import com.wb.baselib.bean.Result;
import com.zhiyun88.www.module_main.community.bean.CommunityDetailsBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentBean;
import com.zhiyun88.www.module_main.community.bean.DetailsCommentListBean;
import com.zhiyun88.www.module_main.community.bean.DetailsLikeBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * 帖子详情
 */
public interface CommunityDetailsContranct {
    interface CommunityDetailsView extends MvpView {
        void isLoadMore(boolean isLoadMore);

        void CommentListData(int total, List<DetailsCommentListBean> list);

        void sendSuccess(String msg);

        void setLikeSuccess(DetailsLikeBean msg);

        /**
         * 删除帖子
         * @param msg
         */
        void deleteTopicSuccess(String msg);
        void deleteConmmentSuccess(String msg,int position);
    }
    interface CommunityDetailsModel extends BaseModel {
        Observable<Result<CommunityDetailsBean>> getCommunityDetails(String question_id);
        Observable<Result<DetailsCommentBean>> getCommentList(String question_id, String st, int page);
        Observable<Result> sendComment(String question_id, String content, String is_anonymity, String comment_id);
        Observable<Result<DetailsLikeBean>> setLike(String question_id);
        Observable<Result> deleteTopic(String topicId);
        Observable<Result> deleteConmment(String conmmentId);

    }
    abstract class CommunityDetailsPresenter extends BasePreaenter<CommunityDetailsView,CommunityDetailsModel> {
        public abstract void getCommunityDetails(String question_id);
        public abstract void getCommentList(String question_id,String st,int page);
        public abstract void sendComment(String question_id,String content,String is_anonymity,String comment_id);
        public abstract void setLike(String question_id);
        public abstract void deleteTopic(String topicId);
        public abstract void deleteConmment(String conmmentId,int position);
    }
}
