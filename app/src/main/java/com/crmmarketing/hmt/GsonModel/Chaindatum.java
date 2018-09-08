package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chaindatum {

    @SerializedName("inq_childname")
    @Expose
    private String inqChildname;
    @SerializedName("inq_parentname")
    @Expose
    private String inqParentname;
    @SerializedName("inq_branchid")
    @Expose
    private String inqBranchid;
    @SerializedName("inq_branchname")
    @Expose
    private String inqBranchname;
    @SerializedName("inq_investrange")
    @Expose
    private String inqInvestrange;
    @SerializedName("inq_feedbacktime")
    @Expose
    private String inqFeedbacktime;
    @SerializedName("inq_feed_time")
    @Expose
    private String inqFeedTime;
    @SerializedName("inq_remark")
    @Expose
    private String inqRemark;
    @SerializedName("inq_date")
    @Expose
    private String inqDate;
    @SerializedName("products")
    @Expose
    private String products;

    public String getInq_status() {
        return inq_status;
    }

    public void setInq_status(String inq_status) {
        this.inq_status = inq_status;
    }

    @SerializedName("inq_status")
    @Expose
    private String inq_status;

    public String getInqChildname() {
        return inqChildname;
    }

    public void setInqChildname(String inqChildname) {
        this.inqChildname = inqChildname;
    }

    public String getInqParentname() {
        return inqParentname;
    }

    public void setInqParentname(String inqParentname) {
        this.inqParentname = inqParentname;
    }

    public String getInqBranchid() {
        return inqBranchid;
    }

    public void setInqBranchid(String inqBranchid) {
        this.inqBranchid = inqBranchid;
    }

    public String getInqBranchname() {
        return inqBranchname;
    }

    public void setInqBranchname(String inqBranchname) {
        this.inqBranchname = inqBranchname;
    }

    public String getInqInvestrange() {
        return inqInvestrange;
    }

    public void setInqInvestrange(String inqInvestrange) {
        this.inqInvestrange = inqInvestrange;
    }

    public String getInqFeedbacktime() {
        return inqFeedbacktime;
    }

    public void setInqFeedbacktime(String inqFeedbacktime) {
        this.inqFeedbacktime = inqFeedbacktime;
    }

    public String getInqFeedTime() {
        return inqFeedTime;
    }

    public void setInqFeedTime(String inqFeedTime) {
        this.inqFeedTime = inqFeedTime;
    }

    public String getInqRemark() {
        return inqRemark;
    }

    public void setInqRemark(String inqRemark) {
        this.inqRemark = inqRemark;
    }

    public String getInqDate() {
        return inqDate;
    }

    public void setInqDate(String inqDate) {
        this.inqDate = inqDate;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

}