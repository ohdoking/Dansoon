package com.ohdoking.dansoondiary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;


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

                Intent intent = new Intent(SplashActivity.this,MainListActivity.class);
                startActivity(intent);

                /*handler.postDelayed(this, 300);*/
            }
        };
        handler.postDelayed(runnable, 1000);
    }
}
