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

public class FragmentFour extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_four_layout, container, false);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_background, options);
//
//        LinearLayout l = (LinearLayout) view.findViewById(R.id.layoutTutorial4);
//        BitmapDrawable ob = new BitmapDrawable(getResources(), src);
//        l.setBackground(ob);


//        Bitmap src2 = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial04, options);
//        BitmapDrawable ob2 = new BitmapDrawable(getResources(), src2);
//        ImageView imageView = (ImageView) view.findViewById(R.id.tutorialImg4);
//        imageView.setBackgroundResource(R.drawable.tutorial04);

        return view;
    }

}