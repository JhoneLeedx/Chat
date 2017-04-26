package com.jhonelee.chat.ui.pscenter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jhonelee.chat.R;
import com.jhonelee.chat.weidget.EditsView;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendshipManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by JhoneLee on 2017/4/26.
 */

public class SignatureActivity extends AppCompatActivity implements TextView.OnEditorActionListener {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_search)
    EditsView etSearch;

    private String TAG = "SignatureActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);
        ButterKnife.bind(this);
        toolbar.setTitle("个性签名");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etSearch.setOnEditorActionListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            TIMFriendshipManager.getInstance().setSelfSignature(etSearch.getText().toString().toLowerCase().trim(), new TIMCallBack() {
                @Override
                public void onError(int i, String s) {
                    Log.d(TAG,s);
                }
                @Override
                public void onSuccess() {
                    finish();
                }
            });
        }
        return false;
    }
}