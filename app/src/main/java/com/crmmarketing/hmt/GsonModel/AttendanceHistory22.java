package com.crmmarketing.hmt.GsonModel;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceHistory22 {

@SerializedName("attendancehistory")
@Expose
private List<Attendancehistory> attendancehistory = null;

public List<Attendancehistory> getAttendancehistory() {
return attendancehistory;
}

public void setAttendancehistory(List<Attendancehistory> attendancehistory) {
this.attendancehistory = attendancehistory;
}

}