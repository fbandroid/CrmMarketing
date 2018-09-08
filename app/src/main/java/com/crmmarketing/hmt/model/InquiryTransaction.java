package com.crmmarketing.hmt.model;


import android.os.Parcel;
import android.os.Parcelable;

public class InquiryTransaction implements Parcelable {

    public String getKEY_INQUIRY_TRANSACTION_ID() {
        return KEY_INQUIRY_TRANSACTION_ID;
    }

    public void setKEY_INQUIRY_TRANSACTION_ID(String KEY_INQUIRY_TRANSACTION_ID) {
        this.KEY_INQUIRY_TRANSACTION_ID = KEY_INQUIRY_TRANSACTION_ID;
    }

    public String getKEY_INQUIRY_TRANSACTION_TIMESTAMP() {
        return KEY_INQUIRY_TRANSACTION_TIMESTAMP;
    }

    public void setKEY_INQUIRY_TRANSACTION_TIMESTAMP(String KEY_INQUIRY_TRANSACTION_TIMESTAMP) {
        this.KEY_INQUIRY_TRANSACTION_TIMESTAMP = KEY_INQUIRY_TRANSACTION_TIMESTAMP;
    }

    public String getKEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK() {
        return KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK;
    }

    public void setKEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK(String KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK) {
        this.KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK = KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK;
    }

    public String getKEY_INQUIRY_TRANSACTION_PRODUCT_ID() {
        return KEY_INQUIRY_TRANSACTION_PRODUCT_ID;
    }

    public void setKEY_INQUIRY_TRANSACTION_PRODUCT_ID(String KEY_INQUIRY_TRANSACTION_PRODUCT_ID) {
        this.KEY_INQUIRY_TRANSACTION_PRODUCT_ID = KEY_INQUIRY_TRANSACTION_PRODUCT_ID;
    }

    public String getKEY_INQUIRY_TRANSACTION_QUANTITY() {
        return KEY_INQUIRY_TRANSACTION_QUANTITY;
    }

    public void setKEY_INQUIRY_TRANSACTION_QUANTITY(String KEY_INQUIRY_TRANSACTION_QUANTITY) {
        this.KEY_INQUIRY_TRANSACTION_QUANTITY = KEY_INQUIRY_TRANSACTION_QUANTITY;
    }

    public String getKEY_INQUIRY_TRANSACTION_STATUS() {
        return KEY_INQUIRY_TRANSACTION_STATUS;
    }

    public void setKEY_INQUIRY_TRANSACTION_STATUS(String KEY_INQUIRY_TRANSACTION_STATUS) {
        this.KEY_INQUIRY_TRANSACTION_STATUS = KEY_INQUIRY_TRANSACTION_STATUS;
    }

    private String KEY_INQUIRY_TRANSACTION_ID;
    private String KEY_INQUIRY_TRANSACTION_TIMESTAMP;
    private String KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK;
    private String KEY_INQUIRY_TRANSACTION_PRODUCT_ID;
    private String KEY_INQUIRY_TRANSACTION_QUANTITY;
    private String KEY_INQUIRY_TRANSACTION_STATUS = "0";

    public String getKEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP() {
        return KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP;
    }

    public void setKEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP(String KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP) {
        this.KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP = KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP;
    }

    private String KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_ID);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_TIMESTAMP);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_PRODUCT_ID);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_QUANTITY);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_STATUS);
        dest.writeString(this.KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP);
    }

    public InquiryTransaction() {
    }

    protected InquiryTransaction(Parcel in) {
        this.KEY_INQUIRY_TRANSACTION_ID = in.readString();
        this.KEY_INQUIRY_TRANSACTION_TIMESTAMP = in.readString();
        this.KEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK = in.readString();
        this.KEY_INQUIRY_TRANSACTION_PRODUCT_ID = in.readString();
        this.KEY_INQUIRY_TRANSACTION_QUANTITY = in.readString();
        this.KEY_INQUIRY_TRANSACTION_STATUS = in.readString();
        this.KEY_INQUIRY_TRANSACTION_STATUS_CHANGE_TIMESTAMP = in.readString();
    }

    public static final Parcelable.Creator<InquiryTransaction> CREATOR = new Parcelable.Creator<InquiryTransaction>() {
        @Override
        public InquiryTransaction createFromParcel(Parcel source) {
            return new InquiryTransaction(source);
        }

        @Override
        public InquiryTransaction[] newArray(int size) {
            return new InquiryTransaction[size];
        }
    };
}
