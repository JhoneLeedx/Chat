package com.jhonelee.chat.ui.contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jhonelee.chat.R;
import com.jhonelee.chat.listener.ContactListener;
import com.jhonelee.chat.weidget.CircleImageView;
import com.tencent.TIMUserProfile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JhoneLee on 2017/4/26.
 */

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {


    private Context mContext;
    private List<TIMUserProfile> mList;
    private ContactListener listener;

    public ContactAdapter(Context mContext, List<TIMUserProfile> mList, ContactListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.listener = listener;
    }

    public ContactAdapter(Context mContext, List<TIMUserProfile> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_add_friend, parent, false);
        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final TIMUserProfile userProfile = mList.get(position);
        Glide.with(mContext).load(userProfile.getFaceUrl()).into(holder.image);
        holder.textSignname.setText(userProfile.getSelfSignature());
        holder.tvNickname.setText(userProfile.getNickName());
        if (listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.Contact(userProfile);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ContactHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.tv_nickname)
        TextView tvNickname;
        @BindView(R.id.text_signname)
        TextView textSignname;
        public ContactHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
