package com.example.mytownpcbang.PcbangArray;

import java.io.Serializable;

/**
 * Created by Jeongmin on 2018-01-18.
 */

public class PcBang_info implements Serializable {
    private String pcBangName;//
    private String pcBangTel;
    private String postCode;
    private String _id;
    private String roadAddress;
    private String detailAddress;
    private String starnum;
    private String Latitude;
    private String Longitude;



    public PcBang_info(String pcBangName, String pcBangTel, String postCode, String roadAddress, String _id,String detailAddress, String starnum,String Latitude,String Longitude) {
        this.pcBangName = pcBangName;
        this.pcBangTel = pcBangTel;
        this.postCode = postCode;
        this._id=_id;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.starnum=starnum;
        this.Latitude=Latitude;
        this.Longitude=Longitude;
    }

    public void setPcBangName(String pcBangName) {
        this.pcBangName = pcBangName;
    }

    public void setpcBangTel(String pcBangTel) {
        this.pcBangTel = pcBangTel;
    }

    public void setpostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setroadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setdetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setLatitude(String Latitude){this.Latitude=Latitude;}

    public void setLongitude(String Longitude){this.Longitude=Longitude;}
    public void set_id(String _id){this._id=_id;}


    public String get_id(){return  _id;}
    public String getLatitude(){return Latitude;}

    public String getLongitude(){return Longitude;}

    public String getPcBangName() {
        return pcBangName;
    }

    public String getpcBangTel() {
        return pcBangTel;
    }

    public String getpostCode() {
        return postCode;
    }

    public String getroadAddress() {
        return roadAddress;
    }

    public String getdetailAddress() {
        return detailAddress;
    }

    public String getStarnum(){return starnum;}



}
