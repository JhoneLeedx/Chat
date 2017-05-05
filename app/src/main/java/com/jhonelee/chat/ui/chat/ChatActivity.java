package com.jhonelee.chat.ui.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.jhonelee.chat.R;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMTextElem;
import com.tencent.TIMValueCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JhoneLee on 2017/4/20.
 */

public class ChatActivity extends AppCompatActivity implements TIMMessageListener{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refresh;
    @BindView(R.id.img_yuyin)
    ImageView imgYuyin;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.et_msg)
    EditText etMsg;
    @BindView(R.id.imbtn_send)
    ImageButton imbtnSend;

    private String TAG = "ChatActivity";
    private String identify;
    private String nickName;
    private TIMConversation conversation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        identify = getIntent().getStringExtra("identify");
        nickName = getIntent().getStringExtra("nickName");
        if (!"".equals(nickName)){
            toolbar.setTitle(nickName);
        }else {
            toolbar.setTitle(identify);
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C,identify);
        TIMManager.getInstance().addMessageListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);//加载menu文件到布局
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.nav_user:
                Toast.makeText(this, "user被点击......", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.imbtn_send,R.id.img_add,R.id.img_yuyin})
    public void Click(View view){
        switch (view.getId()){
            case R.id.imbtn_send:
                sendMsg();
                break;
            case R.id.img_add:
                break;
            case R.id.img_yuyin:
                break;
            default:
                break;
        }
    }

    private void sendMsg(){
        String mssg = etMsg.getText().toString();
        //构造一条消息
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(mssg);

        //将elem添加到消息
        if(msg.addElement(elem) != 0) {
            Log.d("TAG", "addElement failed");
            return;
        }
        //发送消息
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code含义请参见错误码表
                Log.d(TAG, "send message failed. code: " + code + " errmsg: " + desc);
            }
            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e(TAG, "SendMsg ok");
            }
        });
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {

        return false;
    }
}
