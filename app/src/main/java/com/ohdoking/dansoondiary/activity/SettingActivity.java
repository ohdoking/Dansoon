package com.ohdoking.dansoondiary.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.ohdoking.dansoondiary.R;

public class SettingActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
