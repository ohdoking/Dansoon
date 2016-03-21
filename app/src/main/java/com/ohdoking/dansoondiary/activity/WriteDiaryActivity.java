package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.ohdoking.dansoondiary.service.GridImageAdapter;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;

public class WriteDiaryActivity extends BaseAppCompatActivity {

    GridView gridView;
    GridImageAdapter gridImageAdapter;
    DiaryDao diaryDao;
    ArrayList<Integer> selectArrayList;
    Intent intent;
    Intent intent2;
    ImageView backList;
    ImageView saveDiary;

    Integer counter=0;

    String where;

    ArrayList<Integer> favoriteIconArrayList;
    ArrayList<Integer> alreadySelectedArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        selectArrayList = new ArrayList<Integer>();

        alreadySelectedArrayList = new ArrayList<Integer>();
        intent = new Intent();
        intent2 = getIntent();

        initId();
        diaryDao = new DiaryDao(getApplicationContext());

        where = intent2.getStringExtra(DsStatic.STARTACTIVITY);
        if(intent2.hasExtra(DsStatic.STARTACTIVITY)) {
            if (where.equals(DsStatic.MODIDIARYACTIVITY)) {
                int size = intent2.getIntExtra("count", 0);
                if (size != 0) {
                    for (int j = 0; j < size; j++) {
                        Integer tempImage = intent2.getIntExtra("image" + j, 1);
                        for (int z = 0; z < DsStatic.buttonList.length; z++) {
                            if (tempImage.equals(DsStatic.buttonList[z])) {
                                Log.i("ohdoking3", "checked : " + DsStatic.buttonList[z]);
                            }
                        }
                        alreadySelectedArrayList.add(tempImage);

                    }
                }

            }
        }
        selectArrayList.addAll(alreadySelectedArrayList);


        gridImageAdapter = new GridImageAdapter(getApplicationContext(),alreadySelectedArrayList);
        if(intent2.hasExtra(DsStatic.STARTACTIVITY)) {
            setGridBasicImageAdapter();
        }
        else{
            setGridImageAdapter();
        }



        clickEvent();
    }

    /**
     * 이미지 비교하기
     */
    boolean diff(ImageView imageView,Integer resource){
        Drawable temp = imageView.getDrawable();
        Drawable temp1 = getResources().getDrawable(resource);

        Bitmap tmpBitmap = ((BitmapDrawable)temp).getBitmap();
        Bitmap tmpBitmap1 = ((BitmapDrawable)temp1).getBitmap();

        if(tmpBitmap.equals(tmpBitmap1)){
           return true;
        }
        return false;
    }

    /**
     * id를 초기화
     */
    void initId(){
        backList = (ImageView) findViewById(R.id.moveMain);
        saveDiary = (ImageView) findViewById(R.id.saveDiary);

        gridView = (GridView) findViewById(R.id.gridView1);
    }

    /**
     * 수정 화면에서 Grid 초기화
     */
    void setGridBasicImageAdapter(){
        gridView.setAdapter(gridImageAdapter);

        favoriteIconArrayList = new ArrayList<Integer>();
        for(int i = 2; i < DsStatic.buttonList.length ; i++){
            favoriteIconArrayList.add(i);
            gridImageAdapter.addImage(i);
        }
    }

    /**
     * 등록하기 화면에서 Grid 초기화
     */
    void setGridImageAdapter(){
        favoriteIconArrayList = getFavoritesIcon();

        gridView.setAdapter(gridImageAdapter);
        gridImageAdapter.addArrayImage(favoriteIconArrayList);
        gridImageAdapter.addImage(0);
    }

    /**
     * 클릭 이벤트
     */
    void clickEvent(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int lastIconPostion = position+1;

                LinearLayout l =(LinearLayout)v;
                ImageView image = (ImageView) l.getChildAt(0);//.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                LinearLayout l= (LinearLayout)parent.getChildAt(0);
//                Image image = (ImageView)l.getChildAt(0);
                Log.i("ohdoking1234",gridImageAdapter.getCount() +"=="+ lastIconPostion);
                if(gridImageAdapter.getCount() == lastIconPostion){
                    Intent intent  = new Intent(WriteDiaryActivity.this, IconListActivity.class);
                    startActivity(intent);
                    finish();
                }
                int realPosition = favoriteIconArrayList.get(position);

//                Log.i("ohdoking7",gridImageAdapter.getCount()+" : "+gridImageAdapter.getItem(position));

                if(hasDuplicates(gridImageAdapter.getItem(position))){
                    image.setImageResource(DsStatic.buttonList[realPosition]);
                    Integer temp = gridImageAdapter.getItem(position);
                    for(int i = 0; i < selectArrayList.size() ; i++) {
                        Log.i("test123",selectArrayList.get(i) +"=="+ temp);
                        if(selectArrayList.get(i) == temp){
                            selectArrayList.remove(i);
                            i--;
                        }
                    }
                    counter--;
                }
                else{
                    if(counter < 4 ) {
                        image.setImageResource(DsStatic.buttonListRev[realPosition]);
                        selectArrayList.add(realPosition);
                        counter++;
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"4개 이상 추가 하실수 없습니다.",Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1, intent);
                finish();
            }
        });

        saveDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(intent2.hasExtra("where")) {
                    if (where.equals("modi")) {

                    }
                }else {
                    Diary diary = new Diary();
                    diary.setImage(selectArrayList);


                    try {
                        diaryDao.open();
                        int reusltValue = intent2.getIntExtra("resultKey",0);
                        Log.i("ohdokingtest",reusltValue+"");
                        if(reusltValue != 9999){
                            diaryDao.addDiary(diary);
                        }
                        else{
                            Integer year = intent2.getIntExtra("date-key-year",99);
                            Integer month = intent2.getIntExtra("date-key-month",99);
                            Integer day = intent2.getIntExtra("date-key-day",99);
                            Log.i("ohdokingtest",year+" "+month+" "+day);
                            diaryDao.addSpecificDayDiary(diary,year,month,day);
                        }

                        diaryDao.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                intent2.putExtra("selectImagesize",selectArrayList.size());
                for(int j = 0;j < selectArrayList.size();j++){
                    intent2.putExtra("selectImage"+j,selectArrayList.get(j));
                }


                setResult(1, intent2);
                finish();
            }
        });


    }

    boolean hasDuplicates(Integer image) {
        for (Integer tempimage : selectArrayList) {
            Log.i("ohdoking1",image.toString() + " == " + tempimage);
            if (image.equals(tempimage)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Backpress 버튼
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        setResult(1, intent);
        finish();
    }

    /**
     * 선호하는 icon 목록 가져오기
     * @return
     */
    public ArrayList<Integer> getFavoritesIcon(){
        ArrayList<Integer> iconList;
        SharedPreferences pref = getSharedPreferences(DsStatic.ICONTABLE, MODE_PRIVATE);
        String iconListString =  pref.getString(DsStatic.FAVORITEICON,"[2]");
        if (!iconListString.equals("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
            iconList = gson.fromJson(iconListString, type);
        }
        else {
            iconList = null;
        }

        return iconList;
    }
}
