package com.crmmarketing.hmt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MyRes {

    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("master_id")
    @Expose
    private String travel_id;

    public MyRes() {
    }

    public String getTravel_id() {
        return travel_id;
    }

    public void setTravel_id(String travel_id) {
        this.travel_id = travel_id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
