package com.crmmarketing.hmt.inquirytogson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackInfo {

    @SerializedName("feed_remark")
    @Expose
    private String feedRemark;

    public String getFeedRemark() {
        return feedRemark;
    }

    public void setFeedRemark(String feedRemark) {
        this.feedRemark = feedRemark;
    }

}