package com.ohdoking.dansoondiary.activity;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by Administrator on 2016-02-26.
 */
public class BaseFragmentActivity extends FragmentActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
