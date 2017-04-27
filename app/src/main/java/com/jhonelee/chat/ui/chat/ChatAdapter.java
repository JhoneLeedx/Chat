package com.jhonelee.chat.ui.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jhonelee.chat.util.TypeItem;
import com.tencent.TIMMessage;

import java.util.List;

/**
 * Created by JhoneLee on 2017/4/27.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<TIMMessage> mList;
    private Context mContext;

    public ChatAdapter(List<TIMMessage> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType== TypeItem.TYPE_LEFT.ordinal()){

        }else if (viewType == TypeItem.TYPE_RIGHT.ordinal()){

        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        TIMMessage msg = mList.get(position);
        if (msg.isSelf()){
            return TypeItem.TYPE_RIGHT.ordinal();
        }else {
            return TypeItem.TYPE_LEFT.ordinal();
        }
    }

    class ChatLeftHolder extends RecyclerView.ViewHolder{

        public ChatLeftHolder(View itemView) {
            super(itemView);
        }
    }
    class ChatRightHolder extends RecyclerView.ViewHolder{

        public ChatRightHolder(View itemView) {
            super(itemView);
        }
    }
}
