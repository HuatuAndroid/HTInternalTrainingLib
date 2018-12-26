package com.jungan.www.module_blackplay.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jungan.www.module_blackplay.R;
import com.jungan.www.module_blackplay.activity.PBRouterListener;
import com.baijiahulian.player.bean.VideoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szw on 17/8/29.
 */

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionAdapter.DefinitionHolder> {
    private Context context;
    private List<VideoItem.DefinitionItem> definitionItems;
    private PBRouterListener routerListener;
    private int selectPosition = 0;//记录选中的position

    public DefinitionAdapter(Context context, List<VideoItem.DefinitionItem> definitionItems) {
        this.context = context;
        this.definitionItems = definitionItems;
    }

    public void setRouterListener(PBRouterListener routerListener) {
        this.routerListener = routerListener;
    }

    @Override
    public DefinitionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pb_definition_item, parent, false);
        return new DefinitionHolder(view);
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
    }

    @Override
    public void onBindViewHolder(final DefinitionHolder holder, final int position) {
        if (selectPosition == position) {
            holder.definitioinName.setText(definitionItems.get(position).name);
            holder.definitioinName.setTextColor(ContextCompat.getColor(context, R.color.pb_live_blue));
            holder.definitioinName.setBackgroundResource(R.drawable.shape_definition_bg);
        } else {
            holder.definitioinName.setText(definitionItems.get(position).name);
            holder.definitioinName.setTextColor(ContextCompat.getColor(context, R.color.pb_live_white));
            holder.definitioinName.setBackgroundResource(0);
        }

        holder.definitioinName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routerListener.selectDefinition(definitionItems.get(position).type, position);
                holder.definitioinName.setTextColor(ContextCompat.getColor(context, R.color.pb_live_blue));
                holder.definitioinName.setBackgroundResource(R.drawable.shape_definition_bg);
                selectPosition = position;
            }
        });
    }

    @Override
    public int getItemCount() {
        return definitionItems.size();
    }

    class DefinitionHolder extends RecyclerView.ViewHolder {
        TextView definitioinName;

        public DefinitionHolder(View itemView) {
            super(itemView);
            definitioinName = (TextView) itemView.findViewById(R.id.item_pb_definition_tv);
        }
    }
}

