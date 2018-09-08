package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Document {

@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("inquiryid")
@Expose
private String inquiryid;
@SerializedName("name")
@Expose
private String name;
@SerializedName("status")
@Expose
private String status;

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

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}