package com.crmmarketing.hmt.gsonmodel22;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendancelist {

@SerializedName("u_id")
@Expose
private String uId;
@SerializedName("u_name")
@Expose
private String uName;
@SerializedName("u_contact")
@Expose
private String uContact;
@SerializedName("u_email")
@Expose
private String uEmail;
@SerializedName("post")
@Expose
private String post;
@SerializedName("branch")
@Expose
private String branch;
@SerializedName("punchin")
@Expose
private String punchin;
@SerializedName("punchout")
@Expose
private String punchout;
@SerializedName("totalhour")
@Expose
private String totalhour;

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

public String getUEmail() {
return uEmail;
}

public void setUEmail(String uEmail) {
this.uEmail = uEmail;
}

public String getPost() {
return post;
}

public void setPost(String post) {
this.post = post;
}

public String getBranch() {
return branch;
}

public void setBranch(String branch) {
this.branch = branch;
}

public String getPunchin() {
return punchin;
}

public void setPunchin(String punchin) {
this.punchin = punchin;
}

public String getPunchout() {
return punchout;
}

public void setPunchout(String punchout) {
this.punchout = punchout;
}

public String getTotalhour() {
return totalhour;
}

public void setTotalhour(String totalhour) {
this.totalhour = totalhour;
}

}