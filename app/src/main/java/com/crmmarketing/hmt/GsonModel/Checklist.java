package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checklist {

    @SerializedName("inq_id")
    @Expose
    private String inqId;
    @SerializedName("inq_datetime")
    @Expose
    private String inqDatetime;
    @SerializedName("inq_userid")
    @Expose
    private String inqUserid;
    @SerializedName("inq_custid")
    @Expose
    private String inqCustid;
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
    @SerializedName("inq_counter")
    @Expose
    private String inqCounter;
    @SerializedName("inq_status")
    @Expose
    private String inqStatus;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("inquiryid")
    @Expose
    private String inquiryid;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("update_datetime")
    @Expose
    private String updateDatetime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    @SerializedName("cus_name")
    @Expose
    private String cus_name;

    private boolean isChecked;

    public String getInqId() {
        return inqId;
    }

    public void setInqId(String inqId) {
        this.inqId = inqId;
    }

    public String getInqDatetime() {
        return inqDatetime;
    }

    public void setInqDatetime(String inqDatetime) {
        this.inqDatetime = inqDatetime;
    }

    public String getInqUserid() {
        return inqUserid;
    }

    public void setInqUserid(String inqUserid) {
        this.inqUserid = inqUserid;
    }

    public String getInqCustid() {
        return inqCustid;
    }

    public void setInqCustid(String inqCustid) {
        this.inqCustid = inqCustid;
    }

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

    public String getInqCounter() {
        return inqCounter;
    }

    public void setInqCounter(String inqCounter) {
        this.inqCounter = inqCounter;
    }

    public String getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(String inqStatus) {
        this.inqStatus = inqStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(String inquiryid) {
        this.inquiryid = inquiryid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}