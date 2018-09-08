package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceDetail {

@SerializedName("attendancelist")
@Expose
private List<Attendancelist> attendancelist = null;

public List<Attendancelist> getAttendancelist() {
return attendancelist;
}

public void setAttendancelist(List<Attendancelist> attendancelist) {
this.attendancelist = attendancelist;
}

}