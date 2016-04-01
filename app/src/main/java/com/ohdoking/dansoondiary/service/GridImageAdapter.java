package com.ohdoking.dansoondiary.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-02-27.
 */
public class GridImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Integer> imageArrayList;
    private ArrayList<Integer> alreadyImageArrayList;




    public GridImageAdapter(Context context) {
        this.context = context;
        imageArrayList = new ArrayList<Integer>();
    }

    public GridImageAdapter(Context context, ArrayList<Integer> alreadyImageArrayList) {
        this.context = context;
        this.alreadyImageArrayList = alreadyImageArrayList;
        imageArrayList = new ArrayList<Integer>();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Integer imageNum = imageArrayList.get(position);

        Log.i("ohdoking12345",position+"");

        Holder holder = null;
        if(convertView==null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.gridview_item, null);
            holder = new Holder();
            holder.image =(ImageView) convertView.findViewById(R.id.grid_item_image);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }

        holder.image.setImageResource(DsStatic.buttonList44[imageNum]);

        if(alreadyImageArrayList != null){
            for(Integer img:alreadyImageArrayList){
                if(imageNum.equals(img)){
                    Log.i("ohdoking123456",img+"!!");
                    holder.image.setImageResource(DsStatic.buttonList44Rev[img]);
                }
            }
        }
        return convertView;

        /*Integer imageNum = imageArrayList.get(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.gridview_item, null);
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.grid_item_image);
            Log.i("ohdoking1234",imageNum+"");
            imageView.setImageResource(DsStatic.buttonList44Rev[imageNum]);

            if(alreadyImageArrayList != null){
                for(Integer img:alreadyImageArrayList){

                    if(imageNum.equals(img)){
                        imageView.setImageResource(DsStatic.buttonList44[img]);
                    }
                }
            }



        } else {
            gridView = (View) convertView;
        }*/

//        return gridView;
    }

    public void addAlreadyListView(ArrayList<Integer> images){
        alreadyImageArrayList.addAll(images);
    }

    /**
     * image 하나 추가하기
     * @param image
     */
    public void addImage(Integer image){
        imageArrayList.add(image);
    }

    public void removeImage(Integer pos){
        imageArrayList.remove(pos);
    }
    public void addAlreadyImage(Integer image){
        alreadyImageArrayList.add(image);
    }

    public void removeAlreadyImage(Integer pos){
        alreadyImageArrayList.remove(pos);
    }

    /**
     * 이미지 비교하기
     */
    boolean diff(ImageView imageView,Integer resource){
        Drawable temp = imageView.getDrawable();
        Drawable temp1 = context.getResources().getDrawable(resource);

        Bitmap tmpBitmap = ((BitmapDrawable)temp).getBitmap();
        Bitmap tmpBitmap1 = ((BitmapDrawable)temp1).getBitmap();

        if(tmpBitmap.equals(tmpBitmap1)){
            return true;
        }
        return false;
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

    public static class Holder{
        public ImageView image;
    }

}