package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attendancelist implements Parcelable {

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
@SerializedName("totaldays")
@Expose
private String totaldays;

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

public String getTotaldays() {
return totaldays;
}

public void setTotaldays(String totaldays) {
this.totaldays = totaldays;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uId);
        dest.writeString(this.uName);
        dest.writeString(this.uContact);
        dest.writeString(this.uEmail);
        dest.writeString(this.post);
        dest.writeString(this.branch);
        dest.writeString(this.totaldays);
    }

    public Attendancelist() {
    }

    protected Attendancelist(Parcel in) {
        this.uId = in.readString();
        this.uName = in.readString();
        this.uContact = in.readString();
        this.uEmail = in.readString();
        this.post = in.readString();
        this.branch = in.readString();
        this.totaldays = in.readString();
    }

    public static final Parcelable.Creator<Attendancelist> CREATOR = new Parcelable.Creator<Attendancelist>() {
        @Override
        public Attendancelist createFromParcel(Parcel source) {
            return new Attendancelist(source);
        }

        @Override
        public Attendancelist[] newArray(int size) {
            return new Attendancelist[size];
        }
    };
}