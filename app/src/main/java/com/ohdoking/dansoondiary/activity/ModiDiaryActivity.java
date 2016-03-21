package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.roomorama.caldroid.CalendarHelper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hirondelle.date4j.DateTime;

public class ModiDiaryActivity extends BaseAppCompatActivity {

    LinearLayout modiIcon ;
    EditText modiText;

    DiaryDao diaryDao;
    Diary diary;
    Intent i;
    Integer resultKey;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    TextView dateTv;
    TextView time;

    ImageView moveList;
    TextView modiDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modi_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initId();


        diaryDao = new DiaryDao(getApplicationContext());
        i = getIntent();
        resultKey = i.getIntExtra("key",9999);



        try {
            diaryDao.open();
            diary = diaryDao.getDiaryById(resultKey);

            Date date = new Date(diary.getDate());
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(diary.getDate());

            DateFormat format2=new SimpleDateFormat("EEEE");
            DateTime tempdate = CalendarHelper.convertDateToDateTime(date);
            String finalDay=format2.format(date);
            dateTv.setText(tempdate.getYear()+"."+tempdate.getMonth()+"."+tempdate.getDay()+" "+finalDay);



            time.setText(c.get(Calendar.HOUR)+":"+c.get(Calendar.MINUTE));


            for(Integer image = 0; image < diary.getImage().size() ; image++){
                Integer imageValue = diary.getImage().get(image);
                Log.i("ohdoking4",imageValue+"");
                if(image == 0 && imageValue != null){
                    imageView1.setImageResource(DsStatic.buttonList[imageValue]);
                    imageView1.setTag(DsStatic.buttonList[imageValue]);
                }
                else if(image == 1 && imageValue != null){
                    imageView2.setImageResource(DsStatic.buttonList[imageValue]);
                    imageView2.setTag(DsStatic.buttonList[imageValue]);
                }
                else if(image == 2 && imageValue != null){
                    imageView3.setImageResource(DsStatic.buttonList[imageValue]);
                    imageView3.setTag(DsStatic.buttonList[imageValue]);
                }
                else if(image == 3 && imageValue != null){
                    imageView4.setImageResource(DsStatic.buttonList[imageValue]);
                    imageView4.setTag(DsStatic.buttonList[imageValue]);
                }
            }

            String editString = diary.getMemo().toString();
            if(editString.equals("none")){
                modiText.setHint("당신의 일상을 입력해주세요");
            }
            else{
                modiText.setText(editString);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        moveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1, i);
                finish();
            }
        });

        modiDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Diary newDiary = new Diary();

                Toast.makeText(getApplicationContext(),"수정되었습니다.",Toast.LENGTH_SHORT).show();

                ArrayList<Integer> images = new ArrayList<Integer>();
                for(int count = 0; count < 4;count++){
                    Integer resultInt = null;
                    if(count == 0){
                        resultInt = (Integer) imageView1.getTag();
                    }
                    else if(count == 1){
                        resultInt = (Integer) imageView2.getTag();
                    }
                    else if(count == 2){
                        resultInt = (Integer) imageView3.getTag();
                    }
                    else if(count == 3){
                        resultInt = (Integer) imageView4.getTag();
                    }
                    Log.i("ohdoking4",resultInt+"");
                    if(resultInt == null){
                        break;

                    }
                    images.add(resultInt);
                }
                newDiary.setId(resultKey);
                newDiary.setImage(diary.getImage());
                String modiTextValue = modiText.getText().toString();
                if(modiTextValue.equals("")){
                    modiTextValue = "none";
                }
                newDiary.setMemo(modiTextValue);

                try {
                    diaryDao.open();
                    boolean isSuccessUpdate = diaryDao.updateDiary(newDiary);
                    if(isSuccessUpdate){
                        diaryDao.close();
                        setResult(1, i);
                        finish();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });
        modiIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModiDiaryActivity.this, WriteDiaryActivity.class);
                i.putExtra("where","modi");
                i.putExtra("count",diary.getImage().size());
                for(int j = 0;j<diary.getImage().size();j++){
                    i.putExtra("image"+j,diary.getImage().get(j));
                }
                startActivityForResult(i,2);
            }
        });
    }

    void initId(){
        modiIcon = (LinearLayout) findViewById(R.id.modiIcon);
        modiText = (EditText) findViewById(R.id.modiMemo1);

        imageView1 = (ImageView) findViewById(R.id.image);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView4 = (ImageView) findViewById(R.id.image4);



        dateTv = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        moveList = (ImageView) findViewById(R.id.moveList);
        modiDiary = (TextView) findViewById(R.id.modiDiary);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                ArrayList<Integer> imageList = new ArrayList<Integer>();
                int size = data.getExtras().getInt("selectImagesize");
                for(int j = 0;j < size;j++){
                    Integer selectImage = data.getExtras().getInt("selectImage"+j);
                    imageList.add(selectImage);
                    Log.i("ohdoking6",selectImage+"");
                }

                diary.setImage(imageList);

                for(Integer image = 0; image < diary.getImage().size() ; image++){
                    Integer imageValue = diary.getImage().get(image);
                    Log.i("ohdoking4",imageValue+"");
                    if(image == 0 && imageValue != null){
                        imageView1.setImageResource(DsStatic.buttonList[imageValue]);
                        imageView1.setTag(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 1 && imageValue != null){
                        imageView2.setImageResource(DsStatic.buttonList[imageValue]);
                        imageView2.setTag(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 2 && imageValue != null){
                        imageView3.setImageResource(DsStatic.buttonList[imageValue]);
                        imageView3.setTag(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 3 && imageValue != null){
                        imageView4.setImageResource(DsStatic.buttonList[imageValue]);
                        imageView4.setTag(DsStatic.buttonList[imageValue]);
                    }

                    if(imageValue == null){
                        imageView4.setImageResource(R.drawable.diary_null_icon);
                    }
                }


                /*try {
                    diaryDao.open();
                    diaryDao.updateDiary(diary);
                    diaryDao.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }*/

//                setDateInView();
                break;

            default:
                break;
        }
    }

}
