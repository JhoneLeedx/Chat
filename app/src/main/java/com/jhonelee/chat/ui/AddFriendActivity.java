package com.jhonelee.chat.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jhonelee.chat.R;
import com.jhonelee.chat.ui.contact.ContactAdapter;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMUserSearchSucc;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JhoneLee on 2017/4/25.
 */

public class AddFriendActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private String TAG = "AddFriendActivity";
    private List<TIMUserProfile> list;
    private ContactAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
        toolbar.setTitle("添加好友");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etSearch.setOnEditorActionListener(this);

        list = new ArrayList<>();
        adapter = new ContactAdapter(this,list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }
    @OnClick(R.id.ibt_search)
    public void Click(){
        Log.d(TAG, "search user failed, code:" +etSearch.getText().toString().trim());
        TIMFriendshipManager.getInstance().searchUser(etSearch.getText().toString().toLowerCase().trim(),0, 10, new TIMValueCallBack<TIMUserSearchSucc>() {
            @Override
            public void onError(int code, String desc) {
                Log.d(TAG, "search user failed, code:" + code + " desc:" + desc);
            }

            @Override
            public void onSuccess(TIMUserSearchSucc timUserSearchSucc) {
                Log.d(TAG, "search user succ. total:" + timUserSearchSucc.getTotalNum() + "|vecSize:" + timUserSearchSucc.getInfoList().size());
                list.clear();
                list.addAll(timUserSearchSucc.getInfoList());
                handler.sendEmptyMessage(0);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {

        }
        return false;
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                adapter.notifyDataSetChanged();
            }
        }
    };
}
