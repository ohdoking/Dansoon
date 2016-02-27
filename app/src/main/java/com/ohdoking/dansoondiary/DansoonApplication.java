package com.ohdoking.dansoondiary;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

public class DansoonApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "fonts/thejunggodic130.ttf"));
                /*.addBold(Typekit.createFromAsset(this, "fonts/thejunggodic.ttf"));*/
    }


}
