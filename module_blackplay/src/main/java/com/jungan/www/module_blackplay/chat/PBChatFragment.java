package com.jungan.www.module_blackplay.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jungan.www.module_blackplay.R;
import com.jungan.www.module_blackplay.adapters.PBMessageAdapter;
import com.baijia.player.playback.PBRoom;
import com.baijiahulian.livecore.models.imodels.IMessageModel;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by wangkangfei on 17/8/17.
 */

public class PBChatFragment extends Fragment implements PBChatContract.View {
    //view
    private RecyclerView rvChat;
    //data
    private PBRoom mRoom;
    //adapter
    private PBMessageAdapter messageAdapter;
    private PBChatPresenter pbChatPresenter;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pb_chat, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        messageAdapter = new PBMessageAdapter(getContext(), pbChatPresenter);
        rvChat = view.findViewById(R.id.rv_pb_fragment_chat);
        rvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvChat.setAdapter(messageAdapter);
        if(mRoom != null){
            mRoom.getChatVM().getObservableOfNotifyDataChange()
                    .onBackpressureBuffer(1000)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<List<IMessageModel>>() {
                        @Override
                        public void call(List<IMessageModel> iMessageModels) {
                            messageAdapter.setMessageModelList(iMessageModels);
                            messageAdapter.notifyDataSetChanged();
//                        rvChat.smoothScrollToPosition(messageAdapter.getItemCount());
                            rvChat.scrollToPosition(messageAdapter.getItemCount() - 1);
                        }
                    });
        }
    }

    @Override
    public void setPresenter(PBChatContract.Presenter presenter) {
        pbChatPresenter = (PBChatPresenter) presenter;
    }
}
