package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveDate {

@SerializedName("date")
@Expose
private String date;

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

}