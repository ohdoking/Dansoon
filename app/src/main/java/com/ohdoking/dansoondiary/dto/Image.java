package com.ohdoking.dansoondiary.dto;

/**
 * Created by Administrator on 2016-03-20.
 */
public class Image {

    private Integer icon1;
    private Integer icon2;
    private Integer icon3;
    private Integer icon4;

    public Integer getIcon1() {
        return icon1;
    }

    public void setIcon1(Integer icon1) {
        this.icon1 = icon1;
    }

    public Integer getIcon2() {
        return icon2;
    }

    public void setIcon2(Integer icon2) {
        this.icon2 = icon2;
    }

    public Integer getIcon3() {
        return icon3;
    }

    public void setIcon3(Integer icon3) {
        this.icon3 = icon3;
    }

    public Integer getIcon4() {
        return icon4;
    }

    public void setIcon4(Integer icon4) {
        this.icon4 = icon4;
    }
    public Image() {
    }

    public Image(Integer icon1, Integer icon2, Integer icon3, Integer icon4) {

        this.icon1 = icon1;
        this.icon2 = icon2;
        this.icon3 = icon3;
        this.icon4 = icon4;
    }


}
