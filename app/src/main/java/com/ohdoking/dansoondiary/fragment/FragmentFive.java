package com.ohdoking.dansoondiary.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.activity.IconListActivity;
import com.ohdoking.dansoondiary.activity.TutorialActivity;
import com.ohdoking.dansoondiary.common.DsStatic;

public class FragmentFive extends Fragment implements View.OnClickListener {

    ImageButton finishButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_five_layout, container, false);
        finishButton = (ImageButton) view.findViewById(R.id.finishTutorial);
        finishButton.setOnClickListener(this);

//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 4;
//        Bitmap src = BitmapFactory.decodeResource(getResources(), R.drawable.tutorial_background, options);
//
//        FrameLayout l = (FrameLayout) view.findViewById(R.id.layoutTutorial5);
//        BitmapDrawable ob = new BitmapDrawable(getResources(), src);
//        l.setBackground(ob);


//        Bitmap src2 = BitmapFactory.decodeResource(getResources(), , options);
//        BitmapDrawable ob2 = new BitmapDrawable(getResources(), src2);
//        ImageView imageView = (ImageView) view.findViewById(R.id.tutorialImg5);
//        imageView.setBackgroundResource(R.drawable.tutorial05);

        return view;
    }

    public void onClick(final View v) { //check for what button is pressed
        switch (v.getId()) {
            case R.id.finishTutorial:

                Intent intent = new Intent(getActivity(),IconListActivity.class);
                intent.putExtra(DsStatic.ICONLIST,DsStatic.ICONFIRSTTIME);
                startActivity(intent);
                getActivity().finish();

                break;
            default:
                break;
        }
    }

}