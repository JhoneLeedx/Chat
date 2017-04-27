package com.jhonelee.chat.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jhonelee.chat.MainActivity;
import com.jhonelee.chat.R;
import com.jhonelee.chat.ui.ContactFriendActivity;
import com.tencent.TIMConversation;
import com.tencent.TIMManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JhoneLee on 2017/4/24.
 */

public class SessionFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;

    private String TAG = "SessionFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle("会话");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        initSession();
    }

    private void initSession(){
        //获取会话个数
        long cnt = TIMManager.getInstance().getConversationCount();
        //遍历会话列表
        for(long i = 0; i < cnt; ++i) {
            //根据索引获取会话
            TIMConversation conversation =
                    TIMManager.getInstance().getConversationByIndex(i);
            Log.d(TAG, "get conversation. type: " + conversation.getType());
        }
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.session_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.nav_add){
            Toast.makeText(getContext(), "add被点击", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), ContactFriendActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
