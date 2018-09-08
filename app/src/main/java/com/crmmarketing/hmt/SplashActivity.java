package com.crmmarketing.hmt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.Branch;
import com.crmmarketing.hmt.GsonModel.BranchList;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    private int SPLASH_TIME_OUT = 2;
    private Handler handler;
    private Runnable runnable;
    private String username;
    private boolean isLogin = false;
    private String role;
    private String id;
    private RetrofitClient.getBranchList getBranchList;
    private ArrayList<Branch> branchArrayList;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);
        username = sharedPref.getString("user_name", null);
        branchArrayList = new ArrayList<>();
        if (username != null) {

            isLogin = sharedPref.getBoolean(username, false);
            role = sharedPref.getString("role", null);

        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
        setContentView(R.layout.activity_splash);

        getBranchList = RetrofitClient.getBranchList(Constants.BASE_URL);


        getBranchList.branch().enqueue(new Callback<BranchList>() {
            @Override
            public void onResponse(Call<BranchList> call, Response<BranchList> response) {

                if (response != null) {
                    branchArrayList = (ArrayList<Branch>) response.body().getBranch();

                    Branch branch = new Branch();
                    branch.setName("All");
                    branch.setId("0");

                    branchArrayList.add(0, branch);

                    if (branchArrayList.size() > 0) {

                        if (isLogin) {
                            Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                            i.putExtra("role", role);
                            i.putExtra("username", username);
                            i.putParcelableArrayListExtra("branch", branchArrayList);
                            startActivity(i);
                            finish();

                        } else {
                            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                            i.putParcelableArrayListExtra("branch", branchArrayList);
                            startActivity(i);
                            finish();
                        }

                    } else {
                        Toast.makeText(SplashActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }


            }

            @Override
            public void onFailure(Call<BranchList> call, Throwable t) {


                Toast.makeText(SplashActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                finish();

            }
        });

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SPLASH_TIME_OUT--;
                //  handler.postDelayed(runnable, 1000);

                if (SPLASH_TIME_OUT == 0) {
                    handler.removeCallbacks(runnable);
                    handler.removeCallbacksAndMessages(null);

                    if (isLogin) {
                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                        i.putExtra("role", role);
                        i.putExtra("username", username);
                        i.putParcelableArrayListExtra("branch", branchArrayList);
                        startActivity(i);
                        finish();

                    } else {
                        Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }


                }


            }
        };
        // handler.post(runnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, SPLASH_TIME_OUT);
    }


}

