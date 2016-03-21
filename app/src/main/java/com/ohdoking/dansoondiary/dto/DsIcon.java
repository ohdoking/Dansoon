package com.ohdoking.dansoondiary.dto;

import java.util.Comparator;

/**
 * Created by Administrator on 2016-03-15.
 */
public class DsIcon {
    private Integer icon;
    private Integer count;

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    /*Comparator for sorting the list by roll no*/
    public static Comparator<DsIcon> iconCount = new Comparator<DsIcon>() {

        @Override
        public int compare(DsIcon s1, DsIcon s2) {

            int rollno1 = s1.getCount();
            int rollno2 = s2.getCount();

	   /*For ascending order*/
//            return rollno-rollno2;
	   /*For descending order*/
            return rollno2-rollno1;
        }};
}
