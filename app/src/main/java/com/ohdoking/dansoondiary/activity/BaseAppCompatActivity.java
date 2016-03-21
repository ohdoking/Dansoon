package com.ohdoking.dansoondiary.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.ohdoking.dansoondiary.R;
import com.tsengvn.typekit.TypekitContextWrapper;

/**
 * Created by Administrator on 2016-02-27.
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    protected String[] mMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[] {
            "ICON A", "ICON B", "ICON C", "ICON D", "ICON E", "ICON F", "ICON G", "ICON H",
            "ICON I", "ICON J", "ICON K", "ICON L", "ICON M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
