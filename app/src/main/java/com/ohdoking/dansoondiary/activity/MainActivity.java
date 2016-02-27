package com.ohdoking.dansoondiary.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.fragment.CaldroidSampleCustomFragment;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.util.Calendar;
import java.util.Date;

public class MainActivity  extends BaseAppCompatActivity {

    Date date;
    CaldroidSampleCustomFragment caldroidFragment;
    // A integer, that identifies each notification uniquely
    public static final int NOTIFICATION_ID = 1;

    ImageView moveList;
    ImageView moveSetting;
    ImageView moveStatic;

    public final static String
            DIALOG_TITLE = "dialogTitle",
            MONTH = "month",
            YEAR = "year",
            SHOW_NAVIGATION_ARROWS = "showNavigationArrows",
            DISABLE_DATES = "disableDates",
            SELECTED_DATES = "selectedDates",
            MIN_DATE = "minDate",
            MAX_DATE = "maxDate",
            ENABLE_SWIPE = "enableSwipe",
            START_DAY_OF_WEEK = "startDayOfWeek",
            SIX_WEEKS_IN_CALENDAR = "sixWeeksInCalendar",
            ENABLE_CLICK_ON_DISABLED_DATES = "enableClickOnDisabledDates",
            SQUARE_TEXT_VIEW_CELL = "squareTextViewCell",
            THEME_RESOURCE = "themeResource";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);

        initId();
        menuClickEvent();
        calendarInit();
        ClickEvent();
    }

    /**
     * init Xml Id
     */
    void initId(){
        moveList = (ImageView) findViewById(R.id.moveList);
        moveStatic = (ImageView) findViewById(R.id.moveStatic);
        moveSetting = (ImageView) findViewById(R.id.moveSetting);
    }

    /**
     * 캘린더 초기화
     */
    void calendarInit(){

        caldroidFragment = new CaldroidSampleCustomFragment();
        date = new Date();
        Bundle args = new Bundle();

        Calendar cal = Calendar.getInstance();
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        caldroidFragment.setArguments(args);

        caldroidFragment.setCaldroidListener(listener);

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();

        t.replace(R.id.calendar, caldroidFragment);
        t.commit();

        /**
         * 캘린더 특정 요일에 이미지 삽임
         */
         /*Drawable d = getResources().getDrawable(R.mipmap.ic_launcher );

        caldroidFragment.setBackgroundDrawableForDate(d,date);*/

        /**
         * popup 캘린더 호출
         */
        /*Button b = (Button) findViewById(R.id.click);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaldroidFragment dialogCaldroidFragment = CaldroidFragment.newInstance("Select a date", 3, 2013);
                dialogCaldroidFragment.show(getSupportFragmentManager(),"TAG");
            }
        });*/

    }

    /**
     * 캘린더 클릭 이벤트 리스너
     */
    final CaldroidListener listener = new CaldroidListener() {

        @Override
        public void onSelectDate(Date date, View view) {

            LinearLayout l = (LinearLayout) view;
            ImageView i = (ImageView) l.getChildAt(0);

            i.setImageResource(R.mipmap.ic_launcher);

            Toast.makeText(getApplicationContext(), String.valueOf(date),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onChangeMonth(int month, int year) {
            String text = "month: " + month + " year: " + year;
            Toast.makeText(getApplicationContext(), text,
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onLongClickDate(Date date, View view) {
            Toast.makeText(getApplicationContext(),
                    "Long click " + String.valueOf(date),
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCaldroidViewCreated() {
            Toast.makeText(getApplicationContext(),
                    "Caldroid view is created",
                    Toast.LENGTH_SHORT).show();
        }

    };


    /**
     * menu click event
     */
    void menuClickEvent(){
        moveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainListActivity.class);
                startActivity(intent);
            }
        });
        moveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
        moveStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StaticsActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Click Event
     */
    private void ClickEvent() {

        /**
         * Floating Button
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,WriteDiaryActivity.class);
                startActivity(i);
            }
        });
    }


    /**
     * notification View
     * @param view
     */
    public void sendNotification(View view) {

        // Use NotificationCompat.Builder to set up our notification.
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //icon appears in device notification bar and right hand corner of notification
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // This intent is fired when notification is clicked
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://javatechig.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // Set the intent that will fire when the user taps the notification.
        builder.setContentIntent(pendingIntent);

        // Large icon appears on the left of the notification
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        // Content title, which appears in large type at the top of the notification
        builder.setContentTitle("Notifications Title");

        // Content text, which appears in smaller text below the title
        builder.setContentText("Your notification content here.");

        // The subtext, which appears under the text on newer devices.
        // This will show-up in the devices with Android 4.2 and above only
        builder.setSubText("Tap to view documentation about notifications.");

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Will display the notification in the notification bar
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

}
