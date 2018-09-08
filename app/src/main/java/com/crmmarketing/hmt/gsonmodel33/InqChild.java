package com.crmmarketing.hmt.gsonmodel33;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InqChild {

@SerializedName("Int_id")
@Expose
private String intId;
@SerializedName("int_datetime")
@Expose
private String intDatetime;
@SerializedName("inquiryid")
@Expose
private String inquiryid;
@SerializedName("categoryid")
@Expose
private String categoryid;
@SerializedName("categoryname")
@Expose
private String categoryname;
@SerializedName("productid")
@Expose
private String productid;
@SerializedName("productname")
@Expose
private String productname;
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

}