package com.jhonelee.chat;


import android.app.Application;
import android.content.Context;

import com.jhonelee.chat.util.Foreground;
import com.tencent.TIMGroupReceiveMessageOpt;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.qalsdk.sdk.MsfSdkUtils;

import java.util.List;


/**
 * Created by JhoneLee on 2017/4/20.
 */


public class ChatApplication extends Application implements TIMMessageListener{

    private String TAG = "ChatApplication";
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        TIMManager.getInstance().addMessageListener(this);
        TIMManager.getInstance().init(this);
        Foreground.init(this);
        context = getApplicationContext();
        if(MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    if (notification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify){
                        //消息被设置为需要提醒
                        notification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                    }
                }
            });
        }
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        return false;
    }
}

