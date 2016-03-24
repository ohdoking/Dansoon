package com.ohdoking.dansoondiary.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.db.DataBaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SettingActivity extends BaseAppCompatActivity {

    ImageView moveList;

    RelativeLayout backup;
    RelativeLayout changePw;
    RelativeLayout lockSetting;
    RelativeLayout sendEmail;
    RelativeLayout sendRecommand;
    RelativeLayout sendFighting;

    TextView versionTv;

    ToggleButton dataSwitch;
    String version;

    File backupFile;
    public DiaryDao diaryDao;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initId();
        clickEvent();

        if(getUsingPassword()){
            dataSwitch.setChecked(true);
        }
        else{
            dataSwitch.setChecked(false);
        }


        try {
            PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = i.versionName;
            versionTv.setText("v "+version);
        } catch(PackageManager.NameNotFoundException e) { }


    }

    void initId(){
        moveList = (ImageView) findViewById(R.id.moveList);

        backup = (RelativeLayout) findViewById(R.id.layout_backup);
        changePw = (RelativeLayout) findViewById(R.id.layout_changepw);
        lockSetting = (RelativeLayout) findViewById(R.id.layout_locksetting);
        sendEmail = (RelativeLayout) findViewById(R.id.layout_ask);
        sendRecommand = (RelativeLayout) findViewById(R.id.layout_recommand);
        sendFighting = (RelativeLayout) findViewById(R.id.layout_fighting);

        versionTv = (TextView) findViewById(R.id.versionTv);

        dataSwitch = (ToggleButton) findViewById(R.id.dataSwitch);
    }

    void clickEvent(){
        moveList.setOnClickListener(mClickListener);
        backup.setOnClickListener(mClickListener);
        changePw.setOnClickListener(mClickListener);
        lockSetting.setOnClickListener(mClickListener);
        sendEmail.setOnClickListener(mClickListener);
        sendRecommand.setOnClickListener(mClickListener);
        sendFighting.setOnClickListener(mClickListener);

        backup.setOnTouchListener(settingTouchListenr);
        changePw.setOnTouchListener(settingTouchListenr);
        lockSetting.setOnTouchListener(settingTouchListenr);
        sendEmail.setOnTouchListener(settingTouchListenr);
        sendRecommand.setOnTouchListener(settingTouchListenr);
        sendFighting.setOnTouchListener(settingTouchListenr);

        moveList.setOnTouchListener(menuTouchListenr);

        dataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    saveUsingPassword(true);
                }
                else{
                    saveUsingPassword(false);
                }
            }
        });
    }

    /**
     * Dialog 생서
     * @return
     */
    public Dialog onCreateDialog() {
        builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle("백업 설정")
        .setItems(R.array.backup_list, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    saveBackup(getApplicationContext());
                }
                else if(which == 1){
                    loadBackup(getApplicationContext());
                }
                else if(which == 2){
                    sendBackupDataUsingEmail();
                }
            }
        });
        return builder.create();
    }


    /**
     * backup DB 이메일로 보내기
     */
    void sendBackupDataUsingEmail(){

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분");

        String to = "ohdoking@softwareinlife.com";
        String subj = "단순 백업 데이터";
        String msg = "단순의 백업 데이터입니다.\n"+CurDateFormat.format(date)+"에 백업하였습니다.";

        if(to.length() < 1)
        {
            Toast.makeText(getApplicationContext(), "Please Enter Recipent Email", Toast.LENGTH_LONG).show();
        }
        else if (subj.length() < 1) {
            Toast.makeText(getApplicationContext(), "Please Enter Subject", Toast.LENGTH_LONG).show();
        }
        else if (msg.length() < 1) {
            Toast.makeText(getApplicationContext(), "Please Enter Message", Toast.LENGTH_LONG).show();
        }
        else {

            File backupTempFile = new File(Environment.getExternalStorageDirectory(),"dansoonBackupFolder/diaryBackupDb.db");

            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("image/jpeg");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{to});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subj);
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, msg);
            emailIntent.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(backupTempFile));
            Log.v(getClass().getSimpleName(), "sPhotoUri=" + Uri.fromFile(backupTempFile));
            if(!backupTempFile.exists()){
                Toast.makeText(getApplicationContext(), "백업 파일이 없습니다.\n백업을 해주세요.", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            }
        }
    }

    /**
     * 클릭 이벤트
     */
    View.OnClickListener mClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.moveList:
                    finish();
                    break;
                case R.id.layout_backup:
                    onCreateDialog().show();
                    break;
                case R.id.layout_changepw:
                    Intent i = new Intent(SettingActivity.this, PasswordActivity.class);
                    i.putExtra(DsStatic.PASSWORDSATE,DsStatic.SETPASSWORD);
                    startActivity(i);
                    break;
                case R.id.layout_locksetting:
                    if(!dataSwitch.isChecked()){
                        saveUsingPassword(true);
                        dataSwitch.setChecked(true);
                    }
                    else{
                        saveUsingPassword(false);
                        dataSwitch.setChecked(false);
                    }
                    break;
                case R.id.layout_ask:

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");

                    emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"ohdoking@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[단순] 문의합니다.");
//                    emailIntent.putExtra(Intent.EXTRA_TEXT   , "Message Body");

                    startActivity(Intent.createChooser(emailIntent, "단순 문의하기"));
                    finish();

                    break;
                case R.id.layout_recommand:

                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "단순 추천합니다!");
                    startActivity(Intent.createChooser(sendIntent,"단순 추천하기"));


                    /*Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                            + "/mipmap/" + "ic_launcher");
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "단순");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "단순 추천합니다.");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(Intent.createChooser(shareIntent, "단순 추천하기"));*/


                   /* Uri imageUri = Uri.parse("android.resource://" + getPackageName()
                            + "/mipmap/" + "ic_launcher");
                    Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                    shareIntent.setType("text/html");
                    shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Test Mail");

                    shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Launcher");
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(shareIntent, "단순 추천합니다."));*/
                    break;
                case R.id.layout_fighting:

                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    // To count with Play market backstack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    try {
                        startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    }

                    break;
            }
        }
    };


    /**
     * Password 이용 여부 가져옴
     * @return
     */
    private boolean getUsingPassword(){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        return pref.getBoolean(DsStatic.USEPASSWORD,false);
    }
    // 값 저장하기
    private void saveUsingPassword(boolean usepassword){
        SharedPreferences pref = getSharedPreferences(DsStatic.SHARDPREFERENCETABLE, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(DsStatic.USEPASSWORD, usepassword);
        editor.commit();
    }

    /**
     * backup 하기
     * @param c
     */
    public void saveBackup(Context c) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File BackupDir = new File(sd, "dansoonBackupFolder");
                BackupDir.mkdir();

                File currentDB = new File(data,
                        "//data//com.ohdoking.dansoondiary//databases//"+ DataBaseHelper.DATABASE_NAME);
                File backupDB = new File(sd, "dansoonBackupFolder/diaryBackupDb.db");
                backupFile = backupDB;

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();

                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(c, "백업이 성공적으로 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.i("ohdokingBackError",e.getMessage());
            Toast.makeText(c, "Fail", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * backup 불러오기
     * @param c
     */
    public void loadBackup(Context c) {

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                File backupDB = new File(data,
                        "//data//com.ohdoking.dansoondiary//databases//"+DataBaseHelper.DATABASE_NAME);
                File currentDB = new File(sd, "dansoonBackupFolder/diaryBackupDb.db");

                if(!currentDB.exists()){

                    currentDB = getbackupFileInFolder(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()));
                }

                if(currentDB == null){
                    Toast.makeText(c, "백업 파일이 없습니다.", Toast.LENGTH_SHORT).show();
                }

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());

                src.close();
                dst.close();
                Toast.makeText(c, "복원 성공적으로 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(c, "복원 실패", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 폴더 리스트에서 특정 db파일 가져오기
     * @param parentDir
     * @return
     */
    private File getbackupFileInFolder(File parentDir) {
        File inFile = null;
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.getName().equals("diaryBackupDb.db")) {
                inFile = file;
            }
        }
        return inFile;
    }

    /*private class ExportDatabaseCSVTask extends AsyncTask<String, Boolean, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(SettingActivity.this);


        @Override
        protected void onPreExecute() {


            this.dialog.setMessage("Exporting database...");
            this.dialog.show();

        }
        protected Boolean doInBackground(final String... args){

            File dbFile=getDatabasePath(DataBaseHelper.DATABASE_NAME);
            Log.v("ohdoking123", "Db path is: "+dbFile);  //get the path of db

            File exportDir = new File(Environment.getExternalStorageDirectory(), "");
            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            file = new File(exportDir, "DansoonCSV.csv");
            try {

                DiaryDao diaryDao2 = new DiaryDao(getApplicationContext());

                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                //ormlite core method
                ArrayList<RealDiary> listdata= diaryDao2.getRealAllDiary();
                RealDiary realDiary=null;

                // this is the Column of the table and same for Header of CSV file

                String[] arrStr1 = {DataBaseHelper.DIARYS_ID, DataBaseHelper.DIARYS_MEMO, DataBaseHelper.DIARYS_IMAGE, DataBaseHelper.DIARYS_DATE, DataBaseHelper.DIARYS_MONTH, DataBaseHelper.DIARYS_YEAR,DataBaseHelper.DIARYS_DAY };
                csvWrite.writeNext(arrStr1);

                if(listdata.size() > 1)
                {
                    for(int index=0; index < listdata.size(); index++)
                    {
                        realDiary=listdata.get(index);
                        String arrStr[] ={String.valueOf(realDiary.getId()), realDiary.getMemo(), realDiary.getImage(), String.valueOf(realDiary.getDate()), String.valueOf(realDiary.getMonth()), String.valueOf(realDiary.getYear()), String.valueOf(realDiary.getDay())};
                        csvWrite.writeNext(arrStr);
                    }
                }

                csvWrite.close();
                return true;
            }
            catch (IOException e){
                Log.e("MainActivity", e.getMessage(), e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (this.dialog.isShowing()){
                this.dialog.dismiss();
            }
            if (success){
                Toast.makeText(SettingActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SettingActivity.this, "Export failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }*/



}
