package com.crmmarketing.hmt.inquirytogson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Inquiry {

    @SerializedName("personal_info")
    @Expose
    private PersonalInfo personalInfo;
    @SerializedName("product_detail")
    @Expose
    private ProductDetail productDetail;
    @SerializedName("feedback_info")
    @Expose
    private FeedbackInfo feedbackInfo;
    @SerializedName("ref_info")
    @Expose
    private RefInfo refInfo;
    @SerializedName("emp_id")
    @Expose
    private String empId;
    @SerializedName("pr_range")
    @Expose
    private String prRange;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public FeedbackInfo getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(FeedbackInfo feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public RefInfo getRefInfo() {
        return refInfo;
    }

    public void setRefInfo(RefInfo refInfo) {
        this.refInfo = refInfo;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPrRange() {
        return prRange;
    }

    public void setPrRange(String prRange) {
        this.prRange = prRange;
    }

}