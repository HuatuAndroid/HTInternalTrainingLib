package com.jungan.www.module_blackplay.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jungan.www.module_blackplay.R;
import com.jungan.www.module_blackplay.chat.PBChatPresenter;
import com.jungan.www.module_blackplay.utils.PBDisplayUtils;
import com.baijiahulian.livecore.context.LPConstants;
import com.baijiahulian.livecore.models.imodels.IMessageModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangkangfei on 17/8/17.
 */

public class PBMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MESSAGE_TYPE_TEXT = 0;
    private static final int MESSAGE_TYPE_EMOJI = 1;
    private static final int MESSAGE_TYPE_IMAGE = 2;

    private Context context;
    private int emojiSize;
    private int orientationState;
    private List<IMessageModel> messageModelList = new ArrayList<>();
    private PBChatPresenter pbChatPresenter;

    public PBMessageAdapter(Context context, PBChatPresenter pbChatPresenter) {
        this.context = context;
        emojiSize = (int) (PBDisplayUtils.getScreenDensity(context) * 32);
        this.pbChatPresenter = pbChatPresenter;
    }

    public void setMessageModelList(List<IMessageModel> messageModelList) {
        this.messageModelList.clear();
        this.messageModelList.addAll(messageModelList);
    }

    public void setOrientation(int state) {
        this.orientationState = state;
    }

    @Override
    public int getItemViewType(int position) {
        switch (messageModelList.get(position).getMessageType()) {
            case Text:
                return MESSAGE_TYPE_TEXT;
            case Emoji:
            case EmojiWithName:
                return MESSAGE_TYPE_EMOJI;
            case Image:
                return MESSAGE_TYPE_IMAGE;
            default:
                return MESSAGE_TYPE_TEXT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case MESSAGE_TYPE_TEXT:
                View textView = LayoutInflater.from(context).inflate(R.layout.item_pb_chat_text, parent, false);
                return new PBTextViewHolder(textView);
            case MESSAGE_TYPE_IMAGE:
                View imgView = LayoutInflater.from(context).inflate(R.layout.item_pb_chat_image, parent, false);
                return new PBImageViewHolder(imgView);
            case MESSAGE_TYPE_EMOJI:
                View emojiView = LayoutInflater.from(context).inflate(R.layout.item_pb_chat_emoji, parent, false);
                return new PBEmojiViewHolder(emojiView);
        }
        View textView = LayoutInflater.from(context).inflate(R.layout.item_pb_chat_text, parent, false);
        return new PBTextViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final IMessageModel messageModel = messageModelList.get(position);

        int color;
        if (messageModel.getFrom().getType() == LPConstants.LPUserType.Teacher) {
            color = ContextCompat.getColor(context, R.color.pb_live_blue);
        } else {
            color = ContextCompat.getColor(context, R.color.pb_secondary_text);
        }
        String name = messageModel.getFrom().getName() + "：";
        SpannableString spanText = new SpannableString(name);
        spanText.setSpan(new ForegroundColorSpan(color), 0, name.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        if (holder instanceof PBTextViewHolder) {
            PBTextViewHolder textViewHolder = (PBTextViewHolder) holder;
            textViewHolder.textView.setText(spanText);
            textViewHolder.textView.append(messageModel.getContent());
            if (messageModel.getFrom().getType() == LPConstants.LPUserType.Teacher ||
                    messageModel.getFrom().getType() == LPConstants.LPUserType.Assistant) {
                Linkify.addLinks(textViewHolder.textView, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
            } else {
                textViewHolder.textView.setAutoLinkMask(0);
            }
            if (orientationState == Configuration.ORIENTATION_LANDSCAPE) {
                textViewHolder.textView.setBackgroundResource(R.drawable.shape_pb_live_item_chat_blue_bg);
                textViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.pb_live_white));
            } else {
                textViewHolder.textView.setBackgroundResource(R.drawable.shape_pb_live_item_chat_bg);
                textViewHolder.textView.setTextColor(ContextCompat.getColor(context, R.color.pb_primary_text));
            }
        } else if (holder instanceof PBImageViewHolder) {
            final PBImageViewHolder imageViewHolder = (PBImageViewHolder) holder;
            imageViewHolder.ivImg.setOnClickListener(null);
            imageViewHolder.tvName.setText(spanText);
//            RequestOptions options = new RequestOptions()
//                    .fitCenter()
//                    .override(PBDisplayUtils.dip2px(context, 200), PBDisplayUtils.dip2px(context, 150));
//            Glide.with(context).load(messageModel.getUrl()).listener(new RequestListener<Drawable>() {
//                @Override
//                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
//                    imageViewHolder.tvExclamation.setVisibility(View.VISIBLE);
//                    return false;
//                }
//
//                @Override
//                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
//                    imageViewHolder.tvExclamation.setVisibility(View.GONE);
//                    return false;
//                }
//            }).apply(options).into(imageViewHolder.ivImg);
            Picasso.with(context).load(messageModel.getUrl())
                    .resize(PBDisplayUtils.dip2px(context, 200), PBDisplayUtils.dip2px(context, 150))
                    .into(imageViewHolder.ivImg, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageViewHolder.tvExclamation.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            imageViewHolder.tvExclamation.setVisibility(View.VISIBLE);
                        }
                    });
            imageViewHolder.ivImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pbChatPresenter.routerListener.showBigChatPic(messageModel.getUrl());
                }
            });
            if (orientationState == Configuration.ORIENTATION_LANDSCAPE) {
                imageViewHolder.chatImageGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_blue_bg);
            } else {
                imageViewHolder.chatImageGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_bg);
            }
        } else if (holder instanceof PBEmojiViewHolder) {
            PBEmojiViewHolder emojiViewHolder = (PBEmojiViewHolder) holder;
            emojiViewHolder.tvName.setText(spanText);
            Picasso.with(context).load(messageModel.getUrl())
                    .error(R.drawable.pb_ic_exit)
                    .resize(emojiSize, emojiSize)
                    .into(emojiViewHolder.ivEmoji);
            if (orientationState == Configuration.ORIENTATION_LANDSCAPE) {
                emojiViewHolder.chatEmojiGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_blue_bg);
            } else {
                emojiViewHolder.chatEmojiGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_bg);
            }
        }

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    private static class PBTextViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
//        private LinearLayout chatBackGround;

        PBTextViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.pb_item_chat_text);
//            chatBackGround = (LinearLayout) itemView.findViewById(R.id.pb_item_chat_group);
        }
    }

    private static class PBImageViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvExclamation, tvMask;
        ImageView ivImg;
        private LinearLayout chatImageGroup;

        PBImageViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.pb_item_chat_image_name);
            ivImg = (ImageView) itemView.findViewById(R.id.pb_item_chat_image);
            tvExclamation = (TextView) itemView.findViewById(R.id.pb_item_chat_image_exclamation);
            //tvMask = (TextView) itemView.findViewById(R.id.pb_item_chat_image_mask);
            chatImageGroup = (LinearLayout) itemView.findViewById(R.id.pb_item_chat_image_group);
        }
    }

    private static class PBEmojiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivEmoji;
        LinearLayout chatEmojiGroup;

        PBEmojiViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.pb_item_chat_emoji_name);
            ivEmoji = (ImageView) itemView.findViewById(R.id.pb_item_chat_emoji);
            chatEmojiGroup = (LinearLayout) itemView.findViewById(R.id.pb_item_chat_emoji_group);
        }
    }
}
