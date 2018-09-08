package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("event_date")
@Expose
private String eventDate;
@SerializedName("name")
@Expose
private String name;
@SerializedName("description")
@Expose
private String description;
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

public String getEventDate() {
return eventDate;
}

public void setEventDate(String eventDate) {
this.eventDate = eventDate;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}