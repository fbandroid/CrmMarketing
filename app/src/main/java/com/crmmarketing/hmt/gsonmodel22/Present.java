package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Present {

    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("event_date")
    @Expose
    private String eventDate;
    @SerializedName("present")
    @Expose
    private List<Present_> present = null;

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<Present_> getPresent() {
        return present;
    }

    public void setPresent(List<Present_> present) {
        this.present = present;
    }

}