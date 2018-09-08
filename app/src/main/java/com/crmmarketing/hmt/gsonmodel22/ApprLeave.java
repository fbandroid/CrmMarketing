package com.crmmarketing.hmt.gsonmodel22;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApprLeave {

@SerializedName("approveleavelist")
@Expose
private List<Approveleavelist> approveleavelist = null;

public List<Approveleavelist> getApproveleavelist() {
return approveleavelist;
}

public void setApproveleavelist(List<Approveleavelist> approveleavelist) {
this.approveleavelist = approveleavelist;
}

}