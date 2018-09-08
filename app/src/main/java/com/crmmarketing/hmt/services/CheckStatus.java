package com.crmmarketing.hmt.services;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.crmmarketing.hmt.SplashActivity;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CheckStatus extends IntentService {

    private RetrofitClient.APIServiceCheckLoginStatus checkLoginStatus;
    private RetrofitClient.APIServiceLogOut apiServiceLogOut;
    private String id;

    public CheckStatus() {
        super("CheckStatus");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        checkLoginStatus = RetrofitClient.checkLoginStatus(Constants.BASE_URL);
        apiServiceLogOut = RetrofitClient.logOut(Constants.BASE_URL);

    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent != null) {

            id = intent.getStringExtra("id");


//            checkLoginStatus.checkLoginStatus(id, Constants.root).enqueue(new Callback<MyRes>() {
//                @Override
//                public void onResponse(Call<MyRes> call, Response<MyRes> response) {
//
//                    Log.e("response", response.body().getMsg());
//                    if (response.body().getMsg().equals("0")) {
//                        Log.e("service started", "intent");
//                        Intent intent1 = new Intent();
//                        intent1.setAction("status");
//                        sendBroadcast(intent1);
//
//
//                        if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
//                            ((ActivityManager) getSystemService(ACTIVITY_SERVICE))
//                                    .clearApplicationUserData(); // note: it has a return value!
//
//                            apiServiceLogOut.logOut(id).enqueue(new Callback<MyRes>() {
//                                @Override
//                                public void onResponse(Call<MyRes> call, Response<MyRes> response) {
//
//                                }
//
//                                @Override
//                                public void onFailure(Call<MyRes> call, Throwable t) {
//
//                                }
//                            });
//
//                            Intent intent2 = new Intent(getApplicationContext(), SplashActivity.class);
//                            startActivity(intent2);
//
//
//                        } else {
//                            // use old hacky way, which can be removed
//                            // once minSdkVersion goes above 19 in a few years.
//                        }
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<MyRes> call, Throwable t) {
//
//                }
//            });
        }
    }


}
