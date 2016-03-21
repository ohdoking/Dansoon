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
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dto.Diary;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(diaryItem.getDate());


//        Date date = new Date();
//        DateTime tempdate = CalendarHelper.convertDateToDateTime(date);
//        DateFormat format2=new SimpleDateFormat("EEEE");
//        String finalDay=format2.format(date);
//        Log.i("ohdoking1",String.valueOf(finalDay));
//        Log.i("ohdoking1",date.getDay()+"");
        diaryViewHolder.dayView.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        diaryViewHolder.weekDayView.setText(dayLongName);

        diaryViewHolder.iconView1.setImageResource(R.drawable.diary_null_icon);
        diaryViewHolder.iconView2.setImageResource(R.drawable.diary_null_icon);
        diaryViewHolder.iconView3.setImageResource(R.drawable.diary_null_icon);
        diaryViewHolder.iconView4.setImageResource(R.drawable.diary_null_icon);

        for(Integer image = 0; image < diaryItem.getImage().size() ; image++){
            Integer imageValue = diaryItem.getImage().get(image);
            Log.i("ohdoking2",String.valueOf(imageValue));
            if(image == 0 && imageValue != null){
                diaryViewHolder.iconView1.setImageResource(DsStatic.buttonList[imageValue]);
            }
            else if(image == 1 && imageValue != null){
                diaryViewHolder.iconView2.setImageResource(DsStatic.buttonList[imageValue]);
            }
            else if(image == 2 && imageValue != null){
                diaryViewHolder.iconView3.setImageResource(DsStatic.buttonList[imageValue]);
            }
            else if(image == 3 && imageValue != null){
                diaryViewHolder.iconView4.setImageResource(DsStatic.buttonList[imageValue]);
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
    public void swap(ArrayList<Diary> datas){
        diaryItemList.clear();
        diaryItemList.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != diaryItemList ? diaryItemList.size() : 0);
    }

    public Diary getItem(int position) {
        return diaryItemList.get(position);
    }

    public void addItem(Diary diary){
        diaryItemList.add(diary);
    }

    public void addAllItem(ArrayList<Diary> diarys){
        diaryItemList.addAll(diarys);
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
