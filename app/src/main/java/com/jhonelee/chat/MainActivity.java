package com.jhonelee.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.jhonelee.chat.ui.ChatActivity;
import com.jhonelee.chat.ui.PersonMsgActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("首页");
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn,R.id.btn_1})
    public void Click(View v) {
                Intent in = new Intent();
        switch (v.getId()){
            case R.id.btn:
                Toast.makeText(this, "btn被点击了", Toast.LENGTH_SHORT).show();
                in.setClass(this,ChatActivity.class);
                startActivity(in);
                break;
            case R.id.btn_1:
                Toast.makeText(this, "btn1被点击了", Toast.LENGTH_SHORT).show();
                in.setClass(this,PersonMsgActivity.class);
                startActivity(in);
                break;
            default:
                break;
        }

        //startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }

}
