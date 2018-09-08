package com.crmmarketing.hmt.model;


import android.os.Parcel;
import android.os.Parcelable;

public class CusInfo implements Parcelable {


    private String cusId;

    private String cusName;

    private String cusDob;

    private String cusEmail;

    private String cusMobile;

    private String cusLand;

    private String cusOptEmail;

    private String cusOccupation;

    private String cusRefName;

    private String cusRefEmail;

    private String cusRefMobile;

    private String cusRefCode;

    private String cusRefOther;

    public String getCusMother() {
        return cusMother;
    }

    public void setCusMother(String cusMother) {
        this.cusMother = cusMother;
    }

    public String getCusFamily() {
        return cusFamily;
    }

    public void setCusFamily(String cusFamily) {
        this.cusFamily = cusFamily;
    }

    public String getCusTSB() {
        return cusTSB;
    }

    public void setCusTSB(String cusTSB) {
        this.cusTSB = cusTSB;
    }

    private String cusMother;
    private String cusFamily;
    private String cusTSB;

    public String getNomineeName() {
        return nomineeName;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
    }

    public String getNomineePAN() {
        return nomineePAN;
    }

    public void setNomineePAN(String nomineePAN) {
        this.nomineePAN = nomineePAN;
    }

    private String nomineeName;
    private String nomineePAN;

    public String getU_name() {
        return u_name;
    }

    public void setU_name(String u_name) {
        this.u_name = u_name;
    }

    public String getParent_name() {
        return parent_name;
    }

    public void setParent_name(String parent_name) {
        this.parent_name = parent_name;
    }

    private String u_name;

    private String parent_name;

    public String getCusAddr() {
        return cusAddr;
    }

    public void setCusAddr(String cusAddr) {
        this.cusAddr = cusAddr;
    }

    private String cusAddr;

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusDob() {
        return cusDob;
    }

    public void setCusDob(String cusDob) {
        this.cusDob = cusDob;
    }

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusMobile() {
        return cusMobile;
    }

    public void setCusMobile(String cusMobile) {
        this.cusMobile = cusMobile;
    }

    public String getCusLand() {
        return cusLand;
    }

    public void setCusLand(String cusLand) {
        this.cusLand = cusLand;
    }

    public String getCusOptEmail() {
        return cusOptEmail;
    }

    public void setCusOptEmail(String cusOptEmail) {
        this.cusOptEmail = cusOptEmail;
    }

    public String getCusOccupation() {
        return cusOccupation;
    }

    public void setCusOccupation(String cusOccupation) {
        this.cusOccupation = cusOccupation;
    }

    public String getCusRefName() {
        return cusRefName;
    }

    public void setCusRefName(String cusRefName) {
        this.cusRefName = cusRefName;
    }

    public String getCusRefEmail() {
        return cusRefEmail;
    }

    public void setCusRefEmail(String cusRefEmail) {
        this.cusRefEmail = cusRefEmail;
    }

    public String getCusRefMobile() {
        return cusRefMobile;
    }

    public void setCusRefMobile(String cusRefMobile) {
        this.cusRefMobile = cusRefMobile;
    }

    public String getCusRefCode() {
        return cusRefCode;
    }

    public void setCusRefCode(String cusRefCode) {
        this.cusRefCode = cusRefCode;
    }

    public String getCusRefOther() {
        return cusRefOther;
    }

    public void setCusRefOther(String cusRefOther) {
        this.cusRefOther = cusRefOther;
    }

    public CusInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cusId);
        dest.writeString(this.cusName);
        dest.writeString(this.cusDob);
        dest.writeString(this.cusEmail);
        dest.writeString(this.cusMobile);
        dest.writeString(this.cusLand);
        dest.writeString(this.cusOptEmail);
        dest.writeString(this.cusOccupation);
        dest.writeString(this.cusRefName);
        dest.writeString(this.cusRefEmail);
        dest.writeString(this.cusRefMobile);
        dest.writeString(this.cusRefCode);
        dest.writeString(this.cusRefOther);
        dest.writeString(this.cusMother);
        dest.writeString(this.cusFamily);
        dest.writeString(this.cusTSB);
        dest.writeString(this.nomineeName);
        dest.writeString(this.nomineePAN);
        dest.writeString(this.u_name);
        dest.writeString(this.parent_name);
        dest.writeString(this.cusAddr);
    }

    protected CusInfo(Parcel in) {
        this.cusId = in.readString();
        this.cusName = in.readString();
        this.cusDob = in.readString();
        this.cusEmail = in.readString();
        this.cusMobile = in.readString();
        this.cusLand = in.readString();
        this.cusOptEmail = in.readString();
        this.cusOccupation = in.readString();
        this.cusRefName = in.readString();
        this.cusRefEmail = in.readString();
        this.cusRefMobile = in.readString();
        this.cusRefCode = in.readString();
        this.cusRefOther = in.readString();
        this.cusMother = in.readString();
        this.cusFamily = in.readString();
        this.cusTSB = in.readString();
        this.nomineeName = in.readString();
        this.nomineePAN = in.readString();
        this.u_name = in.readString();
        this.parent_name = in.readString();
        this.cusAddr = in.readString();
    }

    public static final Creator<CusInfo> CREATOR = new Creator<CusInfo>() {
        @Override
        public CusInfo createFromParcel(Parcel source) {
            return new CusInfo(source);
        }

        @Override
        public CusInfo[] newArray(int size) {
            return new CusInfo[size];
        }
    };
}