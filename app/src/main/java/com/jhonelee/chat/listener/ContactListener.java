package com.jhonelee.chat.listener;

import com.tencent.TIMUserProfile;

/**
 * Created by JhoneLee on 2017/4/27.
 */

public interface ContactListener {

    void Contact(TIMUserProfile identify);
}
