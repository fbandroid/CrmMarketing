package com.crmmarketing.hmt.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class InqMaster implements Parcelable {


    private String inqMasterId;

    private String branchId;

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }

    private String feedTime="";

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    private String branchName;

    private String inqMasterLastUpdate;

    private String inqMasterStatusUpdate;

    private String inqMasterTimeStamp;

    private String inqEmpId;

    private String inqInvestRange;

    private String inqFeedbackTime;

    private String inqMasterStatus;

    public String getInqCounter() {
        return inqCounter;
    }

    public void setInqCounter(String inqCounter) {
        this.inqCounter = inqCounter;
    }

    private String inqCounter;

    public String getInqRemark() {
        return inqRemark;
    }

    public void setInqRemark(String inqRemark) {
        this.inqRemark = inqRemark;
    }

    private String inqRemark;

    public String getInqCusId() {
        return inqCusId;
    }

    public void setInqCusId(String inqCusId) {
        this.inqCusId = inqCusId;
    }

    private String inqCusId;

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    private String syncStatus;

    public InqChild getInqChildObject() {
        return inqChildObject;
    }

    public void setInqChildObject(InqChild inqChildObject) {
        this.inqChildObject = inqChildObject;
    }

    private InqChild inqChildObject;

    private CusInfo cusInfo;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    private String empName;
    private String parentName;

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    private String panNo;



    public String getStausInq() {
        return stausInq;
    }

    public void setStausInq(String stausInq) {
        this.stausInq = stausInq;
    }

    private String stausInq="";

    private List<InqChild> inqChild = null;

    public String getInqMasterId() {
        return inqMasterId;
    }

    public void setInqMasterId(String inqMasterId) {
        this.inqMasterId = inqMasterId;
    }

    public String getInqMasterLastUpdate() {
        return inqMasterLastUpdate;
    }

    public void setInqMasterLastUpdate(String inqMasterLastUpdate) {
        this.inqMasterLastUpdate = inqMasterLastUpdate;
    }

    public String getInqMasterStatusUpdate() {
        return inqMasterStatusUpdate;
    }

    public void setInqMasterStatusUpdate(String inqMasterStatusUpdate) {
        this.inqMasterStatusUpdate = inqMasterStatusUpdate;
    }

    public String getInqMasterTimeStamp() {
        return inqMasterTimeStamp;
    }

    public void setInqMasterTimeStamp(String inqMasterTimeStamp) {
        this.inqMasterTimeStamp = inqMasterTimeStamp;
    }

    public String getInqEmpId() {
        return inqEmpId;
    }

    public void setInqEmpId(String inqEmpId) {
        this.inqEmpId = inqEmpId;
    }

    public String getInqInvestRange() {
        return inqInvestRange;
    }

    public void setInqInvestRange(String inqInvestRange) {
        this.inqInvestRange = inqInvestRange;
    }

    public String getInqFeedbackTime() {
        return inqFeedbackTime;
    }

    public void setInqFeedbackTime(String inqFeedbackTime) {
        this.inqFeedbackTime = inqFeedbackTime;
    }

    public String getInqMasterStatus() {
        return inqMasterStatus;
    }

    public void setInqMasterStatus(String inqMasterStatus) {
        this.inqMasterStatus = inqMasterStatus;
    }

    public CusInfo getCusInfo() {
        return cusInfo;
    }

    public void setCusInfo(CusInfo cusInfo) {
        this.cusInfo = cusInfo;
    }

    public List<InqChild> getInqChild() {
        return inqChild;
    }

    public void setInqChild(List<InqChild> inqChild) {
        this.inqChild = inqChild;
    }

    public InqMaster() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inqMasterId);
        dest.writeString(this.branchId);
        dest.writeString(this.feedTime);
        dest.writeString(this.branchName);
        dest.writeString(this.inqMasterLastUpdate);
        dest.writeString(this.inqMasterStatusUpdate);
        dest.writeString(this.inqMasterTimeStamp);
        dest.writeString(this.inqEmpId);
        dest.writeString(this.inqInvestRange);
        dest.writeString(this.inqFeedbackTime);
        dest.writeString(this.inqMasterStatus);
        dest.writeString(this.inqCounter);
        dest.writeString(this.inqRemark);
        dest.writeString(this.inqCusId);
        dest.writeString(this.syncStatus);
        dest.writeParcelable(this.inqChildObject, flags);
        dest.writeParcelable(this.cusInfo, flags);
        dest.writeString(this.empName);
        dest.writeString(this.parentName);
        dest.writeString(this.panNo);
        dest.writeString(this.stausInq);
        dest.writeTypedList(this.inqChild);
    }

    protected InqMaster(Parcel in) {
        this.inqMasterId = in.readString();
        this.branchId = in.readString();
        this.feedTime = in.readString();
        this.branchName = in.readString();
        this.inqMasterLastUpdate = in.readString();
        this.inqMasterStatusUpdate = in.readString();
        this.inqMasterTimeStamp = in.readString();
        this.inqEmpId = in.readString();
        this.inqInvestRange = in.readString();
        this.inqFeedbackTime = in.readString();
        this.inqMasterStatus = in.readString();
        this.inqCounter = in.readString();
        this.inqRemark = in.readString();
        this.inqCusId = in.readString();
        this.syncStatus = in.readString();
        this.inqChildObject = in.readParcelable(InqChild.class.getClassLoader());
        this.cusInfo = in.readParcelable(CusInfo.class.getClassLoader());
        this.empName = in.readString();
        this.parentName = in.readString();
        this.panNo = in.readString();
        this.stausInq = in.readString();
        this.inqChild = in.createTypedArrayList(InqChild.CREATOR);
    }

    public static final Creator<InqMaster> CREATOR = new Creator<InqMaster>() {
        @Override
        public InqMaster createFromParcel(Parcel source) {
            return new InqMaster(source);
        }

        @Override
        public InqMaster[] newArray(int size) {
            return new InqMaster[size];
        }
    };
}