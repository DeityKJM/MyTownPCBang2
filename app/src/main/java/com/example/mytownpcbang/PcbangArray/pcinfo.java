package com.example.mytownpcbang.PcbangArray;

import java.io.Serializable;


/**
 * Created by KimJeongMin on 2017-12-15.
 */

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


    public pcinfo(String pcBangName, int pcBangTel, int postCode, String roadAddress, String detailAddress, int ipFirst, int ipSecond, int ipThird,float starnum) {
        this.pcBangName = pcBangName;
        this.pcBangTel = pcBangTel;
        this.postCode = postCode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.ipFirst = ipFirst;
        this.ipSecond = ipSecond;
        this.ipThird = ipThird;
        this.starnum=starnum;
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
/*
    @Override
    public int describeContents() {
        return 0;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pcBangName);
        dest.writeInt(pcBangTel);
        dest.writeInt(postCode);
        dest.writeString(roadAddress);
        dest.writeString(detailAddress);
        dest.writeInt(ipFirst);
        dest.writeInt(ipSecond);
        dest.writeInt(ipThird);
    }

    private void readFromParcel(Parcel in) {
        pcBangName = in.readString();
        pcBangTel = in.readInt();
        postCode = in.readInt();
        roadAddress = in.readString();
        detailAddress = in.readString();
        ipFirst = in.readInt();
        ipSecond = in.readInt();
        ipThird = in.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public pcinfo createFromParcel(Parcel in) {
            return new pcinfo(in);
        }

        public pcinfo[] newArray(int size) {
            return new pcinfo[size];
        }
    };
*/
}

