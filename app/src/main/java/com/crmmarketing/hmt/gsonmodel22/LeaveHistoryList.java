package com.crmmarketing.hmt.gsonmodel22;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class LeaveHistoryList {

    @SerializedName("leavehistory")
    @Expose
    private List<Leavehistory> leavehistory = null;

    public List<Leavehistory> getLeavehistory() {
        return leavehistory;
    }

    public void setLeavehistory(List<Leavehistory> leavehistory) {
        this.leavehistory = leavehistory;
    }
}
