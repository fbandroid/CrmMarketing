package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InqChild implements Parcelable {

    @SerializedName("Int_id")
    @Expose
    private String intId;
    @SerializedName("int_datetime")
    @Expose
    private String intDatetime;
    @SerializedName("inquiryid")
    @Expose
    private String inquiryid;
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("updateddatetime")
    @Expose
    private String updateddatetime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getIntId() {
        return intId;
    }

    public void setIntId(String intId) {
        this.intId = intId;
    }

    public String getIntDatetime() {
        return intDatetime;
    }

    public void setIntDatetime(String intDatetime) {
        this.intDatetime = intDatetime;
    }

    public String getInquiryid() {
        return inquiryid;
    }

    public void setInquiryid(String inquiryid) {
        this.inquiryid = inquiryid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUpdateddatetime() {
        return updateddatetime;
    }

    public void setUpdateddatetime(String updateddatetime) {
        this.updateddatetime = updateddatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.intId);
        dest.writeString(this.intDatetime);
        dest.writeString(this.inquiryid);
        dest.writeString(this.productid);
        dest.writeString(this.quantity);
        dest.writeString(this.updateddatetime);
        dest.writeString(this.status);
    }

    public InqChild() {
    }

    protected InqChild(Parcel in) {
        this.intId = in.readString();
        this.intDatetime = in.readString();
        this.inquiryid = in.readString();
        this.productid = in.readString();
        this.quantity = in.readString();
        this.updateddatetime = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<InqChild> CREATOR = new Parcelable.Creator<InqChild>() {
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