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

import com.jhonelee.chat.R;
import com.jhonelee.chat.listener.ContactListener;
import com.jhonelee.chat.ui.contact.ContactAdapter;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JhoneLee on 2017/4/25.
 */

public class ContactFriendActivity extends AppCompatActivity implements TextView.OnEditorActionListener,ContactListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private String TAG = "ContactFriendActivity";
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
        adapter = new ContactAdapter(this,list,this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }
    @OnClick(R.id.ibt_search)
    public void Click(){
        Log.d(TAG, "search user failed, code:" +etSearch.getText().toString().trim());
        //通过账号查找
        TIMFriendshipManager.getInstance().searchFriend(etSearch.getText().toString().trim(), new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "search user failed, code:" + s);
            }

            @Override
            public void onSuccess(TIMUserProfile timUserProfile) {
                list.clear();
                list.add(timUserProfile);
                handler.sendEmptyMessage(0);
            }
        });
        //通过昵称查找失败
/*        TIMFriendshipManager.getInstance().searchUser(etSearch.getText().toString(),1, 10, new TIMValueCallBack<TIMUserSearchSucc>() {
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
        });*/
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

    @Override
    public void Contact(TIMUserProfile identify) {
        //创建请求列表
        List<TIMAddFriendRequest> reqList = new ArrayList<TIMAddFriendRequest>();
        //添加好友请求
        TIMAddFriendRequest req = new TIMAddFriendRequest();
        req.setAddrSource("AddSource_Type_app");//添加来源，固定字串，在页面上申请，留空表示未知来源
        req.setAddWording("add me");//添加请求说明，最大 120 字节，如果用户设置为添加好友需要审核，对方会收到此信息并决定是否通过。
        req.setIdentifier(identify.getIdentifier());//设置添加好友的identifier
        req.setRemark("测试");//添加成功后给用户的备注信息，最大96字节
        reqList.add(req);

        TIMFriendshipManager.getInstance().addFriend(reqList, new TIMValueCallBack<List<TIMFriendResult>>() {
            @Override
            public void onError(int code, String desc){
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "addFriend failed: " + code + " desc");
            }

            @Override
            public void onSuccess(List<TIMFriendResult> result){
                Log.e(TAG, "addFriend succ");
                for(TIMFriendResult res : result){
                    Log.e(TAG, "identifier: " + res.getIdentifer() + " status: " + res.getStatus());
                }
            }
        });
    }
}
