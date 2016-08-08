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

package com.umeng.commm.ui.activities;

import android.os.Bundle;

import com.umeng.comm.core.utils.ResFinder;
import com.umeng.commm.ui.adapters.viewholders.NavigationCommandImpl;
import com.umeng.common.ui.fragments.RelativeUserFragment;
import com.umeng.common.ui.activities.BaseFragmentActivity;

public class RelativeUserActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(ResFinder.getLayout("umeng_comm_feeds_activity"));
        int container = ResFinder.getId("umeng_comm_main_container");
        RelativeUserFragment relativeUserFragment = new RelativeUserFragment();
        relativeUserFragment.setNavigation(new NavigationCommandImpl(this));
        relativeUserFragment.setArguments(getIntent().getExtras());
        setFragmentContainerId(container);
        showFragmentInContainer(container, relativeUserFragment);
    }
}
