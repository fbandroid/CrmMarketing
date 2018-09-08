package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Report {

@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;
@SerializedName("cu_id")
@Expose
private String cuId;
@SerializedName("rm_name")
@Expose
private String rmName;
@SerializedName("mobile")
@Expose
private String mobile;
@SerializedName("pan")
@Expose
private String pan;
@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("event_id")
@Expose
private String eventId;
@SerializedName("attendance")
@Expose
private String attendance;

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCuId() {
return cuId;
}

public void setCuId(String cuId) {
this.cuId = cuId;
}

public String getRmName() {
return rmName;
}

public void setRmName(String rmName) {
this.rmName = rmName;
}

public String getMobile() {
return mobile;
}

public void setMobile(String mobile) {
this.mobile = mobile;
}

public String getPan() {
return pan;
}

public void setPan(String pan) {
this.pan = pan;
}

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

public String getEventId() {
return eventId;
}

public void setEventId(String eventId) {
this.eventId = eventId;
}

public String getAttendance() {
return attendance;
}

public void setAttendance(String attendance) {
this.attendance = attendance;
}

}