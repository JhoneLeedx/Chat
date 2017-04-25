package com.jhonelee.chat.ui.fragment;

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
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by JhoneLee on 2017/4/24.
 */

public class ContactsFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;

    private String TAG = "ContactsFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle("联系人");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        initFriends();
    }

    private void initFriends(){
        //获取好友列表

        TIMFriendshipManager.getInstance().getFriendList(new TIMValueCallBack<List<TIMUserProfile>>(){
            @Override
            public void onError(int code, String desc){
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "getFriendList failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMUserProfile> result){
                for(TIMUserProfile res : result){
                    Log.e(TAG, "identifier: " + res.getIdentifier() + " nickName: " + res.getNickName()
                            + " remark: " + res.getRemark());

                }
            }
        });
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
        inflater.inflate(R.menu.contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.nav_search){
            Toast.makeText(getContext(), "search被点击", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
