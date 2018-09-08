package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example implements Parcelable {

    @SerializedName("getinquiry")
    @Expose
    private List<Getinquiry> getinquiry = null;

    public List<Getinquiry> getGetinquiry() {
        return getinquiry;
    }

    public void setGetinquiry(List<Getinquiry> getinquiry) {
        this.getinquiry = getinquiry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.getinquiry);
    }

    public Example() {
    }

    protected Example(Parcel in) {
        this.getinquiry = new ArrayList<Getinquiry>();
        in.readList(this.getinquiry, Getinquiry.class.getClassLoader());
    }

    public static final Parcelable.Creator<Example> CREATOR = new Parcelable.Creator<Example>() {
        @Override
        public Example createFromParcel(Parcel source) {
            return new Example(source);
        }

        @Override
        public Example[] newArray(int size) {
            return new Example[size];
        }
    };
}