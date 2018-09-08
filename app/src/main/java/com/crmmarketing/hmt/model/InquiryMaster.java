package com.crmmarketing.hmt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 13-02-2017.
 */

public class InquiryMaster implements Parcelable {


    public String getKEY_INQUIRY_MASTER_ID() {
        return KEY_INQUIRY_MASTER_ID;
    }

    public void setKEY_INQUIRY_MASTER_ID(String KEY_INQUIRY_MASTER_ID) {
        this.KEY_INQUIRY_MASTER_ID = KEY_INQUIRY_MASTER_ID;
    }

    public String getKEY_INQUIRY_MASTER_TIMESTAMP() {
        return KEY_INQUIRY_MASTER_TIMESTAMP;
    }

    public void setKEY_INQUIRY_MASTER_TIMESTAMP(String KEY_INQUIRY_MASTER_TIMESTAMP) {
        this.KEY_INQUIRY_MASTER_TIMESTAMP = KEY_INQUIRY_MASTER_TIMESTAMP;
    }

    public String getKEY_INQUIRY_MASTER_USER_ID_FK() {
        return KEY_INQUIRY_MASTER_USER_ID_FK;
    }

    public void setKEY_INQUIRY_MASTER_USER_ID_FK(String KEY_INQUIRY_MASTER_USER_ID_FK) {
        this.KEY_INQUIRY_MASTER_USER_ID_FK = KEY_INQUIRY_MASTER_USER_ID_FK;
    }

    public String getKEY_INQUIRY_MASTER_CUSTOMER_ID_FK() {
        return KEY_INQUIRY_MASTER_CUSTOMER_ID_FK;
    }

    public void setKEY_INQUIRY_MASTER_CUSTOMER_ID_FK(String KEY_INQUIRY_MASTER_CUSTOMER_ID_FK) {
        this.KEY_INQUIRY_MASTER_CUSTOMER_ID_FK = KEY_INQUIRY_MASTER_CUSTOMER_ID_FK;
    }

    public String getKEY_INQUIRY_MASTER_INVEST_RANGE() {
        return KEY_INQUIRY_MASTER_INVEST_RANGE;
    }

    public void setKEY_INQUIRY_MASTER_INVEST_RANGE(String KEY_INQUIRY_MASTER_INVEST_RANGE) {
        this.KEY_INQUIRY_MASTER_INVEST_RANGE = KEY_INQUIRY_MASTER_INVEST_RANGE;
    }

    public String getKEY_INQUIRY_MASTER_FEEDBACK_TIME() {
        return KEY_INQUIRY_MASTER_FEEDBACK_TIME;
    }

    public void setKEY_INQUIRY_MASTER_FEEDBACK_TIME(String KEY_INQUIRY_MASTER_FEEDBACK_TIME) {
        this.KEY_INQUIRY_MASTER_FEEDBACK_TIME = KEY_INQUIRY_MASTER_FEEDBACK_TIME;
    }

    public String getKEY_INQUIRY_MASTER_STATUS() {
        return KEY_INQUIRY_MASTER_STATUS;
    }

    public void setKEY_INQUIRY_MASTER_STATUS(String KEY_INQUIRY_MASTER_STATUS) {
        this.KEY_INQUIRY_MASTER_STATUS = KEY_INQUIRY_MASTER_STATUS;
    }

    private String KEY_INQUIRY_MASTER_ID;
    private String KEY_INQUIRY_MASTER_TIMESTAMP;
    private String KEY_INQUIRY_MASTER_USER_ID_FK;
    private String KEY_INQUIRY_MASTER_CUSTOMER_ID_FK;
    private String KEY_INQUIRY_MASTER_INVEST_RANGE;
    private String KEY_INQUIRY_MASTER_FEEDBACK_TIME;
    private String KEY_INQUIRY_MASTER_STATUS = "0";
    private String inq_childname;

    public String getFeedTime() {
        return feedTime;
    }

    public void setFeedTime(String feedTime) {
        this.feedTime = feedTime;
    }

    private String feedTime;

    public String getInq_parentname() {
        return inq_parentname;
    }

    public void setInq_parentname(String inq_parentname) {
        this.inq_parentname = inq_parentname;
    }

    public String getInq_childname() {
        return inq_childname;
    }

    public void setInq_childname(String inq_childname) {
        this.inq_childname = inq_childname;
    }

    private String inq_parentname;

    public String getKEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP() {
        return KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP;
    }

    public void setKEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP(String KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP) {
        this.KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP = KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP;
    }

    private String KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP;

    public InquiryMaster() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.KEY_INQUIRY_MASTER_ID);
        dest.writeString(this.KEY_INQUIRY_MASTER_TIMESTAMP);
        dest.writeString(this.KEY_INQUIRY_MASTER_USER_ID_FK);
        dest.writeString(this.KEY_INQUIRY_MASTER_CUSTOMER_ID_FK);
        dest.writeString(this.KEY_INQUIRY_MASTER_INVEST_RANGE);
        dest.writeString(this.KEY_INQUIRY_MASTER_FEEDBACK_TIME);
        dest.writeString(this.KEY_INQUIRY_MASTER_STATUS);
        dest.writeString(this.inq_childname);
        dest.writeString(this.feedTime);
        dest.writeString(this.inq_parentname);
        dest.writeString(this.KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP);
    }

    protected InquiryMaster(Parcel in) {
        this.KEY_INQUIRY_MASTER_ID = in.readString();
        this.KEY_INQUIRY_MASTER_TIMESTAMP = in.readString();
        this.KEY_INQUIRY_MASTER_USER_ID_FK = in.readString();
        this.KEY_INQUIRY_MASTER_CUSTOMER_ID_FK = in.readString();
        this.KEY_INQUIRY_MASTER_INVEST_RANGE = in.readString();
        this.KEY_INQUIRY_MASTER_FEEDBACK_TIME = in.readString();
        this.KEY_INQUIRY_MASTER_STATUS = in.readString();
        this.inq_childname = in.readString();
        this.feedTime = in.readString();
        this.inq_parentname = in.readString();
        this.KEY_INQUIRY_MASTER_STATUS_CHANGE_TIMESTAMP = in.readString();
    }

    public static final Creator<InquiryMaster> CREATOR = new Creator<InquiryMaster>() {
        @Override
        public InquiryMaster createFromParcel(Parcel source) {
            return new InquiryMaster(source);
        }

        @Override
        public InquiryMaster[] newArray(int size) {
            return new InquiryMaster[size];
        }
    };
}
