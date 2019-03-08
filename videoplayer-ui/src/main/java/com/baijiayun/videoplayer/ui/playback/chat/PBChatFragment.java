package com.baijiayun.videoplayer.ui.playback.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baijiayun.playback.PBRoom;
import com.baijiayun.playback.util.LPRxUtils;
import com.baijiayun.videoplayer.ui.R;
import com.baijiayun.videoplayer.ui.playback.adapters.PBMessageAdapter;
import com.baijiayun.videoplayer.ui.playback.chat.preview.IChatMessageCallback;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;


/**
 * Created by wangkangfei on 17/8/17.
 */

public class PBChatFragment extends Fragment{
    //view
    private RecyclerView rvChat;
    //data
    private PBRoom mRoom;
    //adapter
    private PBMessageAdapter messageAdapter;
    private Disposable chatDisposable;

    public void setRoom(PBRoom room) {
        this.mRoom = room;
        mRoom.getChatVM();
    }

    public void setOrientation(int state) {
        if(messageAdapter != null){
            messageAdapter.setOrientation(state);
        }
        if(rvChat != null){
            rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
            rvChat.setAdapter(messageAdapter);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pb_chat, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        messageAdapter = new PBMessageAdapter(getContext(), (IChatMessageCallback) getActivity());
        rvChat = view.findViewById(R.id.rv_pb_fragment_chat);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChat.setAdapter(messageAdapter);
        if(mRoom != null){
            chatDisposable = mRoom.getChatVM().getObservableOfNotifyDataChange()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(iMessageModels -> {
                        messageAdapter.setMessageModelList(iMessageModels);
                        messageAdapter.notifyDataSetChanged();
                        rvChat.scrollToPosition(messageAdapter.getItemCount() - 1);
                    });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LPRxUtils.dispose(chatDisposable);
        messageAdapter.destroy();
    }
}
