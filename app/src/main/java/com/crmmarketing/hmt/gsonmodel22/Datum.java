package com.crmmarketing.hmt.gsonmodel22;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("trading")
    @Expose
    private String trading;
    @SerializedName("delivery")
    @Expose
    private String delivery;
    @SerializedName("brokerage")
    @Expose
    private String brokerage;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("fut_trd_val")
    @Expose
    private String fut_trd_val;
    @SerializedName("opt_trd_val")
    @Expose
    private String opt_trd_val;
    @SerializedName("cld_out_val")
    @Expose
    private String cld_out_val;
    @SerializedName("ledger")
    @Expose
    private String ledger;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("segment")
    @Expose
    private String segment;
    @SerializedName("pending")
    @Expose
    private String pending;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("ipo")
    @Expose
    private String ipo;
    @SerializedName("lot")
    @Expose
    private String lot;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("parmenant_dp")
    @Expose
    private String parmenant_dp;
    @SerializedName("aging_debit")
    @Expose
    private String aging_debit;
    @SerializedName("cr_amount")
    @Expose
    private String cr_amount;

    public String getIpo() {
        return ipo;
    }

    public void setIpo(String ipo) {
        this.ipo = ipo;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getParmenant_dp() {
        return parmenant_dp;
    }

    public void setParmenant_dp(String parmenant_dp) {
        this.parmenant_dp = parmenant_dp;
    }

    public String getAging_debit() {
        return aging_debit;
    }

    public void setAging_debit(String aging_debit) {
        this.aging_debit = aging_debit;
    }

    public String getCr_amount() {
        return cr_amount;
    }

    public void setCr_amount(String cr_amount) {
        this.cr_amount = cr_amount;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getCld_out_val() {
        return cld_out_val;
    }

    public void setCld_out_val(String cld_out_val) {
        this.cld_out_val = cld_out_val;
    }

    public String getLedger() {
        return ledger;
    }

    public void setLedger(String ledger) {
        this.ledger = ledger;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFut_trd_val() {
        return fut_trd_val;
    }

    public void setFut_trd_val(String fut_trd_val) {
        this.fut_trd_val = fut_trd_val;
    }

    public String getOpt_trd_val() {
        return opt_trd_val;
    }

    public void setOpt_trd_val(String opt_trd_val) {
        this.opt_trd_val = opt_trd_val;
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

    public String getTrading() {
        return trading;
    }

    public void setTrading(String trading) {
        this.trading = trading;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(String brokerage) {
        this.brokerage = brokerage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public Datum() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeString(this.trading);
        dest.writeString(this.delivery);
        dest.writeString(this.brokerage);
        dest.writeString(this.total);
        dest.writeString(this.fut_trd_val);
        dest.writeString(this.opt_trd_val);
        dest.writeString(this.cld_out_val);
        dest.writeString(this.ledger);
        dest.writeString(this.stock);
        dest.writeString(this.segment);
        dest.writeString(this.pending);
        dest.writeString(this.cover);
        dest.writeString(this.ipo);
        dest.writeString(this.lot);
        dest.writeString(this.amount);
        dest.writeString(this.parmenant_dp);
        dest.writeString(this.aging_debit);
        dest.writeString(this.cr_amount);
    }

    protected Datum(Parcel in) {
        this.id = in.readString();
        this.code = in.readString();
        this.name = in.readString();
        this.trading = in.readString();
        this.delivery = in.readString();
        this.brokerage = in.readString();
        this.total = in.readString();
        this.fut_trd_val = in.readString();
        this.opt_trd_val = in.readString();
        this.cld_out_val = in.readString();
        this.ledger = in.readString();
        this.stock = in.readString();
        this.segment = in.readString();
        this.pending = in.readString();
        this.cover = in.readString();
        this.ipo = in.readString();
        this.lot = in.readString();
        this.amount = in.readString();
        this.parmenant_dp = in.readString();
        this.aging_debit = in.readString();
        this.cr_amount = in.readString();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}