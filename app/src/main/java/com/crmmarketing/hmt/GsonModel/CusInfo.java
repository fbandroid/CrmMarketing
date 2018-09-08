package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CusInfo implements Parcelable {

    @SerializedName("cu_id")
    @Expose
    private String cuId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("source_detail")
    @Expose
    private String sourceDetail;
    @SerializedName("referee_name")
    @Expose
    private String refereeName;
    @SerializedName("referee_code")
    @Expose
    private String refereeCode;
    @SerializedName("referee_email")
    @Expose
    private String refereeEmail;
    @SerializedName("referee_mobile")
    @Expose
    private String refereeMobile;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("alternate_email")
    @Expose
    private String alternateEmail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("landline")
    @Expose
    private String landline;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("comments")
    @Expose
    private String comments;

    public String getCuId() {
        return cuId;
    }

    public void setCuId(String cuId) {
        this.cuId = cuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSourceDetail() {
        return sourceDetail;
    }

    public void setSourceDetail(String sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getRefereeCode() {
        return refereeCode;
    }

    public void setRefereeCode(String refereeCode) {
        this.refereeCode = refereeCode;
    }

    public String getRefereeEmail() {
        return refereeEmail;
    }

    public void setRefereeEmail(String refereeEmail) {
        this.refereeEmail = refereeEmail;
    }

    public String getRefereeMobile() {
        return refereeMobile;
    }

    public void setRefereeMobile(String refereeMobile) {
        this.refereeMobile = refereeMobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cuId);
        dest.writeString(this.name);
        dest.writeString(this.occupation);
        dest.writeString(this.sourceDetail);
        dest.writeString(this.refereeName);
        dest.writeString(this.refereeCode);
        dest.writeString(this.refereeEmail);
        dest.writeString(this.refereeMobile);
        dest.writeString(this.email);
        dest.writeString(this.alternateEmail);
        dest.writeString(this.mobile);
        dest.writeString(this.landline);
        dest.writeString(this.dob);
        dest.writeString(this.address);
        dest.writeString(this.comments);
    }

    public CusInfo() {
    }

    protected CusInfo(Parcel in) {
        this.cuId = in.readString();
        this.name = in.readString();
        this.occupation = in.readString();
        this.sourceDetail = in.readString();
        this.refereeName = in.readString();
        this.refereeCode = in.readString();
        this.refereeEmail = in.readString();
        this.refereeMobile = in.readString();
        this.email = in.readString();
        this.alternateEmail = in.readString();
        this.mobile = in.readString();
        this.landline = in.readString();
        this.dob = in.readString();
        this.address = in.readString();
        this.comments = in.readString();
    }

    public static final Parcelable.Creator<CusInfo> CREATOR = new Parcelable.Creator<CusInfo>() {
        @Override
        public CusInfo createFromParcel(Parcel source) {
            return new CusInfo(source);
        }

        @Override
        public CusInfo[] newArray(int size) {
            return new CusInfo[size];
        }
    };
}