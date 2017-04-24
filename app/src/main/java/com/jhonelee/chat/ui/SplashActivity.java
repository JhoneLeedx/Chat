package com.jhonelee.chat.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jhonelee.chat.R;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;

import java.util.List;


/**
 * Created by JhoneLee on 2017/4/21.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },3000);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
