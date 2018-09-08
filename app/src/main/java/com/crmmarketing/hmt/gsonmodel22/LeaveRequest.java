package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LeaveRequest {

@SerializedName("leave_date")
@Expose
private List<LeaveDate> leaveDate = null;
@SerializedName("day")
@Expose
private String day;
@SerializedName("id")
@Expose
private String id;
@SerializedName("reason")
@Expose
private String reason;

public List<LeaveDate> getLeaveDate() {
return leaveDate;
}

public void setLeaveDate(List<LeaveDate> leaveDate) {
this.leaveDate = leaveDate;
}

public String getDay() {
return day;
}

public void setDay(String day) {
this.day = day;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getReason() {
return reason;
}

public void setReason(String reason) {
this.reason = reason;
}

}