package com.ohdoking.dansoondiary.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;

public class SettingActivity extends BaseAppCompatActivity {

    ImageView moveList;

    RelativeLayout backup;
    RelativeLayout changePw;
    RelativeLayout lockSetting;
    RelativeLayout sendEmail;
    RelativeLayout sendRecommand;
    RelativeLayout sendFighting;

    TextView versionTv;

    ToggleButton dataSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    private boolean getUsingPassword(){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        return pref.getBoolean(DsStatic.USEPASSWORD,false);
    }
    // 값 저장하기
    private void saveUsingPassword(boolean usepassword){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(DsStatic.USEPASSWORD, usepassword);
        editor.commit();
    }



}
