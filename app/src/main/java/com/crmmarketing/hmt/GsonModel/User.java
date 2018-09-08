package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

@SerializedName("u_id")
@Expose
private String uId;
@SerializedName("parentid")
@Expose
private String parentid;
@SerializedName("u_name")
@Expose
private String uName;
@SerializedName("u_username")
@Expose
private String uUsername;
@SerializedName("u_email")
@Expose
private String uEmail;
@SerializedName("u_contact")
@Expose
private String uContact;
@SerializedName("u_address")
@Expose
private String uAddress;
@SerializedName("u_role")
@Expose
private String uRole;
@SerializedName("post")
@Expose
private String post;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked;

public String getUId() {
return uId;
}

public void setUId(String uId) {
this.uId = uId;
}

public String getParentid() {
return parentid;
}

public void setParentid(String parentid) {
this.parentid = parentid;
}

public String getUName() {
return uName;
}

public void setUName(String uName) {
this.uName = uName;
}

public String getUUsername() {
return uUsername;
}

public void setUUsername(String uUsername) {
this.uUsername = uUsername;
}

public String getUEmail() {
return uEmail;
}

public void setUEmail(String uEmail) {
this.uEmail = uEmail;
}

public String getUContact() {
return uContact;
}

public void setUContact(String uContact) {
this.uContact = uContact;
}

public String getUAddress() {
return uAddress;
}

public void setUAddress(String uAddress) {
this.uAddress = uAddress;
}

public String getURole() {
return uRole;
}

public void setURole(String uRole) {
this.uRole = uRole;
}

public String getPost() {
return post;
}

public void setPost(String post) {
this.post = post;
}

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uId);
        dest.writeString(this.parentid);
        dest.writeString(this.uName);
        dest.writeString(this.uUsername);
        dest.writeString(this.uEmail);
        dest.writeString(this.uContact);
        dest.writeString(this.uAddress);
        dest.writeString(this.uRole);
        dest.writeString(this.post);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
    }

    protected User(Parcel in) {
        this.uId = in.readString();
        this.parentid = in.readString();
        this.uName = in.readString();
        this.uUsername = in.readString();
        this.uEmail = in.readString();
        this.uContact = in.readString();
        this.uAddress = in.readString();
        this.uRole = in.readString();
        this.post = in.readString();
        this.isChecked = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}