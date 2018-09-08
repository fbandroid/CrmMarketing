package com.crmmarketing.hmt.inquirytogson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrList {

    @SerializedName("pr_id")
    @Expose
    private String prId;

    public String getPrId() {
        return prId;
    }

    public void setPrId(String prId) {
        this.prId = prId;
    }

}