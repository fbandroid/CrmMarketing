package com.crmmarketing.hmt.gsonmodel22;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientDetail implements Parcelable {

@SerializedName("customers")
@Expose
private List<Customer> customers = null;

public List<Customer> getCustomers() {
return customers;
}

public void setCustomers(List<Customer> customers) {
this.customers = customers;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.customers);
    }

    public ClientDetail() {
    }

    protected ClientDetail(Parcel in) {
        this.customers = in.createTypedArrayList(Customer.CREATOR);
    }

    public static final Parcelable.Creator<ClientDetail> CREATOR = new Parcelable.Creator<ClientDetail>() {
        @Override
        public ClientDetail createFromParcel(Parcel source) {
            return new ClientDetail(source);
        }

        @Override
        public ClientDetail[] newArray(int size) {
            return new ClientDetail[size];
        }
    };
}