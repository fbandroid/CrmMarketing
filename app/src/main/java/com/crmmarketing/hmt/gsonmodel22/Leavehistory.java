package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Leavehistory {

@SerializedName("u_id")
@Expose
private String uId;
@SerializedName("u_name")
@Expose
private String uName;
@SerializedName("u_contact")
@Expose
private String uContact;
@SerializedName("post")
@Expose
private String post;
@SerializedName("id")
@Expose
private String id;
@SerializedName("date_time")
@Expose
private String dateTime;
@SerializedName("start")
@Expose
private String leaveDate;

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @SerializedName("end")
@Expose
private String endDate;

@SerializedName("reason")
@Expose
private String reason;
@SerializedName("status")
@Expose
private String status;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @SerializedName("type")
@Expose
private String type;

public String getUId() {
return uId;
}

public void setUId(String uId) {
this.uId = uId;
}

public String getUName() {
return uName;
}

public void setUName(String uName) {
this.uName = uName;
}

public String getUContact() {
return uContact;
}

public void setUContact(String uContact) {
this.uContact = uContact;
}

public String getPost() {
return post;
}

public void setPost(String post) {
this.post = post;
}

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getDateTime() {
return dateTime;
}

public void setDateTime(String dateTime) {
this.dateTime = dateTime;
}

public String getLeaveDate() {
return leaveDate;
}

public void setLeaveDate(String leaveDate) {
this.leaveDate = leaveDate;
}

public String getReason() {
return reason;
}

public void setReason(String reason) {
this.reason = reason;
}

public String getStatus() {
return status;
}

public void setStatus(String status) {
this.status = status;
}

}