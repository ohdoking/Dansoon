package com.ohdoking.dansoondiary.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.dto.Image;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-03-20.
 */
public class CardInListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Image> mListAdapter = new ArrayList<Image>();

    public CardInListAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getCount() {
        return mListAdapter.size();
    }

    @Override
    public Image getItem(int position) {
        return mListAdapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(Image image){
        mListAdapter.add(image);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageViewHolder holder;
        if(convertView == null){

            holder = new ImageViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.card_in_list_item,null);

            holder.icon1 = (ImageView) convertView.findViewById(R.id.icon1);
            holder.icon2 = (ImageView) convertView.findViewById(R.id.icon2);
            holder.icon3 = (ImageView) convertView.findViewById(R.id.icon3);
            holder.icon4 = (ImageView) convertView.findViewById(R.id.icon4);

            convertView.setTag(holder);
        }
        else{
            holder = (ImageViewHolder) convertView.getTag();
        }

        Image image = mListAdapter.get(position);

        if(image.getIcon1() != null){
            holder.icon1.setImageResource(image.getIcon1());
        }
        if(image.getIcon2() != null){
            holder.icon2.setImageResource(image.getIcon2());
        }
        if(image.getIcon3() != null){
            holder.icon3.setImageResource(image.getIcon3());
        }
        if(image.getIcon4() != null) {
            holder.icon4.setImageResource(image.getIcon4());
        }

        return convertView;
    }
}
