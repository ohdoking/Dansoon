package com.ohdoking.dansoondiary.dto;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-02-26.
 */
public class Diary{
    Integer id;
    ArrayList<Integer> image;
    String memo;
    Long date;

    public Diary() {
        super();
    }

    public Diary(ArrayList<Integer> image, String memo) {
        super();
        this.image = image;
        this.memo = memo;
        this.date = date;
    }

    public Diary(ArrayList<Integer> image, String memo, long date) {
        super();
        this.image = image;
        this.memo = memo;
        this.date = date;
    }

   /* protected Diary(Parcel in) {
        //memo = in.createStringArrayList();

        this.id = id;
        this.Image = Image;
        this.memo = memo;
        this.date = date;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getId());
        dest.writeStringList(memo);
        dest.writeStringList(memo);
        dest.writeStringList(memo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ArrayList<Integer> getImage() {
        return image;
    }

    public void setImage(ArrayList<Integer> image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
