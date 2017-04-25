package com.jhonelee.chat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import com.jhonelee.chat.ui.fragment.ContactsFragment;
import com.jhonelee.chat.ui.fragment.MineFragment;
import com.jhonelee.chat.ui.fragment.SessionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Fragment isFragment;
    private Fragment mineFragment;
    private Fragment contactFragment;
    private Fragment sessionFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        addFirstFragment();
    }
    private void addFirstFragment(){
       contactFragment = new ContactsFragment();
        isFragment = contactFragment;
        getSupportFragmentManager().beginTransaction().add(R.id.linearCenter,isFragment).commit();
    }
    @OnClick({R.id.rb_mine,R.id.rb_msg,R.id.rb_contract})
    public void Click(View v) {
        switch (v.getId()){
            case R.id.rb_mine:
                Toast.makeText(this, "btn被点击了", Toast.LENGTH_SHORT).show();
                if (mineFragment==null){
                    mineFragment = new MineFragment();
                }
                switchContent(isFragment,mineFragment);
                break;
            case R.id.rb_msg:
                if (sessionFragment==null){
                    sessionFragment = new SessionFragment();
                }
                switchContent(isFragment,sessionFragment);
                Toast.makeText(this, "btn1被点击了", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb_contract:
                if (contactFragment==null){
                    contactFragment = new ContactsFragment();
                }
                switchContent(isFragment,contactFragment);
                Toast.makeText(this, "btn1被点击了", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        //startActivity(new Intent(MainActivity.this, ChatActivity.class));
    }
    public void switchContent(Fragment from, Fragment to) {
        if (isFragment != to) {
            isFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            //添加渐隐渐现的动画
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(from).add(R.id.linearCenter, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
        }
    }
}
