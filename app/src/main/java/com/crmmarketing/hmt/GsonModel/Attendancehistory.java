package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendancehistory {

@SerializedName("punchin")
@Expose
private String punchin;
@SerializedName("punchout")
@Expose
private String punchout;

public String getPunchin() {
return punchin;
}

public void setPunchin(String punchin) {
this.punchin = punchin;
}

public String getPunchout() {
return punchout;
}

public void setPunchout(String punchout) {
this.punchout = punchout;
}

}