package com.customme.fullhdvideo.dataGlory;

import java.io.Serializable;


public class Itemjamshaid implements Serializable {
    String _ID;
    String ARTIST;
    String TITLE;
    String DATA;
    String DISPLAY_NAME;
    String DURATION;
    Long SIZE;
    public Itemjamshaid(String _ID, String ARTIST, String TITLE,
                        String DATA, String DISPLAY_NAME, String DURATION,Long SIZE){

        this._ID=_ID;
        this.ARTIST=ARTIST;
        this.TITLE=TITLE;
        this.DATA=DATA;
        this.DISPLAY_NAME=DISPLAY_NAME;
        this.DURATION=DURATION;
        this.SIZE = SIZE;
    };
    public Itemjamshaid(){

    }
    public String get_ID() {
        return _ID;
    }

    public void set_ID(String _ID) {
        this._ID = _ID;
    }

    public String getARTIST() {
        return ARTIST;
    }

    public void setARTIST(String ARTIST) {
        this.ARTIST = ARTIST;
    }
/**/
    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    public String getDISPLAY_NAME() {
        return DISPLAY_NAME;
    }

    public void setDISPLAY_NAME(String DISPLAY_NAME) {
        this.DISPLAY_NAME = DISPLAY_NAME;
    }

    public String getDURATION() {
        return DURATION;
    }

    public void setDURATION(String DURATION) {
        this.DURATION = DURATION;
    }

    public Long getSize() {
        return SIZE;
    }

    public void setSIZE(Long SIZE) {
        this.SIZE = SIZE;
    }


}
