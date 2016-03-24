package com.ohdoking.dansoondiary.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    View.OnTouchListener menuTouchListenr = new View.OnTouchListener() {
        private Rect rect;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                ((ImageView)v).setColorFilter(Color.argb(50, 0, 0, 0));
                rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                ((ImageView)v).setColorFilter(Color.argb(0, 0, 0, 0));
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                    ((ImageView)v).setColorFilter(Color.argb(0, 0, 0, 0));
                }
            }
            return false;
        }
    };

    View.OnTouchListener settingTouchListenr = new View.OnTouchListener() {
        private Rect rect;

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            PorterDuffColorFilter greyFilter = new PorterDuffColorFilter(Color.argb(50, 0, 0, 0), PorterDuff.Mode.MULTIPLY);
            PorterDuffColorFilter whiteFilter = new PorterDuffColorFilter(Color.argb(0, 0, 0, 0), PorterDuff.Mode.MULTIPLY);
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                ((RelativeLayout)v).setBackgroundColor(Color.argb(50, 0, 0, 0));//.setColorFilter(greyFilter);
                rect = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                ((RelativeLayout)v).setBackgroundColor(Color.argb(0, 0, 0, 0));//.setColorFilter(whiteFilter);
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE){
                if(!rect.contains(v.getLeft() + (int) event.getX(), v.getTop() + (int) event.getY())){
                    ((RelativeLayout)v).setBackgroundColor(Color.argb(0, 0, 0, 0));
                }
            }
            return false;
        }
    };
}
