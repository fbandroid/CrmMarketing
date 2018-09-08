package com.crmmarketing.hmt.GsonModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetail implements Parcelable {

@SerializedName("users")
@Expose
private List<User> users = null;

public List<User> getUsers() {
return users;
}

public void setUsers(List<User> users) {
this.users = users;
}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.users);
    }

    public UserDetail() {
    }

    protected UserDetail(Parcel in) {
        this.users = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<UserDetail> CREATOR = new Parcelable.Creator<UserDetail>() {
        @Override
        public UserDetail createFromParcel(Parcel source) {
            return new UserDetail(source);
        }

        @Override
        public UserDetail[] newArray(int size) {
            return new UserDetail[size];
        }
    };
}