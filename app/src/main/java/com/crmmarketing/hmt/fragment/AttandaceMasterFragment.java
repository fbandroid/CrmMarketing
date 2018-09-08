package com.crmmarketing.hmt.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;


import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.crmmarketing.hmt.common.GPSTracker;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class AttandaceMasterFragment extends Fragment implements
        ConnectionCallbacks, OnConnectionFailedListener, LocationListener {

    protected static final String TAG = "MainActivity";
    private final long DOUBLE_TAP = 1500;
    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;
    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;
    GPSTracker gps;
    private TextView tvLocation;
    private LocationRequest mLocationRequest;
    private TextView tvPunchIn;
    private TextView tvPunchOut;
    private TextView tvTravel;
    private String id;
    private CardView cvPunchIn;
    private CardView cvPunchOut;
    private CardView cvReport;
    private CardView cvTravelReport;
    private String role;
    private String b_id;
    private RetrofitClient.APIServiceSendLocation apiServiceSendLocation;
    private RetrofitClient.APIServiceCheckLocation apiServiceCheckLocation;
    private ProgressDialog progressDialog;
    private Context context;
    private long lastclick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        role = sharedPref.getString("role", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        b_id = sharedPref.getString("b_id", "");

        checkPermission();



        // gps tracker
        gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


        apiServiceSendLocation = RetrofitClient.sendLocation(Constants.BASE_URL);
        apiServiceCheckLocation = RetrofitClient.checkLocation(Constants.BASE_URL);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(5000);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance_master, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) context).setTitle("Attendance");
        tvPunchIn = (TextView) view.findViewById(R.id.tvPunchIn);
        tvPunchOut = (TextView) view.findViewById(R.id.tvPunchOut);
        tvTravel = (TextView) view.findViewById(R.id.tvTravelling);

        tvLocation = (TextView) view.findViewById(R.id.tvLocation);

        cvPunchIn = (CardView) view.findViewById(R.id.cvPunchIn);
        cvPunchOut = (CardView) view.findViewById(R.id.cvPunchOut);
        cvReport = (CardView) view.findViewById(R.id.cvReport);
        cvTravelReport = (CardView) view.findViewById(R.id.cvTravelReport);
        cvPunchOut.setVisibility(View.GONE);

        showProgressDialog();

        apiServiceCheckLocation.checKLoc(id).enqueue(new Callback<MyRes>() {
            @Override
            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                if (getActivity() != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Log.e("response", response.toString());

                String msg = response.body().getMsg();
                Log.e("message", msg);

                if (msg.equalsIgnoreCase("false")) {

                    tvPunchOut.setVisibility(View.GONE);
                    tvPunchIn.setVisibility(View.VISIBLE);
                    cvPunchOut.setVisibility(View.GONE);
                    cvPunchIn.setVisibility(View.VISIBLE);


                    if (response.body().getStatus().equals("0")) {
                        tvPunchIn.setEnabled(false);
                        cvPunchIn.setEnabled(false);
                        cvPunchIn.setClickable(false);

                        Toast.makeText(getActivity(), "Today's time completed", Toast.LENGTH_SHORT).show();

                    } else if (response.body().getStatus().equals("1")) {

                        tvPunchIn.setEnabled(true);
                        cvPunchIn.setEnabled(true);
                        cvPunchIn.setClickable(true);
                    }


                } else if (msg.equalsIgnoreCase("true")) {
                    tvPunchOut.setVisibility(View.VISIBLE);
                    tvPunchIn.setVisibility(View.GONE);
                    cvPunchIn.setVisibility(View.GONE);
                    cvPunchOut.setVisibility(View.VISIBLE);


                }

            }

            @Override
            public void onFailure(Call<MyRes> call, Throwable t) {
                if (getActivity() != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    getFragmentManager().popBackStack();
                }
            }
        });


        tvPunchIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }

                if (mLastLocation != null) {
                    showAlertDialog();
                } else {
                    Toast.makeText(getActivity(), "No Location Detected", Toast.LENGTH_LONG).show();
                }


            }
        });


        tvPunchOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }
                showAlertDialog();
            }
        });


        tvTravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }

                addFragment(new TravellingFragment(), AttandaceMasterFragment.this);
            }
        });


        cvReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addFragment(new AttendanceReportFragment(), AttandaceMasterFragment.this);


            }
        });

        cvTravelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                addFragment(new TravellingReportFragment(), AttandaceMasterFragment.this);
            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (mLastLocation != null) {

            Log.e("langitude", String.valueOf(mLastLocation.getLongitude()));

            tvLocation.setText("Latitude:-".concat(String.valueOf(mLastLocation.getLatitude())).concat("\n")
                    .concat("Longitude:-").concat(String.valueOf(mLastLocation.getLongitude())));

        } else {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


        if (getActivity() != null) {

            Toast.makeText(getActivity(), "Update Google play service ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        gps.stopUsingGPS();
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        Log.e("location changed", "" + location.getProvider());
        if (mLastLocation != null) {

            Log.e("latitude", String.valueOf(mLastLocation.getLatitude()));
            Log.e("longtitude", String.valueOf(mLastLocation.getLongitude()));

//            tvLocation.setText("Latitude:-".concat(String.valueOf(mLastLocation.getLatitude())).concat("\n")
//                    .concat("Longitude:-").concat(String.valueOf(mLastLocation.getLongitude())));
//
//            Toast.makeText(getActivity(), "Latitude:-".concat(String.valueOf(mLastLocation.getLatitude())).concat("\n")
//                    .concat("Longitude:-").concat(String.valueOf(mLastLocation.getLongitude())), Toast.LENGTH_LONG).show();

//            if (isWithinRange(mLastLocation.getLatitude(), mLastLocation.getLongitude())) {
//                Toast.makeText(getActivity(), "location within range", Toast.LENGTH_LONG).show();
//            } else {
//
//            }
        }
    }


    private boolean isWithinRange(double lat, double lng) {


        float[] results = new float[1];
        float[] resultModasa = new float[1];
        float[] resultIdarr = new float[1];
        float[] resultAhmedabad = new float[1];

        Location.distanceBetween(23.594665, 72.963813, lat, lng, results);

        Location.distanceBetween(23.4618884, 73.3032443, lat, lng, resultModasa);

        Location.distanceBetween(23.8385518, 73.0011484, lat, lng, resultIdarr);

        Location.distanceBetween(23.0717914, 72.5161089, lat, lng, resultAhmedabad);

        float distanceInMeters = results[0];
        float distanceInMeterModasa = resultModasa[0];
        float distanceInMeterIdarr = resultIdarr[0];
        float distanceInMeterAhmedabad = resultAhmedabad[0];

        return distanceInMeters < 50 || distanceInMeterModasa < 50 || distanceInMeterIdarr < 50
                || distanceInMeterAhmedabad < 100;


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Attendance");
    }


    public void showAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());


        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();


                if (mLastLocation != null && isWithinRange(mLastLocation.getLatitude(), mLastLocation.getLongitude())) {

                    showProgressDialog();

                    apiServiceCheckLocation.checKLoc(id).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (getActivity() != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("response", response.toString());

                            String msg = response.body().getMsg();
                            Log.e("message", msg);

                            if (msg.equalsIgnoreCase("false")) {

                                tvPunchOut.setVisibility(View.GONE);
                                tvPunchIn.setVisibility(View.VISIBLE);

                                progressDialog.show();

                                apiServiceSendLocation.sendLoc(id, String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()), "1", role).enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {


                                        if (getActivity() != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        if (response.body().getMsg().equalsIgnoreCase("true")) {
                                            if (getActivity() != null) {

                                                getFragmentManager().popBackStack();
                                                Toast.makeText(getActivity(), "Successfully punch in", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (getActivity() != null) {
                                                getFragmentManager().popBackStack();
                                                Toast.makeText(getActivity(), "Internal error", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {
                                        if (getActivity() != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });


                            } else if (msg.equalsIgnoreCase("true")) {
                                tvPunchOut.setVisibility(View.VISIBLE);
                                tvPunchIn.setVisibility(View.GONE);

                                progressDialog.show();

                                apiServiceSendLocation.sendLoc(id, String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()), "2", role).enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                        if (getActivity() != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                            getFragmentManager().popBackStack();
                                        }


                                        if (response.body().getMsg().equalsIgnoreCase("true")) {
                                            if (getActivity() != null) {
                                                getFragmentManager().popBackStack();
                                                Toast.makeText(getActivity(), "Successfully punch out", Toast.LENGTH_LONG).show();
                                            }
                                        } else {
                                            if (getActivity() != null) {
                                                getFragmentManager().popBackStack();
                                                Toast.makeText(getActivity(), "Internal error", Toast.LENGTH_LONG).show();
                                            }
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {
                                        if (getActivity() != null && progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                    }
                                });

                                }

                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            if (getActivity() != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });


                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "You are out of office", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass


            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    public boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastclick < DOUBLE_TAP) {
            lastclick = clickTime;
            return true;
        }
        lastclick = clickTime;
        return false;
    }

    private void checkPermission() {


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        420);

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {



                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                       420);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        420);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

        }
    }

}
