package com.crmmarketing.hmt.model;


import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeInfo implements Parcelable {
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpDesg() {
        return empDesg;
    }

    public void setEmpDesg(String empDesg) {
        this.empDesg = empDesg;
    }

    public String getEmpPicUrl() {
        return empPicUrl;
    }

    public void setEmpPicUrl(String empPicUrl) {
        this.empPicUrl = empPicUrl;
    }

    private String empName;
    private String empNo;
    private String empDesg;
    private String empPicUrl;

    public boolean isTempIsChecked() {
        return tempIsChecked;
    }

    public void setTempIsChecked(boolean tempIsChecked) {
        this.tempIsChecked = tempIsChecked;
    }

    public static Creator<EmployeeInfo> getCREATOR() {
        return CREATOR;
    }

    private boolean tempIsChecked;

    public boolean ischecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    private boolean ischecked;


    public EmployeeInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.empName);
        dest.writeString(this.empNo);
        dest.writeString(this.empDesg);
        dest.writeString(this.empPicUrl);
        dest.writeByte(this.tempIsChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.ischecked ? (byte) 1 : (byte) 0);
    }

    protected EmployeeInfo(Parcel in) {
        this.empName = in.readString();
        this.empNo = in.readString();
        this.empDesg = in.readString();
        this.empPicUrl = in.readString();
        this.tempIsChecked = in.readByte() != 0;
        this.ischecked = in.readByte() != 0;
    }

    public static final Creator<EmployeeInfo> CREATOR = new Creator<EmployeeInfo>() {
        @Override
        public EmployeeInfo createFromParcel(Parcel source) {
            return new EmployeeInfo(source);
        }

        @Override
        public EmployeeInfo[] newArray(int size) {
            return new EmployeeInfo[size];
        }
    };
}
