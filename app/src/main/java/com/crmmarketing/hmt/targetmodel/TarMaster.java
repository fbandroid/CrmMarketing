package com.crmmarketing.hmt.targetmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TarMaster implements Parcelable {

@SerializedName("tar_id")
@Expose
private String tarId;
@SerializedName("tar_datetime")
@Expose
private String tarDatetime;
@SerializedName("tar_userid")
@Expose
private String tarUserid;
@SerializedName("tar_childname")
@Expose
private String tarChildname;
@SerializedName("tar_parentname")
@Expose
private String tarParentname;
@SerializedName("tar_startdate")
@Expose
private String tarStartdate;
@SerializedName("tar_enddate")
@Expose
private String tarEnddate;
@SerializedName("tar_status")
@Expose
private String tarStatus;

public String getTarId() {
return tarId;
}

public void setTarId(String tarId) {
this.tarId = tarId;
}

public String getTarDatetime() {
return tarDatetime;
}

public void setTarDatetime(String tarDatetime) {
this.tarDatetime = tarDatetime;
}

public String getTarUserid() {
return tarUserid;
}

public void setTarUserid(String tarUserid) {
this.tarUserid = tarUserid;
}

public String getTarChildname() {
return tarChildname;
}

public void setTarChildname(String tarChildname) {
this.tarChildname = tarChildname;
}

public String getTarParentname() {
return tarParentname;
}

public void setTarParentname(String tarParentname) {
this.tarParentname = tarParentname;
}

public String getTarStartdate() {
return tarStartdate;
}

public void setTarStartdate(String tarStartdate) {
this.tarStartdate = tarStartdate;
}

public String getTarEnddate() {
return tarEnddate;
}

public void setTarEnddate(String tarEnddate) {
this.tarEnddate = tarEnddate;
}

public String getTarStatus() {
return tarStatus;
}

public void setTarStatus(String tarStatus) {
this.tarStatus = tarStatus;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tarId);
        dest.writeString(this.tarDatetime);
        dest.writeString(this.tarUserid);
        dest.writeString(this.tarChildname);
        dest.writeString(this.tarParentname);
        dest.writeString(this.tarStartdate);
        dest.writeString(this.tarEnddate);
        dest.writeString(this.tarStatus);
    }

    public TarMaster() {
    }

    protected TarMaster(Parcel in) {
        this.tarId = in.readString();
        this.tarDatetime = in.readString();
        this.tarUserid = in.readString();
        this.tarChildname = in.readString();
        this.tarParentname = in.readString();
        this.tarStartdate = in.readString();
        this.tarEnddate = in.readString();
        this.tarStatus = in.readString();
    }

    public static final Parcelable.Creator<TarMaster> CREATOR = new Parcelable.Creator<TarMaster>() {
        @Override
        public TarMaster createFromParcel(Parcel source) {
            return new TarMaster(source);
        }

        @Override
        public TarMaster[] newArray(int size) {
            return new TarMaster[size];
        }
    };
}