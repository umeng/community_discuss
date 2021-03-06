/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.common.ui.util.textspan;

import android.content.Context;
import android.view.View;

import com.umeng.comm.core.beans.CommUser;
import com.umeng.common.ui.listener.FrinendClickSpanListener;


public class UserClickSpan extends AbsClickSpan {

    CommUser mUser;
    Context mContext;
    FrinendClickSpanListener frinendClickSpanListener;
    public UserClickSpan(Context context, CommUser user,FrinendClickSpanListener frinendClickSpanListener) {
        mUser = user;
        mContext = context;
        this.frinendClickSpanListener = frinendClickSpanListener;
    }
    //// TODO: 16/1/14 反射方式跳转
//    @Override
//    protected void doAfterLogin(View v) {
//        frinendClickSpanListener.onClick(mUser);
////        Intent intent = new Intent(mContext,
////                UserInfoActivity.class);
////        intent.putExtra(Constants.TAG_USER, mUser);
////        mContext.startActivity(intent);
//    }

    @Override
    public void onClick(View widget) {
        frinendClickSpanListener.onClick(mUser);
    }
}
