package com.ohdoking.dansoondiary.dto;

/**
 * Created by Administrator on 2016-03-24.
 */
public class RealDiary {

    int id;
    long date;
    String image;
    String memo;
    int month;
    int year;
    int day;


    public RealDiary() {
    }

    public RealDiary(int id, long date, String image, String memo, int month, int year, int day) {
        this.id = id;
        this.date = date;
        this.image = image;
        this.memo = memo;
        this.month = month;
        this.year = year;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

}
