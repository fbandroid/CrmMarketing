package com.crmmarketing.hmt.inquirytogson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RefInfo {

    @SerializedName("ref_name")
    @Expose
    private String refName;
    @SerializedName("ref_email")
    @Expose
    private String refEmail;
    @SerializedName("ref_phone")
    @Expose
    private String refPhone;
    @SerializedName("ref_code")
    @Expose
    private String refCode;
    @SerializedName("ref_other")
    @Expose
    private String refOther;

    public String getRefName() {
        return refName;
    }

    public void setRefName(String refName) {
        this.refName = refName;
    }

    public String getRefEmail() {
        return refEmail;
    }

    public void setRefEmail(String refEmail) {
        this.refEmail = refEmail;
    }

    public String getRefPhone() {
        return refPhone;
    }

    public void setRefPhone(String refPhone) {
        this.refPhone = refPhone;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefOther() {
        return refOther;
    }

    public void setRefOther(String refOther) {
        this.refOther = refOther;
    }
}
