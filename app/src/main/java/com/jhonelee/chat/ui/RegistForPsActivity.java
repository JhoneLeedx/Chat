package com.jhonelee.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.jhonelee.chat.R;
import com.jhonelee.chat.util.Const;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tencent.tls.platform.TLSAccountHelper;
import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSStrAccRegListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * Created by JhoneLee on 2017/4/24.
 */

public class RegistForPsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_passwd)
    EditText etPasswd;


    private TLSAccountHelper account;
    private String TAG = "RegistForPsActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_regist);
        ButterKnife.bind(this);
        toolbar.setTitle("个性化注册");
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
    @OnClick(R.id.btn_register)
    public void click(){
        String name = etName.getText().toString().toLowerCase().trim();
        String pass = etPasswd.getText().toString().trim();
        int reg= account.TLSStrAccReg(name,pass,listener);
        if (reg == TLSErrInfo.INPUT_INVALID){
            Toast.makeText(this, "账号密码不合法", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private TLSStrAccRegListener listener = new TLSStrAccRegListener() {

        @Override
        public void OnStrAccRegSuccess(TLSUserInfo userInfo) {
        /* 成功注册了一个字符串帐号， 可以引导用户使用刚注册的用户名和密码登录 */
            Log.d(TAG, "注册成功");
            startActivity(new Intent(RegistForPsActivity.this,LoginActivity.class));
            finish();
        }

        @Override
        public void OnStrAccRegFail(TLSErrInfo errInfo) {
        /* 注册失败，请提示用户失败原因 */
            Log.d(TAG, errInfo.Title);
            Log.d(TAG, errInfo.Msg);
            Log.d(TAG, errInfo.ExtraMsg);
            Log.d(TAG, errInfo.ErrCode + "");
        }

        @Override
        public void OnStrAccRegTimeout(TLSErrInfo errInfo) {
        /* 网络超时，可能是用户网络环境不稳定，一般让用户重试即可。*/
            Log.d(TAG, "连接超时");
            Log.d(TAG, errInfo.Msg);
            Log.d(TAG, errInfo.Title);
            Log.d(TAG, errInfo.ExtraMsg);
            Log.d(TAG, errInfo.ErrCode + "");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        account = null;
    }
}
