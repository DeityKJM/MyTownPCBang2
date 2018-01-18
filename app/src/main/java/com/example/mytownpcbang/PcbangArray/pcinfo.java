package com.example.mytownpcbang.PcbangArray;

import java.io.Serializable;
import java.util.ArrayList;



public class pcinfo implements Serializable {
    private String pcBangName;//
    private int pcBangTel;
    private int postCode;
    private String roadAddress;
    private String detailAddress;
    private int ipFirst;
    private int ipSecond;
    private int ipThird;
    private float starnum;
    private float Latitude;
    private float Longitude;
    private ArrayList<PcbangReview> Arr;


    public pcinfo(String pcBangName, int pcBangTel, int postCode, String roadAddress, String detailAddress, int ipFirst, int ipSecond, int ipThird, float starnum,float Latitude,float Longitude) {
        this.pcBangName = pcBangName;
        this.pcBangTel = pcBangTel;
        this.postCode = postCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.ipFirst = ipFirst;
        this.ipSecond = ipSecond;
        this.ipThird = ipThird;
        this.starnum=starnum;
        this.Latitude=Latitude;
        this.Longitude=Longitude;
    }

    public void setPcBangName(String pcBangName) {
        this.pcBangName = pcBangName;
    }

    public void setpcBangTel(int pcBangTel) {
        this.pcBangTel = pcBangTel;
    }

    public void setpostCode(int postCode) {
        this.postCode = postCode;
    }

    public void setroadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void setdetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void setipFirst(int ipFirst) {
        this.ipFirst = ipFirst;
    }

    public void setipSecond(int ipSecond) {
        this.ipSecond = ipSecond;
    }

    public void setipThird(int ipThird) {
        this.ipThird = ipThird;
    }

    public void setLatitude(float Latitude){this.Latitude=Latitude;}

    public void setLongitude(float Longitude){this.Longitude=Longitude;}

    public void setArr(PcbangReview arr){Arr.add(arr);}

    public ArrayList<PcbangReview> getArr(){return Arr;}



    public float getLatitude(){return Latitude;}

    public float getLongitude(){return Longitude;}

    public String getPcBangName() {
        return pcBangName;
    }

    public int getpcBangTel() {
        return pcBangTel;
    }

    public int getpostCode() {
        return postCode;
    }

    public String getroadAddress() {
        return roadAddress;
    }

    public String getdetailAddress() {
        return detailAddress;
    }

    public int getipFirst() {
        return ipFirst;
    }

    public int getipSecond() {
        return ipSecond;
    }

    public int getipThird() {
        return ipThird;
    }

    public float getStarnum(){return starnum;}

}

