package com.crmmarketing.hmt.model;

import android.os.Parcel;
import android.os.Parcelable;

public class InqChild implements Parcelable {


    private String inqChildId;

    private String inqChildLastUpdate;

    private String inqChildTimeStamp;

    private String inqChildStatusUpdate;

    private String inqChildProductId;

    private String inqChildQty;

    private String inqChildStatus;

    public String getInqCatName() {
        return inqCatName;
    }

    public void setInqCatName(String inqCatName) {
        this.inqCatName = inqCatName;
    }

    public String getInqCatId() {
        return inqCatId;
    }

    public void setInqCatId(String inqCatId) {
        this.inqCatId = inqCatId;
    }

    private String inqCatName;
    private String inqCatId;

    public String getInqChildproductName() {
        return inqChildproductName;
    }

    public void setInqChildproductName(String inqChildproductName) {
        this.inqChildproductName = inqChildproductName;
    }

    private String inqChildproductName;

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    private String syncStatus;

    public String getInqMasterId() {
        return inqMasterId;
    }

    public void setInqMasterId(String inqMasterId) {
        this.inqMasterId = inqMasterId;
    }

    private String inqMasterId;

    public String getInqChildId() {
        return inqChildId;
    }

    public void setInqChildId(String inqChildId) {
        this.inqChildId = inqChildId;
    }

    public String getInqChildLastUpdate() {
        return inqChildLastUpdate;
    }

    public void setInqChildLastUpdate(String inqChildLastUpdate) {
        this.inqChildLastUpdate = inqChildLastUpdate;
    }

    public String getInqChildTimeStamp() {
        return inqChildTimeStamp;
    }

    public void setInqChildTimeStamp(String inqChildTimeStamp) {
        this.inqChildTimeStamp = inqChildTimeStamp;
    }

    public String getInqChildStatusUpdate() {
        return inqChildStatusUpdate;
    }

    public void setInqChildStatusUpdate(String inqChildStatusUpdate) {
        this.inqChildStatusUpdate = inqChildStatusUpdate;
    }

    public String getInqChildProductId() {
        return inqChildProductId;
    }

    public void setInqChildProductId(String inqChildProductId) {
        this.inqChildProductId = inqChildProductId;
    }

    public String getInqChildQty() {
        return inqChildQty;
    }

    public void setInqChildQty(String inqChildQty) {
        this.inqChildQty = inqChildQty;
    }

    public String getInqChildStatus() {
        return inqChildStatus;
    }

    public void setInqChildStatus(String inqChildStatus) {
        this.inqChildStatus = inqChildStatus;
    }

    public InqChild() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inqChildId);
        dest.writeString(this.inqChildLastUpdate);
        dest.writeString(this.inqChildTimeStamp);
        dest.writeString(this.inqChildStatusUpdate);
        dest.writeString(this.inqChildProductId);
        dest.writeString(this.inqChildQty);
        dest.writeString(this.inqChildStatus);
        dest.writeString(this.inqCatName);
        dest.writeString(this.inqCatId);
        dest.writeString(this.inqChildproductName);
        dest.writeString(this.syncStatus);
        dest.writeString(this.inqMasterId);
    }

    protected InqChild(Parcel in) {
        this.inqChildId = in.readString();
        this.inqChildLastUpdate = in.readString();
        this.inqChildTimeStamp = in.readString();
        this.inqChildStatusUpdate = in.readString();
        this.inqChildProductId = in.readString();
        this.inqChildQty = in.readString();
        this.inqChildStatus = in.readString();
        this.inqCatName = in.readString();
        this.inqCatId = in.readString();
        this.inqChildproductName = in.readString();
        this.syncStatus = in.readString();
        this.inqMasterId = in.readString();
    }

    public static final Creator<InqChild> CREATOR = new Creator<InqChild>() {
        @Override
        public InqChild createFromParcel(Parcel source) {
            return new InqChild(source);
        }

        @Override
        public InqChild[] newArray(int size) {
            return new InqChild[size];
        }
    };
}