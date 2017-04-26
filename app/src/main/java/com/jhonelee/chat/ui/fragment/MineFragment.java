package com.jhonelee.chat.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jhonelee.chat.MainActivity;
import com.jhonelee.chat.R;
import com.jhonelee.chat.ui.pscenter.NickNameActivity;
import com.jhonelee.chat.ui.pscenter.SignatureActivity;
import com.jhonelee.chat.weidget.CircleImageView;
import com.tencent.TIMCallBack;
import com.tencent.TIMFriendGenderType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by JhoneLee on 2017/4/24.
 */

public class MineFragment extends Fragment {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birth)
    TextView tvBirth;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_nickname)
    TextView tvNickName;
    private String TAG = "MineFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle("个人信息");
        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        initMine();
    }

    @OnClick({R.id.linear_signature, R.id.linear_sex, R.id.linear_birthday,R.id.linear_nickname})
    public void Click(View view) {
        switch (view.getId()) {
            case R.id.linear_signature:
                startActivity(new Intent(getContext(), SignatureActivity.class));
                break;
            case R.id.linear_birthday:
                BirthdayClick();
                break;
            case R.id.linear_sex:
                showSexChooseDialog();
                break;
            case R.id.linear_nickname:
                startActivity(new Intent(getContext(), NickNameActivity.class));
                break;
            default:
                break;
        }
    }
    private String[] sexArry = new String[] { "女", "男" };// 性别选择
    /* 性别选择框 */
    private void showSexChooseDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());// 自定义对话框
        int sex = "女".equals(tvSex.getText().toString())?0:1;
        builder.setSingleChoiceItems(sexArry,sex, new DialogInterface.OnClickListener() {// 2默认的选中

            @Override
            public void onClick(final DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                TIMFriendshipManager.getInstance().setGender(which==0?TIMFriendGenderType.Female:TIMFriendGenderType.Male, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "setGender failed: " + s);
                    }

                    @Override
                    public void onSuccess() {
                        dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
                        initMine();
                    }
                });

            }
        });
        builder.show();// 让弹出框显示
    }
    private void initMine() {
        TIMFriendshipManager.getInstance().getSelfProfile(new TIMValueCallBack<TIMUserProfile>() {
            @Override
            public void onError(int i, String s) {
                //错误码code和错误描述desc，可用于定位请求失败原因
                //错误码code列表请参见错误码表
                Log.e(TAG, "getSelfProfile failed: " + i + " desc");
            }
            @Override
            public void onSuccess(TIMUserProfile timUserProfile) {
                Log.e(TAG, "getSelfProfile succ");
                Log.e(TAG, "identifier: " + timUserProfile.getIdentifier() + " nickName: " + timUserProfile.getNickName()
                        + " remark: " + timUserProfile.getRemark() + " allow: " + timUserProfile.getAllowType());
                Message msg = new Message();
                msg.obj = timUserProfile;
                handler.sendMessage(msg);
            }
        });
    }
    public void BirthdayClick() {
        Calendar now = Calendar.getInstance();
        now.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        DatePickerDialog dialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                Calendar temp = Calendar.getInstance();
                temp.clear();
                temp.set(year, monthOfYear, dayOfMonth);
                TIMFriendshipManager.getInstance().setBirthday(temp.getTimeInMillis()/1000, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e(TAG, "setBirthday failed: " + s);
                    }
                    @Override
                    public void onSuccess() {
                        initMine();
                    }
                });
            }
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        dialog.setMaxDate(Calendar.getInstance());
        Calendar minDate = Calendar.getInstance();
        minDate.set(1970, 1, 1);
        dialog.setMinDate(minDate);
        dialog.vibrate(false);
        dialog.show(((AppCompatActivity)getContext()).getFragmentManager(), "DatePickerDialog");
    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            TIMUserProfile timUserProfile = (TIMUserProfile) msg.obj;
            tvSign.setText(timUserProfile.getSelfSignature());
            tvBirth.setText(new SimpleDateFormat("yyyy-MM-dd").format(timUserProfile.getBirthday()*1000));
            tvSex.setText(timUserProfile.getGender().getValue()==0?"未知":(timUserProfile.getGender().getValue()==1?"男":"女"));
            tvName.setText(timUserProfile.getIdentifier());
            tvNickName.setText(timUserProfile.getNickName());
        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.mine_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.nav_setting) {
            Toast.makeText(getContext(), "setting被点击", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
