package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvitedList {

@SerializedName("requestinvitelist")
@Expose
private List<Requestinvitelist> requestinvitelist = null;

public List<Requestinvitelist> getRequestinvitelist() {
return requestinvitelist;
}

public void setRequestinvitelist(List<Requestinvitelist> requestinvitelist) {
this.requestinvitelist = requestinvitelist;
}

}