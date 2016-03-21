package com.ohdoking.dansoondiary.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
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
    String version;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initId();
        clickEvent();

        if(getUsingPassword()){
            dataSwitch.setChecked(true);
        }
        else{
            dataSwitch.setChecked(false);
        }


        try {
            PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = i.versionName;
            versionTv.setText("v "+version);
        } catch(PackageManager.NameNotFoundException e) { }


    }

    void initId(){
        moveList = (ImageView) findViewById(R.id.moveList);

        backup = (RelativeLayout) findViewById(R.id.layout_backup);
        changePw = (RelativeLayout) findViewById(R.id.layout_changepw);
        lockSetting = (RelativeLayout) findViewById(R.id.layout_locksetting);
        sendEmail = (RelativeLayout) findViewById(R.id.layout_ask);
        sendRecommand = (RelativeLayout) findViewById(R.id.layout_recommand);
        sendFighting = (RelativeLayout) findViewById(R.id.layout_fighting);

        versionTv = (TextView) findViewById(R.id.versionTv);

        dataSwitch = (ToggleButton) findViewById(R.id.dataSwitch);
    }

    void clickEvent(){
        moveList.setOnClickListener(mClickListener);
        backup.setOnClickListener(mClickListener);
        changePw.setOnClickListener(mClickListener);
        lockSetting.setOnClickListener(mClickListener);
        sendEmail.setOnClickListener(mClickListener);
        sendRecommand.setOnClickListener(mClickListener);
        sendFighting.setOnClickListener(mClickListener);

        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveUsingPassword(true);
                }
                else{
                    saveUsingPassword(false);
                }
            }
        });
    }

    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.moveList:
                    finish();
                    break;
                case R.id.layout_backup:

                    break;
                case R.id.layout_changepw:
                    Intent i = new Intent(SettingActivity.this, PasswordActivity.class);
                    i.putExtra(DsStatic.PASSWORDSATE,DsStatic.SETPASSWORD);
                    startActivity(i);
                    break;
                case R.id.layout_locksetting:

                    break;
                case R.id.layout_ask:

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ohdoking@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[단순] 문의합니다.");
//                    emailIntent.putExtra(Intent.EXTRA_TEXT   , "Message Body");

                    startActivity(Intent.createChooser(emailIntent, "단순 문의하기"));
                    finish();

                    break;
                case R.id.layout_recommand:

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "단순 추천합니다!");
                    startActivity(Intent.createChooser(sendIntent,"단순 추천하기"));


                    /*Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                            + "/mipmap/" + "ic_launcher");
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "단순");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "단순 추천합니다.");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "단순 추천하기"));*/


                   /* Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                            + "/mipmap/" + "ic_launcher");
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/html");
                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Mail");

                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Launcher");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(shareIntent, "단순 추천합니다."));*/
                    break;
                case R.id.layout_fighting:

                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }

                    break;
            }
        }
    };



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
