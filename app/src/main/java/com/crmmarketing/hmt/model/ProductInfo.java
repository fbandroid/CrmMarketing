package com.crmmarketing.hmt.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 10-02-2017.
 */

public class ProductInfo implements Parcelable {

    public ProductInfo() {

    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    private String productId;
    private String productCategory;
    private String productName;
    private String productPrice;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    private boolean isAdded;

    @Override
    public String toString() {
        return getProductName(); // You can add anything else like maybe getDrinkType()
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeString(this.productCategory);
        dest.writeString(this.productName);
        dest.writeString(this.productPrice);
        dest.writeByte(this.isAdded ? (byte) 1 : (byte) 0);
    }

    protected ProductInfo(Parcel in) {
        this.productId = in.readString();
        this.productCategory = in.readString();
        this.productName = in.readString();
        this.productPrice = in.readString();
        this.isAdded = in.readByte() != 0;
    }

    public static final Parcelable.Creator<ProductInfo> CREATOR = new Parcelable.Creator<ProductInfo>() {
        @Override
        public ProductInfo createFromParcel(Parcel source) {
            return new ProductInfo(source);
        }

        @Override
        public ProductInfo[] newArray(int size) {
            return new ProductInfo[size];
        }
    };
}
