package com.ohdoking.dansoondiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;


public class SplashActivity extends Activity {
    public ImageView bg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);


        bg = (ImageView)findViewById(R.id.splash_title);
        final Handler handler = new Handler();
        
        Runnable runnable = new Runnable() {
            public void run() {

                Intent intent;

                if(getUsingPassword()){
                    intent = new Intent(SplashActivity.this,PasswordActivity.class);

                    /**
                     * 첫 방문시 패스워드 세팅, 그 이후에는 패스워드 확인만
                     */
                    if(getVisitState()){
                        intent.putExtra(DsStatic.PASSWORDSATE,DsStatic.SETPASSWORD);
                        setVisitState(false);
                        saveUsingPassword(false);
                    }
                    else{
                        intent.putExtra(DsStatic.PASSWORDSATE,DsStatic.CONFIRMPASSWORD);
                    }

                }
                else{
                    intent = new Intent(SplashActivity.this,MainListActivity.class);
                }
                startActivity(intent);
                finish();
                /*handler.postDelayed(this, 300);*/
            }
        };
        handler.postDelayed(runnable, 1000);
    }

    // 값 불러오기
    private boolean getUsingPassword(){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        return pref.getBoolean(DsStatic.USEPASSWORD,true);
    }

    private void saveUsingPassword(boolean usepassword){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(DsStatic.USEPASSWORD, usepassword);
        editor.commit();
    }

    // 값 불러오기
    private boolean getVisitState(){
        SharedPreferences pref = getSharedPreferences(DsStatic.USERINFO, MODE_PRIVATE);
        return pref.getBoolean(DsStatic.VISITSTATE,true);
    }


    // 값 불러오기
    private void setVisitState(boolean visit){
        SharedPreferences pref = getSharedPreferences(DsStatic.USERINFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(DsStatic.VISITSTATE, visit);
        editor.commit();
    }
}
