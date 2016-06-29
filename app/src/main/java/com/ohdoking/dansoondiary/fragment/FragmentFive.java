package com.ohdoking.dansoondiary.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.activity.IconListActivity;
import com.ohdoking.dansoondiary.activity.TutorialActivity;
import com.ohdoking.dansoondiary.common.DsStatic;

public class FragmentFive extends Fragment implements View.OnClickListener {

    Button finishButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three_layout, container, false);
        finishButton = (Button) view.findViewById(R.id.finishTutorial);
        finishButton.setOnClickListener(this);

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