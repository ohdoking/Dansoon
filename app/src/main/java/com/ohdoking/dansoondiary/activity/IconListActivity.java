package com.ohdoking.dansoondiary.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.service.GridImageAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IconListActivity extends BaseAppCompatActivity {

    GridView gridView;
    GridImageAdapter gridImageAdapter;
    DiaryDao diaryDao;
    ArrayList<Integer> selectArrayList;
    Intent intent;
    ImageView backList;
    ImageView saveDiary;

    Integer counter=0;

    ArrayList<Integer> alreadySelectedArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        selectArrayList = new ArrayList<Integer>();

        alreadySelectedArrayList = getFavoritesIcon();
        if(alreadySelectedArrayList != null){
            selectArrayList.addAll(alreadySelectedArrayList);
        }
        gridImageAdapter = new GridImageAdapter(getApplicationContext(),alreadySelectedArrayList);


        diaryDao = new DiaryDao(getApplicationContext());

        initId();
        setGridImageAdapter();

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
        backList = (ImageView) findViewById(R.id.backWrite);
        saveDiary = (ImageView) findViewById(R.id.saveIcon);
        gridView = (GridView) findViewById(R.id.gridView2);
    }

    /**
     * Grid Adapter를 세팅
     */
    void setGridImageAdapter(){


        gridView.setAdapter(gridImageAdapter);

        for(int i = 2; i < DsStatic.buttonList44Rev.length ; i++){
            gridImageAdapter.addImage(i);
        }

    }

    /**
     * 클릭 이벤트
     */
    void clickEvent(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int RealPosition = position+2;
                LinearLayout l =(LinearLayout)v;
                Integer key = 0;
                ImageView image = (ImageView) l.getChildAt(0);//.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//                LinearLayout l= (LinearLayout)parent.getChildAt(0);
//                Image image = (ImageView)l.getChildAt(0);


                if(hasDuplicates(gridImageAdapter.getItem(position))){
                    image.setImageResource(DsStatic.buttonList44[RealPosition]);
                    Integer temp = gridImageAdapter.getItem(position);

                    for(int i = 0; i < selectArrayList.size() ; i++) {
                        Log.i("test123",selectArrayList.get(i) +"=="+ temp);
                        if(selectArrayList.get(i) == temp){
                            selectArrayList.remove(i);
                            gridImageAdapter.removeAlreadyImage(RealPosition);
                            i--;
                        }
                    }
                    counter--;
                }
                else{
                    image.setImageResource(DsStatic.buttonList44Rev[RealPosition]);
                    selectArrayList.add(RealPosition);
                    gridImageAdapter.addAlreadyImage(RealPosition);
                    counter++;
                }
            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IconListActivity.this, WriteDiaryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        saveDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveFavoritesIcon(selectArrayList);
                Intent intent;
                if(getVisitState()){
                    setVisitState(false);
                    intent = new Intent(IconListActivity.this, MainListActivity.class);
                }
                else{
                    intent = new Intent(IconListActivity.this, WriteDiaryActivity.class);
                }
                startActivity(intent);
                finish();
            }
        });

        backList.setOnTouchListener(menuTouchListenr);
        saveDiary.setOnTouchListener(menuTouchListenr);


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

    public ArrayList<Integer> getFavoritesIcon(){
        ArrayList<Integer> iconList;
        SharedPreferences pref = getSharedPreferences(DsStatic.ICONTABLE, MODE_PRIVATE);
        String iconListString =  pref.getString(DsStatic.FAVORITEICON,"");
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
    /**
     * 선호 하는 아이콘 sharedpreference에 저장하기
     * @param favorites 선호하는 아이콘 목록
     */
    public void saveFavoritesIcon( List favorites) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = getSharedPreferences(DsStatic.ICONTABLE,Context.MODE_PRIVATE);
        editor = settings.edit();
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);
        editor.putString(DsStatic.FAVORITEICON, jsonFavorites);
        editor.commit();
    }

}
/*extends BaseAppCompatActivity {

    GridView mGrid;
    AppsAdapter iconAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_icon_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        loadApps();

       *//* mGrid = (GridView) findViewById(R.id.iconGridView);
        mGrid.setBackgroundResource(R.color.colorAppMain);
        iconAdapter = new AppsAdapter();
        mGrid.setAdapter(iconAdapter);

        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);
        iconAdapter.addIcon(R.drawable.diary_baby_icon);


        mGrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL);
        mGrid.setMultiChoiceModeListener(new MultiChoiceModeListener());*//*
    }

    *//*private List<ResolveInfo> mApps;

    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }*//*

    public class AppsAdapter extends BaseAdapter {
        ArrayList<Integer> mApps;
        public AppsAdapter() {
            mApps = new ArrayList<Integer>();
        }

        public void addIcon(Integer icon){
            mApps.add(icon);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CheckableLayout l;
            ImageView i;

            if (convertView == null) {
                i = new ImageView(IconListActivity.this);
                i.setBackgroundResource(R.color.colorPrimary);
//                i.setScaleType(ImageView.ScaleType.FIT_CENTER);
                i.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                l = new CheckableLayout(IconListActivity.this);
                LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                        GridView.LayoutParams.WRAP_CONTENT,
                        GridView.LayoutParams.WRAP_CONTENT);


                ll.setMargins(20, 20, 20, 20);

                l.setLayoutParams(ll);
                l.addView(i);
            } else {
                l = (CheckableLayout) convertView;
                i = (ImageView) l.getChildAt(0);
            }

            Integer info = mApps.get(position);
            i.setImageResource(info);

            return l;
        }

        public final int getCount() {
            return mApps.size();
        }

        public final Object getItem(int position) {
            return mApps.get(position);
        }

        public final long getItemId(int position) {
            return position;
        }
    }

    public class CheckableLayout extends FrameLayout implements Checkable {
        private boolean mChecked;

        public CheckableLayout(Context context) {
            super(context);
        }

        @SuppressWarnings("deprecation")
        public void setChecked(boolean checked) {
            mChecked = checked;
            setBackgroundDrawable(checked ? getResources().getDrawable(
                    android.R.drawable.checkbox_on_background) : null);
        }

        public boolean isChecked() {
            return mChecked;
        }

        public void toggle() {
            setChecked(!mChecked);
        }

    }

    public class MultiChoiceModeListener implements GridView.MultiChoiceModeListener {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Select Items");
            mode.setSubtitle("One item selected");
            return true;
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return true;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
            int selectCount = mGrid.getCheckedItemCount();
            switch (selectCount) {
                case 1:
                    mode.setSubtitle("One item selected");
                    break;
                default:
                    mode.setSubtitle("" + selectCount + " items selected");
                    break;
            }
        }
    }
}*/