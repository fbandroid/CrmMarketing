package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InqMaster implements Parcelable {

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
    @SerializedName("inq_investrange")
    @Expose
    private String inqInvestrange;
    @SerializedName("inq_feedbacktime")
    @Expose
    private String inqFeedbacktime;
    @SerializedName("inq_status")
    @Expose
    private String inqStatus;

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

    public String getInqStatus() {
        return inqStatus;
    }

    public void setInqStatus(String inqStatus) {
        this.inqStatus = inqStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.inqId);
        dest.writeString(this.inqDatetime);
        dest.writeString(this.inqUserid);
        dest.writeString(this.inqCustid);
        dest.writeString(this.inqInvestrange);
        dest.writeString(this.inqFeedbacktime);
        dest.writeString(this.inqStatus);
    }

    public InqMaster() {
    }

    protected InqMaster(Parcel in) {
        this.inqId = in.readString();
        this.inqDatetime = in.readString();
        this.inqUserid = in.readString();
        this.inqCustid = in.readString();
        this.inqInvestrange = in.readString();
        this.inqFeedbacktime = in.readString();
        this.inqStatus = in.readString();
    }

    public static final Parcelable.Creator<InqMaster> CREATOR = new Parcelable.Creator<InqMaster>() {
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