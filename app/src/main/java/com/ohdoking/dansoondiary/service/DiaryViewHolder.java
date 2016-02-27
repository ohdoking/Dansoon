package com.ohdoking.dansoondiary.service;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ohdoking.dansoondiary.R;

/**
 * Created by Administrator on 2016-02-27.
 */
public class DiaryViewHolder extends RecyclerView.ViewHolder {
    protected TextView dayView;
    protected TextView weekDayView;
    protected ImageView iconView1;
    protected ImageView iconView2;
    protected ImageView iconView3;
    protected ImageView iconView4;

    public DiaryViewHolder(View view) {
        super(view);
        this.dayView = (TextView) view.findViewById(R.id.day);
        this.weekDayView = (TextView) view.findViewById(R.id.weekDay);
        this.iconView1 = (ImageView) view.findViewById(R.id.icon1);
        this.iconView2= (ImageView) view.findViewById(R.id.icon2);
        this.iconView3 = (ImageView) view.findViewById(R.id.icon3);
        this.iconView4 = (ImageView) view.findViewById(R.id.icon4);
    }
}