package com.crmmarketing.hmt.gsonmodel22;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer implements Parcelable {

    @SerializedName("cu_id")
    @Expose
    private String cuId;
    @SerializedName("date_time")
    @Expose
    private String dateTime;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("occupation")
    @Expose
    private String occupation;
    @SerializedName("occupation_details")
    @Expose
    private String occupationDetails;
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
    @SerializedName("family_code")
    @Expose
    private String familyCode;
    @SerializedName("rm_id")
    @Expose
    private String rmId;
    @SerializedName("rm_name")
    @Expose
    private String rmName;
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
    @SerializedName("pan")
    @Expose
    private String pan;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("comments")
    @Expose
    private String comments;
    @SerializedName("commodity")
    @Expose
    private String commodity;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("nj_mf")
    @Expose
    private String njMf;
    @SerializedName("mosl_mf")
    @Expose
    private String moslMf;
    @SerializedName("nj_sip")
    @Expose
    private String njSip;
    @SerializedName("mosl_sip")
    @Expose
    private String moslSip;
    @SerializedName("mosl_pms")
    @Expose
    private String moslPms;
    @SerializedName("ipo")
    @Expose
    private String ipo;
    @SerializedName("apollo_pa")
    @Expose
    private String apolloPa;
    @SerializedName("apollo_health")
    @Expose
    private String apolloHealth;
    @SerializedName("religare_health")
    @Expose
    private String religareHealth;
    @SerializedName("other_gi")
    @Expose
    private String otherGi;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("mfcode")
    @Expose
    private String mfCode;

    public String getMfCode() {
        return mfCode;
    }

    public void setMfCode(String mfCode) {
        this.mfCode = mfCode;
    }

    public String getPmscode() {
        return pmscode;
    }

    public void setPmscode(String pmscode) {
        this.pmscode = pmscode;
    }

    public String getInsCode() {
        return insCode;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    @SerializedName("pmscode")
    @Expose
    private String pmscode;


    @SerializedName("inscode")
    @Expose
    private String insCode;

    public String getCuId() {
        return cuId;
    }

    public void setCuId(String cuId) {
        this.cuId = cuId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getOccupationDetails() {
        return occupationDetails;
    }

    public void setOccupationDetails(String occupationDetails) {
        this.occupationDetails = occupationDetails;
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

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getRmId() {
        return rmId;
    }

    public void setRmId(String rmId) {
        this.rmId = rmId;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNjMf() {
        return njMf;
    }

    public void setNjMf(String njMf) {
        this.njMf = njMf;
    }

    public String getMoslMf() {
        return moslMf;
    }

    public void setMoslMf(String moslMf) {
        this.moslMf = moslMf;
    }

    public String getNjSip() {
        return njSip;
    }

    public void setNjSip(String njSip) {
        this.njSip = njSip;
    }

    public String getMoslSip() {
        return moslSip;
    }

    public void setMoslSip(String moslSip) {
        this.moslSip = moslSip;
    }

    public String getMoslPms() {
        return moslPms;
    }

    public void setMoslPms(String moslPms) {
        this.moslPms = moslPms;
    }

    public String getIpo() {
        return ipo;
    }

    public void setIpo(String ipo) {
        this.ipo = ipo;
    }

    public String getApolloPa() {
        return apolloPa;
    }

    public void setApolloPa(String apolloPa) {
        this.apolloPa = apolloPa;
    }

    public String getApolloHealth() {
        return apolloHealth;
    }

    public void setApolloHealth(String apolloHealth) {
        this.apolloHealth = apolloHealth;
    }

    public String getReligareHealth() {
        return religareHealth;
    }

    public void setReligareHealth(String religareHealth) {
        this.religareHealth = religareHealth;
    }

    public String getOtherGi() {
        return otherGi;
    }

    public void setOtherGi(String otherGi) {
        this.otherGi = otherGi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Customer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cuId);
        dest.writeString(this.dateTime);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.occupation);
        dest.writeString(this.occupationDetails);
        dest.writeString(this.sourceDetail);
        dest.writeString(this.refereeName);
        dest.writeString(this.refereeCode);
        dest.writeString(this.refereeEmail);
        dest.writeString(this.refereeMobile);
        dest.writeString(this.familyCode);
        dest.writeString(this.rmId);
        dest.writeString(this.rmName);
        dest.writeString(this.email);
        dest.writeString(this.alternateEmail);
        dest.writeString(this.mobile);
        dest.writeString(this.landline);
        dest.writeString(this.pan);
        dest.writeString(this.dob);
        dest.writeString(this.address);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.zip);
        dest.writeString(this.comments);
        dest.writeString(this.commodity);
        dest.writeString(this.currency);
        dest.writeString(this.njMf);
        dest.writeString(this.moslMf);
        dest.writeString(this.njSip);
        dest.writeString(this.moslSip);
        dest.writeString(this.moslPms);
        dest.writeString(this.ipo);
        dest.writeString(this.apolloPa);
        dest.writeString(this.apolloHealth);
        dest.writeString(this.religareHealth);
        dest.writeString(this.otherGi);
        dest.writeString(this.status);
        dest.writeString(this.mfCode);
        dest.writeString(this.pmscode);
        dest.writeString(this.insCode);
    }

    protected Customer(Parcel in) {
        this.cuId = in.readString();
        this.dateTime = in.readString();
        this.code = in.readString();
        this.name = in.readString();
        this.occupation = in.readString();
        this.occupationDetails = in.readString();
        this.sourceDetail = in.readString();
        this.refereeName = in.readString();
        this.refereeCode = in.readString();
        this.refereeEmail = in.readString();
        this.refereeMobile = in.readString();
        this.familyCode = in.readString();
        this.rmId = in.readString();
        this.rmName = in.readString();
        this.email = in.readString();
        this.alternateEmail = in.readString();
        this.mobile = in.readString();
        this.landline = in.readString();
        this.pan = in.readString();
        this.dob = in.readString();
        this.address = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.zip = in.readString();
        this.comments = in.readString();
        this.commodity = in.readString();
        this.currency = in.readString();
        this.njMf = in.readString();
        this.moslMf = in.readString();
        this.njSip = in.readString();
        this.moslSip = in.readString();
        this.moslPms = in.readString();
        this.ipo = in.readString();
        this.apolloPa = in.readString();
        this.apolloHealth = in.readString();
        this.religareHealth = in.readString();
        this.otherGi = in.readString();
        this.status = in.readString();
        this.mfCode = in.readString();
        this.pmscode = in.readString();
        this.insCode = in.readString();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel source) {
            return new Customer(source);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };
}
