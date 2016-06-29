package com.ohdoking.dansoondiary.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ohdoking.dansoondiary.R;

public class FragmentTwo extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two_layout, container, false);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_background, options);
//
//        LinearLayout l = (LinearLayout) view.findViewById(R.id.layoutTutorial2);
//        BitmapDrawable ob = new BitmapDrawable(getResources(), src);
//        l.setBackground(ob);


//        Bitmap src2 = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial02, options);
//        BitmapDrawable ob2 = new BitmapDrawable(getResources(), src2);
//        ImageView imageView = (ImageView) view.findViewById(R.id.tutorialImg2);
//        imageView.setBackgroundResource(R.drawable.tutorial02);


        return view;
    }

}