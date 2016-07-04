package com.dclover.gpsutilities.maps.mapmoduls;

/**
 * Created by Kinghero on 7/4/2016.
 */
public class item {
    private String text;
    private int img;
    private boolean ke=false;
    public item(String text,int img,boolean ke)
    {
        this.text=text;
        this.img=img;
        this.ke=ke;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public boolean isKe() {
        return ke;
    }

    public void setKe(boolean ke) {
        this.ke = ke;
    }
}
