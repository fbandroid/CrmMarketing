package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by USER on 11-07-2017.
 */

public class SendInvitation {

    @SerializedName("event_id")
    @Expose
    private String event_id;
    @SerializedName("cu_id")
    @Expose
    private List<SendInvitation_> sendInvitation_s;

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public List<SendInvitation_> getSendInvitation_s() {
        return sendInvitation_s;
    }

    public void setSendInvitation_s(List<SendInvitation_> sendInvitation_s) {
        this.sendInvitation_s = sendInvitation_s;
    }
}
