package com.crmmarketing.hmt.inquirytogson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalInfo {

    @SerializedName("cus_name")
    @Expose
    private String cusName;
    @SerializedName("cus_addr")
    @Expose
    private String cusAddr;
    @SerializedName("cus_mobile")
    @Expose
    private String cusMobile;
    @SerializedName("cus_land")
    @Expose
    private String cusLand;
    @SerializedName("cus_email")
    @Expose
    private String cusEmail;
    @SerializedName("cus_alt_email")
    @Expose
    private String cusAltEmail;
    @SerializedName("cus_birth")
    @Expose
    private String cusBirth;
    @SerializedName("cus_occupation")
    @Expose
    private String cusOccupation;

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddr() {
        return cusAddr;
    }

    public void setCusAddr(String cusAddr) {
        this.cusAddr = cusAddr;
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

    public String getCusEmail() {
        return cusEmail;
    }

    public void setCusEmail(String cusEmail) {
        this.cusEmail = cusEmail;
    }

    public String getCusAltEmail() {
        return cusAltEmail;
    }

    public void setCusAltEmail(String cusAltEmail) {
        this.cusAltEmail = cusAltEmail;
    }

    public String getCusBirth() {
        return cusBirth;
    }

    public void setCusBirth(String cusBirth) {
        this.cusBirth = cusBirth;
    }

    public String getCusOccupation() {
        return cusOccupation;
    }

    public void setCusOccupation(String cusOccupation) {
        this.cusOccupation = cusOccupation;
    }

}