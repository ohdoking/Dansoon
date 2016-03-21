package com.ohdoking.dansoondiary.service;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.CalendarHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import hirondelle.date4j.DateTime;

public class CaldroidSampleCustomAdapter extends CaldroidGridAdapter {

	TextView tv1;
	ImageView tv2;

	DiaryDao diaryDao;

	ArrayList<Diary> diaryArrayList;

	public CaldroidSampleCustomAdapter(Context context, int month, int year,
									   Map<String, Object> caldroidData,
									   Map<String, Object> extraData) {
		super(context, month, year, caldroidData, extraData);
		getAllDate();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View cellView = convertView;

		// For reuse
		if (convertView == null) {
			cellView = inflater.inflate(R.layout.custom_cell, null);
		}

		int topPadding = cellView.getPaddingTop();
		int leftPadding = cellView.getPaddingLeft();
		int bottomPadding = cellView.getPaddingBottom();
		int rightPadding = cellView.getPaddingRight();

		tv1 = (TextView) cellView.findViewById(R.id.tv1);
		tv2 = (ImageView) cellView.findViewById(R.id.iv1);




//		tv1.setTextColor(Color.BLACK);
		// Get dateTime of this cell
		DateTime dateTime = this.datetimeList.get(position);
		Resources resources = context.getResources();

		if(dateTime.getWeekDay() != 1){

			tv1.setTextColor(context.getResources().getColor(R.color.colorAppMain));
		}
		else{
			tv1.setTextColor(Color.RED);

		}

		for (Diary diary : diaryArrayList){
//			Drawable icon = context.getResources().getDrawable(diary.getImage().get(0) );
			Date tempTime = new Date(diary.getDate());
			Date tempTime2 = new Date();

			DateTime dateTime2 = CalendarHelper.convertDateToDateTime(tempTime);
			if(dateTime2.equals(dateTime)){
				tv2.setImageResource(DsStatic.buttonList[diary.getImage().get(0)]);
			}
		}



		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {
			tv1.setTextColor(resources
					.getColor(com.caldroid.R.color.caldroid_darker_gray));
		}

		boolean shouldResetDiabledView = false;
		boolean shouldResetSelectedView = false;

		// Customize for disabled dates and date outside min/max dates
		if ((minDateTime != null && dateTime.lt(minDateTime))
				|| (maxDateTime != null && dateTime.gt(maxDateTime))
				|| (disableDates != null && disableDates.indexOf(dateTime) != -1)) {

			tv1.setTextColor(CaldroidFragment.disabledTextColor);
			if (CaldroidFragment.disabledBackgroundDrawable == -1) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.disable_cell);
			} else {
				cellView.setBackgroundResource(CaldroidFragment.disabledBackgroundDrawable);
			}

			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border_gray_bg);
			}

		} else {
			shouldResetDiabledView = true;
		}

		// Customize for selected dates
		if (selectedDates != null && selectedDates.indexOf(dateTime) != -1) {
			cellView.setBackgroundColor(resources
					.getColor(R.color.colorAppMain));

//			cellView.setBackgroundResource(R.drawable.app_color_border);

				tv1.setTextColor(Color.BLACK);

		} else {
			shouldResetSelectedView = true;
		}

		if (shouldResetDiabledView && shouldResetSelectedView) {
			// Customize for today
			if (dateTime.equals(getToday())) {
				cellView.setBackgroundResource(com.caldroid.R.drawable.red_border);
			} else {
				cellView.setBackgroundResource(com.caldroid.R.drawable.cell_bg);
			}
		}

		tv1.setText("" + dateTime.getDay());
//        DateTime dt = new DateTime("2016-01-19");

//           tv2.setImageResource(R.mipmap.ic_launcher);
//		tv2.setText("Hi");

		// Somehow after setBackgroundResource, the padding collapse.
		// This is to recover the padding
		cellView.setPadding(leftPadding, topPadding, rightPadding,
				bottomPadding);

		// Set custom color if required
		setCustomResources(dateTime, cellView, tv1);

		return cellView;
	}

	/**
	 * 모든 DB 데이터를 가져옴
	 */
	public void getAllDate(){
		diaryDao = new DiaryDao(context);
		try {
			diaryDao.open();
			diaryArrayList = diaryDao.getAllDiary();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			diaryDao.close();
		}
	}

}