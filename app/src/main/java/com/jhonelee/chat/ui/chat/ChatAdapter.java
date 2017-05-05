package com.jhonelee.chat.ui.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jhonelee.chat.R;
import com.jhonelee.chat.util.TypeItem;
import com.jhonelee.chat.weidget.CircleImageView;
import com.tencent.TIMMessage;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JhoneLee on 2017/4/27.
 */

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<TIMMessage> mList;
    private Context mContext;

    public ChatAdapter(List<TIMMessage> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        if (viewType == TypeItem.TYPE_LEFT.ordinal()) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_left_msg, parent, false);
            holder = new ChatLeftHolder(view);
        } else if (viewType == TypeItem.TYPE_RIGHT.ordinal()) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_right_msg, parent, false);
            holder = new ChatRightHolder(view);
        }
        return holder;
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
        if (msg.isSelf()) {
            return TypeItem.TYPE_RIGHT.ordinal();
        } else {
            return TypeItem.TYPE_LEFT.ordinal();
        }
    }

    class ChatLeftHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.leftAvatar)
        CircleImageView leftAvatar;
        @BindView(R.id.sender)
        TextView sender;
        @BindView(R.id.leftMessage)
        TextView leftMessage;

        public ChatLeftHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ChatRightHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rightAvatar)
        CircleImageView rightAvatar;
        @BindView(R.id.rightMessage)
        TextView rightMessage;
        @BindView(R.id.rightDesc)
        TextView rightDesc;
        @BindView(R.id.sending)
        ProgressBar sending;
        @BindView(R.id.sendError)
        ImageView sendError;
        @BindView(R.id.sendStatus)
        RelativeLayout sendStatus;
        public ChatRightHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
