package com.crmmarketing.hmt.targetmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;

public class TarChild implements Parcelable {

@SerializedName("targetid")
@Expose
private String targetid;
@SerializedName("productid")
@Expose
private String productid;
@SerializedName("productname")
@Expose
private String productname;
@SerializedName("quantity")
@Expose
private String quantity;
@SerializedName("status")
@Expose
private String status;

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    private String categoryid;
private String categoryname;
public String getTargetid() {
return targetid;
}

public void setTargetid(String targetid) {
this.targetid = targetid;
}

public String getProductid() {
return productid;
}

public void setProductid(String productid) {
this.productid = productid;
}

public String getProductname() {
return productname;
}

public void setProductname(String productname) {
this.productname = productname;
}

public String getQuantity() {
return quantity;
}

public void setQuantity(String quantity) {
this.quantity = quantity;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

    public TarChild() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.targetid);
        dest.writeString(this.productid);
        dest.writeString(this.productname);
        dest.writeString(this.quantity);
        dest.writeString(this.status);
        dest.writeString(this.categoryid);
        dest.writeString(this.categoryname);
    }

    protected TarChild(Parcel in) {
        this.targetid = in.readString();
        this.productid = in.readString();
        this.productname = in.readString();
        this.quantity = in.readString();
        this.status = in.readString();
        this.categoryid = in.readString();
        this.categoryname = in.readString();
    }

    public static final Creator<TarChild> CREATOR = new Creator<TarChild>() {
        @Override
        public TarChild createFromParcel(Parcel source) {
            return new TarChild(source);
        }

        @Override
        public TarChild[] newArray(int size) {
            return new TarChild[size];
        }
    };
}