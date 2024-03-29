package com.jhonelee.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jhonelee.chat.MainActivity;
import com.jhonelee.chat.R;
import com.jhonelee.chat.util.Const;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by JhoneLee on 2017/4/20.
 */

public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener{


    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_passwd)
    EditText etPasswd;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.code_linear)
    LinearLayout codeLinear;
    private TLSLoginHelper loginHelper;
    private String TAG = "LoginActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
        codeLinear.setVisibility(View.GONE);
        loginHelper = TLSLoginHelper.getInstance().init(getApplicationContext(), Const.SDK_APPID, Const.ACCOUNT_TYPE, Const.APPVER);
        etPasswd.setOnEditorActionListener(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void LoginRegister(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                //  account.TLSPwdRegAskCode("86-18382409165", this);
                loginHelper.TLSPwdLogin(etName.getText().toString().toLowerCase().toString(), etPasswd.getText().toString().getBytes(), loginListener);
                if (!"".equals(etCode.getText().toString())){
                    loginHelper.TLSPwdLoginVerifyImgcode(etCode.getText().toString(),loginListener);
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_code:
                //account.TLSPwdRegCommit (etPasswd.getText().toString().trim(), this);
              /*  int result = account.TLSStrAccReg(etName.getText().toString().trim().toLowerCase(), etPasswd.getText().toString().trim(), listener);
                if (result == TLSErrInfo.INPUT_INVALID)
                    Log.d(TAG, "引导用户输入合法的用户名和密码");*/
                loginHelper.TLSPwdLoginReaskImgcode(loginListener);
                break;
            default:
                break;
        }
    }

    private TLSPwdLoginListener loginListener = new TLSPwdLoginListener() {
        @Override
        public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {


            TIMUser user = new TIMUser();
            user.setAppIdAt3rd(Const.SDK_APPID+"");
            user.setIdentifier(tlsUserInfo.identifier);
            user.setAccountType(tlsUserInfo.accountType+"");
            //登录到聊天系统
            TIMManager.getInstance().login(Integer.parseInt(String.valueOf(Const.SDK_APPID)), user, Const.PUBLIC_KEY, new TIMCallBack() {
                @Override
                public void onError(int i, String s) {
                    Log.d(TAG,s);
                    Log.d(TAG,""+i);
                }
                @Override
                public void onSuccess() {
                    TIMManager.getInstance().addMessageListener(messageListener);
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });
        }

        @Override
        public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
            Message message = new Message();
            message.obj = bytes;
            handler.sendMessage(message);
        }

        @Override
        public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
            Message message = new Message();
            message.obj = bytes;
            handler.sendMessage(message);
        }

        @Override
        public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
            Log.d(TAG, tlsErrInfo.Msg);
            Log.d(TAG, tlsErrInfo.Title);
            Log.d(TAG, tlsErrInfo.ErrCode + "");
            Log.d(TAG, "登录失败");
        }

        @Override
        public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
            Log.d(TAG, "连接超时");
            Log.d(TAG, tlsErrInfo.Msg);
            Log.d(TAG, tlsErrInfo.Title);
            Log.d(TAG, tlsErrInfo.ErrCode + "");
        }
    };
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            codeLinear.setVisibility(View.VISIBLE);
            byte[] bytes = (byte[]) msg.obj;
            btnCode.setText(new String(bytes));
        }
    };
    private TIMMessageListener messageListener = new TIMMessageListener() {
        @Override
        public boolean onNewMessages(List<TIMMessage> list) {
            return false;
        }
    };

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }
}
