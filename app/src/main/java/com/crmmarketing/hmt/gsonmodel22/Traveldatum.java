package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Traveldatum {

@SerializedName("u_id")
@Expose
private String uId;
@SerializedName("u_name")
@Expose
private String uName;
@SerializedName("date")
@Expose
private String date;
@SerializedName("totaldistance")
@Expose
private String totaldistance;
@SerializedName("src_lat")
@Expose
private String srcLat;
@SerializedName("src_log")
@Expose
private String srcLog;
@SerializedName("dest_lat")
@Expose
private String destLat;
@SerializedName("dest_log")
@Expose
private String destLog;
@SerializedName("src")
@Expose
private String src;
@SerializedName("dest")
@Expose
private String dest;

public String getUId() {
return uId;
}

public void setUId(String uId) {
this.uId = uId;
}

public String getUName() {
return uName;
}

public void setUName(String uName) {
this.uName = uName;
}

public String getDate() {
return date;
}

public void setDate(String date) {
this.date = date;
}

public String getTotaldistance() {
return totaldistance;
}

public void setTotaldistance(String totaldistance) {
this.totaldistance = totaldistance;
}

public String getSrcLat() {
return srcLat;
}

public void setSrcLat(String srcLat) {
this.srcLat = srcLat;
}

public String getSrcLog() {
return srcLog;
}

public void setSrcLog(String srcLog) {
this.srcLog = srcLog;
}

public String getDestLat() {
return destLat;
}

public void setDestLat(String destLat) {
this.destLat = destLat;
}

public String getDestLog() {
return destLog;
}

public void setDestLog(String destLog) {
this.destLog = destLog;
}

public String getSrc() {
return src;
}

public void setSrc(String src) {
this.src = src;
}

public String getDest() {
return dest;
}

public void setDest(String dest) {
this.dest = dest;
}

}