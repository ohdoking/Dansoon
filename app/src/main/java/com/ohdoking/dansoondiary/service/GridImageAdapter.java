package com.ohdoking.dansoondiary.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-02-27.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private final ArrayList<Integer> imageArrayList;

    public GridImageAdapter(Context context) {
        this.context = context;
        imageArrayList = new ArrayList<Integer>();
    }

    public GridImageAdapter(Context context, ArrayList<Integer> imageArrayList) {
        this.context = context;
        this.imageArrayList = imageArrayList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Integer image = imageArrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.gridview_item, null);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            imageView.setImageResource(image);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    /**
     * image 하나 추가하기
     * @param image
     */
    public void addImage(Integer image){
        imageArrayList.add(image);
    }

    /**
     * image 한번에 추가하기
     * @param images
     */
    public void addArrayImage(ArrayList<Integer> images){
        imageArrayList.addAll(images);
    }


    @Override
    public int getCount() {
        return imageArrayList.size();
    }

    @Override
    public Integer getItem(int position) {
        return imageArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}