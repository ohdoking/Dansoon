package com.ohdoking.dansoondiary.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.roomorama.caldroid.CalendarHelper;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hirondelle.date4j.DateTime;

public class DetailDiaryActivity extends BaseAppCompatActivity {

    DiaryDao diaryDao;
    Diary diary;
    Intent i;
    Integer resultKey;
    Long resultDateKey;

    EditText modiText;

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;

    TextView dateTv;
    TextView time;

    ImageView moveList;

    private AlertDialog mDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initId();


        diaryDao = new DiaryDao(getApplicationContext());
        i = getIntent();
        resultKey = i.getIntExtra("key",9999);
        resultDateKey = i.getLongExtra("date-key",Long.valueOf("9999"));



        try {
            diaryDao.open();
            if(resultKey != 9999){
                diary = diaryDao.getDiaryById(resultKey);
            }
            else{
                Integer year = i.getIntExtra("date-key-year",99);
                Integer month = i.getIntExtra("date-key-month",99);
                Integer day = i.getIntExtra("date-key-day",99);
                diary = diaryDao.getDiaryByDate(year,month,day);
            }

            if(diary == null){
                Intent i2 = new Intent(DetailDiaryActivity.this,WriteDiaryActivity.class);

                if(resultKey == 9999){
                    i2.putExtra("resultKey",9999);
                    i2.putExtra("date-key-year", i.getIntExtra("date-key-year",99));
                    i2.putExtra("date-key-month", i.getIntExtra("date-key-month",99));
                    i2.putExtra("date-key-day", i.getIntExtra("date-key-day",99));
                }
                startActivityForResult(i2,1);
            }
            else{
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
                    Log.i("ohdoking2",imageValue+"");

                    if(image == 0 && imageValue != null){
                        imageView1.setImageResource(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 1 && imageValue != null){
                        imageView2.setImageResource(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 2 && imageValue != null){
                        imageView3.setImageResource(DsStatic.buttonList[imageValue]);
                    }
                    else if(image == 3 && imageValue != null){
                        imageView4.setImageResource(DsStatic.buttonList[imageValue]);
                    }
                }
                String editString = diary.getMemo().toString();
                if(editString.equals("none")){
                    modiText.setHint("당신의 일상을 입력해주세요");
                }
                else{
                    modiText.setText(editString);
                }
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
        modiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailDiaryActivity.this, ModiDiaryActivity.class);
                i.putExtra("key",diary.getId());
                startActivityForResult(i,1);
            }
        });
    }

    void initId(){
        modiText = (EditText) findViewById(R.id.modiMemo);

        imageView1 = (ImageView) findViewById(R.id.image);
        imageView2 = (ImageView) findViewById(R.id.image2);
        imageView3 = (ImageView) findViewById(R.id.image3);
        imageView4 = (ImageView) findViewById(R.id.image4);

        dateTv = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);

        moveList = (ImageView) findViewById(R.id.moveList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.modi: {
                Intent i = new Intent(DetailDiaryActivity.this, ModiDiaryActivity.class);
                i.putExtra("key",diary.getId());
                startActivityForResult(i,1);
                break;
            }
            case R.id.delete: {

                mDialog = createDialog();
                mDialog.show();


                break;
            }
        }
        return true;
    }

    /**
     * Dailog
     * @return ab
     */
    private AlertDialog createDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("삭제 확인");
        ab.setMessage("정말 삭제하시겠습니까?");
        ab.setCancelable(false);
        ab.setIcon(getResources().getDrawable(R.mipmap.ic_launcher));

        ab.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                diaryDao.deleteDiary(resultKey);
                setResult(1, i);
                finish();
            }
        });

        ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                setDismiss(mDialog);
            }
        });

        return ab.create();
    }

    /**
     * 다이얼로그 종료
     * @param dialog
     */
    private void setDismiss(Dialog dialog){
        if(dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    /**
     * 엑티비티가 끝나 후
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                finish();
                break;

            default:
                break;
        }
    }

}
