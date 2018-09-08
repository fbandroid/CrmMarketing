package com.crmmarketing.hmt.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Travel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("src_lat")
    @Expose
    private String srcLat;
    @SerializedName("src_log")
    @Expose
    private String srcLog;
    @SerializedName("dest_lat")
    @Expose
    private String destLat;
    @SerializedName("dest_log")
    @Expose
    private String destLog;
    @SerializedName("total")
    @Expose
    private String dist;
    @SerializedName("master_id")
    @Expose
    private String tid;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrcLat() {
        return srcLat;
    }

    public void setSrcLat(String srcLat) {
        this.srcLat = srcLat;
    }

    public String getSrcLog() {
        return srcLog;
    }

    public void setSrcLog(String srcLog) {
        this.srcLog = srcLog;
    }

    public String getDestLat() {
        return destLat;
    }

    public void setDestLat(String destLat) {
        this.destLat = destLat;
    }

    public String getDestLog() {
        return destLog;
    }

    public void setDestLog(String destLog) {
        this.destLog = destLog;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }

}