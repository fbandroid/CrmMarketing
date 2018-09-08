package com.crmmarketing.hmt.GsonModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Counter {

    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("today")
    @Expose
    private String today;
    @SerializedName("completed")
    @Expose
    private String completed;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

}
