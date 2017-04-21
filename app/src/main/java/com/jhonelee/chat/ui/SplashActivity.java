package com.jhonelee.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;

import java.util.List;

import tencent.tls.platform.TLSLoginHelper;

/**
 * Created by JhoneLee on 2017/4/21.
 */

public class SplashActivity extends AppCompatActivity implements TIMMessageListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },3000);
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
