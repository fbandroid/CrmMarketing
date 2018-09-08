package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Eventcustomer {

@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("event_id")
@Expose
private String eventId;
@SerializedName("cu_id")
@Expose
private String cuId;
@SerializedName("attendance")
@Expose
private String attendance;
@SerializedName("code")
@Expose
private String code;
@SerializedName("name")
@Expose
private String name;

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

public String getCuId() {
return cuId;
}

public void setCuId(String cuId) {
this.cuId = cuId;
}

public String getAttendance() {
return attendance;
}

public void setAttendance(String attendance) {
this.attendance = attendance;
}

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

}