package com.example.module_employees_world.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * author:LIENLIN
 * date:2019/3/26
 * 帖子詳情
 */
public class PostDetailBean implements Parcelable{

    /**
     * question_info : {"id":144,"group_id":38,"title":"了了了了了了了了了了","content":"哦了了了了<br/><img src='http://test-px.huatu.com/uploads/images/20181129/9552a2232a66c9e4aecc0495b2a8ce08.jpg' width='100%' _src='http://test-px.huatu.com/uploads/images/20181129/9552a2232a66c9e4aecc0495b2a8ce08.jpg'/>","like_count":0,"read_count":3,"comment_count":0,"is_essence":0,"is_top":0,"is_recommend":0,"is_anonymity":0,"type":1,"created_id":31192,"created_at":"2018-11-29","updated_at":"2018-11-29 14:50:06","is_del":0,"delete_at":null,"solve_status":0,"department_name":"研发组","group_name":"娱乐吧","user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62","allow_del":1,"content_text":"哦了了了了<br/>","content_img":"<img src='http://test-px.huatu.com/uploads/images/20181129/9552a2232a66c9e4aecc0495b2a8ce08.jpg' width='100%' _src='http://test-px.huatu.com/uploads/images/20181129/9552a2232a66c9e4aecc0495b2a8ce08.jpg'/>","is_collect":1,"is_like":0}
     * recommend_list : [{"id":212,"group_id":49,"title":"再测试","content":"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<title><\/title>\n<meta name=\"Generator\" content=\"Cocoa HTML Writer\">\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 40.0px; font: 28.4px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}\np.p2 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 23.0px; font: 16.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}\nspan.s1 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 28.44pt; font-kerning: none}\nspan.s2 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 28.44pt; font-kerning: none}\nspan.s3 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}\nspan.s4 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}\n<\/style>\n<\/head>\n<body>\n<p class=\"p1\"><span class=\"s1\">无<\/span><span class=\"s2\"> <\/span><span class=\"s1\">有<\/span><span class=\"s3\"><img src=\"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<title><\/title>\n<meta name=\"Generator\" content=\"Cocoa HTML Writer\">\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 31.0px; font: 21.3px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}\np.p2 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 31.0px; font: 16.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000; min-height: 22.4px}\np.p3 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 18.0px; font: 12.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}\nspan.s1 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 21.33pt; font-kerning: none}\nspan.s2 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 21.33pt; font-kerning: none}\nspan.s3 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 12.00pt; font-kerning: none}\nspan.s4 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}\nspan.s5 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 12.00pt; font-kerning: none}\n<\/style>\n<\/head>\n<body>\n<p class=\"p1\"><span class=\"s1\">无<\/span><span class=\"s2\"> <\/span><span class=\"s1\">有<\/span><span class=\"s3\"><img src=\"http://test-px.huatu.com/uploads/images/20190225/8530f832979f00bf50f9ccca7ae39579.jpg\" alt=\"8530f832979f00bf50f9ccca7ae39579.jpg\"><\/span><\/p>\n<p class=\"p2\"><span class=\"s4\"><\/span><br><\/p>\n<p class=\"p3\"><span class=\"s5\"><br>\n<\/span><\/p>\n<\/body>\n<\/html>\" alt=\"8530f832979f00bf50f9ccca7ae39579.jpg\"><\/span><\/p>\n<p class=\"p2\"><span class=\"s4\"><br>\n<\/span><\/p>\n<p class=\"p2\"><span class=\"s4\"><br>\n<\/span><\/p>\n<\/body>\n<\/html>","content_picture":"","like_count":1,"read_count":309,"comment_count":8,"is_essence":1,"is_top":1,"is_recommend":1,"is_anonymity":0,"type":1,"created_id":30826,"created_at":"2019-01-24 15:36:41","updated_at":"2019-02-26 20:48:17","is_del":0,"delete_at":"2019-02-21 17:52:10","solve_status":0,"group_name":"新建小组","user_name":"刘亚宁","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/69ae7c83663d45bd96240ccb0a9c3cd1.jpeg"},{"id":175,"group_id":40,"title":"ceshifatie","content":"Cmkfkfkfkfofk ffk","content_picture":"uploads/images/20181229/883ce821d69a6f08baea49eefa140e8a.jpg,uploads/images/20181229/427d298263ad92533b0ba3422968cf65.jpg","like_count":0,"read_count":1,"comment_count":0,"is_essence":0,"is_top":0,"is_recommend":1,"is_anonymity":1,"type":1,"created_id":31192,"created_at":"2018-12-29 14:28:41","updated_at":"2019-01-16 14:02:32","is_del":0,"delete_at":null,"solve_status":0,"group_name":"国家大事吧","user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"},{"id":169,"group_id":48,"title":"怎么样","content":"哈哈哈哈哈","content_picture":"uploads/images/20181220/5123bfb8d97d3858b57933349e8c05a6.png,uploads/images/20181220/be4977ab4540735db0b4c037bda663da.png,uploads/images/20181220/264efbe55e289356df86131c27822a58.png","like_count":0,"read_count":147,"comment_count":3,"is_essence":1,"is_top":0,"is_recommend":1,"is_anonymity":1,"type":1,"created_id":31192,"created_at":"2018-12-20 17:11:15","updated_at":"2019-02-26 18:10:09","is_del":0,"delete_at":null,"solve_status":0,"group_name":"小组名称","user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"},{"id":139,"group_id":40,"title":"聊天的进","content":"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<title><\/title>\n<meta name=\"Generator\" content=\"Cocoa HTML Writer\">\n<style type=\"text/css\">\np.p1 {margin: 0.0px 0.0px 0.0px 0.0px; line-height: 15.0px; font: 12.0px 'Times New Roman'; color: #000000; -webkit-text-stroke: #000000}\nspan.s1 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 12.00pt; font-kerning: none}\n<\/style>\n<\/head>\n<body>\n<p class=\"p1\"><span class=\"s1\">吹牛的出去<\/span><\/p>\n<\/body>\n<\/html>","content_picture":"","like_count":50,"read_count":602,"comment_count":22,"is_essence":1,"is_top":1,"is_recommend":1,"is_anonymity":0,"type":1,"created_id":31192,"created_at":"2018-11-28 14:52:21","updated_at":"2019-02-26 20:39:16","is_del":1,"delete_at":"2019-02-27 13:29:58","solve_status":0,"group_name":"国家大事吧","user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"},{"id":138,"group_id":40,"title":"明明您明明","content":"<p>\u200b<img src=\"http://test-px.huatu.com/uploads/images/20181128/2ed1cd335302558d54e5f907df27fbbc.png\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/129cc55fa3770c38509c9635d0266e4e.png\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/1255a53adb6055b59d20584a3e3473af.jpeg\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/49a8dfe78405df274fd1b80cb82d7d17.png\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/00ec3e2c7aed6f74cc505fd967341fe4.png\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/f9bceb935772d9a6c449172d061c8f56.png\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/8239ce79d979b7649447c6e41708d37f.jpeg\"><img src=\"http://test-px.huatu.com/uploads/images/20181128/d39cdcfb01335de9a76b0fc132f5b7e6.png\"><\/p><br>","content_picture":"","like_count":1,"read_count":224,"comment_count":6,"is_essence":1,"is_top":1,"is_recommend":1,"is_anonymity":0,"type":1,"created_id":31192,"created_at":"2018-11-28 14:15:23","updated_at":"2019-02-27 13:00:54","is_del":1,"delete_at":"2019-02-27 13:31:07","solve_status":0,"group_name":"国家大事吧","user_name":"王晨","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/9255f7f0e86a49d19be19820920b0a62"},{"id":91,"group_id":36,"title":"PG one深夜发\u201c正能量\u201d歌，却暴露出自己生活依然滋润","content":"<p>11月23日，有网友发现PG one在深夜更新了自己的微信公众号，万万没想到的是，他这次没有向以往那样表达对李小璐、贾乃亮不满，反而罕见\u201c蹭\u201d热点写歌抨击DG这两天来的表现。<\/p><p><img src=\"https://inews.gtimg.com/newsapp_bt/0/6479793371/1000\"><\/p><p>在歌词中，开头第一句就是毫不留情的表达了对DG的不满意，直言让对方滚出去不要来赚钱。让人意外的是，一向不屑跟热点、随大众的PG one，十分罕见的流露出\u201c正能量\u201d，和他以往\u201c地下\u201d和怼天怼地的态度简直有360度的变化，难道正如他的表现来看，是打算复出了吗？<\/p><p><img src=\"https://inews.gtimg.com/newsapp_bt/0/6479815481/1000\"><\/p><p>结合起PG one这一年来，虽然因为李小璐事件销声匿迹，但是在私底下他依然非常滋润，不仅卖衣服赚了数百万。<\/p><p><img src=\"https://inews.gtimg.com/newsapp_bt/0/6479880386/1000\"><\/p><p>这两个月来不仅发文频繁，晒出自己的近况和近照。<\/p>","content_picture":"","like_count":13,"read_count":147,"comment_count":10,"is_essence":1,"is_top":0,"is_recommend":1,"is_anonymity":0,"type":1,"created_id":30860,"created_at":"2018-11-23 10:06:12","updated_at":"2019-02-25 15:44:44","is_del":0,"delete_at":null,"solve_status":0,"group_name":"9858","user_name":"江军","avatar":"http://htwuhan.oss-cn-beijing.aliyuncs.com/aadb5a89e142468db307f52ec0a0dd96.jpg"}]
     */

    @SerializedName("question_info")
    public QuestionInfoBean questionInfo;
    @SerializedName("recommend_list")
    public List<RecommendListBean> recommendList;

    protected PostDetailBean(Parcel in) {
        questionInfo = in.readParcelable(QuestionInfoBean.class.getClassLoader());
        recommendList = in.createTypedArrayList(RecommendListBean.CREATOR);
    }

    public static final Creator<PostDetailBean> CREATOR = new Creator<PostDetailBean>() {
        @Override
        public PostDetailBean createFromParcel(Parcel in) {
            return new PostDetailBean(in);
        }

        @Override
        public PostDetailBean[] newArray(int size) {
            return new PostDetailBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(questionInfo, flags);
        dest.writeTypedList(recommendList);
    }

    public static class QuestionInfoBean implements Parcelable{

        @SerializedName("id")
        public int id;
        @SerializedName("group_id")
        public int groupId;
        @SerializedName("title")
        public String title;
        @SerializedName("content")
        public String content;
        @SerializedName("like_count")
        public int likeCount;
        @SerializedName("read_count")
        public int readCount;
        @SerializedName("comment_count")
        public int commentCount;
        @SerializedName("is_essence")
        public int isEssence;
        @SerializedName("is_top")
        public int isTop;
        @SerializedName("is_recommend")
        public int isRecommend;
        @SerializedName("is_anonymity")
        public int isAnonymity;
        @SerializedName("type")
        public int type;
        @SerializedName("created_id")
        public int createdId;
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("updated_at")
        public String updatedAt;
        @SerializedName("is_del")
        public int isDel;
        @SerializedName("delete_at")
        public String deleteAt;
        @SerializedName("solve_status")
        public int solveStatus;
        @SerializedName("department_name")
        public String departmentName;
        @SerializedName("group_name")
        public String groupName;
        @SerializedName("user_name")
        public String userName;
        @SerializedName("avatar")
        public String avatar;
        @SerializedName("allow_del")
        public int allowDel;
        @SerializedName("content_text")
        public String contentText;
        @SerializedName("is_collect")
        public int isCollect;
        @SerializedName("is_like")
        public int isLike;
        @SerializedName("content_img")
        public List<String> contentImg;

        protected QuestionInfoBean(Parcel in) {
            id = in.readInt();
            groupId = in.readInt();
            title = in.readString();
            content = in.readString();
            likeCount = in.readInt();
            readCount = in.readInt();
            commentCount = in.readInt();
            isEssence = in.readInt();
            isTop = in.readInt();
            isRecommend = in.readInt();
            isAnonymity = in.readInt();
            type = in.readInt();
            createdId = in.readInt();
            createdAt = in.readString();
            updatedAt = in.readString();
            isDel = in.readInt();
            deleteAt = in.readString();
            solveStatus = in.readInt();
            departmentName = in.readString();
            groupName = in.readString();
            userName = in.readString();
            avatar = in.readString();
            allowDel = in.readInt();
            contentText = in.readString();
            isCollect = in.readInt();
            isLike = in.readInt();
            contentImg = in.createStringArrayList();
        }

        public static final Creator<QuestionInfoBean> CREATOR = new Creator<QuestionInfoBean>() {
            @Override
            public QuestionInfoBean createFromParcel(Parcel in) {
                return new QuestionInfoBean(in);
            }

            @Override
            public QuestionInfoBean[] newArray(int size) {
                return new QuestionInfoBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(groupId);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeInt(likeCount);
            dest.writeInt(readCount);
            dest.writeInt(commentCount);
            dest.writeInt(isEssence);
            dest.writeInt(isTop);
            dest.writeInt(isRecommend);
            dest.writeInt(isAnonymity);
            dest.writeInt(type);
            dest.writeInt(createdId);
            dest.writeString(createdAt);
            dest.writeString(updatedAt);
            dest.writeInt(isDel);
            dest.writeString(deleteAt);
            dest.writeInt(solveStatus);
            dest.writeString(departmentName);
            dest.writeString(groupName);
            dest.writeString(userName);
            dest.writeString(avatar);
            dest.writeInt(allowDel);
            dest.writeString(contentText);
            dest.writeInt(isCollect);
            dest.writeInt(isLike);
            dest.writeStringList(contentImg);
        }
    }

    public static class RecommendListBean implements Parcelable{
        /**
         * id : 212
         * group_id : 49
         * title : 再测试
         * content : <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
         <html>
         <head>
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
         <meta http-equiv="Content-Style-Type" content="text/css">
         <title></title>
         <meta name="Generator" content="Cocoa HTML Writer">
         <style type="text/css">
         p.p1 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 40.0px; font: 28.4px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}
         p.p2 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 23.0px; font: 16.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}
         span.s1 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 28.44pt; font-kerning: none}
         span.s2 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 28.44pt; font-kerning: none}
         span.s3 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}
         span.s4 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}
         </style>
         </head>
         <body>
         <p class="p1"><span class="s1">无</span><span class="s2"> </span><span class="s1">有</span><span class="s3"><img src="<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
         <html>
         <head>
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
         <meta http-equiv="Content-Style-Type" content="text/css">
         <title></title>
         <meta name="Generator" content="Cocoa HTML Writer">
         <style type="text/css">
         p.p1 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 31.0px; font: 21.3px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}
         p.p2 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 31.0px; font: 16.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000; min-height: 22.4px}
         p.p3 {margin: 0.0px 0.0px 12.0px 0.0px; line-height: 18.0px; font: 12.0px 'PingFang SC'; color: #000000; -webkit-text-stroke: #000000}
         span.s1 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 21.33pt; font-kerning: none}
         span.s2 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 21.33pt; font-kerning: none}
         span.s3 {font-family: 'Times New Roman'; font-weight: normal; font-style: normal; font-size: 12.00pt; font-kerning: none}
         span.s4 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 16.00pt; font-kerning: none}
         span.s5 {font-family: 'PingFangSC-Regular'; font-weight: normal; font-style: normal; font-size: 12.00pt; font-kerning: none}
         </style>
         </head>
         <body>
         <p class="p1"><span class="s1">无</span><span class="s2"> </span><span class="s1">有</span><span class="s3"><img src="http://test-px.huatu.com/uploads/images/20190225/8530f832979f00bf50f9ccca7ae39579.jpg" alt="8530f832979f00bf50f9ccca7ae39579.jpg"></span></p>
         <p class="p2"><span class="s4"></span><br></p>
         <p class="p3"><span class="s5"><br>
         </span></p>
         </body>
         </html>" alt="8530f832979f00bf50f9ccca7ae39579.jpg"></span></p>
         <p class="p2"><span class="s4"><br>
         </span></p>
         <p class="p2"><span class="s4"><br>
         </span></p>
         </body>
         </html>
         * content_picture :
         * like_count : 1
         * read_count : 309
         * comment_count : 8
         * is_essence : 1
         * is_top : 1
         * is_recommend : 1
         * is_anonymity : 0
         * type : 1
         * created_id : 30826
         * created_at : 2019-01-24 15:36:41
         * updated_at : 2019-02-26 20:48:17
         * is_del : 0
         * delete_at : 2019-02-21 17:52:10
         * solve_status : 0
         * group_name : 新建小组
         * user_name : 刘亚宁
         * avatar : http://htwuhan.oss-cn-beijing.aliyuncs.com/69ae7c83663d45bd96240ccb0a9c3cd1.jpeg
         */

        @SerializedName("id")
        public int id;
        @SerializedName("group_id")
        public int groupId;
        @SerializedName("title")
        public String title;
        @SerializedName("content")
        public String content;
        @SerializedName("content_picture")
        public String contentPicture;
        @SerializedName("like_count")
        public int likeCount;
        @SerializedName("read_count")
        public int readCount;
        @SerializedName("comment_count")
        public int commentCount;
        @SerializedName("is_essence")
        public int isEssence;
        @SerializedName("is_top")
        public int isTop;
        @SerializedName("is_recommend")
        public int isRecommend;
        @SerializedName("is_anonymity")
        public int isAnonymity;
        @SerializedName("type")
        public int type;
        @SerializedName("created_id")
        public int createdId;
        @SerializedName("created_at")
        public String createdAt;
        @SerializedName("updated_at")
        public String updatedAt;
        @SerializedName("is_del")
        public int isDel;
        @SerializedName("delete_at")
        public String deleteAt;
        @SerializedName("solve_status")
        public int solveStatus;
        @SerializedName("group_name")
        public String groupName;
        @SerializedName("user_name")
        public String userName;
        @SerializedName("avatar")
        public String avatar;

        protected RecommendListBean(Parcel in) {
            id = in.readInt();
            groupId = in.readInt();
            title = in.readString();
            content = in.readString();
            contentPicture = in.readString();
            likeCount = in.readInt();
            readCount = in.readInt();
            commentCount = in.readInt();
            isEssence = in.readInt();
            isTop = in.readInt();
            isRecommend = in.readInt();
            isAnonymity = in.readInt();
            type = in.readInt();
            createdId = in.readInt();
            createdAt = in.readString();
            updatedAt = in.readString();
            isDel = in.readInt();
            deleteAt = in.readString();
            solveStatus = in.readInt();
            groupName = in.readString();
            userName = in.readString();
            avatar = in.readString();
        }

        public static final Creator<RecommendListBean> CREATOR = new Creator<RecommendListBean>() {
            @Override
            public RecommendListBean createFromParcel(Parcel in) {
                return new RecommendListBean(in);
            }

            @Override
            public RecommendListBean[] newArray(int size) {
                return new RecommendListBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeInt(groupId);
            dest.writeString(title);
            dest.writeString(content);
            dest.writeString(contentPicture);
            dest.writeInt(likeCount);
            dest.writeInt(readCount);
            dest.writeInt(commentCount);
            dest.writeInt(isEssence);
            dest.writeInt(isTop);
            dest.writeInt(isRecommend);
            dest.writeInt(isAnonymity);
            dest.writeInt(type);
            dest.writeInt(createdId);
            dest.writeString(createdAt);
            dest.writeString(updatedAt);
            dest.writeInt(isDel);
            dest.writeString(deleteAt);
            dest.writeInt(solveStatus);
            dest.writeString(groupName);
            dest.writeString(userName);
            dest.writeString(avatar);
        }
    }
}
