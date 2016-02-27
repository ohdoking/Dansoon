package com.ohdoking.dansoondiary.service;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.dto.Diary;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-02-27.
 */
public class DiaryRecyclerAdapter extends RecyclerView.Adapter<DiaryViewHolder> {
    private ArrayList<Diary> diaryItemList;
    private Context mContext;
    DiaryViewHolder viewHolder;

    public DiaryRecyclerAdapter(Context context, ArrayList<Diary> diaryItemList) {
        this.diaryItemList = diaryItemList;
        this.mContext = context;
    }

    @Override
    public DiaryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, viewGroup,false);
        LinearLayout l = new LinearLayout(mContext);
        l.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 20, 0, 20);

        l.addView(view,layoutParams);
        viewHolder = new DiaryViewHolder(l);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DiaryViewHolder diaryViewHolder, int i) {
        Diary diaryItem = diaryItemList.get(i);

        Date date = new Date(diaryItem.getDate());

        DateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(date);
        Log.i("ohdoking",String.valueOf(finalDay));
        diaryViewHolder.dayView.setText(String.valueOf(date.getDay()));
        diaryViewHolder.weekDayView.setText(changeLanuage(finalDay));

        for(Integer image = 0; image > diaryItem.getImage().size() ; i++){
            Integer imageValue = diaryItem.getImage().get(image);
            if(image == 0){
                diaryViewHolder.iconView1.setImageResource(imageValue);
            }
            else if(image == 1){
                diaryViewHolder.iconView2.setImageResource(imageValue);
            }
            else if(image == 2){
                diaryViewHolder.iconView3.setImageResource(imageValue);
            }
            else if(image == 3){
                diaryViewHolder.iconView4.setImageResource(imageValue);
            }
        }

    /*    diaryViewHolder.textView.setOnClickListener(clickListener);
        diaryViewHolder.imageView.setOnClickListener(clickListener);*/

        diaryViewHolder.dayView.setTag(diaryViewHolder);
        diaryViewHolder.weekDayView.setTag(diaryViewHolder);
        diaryViewHolder.iconView1.setTag(diaryViewHolder);
        diaryViewHolder.iconView2.setTag(diaryViewHolder);
        diaryViewHolder.iconView3.setTag(diaryViewHolder);
        diaryViewHolder.iconView4.setTag(diaryViewHolder);
    }


    /**
     * 해당 사용자의 언어로 변경해줌
     * @param value 영어 결과 값
     * @return
     */
    String changeLanuage(String value){
        String result;
        switch (value) {
            case "Monday"    : result = mContext.getResources().getString(R.string.monday);
                break;
            case "Tuesday"    : result = mContext.getResources().getString(R.string.tuesday);
                break;
            case "Wednesday"    : result = mContext.getResources().getString(R.string.wednesday);
                break;
            case "Thursday"    : result = mContext.getResources().getString(R.string.thursday);
                break;
            case "Friday"    : result = mContext.getResources().getString(R.string.friday);
                break;
            case "Saturday"    : result = mContext.getResources().getString(R.string.saturday);
                break;
            case "Sunday"    : result = mContext.getResources().getString(R.string.sunday);
                break;
            default    : result = "해당 없음";
                break;
        }

        return result;
    }
    @Override
    public int getItemCount() {
        return (null != diaryItemList ? diaryItemList.size() : 0);
    }

    public void addItem(Diary diary){
        diaryItemList.add(diary);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DiaryViewHolder holder = (DiaryViewHolder) view.getTag();
            int position = holder.getPosition();

            Diary diaryItem = diaryItemList.get(position);
            Toast.makeText(mContext, diaryItem.getDate().toString(), Toast.LENGTH_SHORT).show();
        }
    };
}
