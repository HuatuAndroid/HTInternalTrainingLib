package com.baijiayun.videoplayer.ui.playback.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
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

import com.baijiayun.glide.Glide;
import com.baijiayun.glide.load.DataSource;
import com.baijiayun.glide.load.engine.GlideException;
import com.baijiayun.glide.request.RequestListener;
import com.baijiayun.glide.request.RequestOptions;
import com.baijiayun.glide.request.target.Target;
import com.baijiayun.playback.bean.models.imodels.IMessageModel;
import com.baijiayun.playback.context.PBConstants;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.playback.chat.preview.IChatMessageCallback;
import com.baijiayun.videoplayer.ui.utils.Utils;

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
    private IChatMessageCallback callback;

    public PBMessageAdapter(Context context, IChatMessageCallback callback) {
        this.context = context;
        emojiSize = (int) (Utils.getScreenDensity(context) * 32);
        this.callback = callback;
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
        if (messageModel.getFrom().getType() == PBConstants.LPUserType.Teacher) {
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
            if (messageModel.getFrom().getType() == PBConstants.LPUserType.Teacher ||
                    messageModel.getFrom().getType() == PBConstants.LPUserType.Assistant) {
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
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .override(Utils.dip2px(context, 200), Utils.dip2px(context, 150));
            Glide.with(context).load(messageModel.getUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                    imageViewHolder.tvExclamation.setVisibility(View.VISIBLE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                    imageViewHolder.tvExclamation.setVisibility(View.GONE);
                    return false;
                }
            }).apply(options).into(imageViewHolder.ivImg);

            imageViewHolder.ivImg.setOnClickListener(v -> {
                callback.displayImage(messageModel.getUrl());
            });
            if (orientationState == Configuration.ORIENTATION_LANDSCAPE) {
                imageViewHolder.chatImageGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_blue_bg);
            } else {
                imageViewHolder.chatImageGroup.setBackgroundResource(R.drawable.shape_pb_live_item_chat_bg);
            }
        } else if (holder instanceof PBEmojiViewHolder) {
            PBEmojiViewHolder emojiViewHolder = (PBEmojiViewHolder) holder;
            emojiViewHolder.tvName.setText(spanText);
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .error(R.drawable.pb_ic_exit)
                    .override(emojiSize, emojiSize);
            Glide.with(context)
                    .load(messageModel.getUrl())
                    .apply(options)
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

        PBTextViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.pb_item_chat_text);
        }
    }

    private static class PBImageViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvExclamation;
        ImageView ivImg;
        private LinearLayout chatImageGroup;

        PBImageViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.pb_item_chat_image_name);
            ivImg = itemView.findViewById(R.id.pb_item_chat_image);
            tvExclamation = itemView.findViewById(R.id.pb_item_chat_image_exclamation);
            chatImageGroup = itemView.findViewById(R.id.pb_item_chat_image_group);
        }
    }

    private static class PBEmojiViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivEmoji;
        LinearLayout chatEmojiGroup;

        PBEmojiViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.pb_item_chat_emoji_name);
            ivEmoji = itemView.findViewById(R.id.pb_item_chat_emoji);
            chatEmojiGroup = itemView.findViewById(R.id.pb_item_chat_emoji_group);
        }
    }

    public void destroy(){
        if(callback != null){
            callback = null;
        }
        if(context != null){
            context = null;
        }
        messageModelList.clear();
    }
}
