package com.jhonelee.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jhonelee.chat.R;
import com.jhonelee.chat.util.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSPwdRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by JhoneLee on 2017/4/24.
 */

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String TAG = "RegisterActivity";
    private TLSAccountHelper account;
    private boolean isSendCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        toolbar.setTitle("手机注册");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        account = TLSAccountHelper.getInstance().init(getApplicationContext(), Const.SDK_APPID,Const.ACCOUNT_TYPE,Const.APPVER);
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
    @OnClick({R.id.btn_code,R.id.btn_register,R.id.btn_regist_person,R.id.btn_login})
    public void Click(View view){
        switch (view.getId()){
            case R.id.btn_code:
                if (isSendCode){
                    account.TLSPwdRegReaskCode(listener);
                }else {
                    account.TLSPwdRegAskCode("86-18408249346",listener);
                    isSendCode = true;
                }

                break;
            case R.id.btn_register:
                account.TLSPwdRegVerifyCode(etCode.getText().toString().trim(),listener);
                break;
            case R.id.btn_regist_person:
                startActivity(new Intent(RegisterActivity.this,RegistForPsActivity.class));
                finish();
                break;
            case R.id.btn_login:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            default:
                break;

        }
    }
    private TLSPwdRegListener listener = new TLSPwdRegListener() {
        @Override
        public void OnPwdRegAskCodeSuccess(int i, int i1) {
            /* 请求下发短信成功，
            可以跳转到输入验证码进行校验的界面，
            同时可以开始倒计时,
            (reaskDuration 秒内不可以重发短信，如果在expireDuration 秒之后仍然没有进行短信验证，则应该回到上一步，重新开始流程)；
            在用户输入收到的短信验证码之后，可以调用PwdRegVerifyCode 进行验证。
            */
            Log.d(TAG, "发送验证码成功");
            handler.sendEmptyMessage(0);

        }

        @Override
        public void OnPwdRegReaskCodeSuccess(int i, int i1) {
            /* 重新请求下发短信成功，可以跳转到输入验证码进行校验的界面，并开始倒计时，
            (reaskDuration 秒内不可以再次请求重发，在expireDuration 秒之后仍然没有进行短信验证，则应该回到第一步，重新开始流程)；
            在用户输入收到的短信验证码之后，可以调用PwdRegVerifyCode 进行验证。
            */
            Log.d(TAG, "重新发送验证码成功");
        }

        @Override
        public void OnPwdRegVerifyCodeSuccess() {
            //* 短信验证成功，接下来可以引导用户输入密码，然后调用PwdRegCommit 进行注册的最后一步*//*
            Log.d(TAG, "验证码 验证成功");
            startActivity(new Intent(RegisterActivity.this,SetPassActivity.class));
            finish();
        }

        @Override
        public void OnPwdRegCommitSuccess(TLSUserInfo tlsUserInfo) {
            //* 最终注册成功，接下来可以引导用户进行密码登录了，登录流程请查看相应章节*//*
            Log.d(TAG, "密码注册成功");

        }

        @Override
        public void OnPwdRegFail(TLSErrInfo tlsErrInfo) {
         /* 密码注册过程中任意一步都可以到达这里，
        可以根据tlsErrInfo 中ErrCode, Title, Msg
         给用户弹提示语，引导相关操作
           */
            Log.d(TAG, tlsErrInfo.Msg);
            Log.d(TAG, tlsErrInfo.Title);
            Log.d(TAG, tlsErrInfo.ErrCode + "");
            Log.d(TAG, "密码注册失败");
        }

        @Override
        public void OnPwdRegTimeout(TLSErrInfo tlsErrInfo) {
            // 密码注册过程中任意一步都可以到达这里，顾名思义，网络超时，可能是用户网络环境不稳定，一般让用户重试即可*//*
            Log.d(TAG, "连接超时");
            Log.d(TAG, tlsErrInfo.Msg);
            Log.d(TAG, tlsErrInfo.Title);
            Log.d(TAG, tlsErrInfo.ErrCode + "");
        }
    };
    private int time = 60;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time==0){
                btnCode.setClickable(true);
                btnCode.setText("重新获取验证码");
                time = 60;
            }else {
                time--;
                btnCode.setText("重新发送"+time+"s");
                btnCode.setClickable(false);
                handler.sendEmptyMessageDelayed(0,1000);
            }

        }
    };
}
