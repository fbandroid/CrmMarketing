package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetail {

@SerializedName("eventcustomers")
@Expose
private List<Eventcustomer> eventcustomers = null;

public List<Eventcustomer> getEventcustomers() {
return eventcustomers;
}

public void setEventcustomers(List<Eventcustomer> eventcustomers) {
this.eventcustomers = eventcustomers;
}

}