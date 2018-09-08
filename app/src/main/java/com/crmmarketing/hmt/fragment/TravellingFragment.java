package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationProvider;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.ResultReceiver;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.GPSTracker;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.MapData;
import com.crmmarketing.hmt.gsonmodel22.Row;
import com.crmmarketing.hmt.model.MyRes;
import com.crmmarketing.hmt.services.GeocodeAddressIntentService;
import com.crmmarketing.hmt.services.MyService;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 17-05-2017.
 */

public class TravellingFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_PLACE_PICKER = 1;
    private final String MAP_KEY = "AIzaSyB9tIwsbo2SQN258HiO01ukNUFYfuL2I5o";
    private final long DOUBLE_TAP = 1500;
    private TextView tvSource;
    private TextView tvDestination;
    private TextView tvDistance;
    private Button btnStart;
    private GoogleApiClient mGoogleApiClient;
    private GPSTracker gpsTracker;
    private AddressResultReceiver mResultReceiver;
    private int distance;
    private boolean isRefresh = false;
    private Location destinationLocation;
    private RetrofitClient.APIServiceGetDistanceMap apiServiceGetDistanceMap;
    private long lastclick;
    private MapData mapData;
    private ArrayList<Row> rowArrayList;
    private LinearLayout lltop;
    private LinearLayout llBottom;
    private Button btnStop;
    private TextView tvSource11;
    private TextView tvDestination11;
    private TextView tvTotalKm;
    private Context context;
    private RetrofitClient.StartTrip startTrip;
    private ProgressDialog progressDialog;
    private String id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));
        destinationLocation = new Location("destination");
        rowArrayList = new ArrayList<>();
        startTrip = RetrofitClient.starttrip(Constants.BASE_URL);

        apiServiceGetDistanceMap = RetrofitClient.getDistanceMap(Constants.MAP_URL);

        gpsTracker = new GPSTracker(getActivity());
        mResultReceiver = new AddressResultReceiver(new Handler());

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        mGoogleApiClient.connect();

        if (!Utils.isLocationEnabled(getActivity())) {
            Toast.makeText(getActivity(), "Please turn on Location Service", Toast.LENGTH_SHORT).show();

            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getActivity().startActivity(myIntent);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Travelling");

        return inflater.inflate(R.layout.fragmet_travelling, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvSource = (TextView) view.findViewById(R.id.tvSourcePlace);
        tvDestination = (TextView) view.findViewById(R.id.tvDestinationPlace);
        tvDistance = (TextView) view.findViewById(R.id.tvDistance);
        btnStart = (Button) view.findViewById(R.id.btnSubmit);
        btnStop = (Button) view.findViewById(R.id.btnStopService);
        lltop = (LinearLayout) view.findViewById(R.id.llTop);
        llBottom = (LinearLayout) view.findViewById(R.id.llBottom);
        tvSource11 = (TextView) view.findViewById(R.id.tvSourcePlace11);
        tvDestination11 = (TextView) view.findViewById(R.id.tvDestinationPlace11);
        tvTotalKm = (TextView) view.findViewById(R.id.tvTotalKm);


        if (isMyServiceRunning(MyService.class)) {

            lltop.setVisibility(View.GONE);
            llBottom.setVisibility(View.VISIBLE);
        } else {
            lltop.setVisibility(View.VISIBLE);
            llBottom.setVisibility(View.GONE);
        }


        tvSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.checkInternetConnection(getActivity())) {

                    Toast.makeText(getActivity(), String.valueOf(gpsTracker.getLatitude()), Toast.LENGTH_SHORT).show();
                }


                if (gpsTracker.getLocation() != null) {

                    startIntentService();
                }
            }
        });


        tvDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.checkInternetConnection(getActivity())) {

                    checkOut(tvDestination);
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }


                if (Utils.isLocationEnabled(getActivity())) {
                    if (isRefresh && !tvDistance.getText().toString().isEmpty() && distance > 0) {
                        showAlertDialog();
                    }
                } else {

                    Toast.makeText(getActivity(), "Please turn on Location Service", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(myIntent);
                }


            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isLocationEnabled(getActivity())) {


                    showStopDialog();

                    // stop explicitly service and call server for last entry


                }

            }
        });
    }

    public void showAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        distance = 0;
        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();


                if (!tvSource.getText().toString().isEmpty() && !tvDestination.getText().toString().isEmpty()) {
                    // further process distant calculation between two points..
//                    distance=gpsTracker.getLocation().distanceTo(destinationLocation);
//                    float[] totleDistance=new float[2];
//                    Location.distanceBetween(gpsTracker.getLatitude(),gpsTracker.getLongitude(),destinationLocation.getLatitude(),destinationLocation.getLongitude(),totleDistance);
//
//                    tvDistance.setText("Approx. Distance:-".concat(String.valueOf(totleDistance[0]/1000)).concat(" Km"));
//                    Log.e("distance..",String.valueOf(totleDistance[0]/1000));

                    String source = String.valueOf(gpsTracker.getLatitude()).concat(",").concat(String.valueOf(gpsTracker.getLongitude()));

                    String destination = String.valueOf(String.valueOf(destinationLocation.getLatitude())).concat(",").concat(String.valueOf(destinationLocation.getLongitude()));

                    // TODO send data to server source,destination,and total km and start tracking

                    // if successfull insert to server we start track and start service


                    apiServiceGetDistanceMap.getMapDate(source, destination, "driving").enqueue(new Callback<MapData>() {
                        @Override
                        public void onResponse(Call<MapData> call, Response<MapData> response) {

                            if (response.isSuccessful()) {

                                rowArrayList = (ArrayList<Row>) response.body().getRows();


                                if (rowArrayList.size() > 0) {

                                    for (int i = 0; i < rowArrayList.size(); i++) {


                                        for (int j = 0; j < rowArrayList.get(i).getElements().size(); j++) {

                                            if (rowArrayList.get(i).getElements().get(j).getDistance() != null) {
                                                distance = distance + rowArrayList.get(i).getElements().get(j).getDistance().getValue();

                                            }

                                        }

                                    }
                                }


                                tvSource11.setText(tvSource.getText().toString());
                                tvDestination11.setText(tvDestination.getText().toString());
                                tvTotalKm.setText(String.valueOf(distance / 1000).concat(" Km"));

                                showProgressDialog();


                                startTrip.startTrip(id, String.valueOf(gpsTracker.getLatitude()),
                                        String.valueOf(gpsTracker.getLongitude()),
                                        String.valueOf(destinationLocation.getLatitude()),
                                        String.valueOf(destinationLocation.getLongitude()),
                                        String.valueOf(distance / 1000)
                                ).enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        if (response.isSuccessful()) {

                                            if (response.body().getMsg().equalsIgnoreCase("true")) {

                                                lltop.setVisibility(View.GONE);
                                                llBottom.setVisibility(View.VISIBLE);

                                                SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPref.edit();
                                                editor.putString("t_id", response.body().getTravel_id());
                                                editor.apply();


                                                PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
                                                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
                                                wl.acquire(7200000);

                                                WifiManager wm = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                                                WifiManager.WifiLock wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, "TAG");
                                                wifiLock.acquire();


                                                Intent intent = new Intent(getActivity(), MyService.class);
                                                intent.putExtra("lat", gpsTracker.getLatitude());
                                                intent.putExtra("log", gpsTracker.getLongitude());
                                                getActivity().startService(intent);
                                            }

                                        } else {

                                            Toast.makeText(getActivity(), "Unable to start trip", Toast.LENGTH_SHORT).show();
                                        }


                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(getActivity(), "Unable to start trip", Toast.LENGTH_SHORT).show();

                                    }
                                });


                                tvDistance.setText("Travel distance:- ".concat(String.valueOf(distance / 1000).concat(" Km")));

                            }


                        }

                        @Override
                        public void onFailure(Call<MapData> call, Throwable t) {

                        }
                    });


                }

            }


        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
                isRefresh = true;


            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    public void checkOut(View view) {
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(getActivity());
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), e.getConnectionStatusCode(),
                    REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(getActivity(), "Please install Google Play Services!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        distance = 0;
        isRefresh = false;
        if (requestCode == REQUEST_PLACE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                Log.e("place", place.getAddress().toString());

                destinationLocation.setLatitude(place.getLatLng().latitude);
                destinationLocation.setLongitude(place.getLatLng().longitude);

                tvDestination.setText(place.getAddress().toString());


                if (place.getAddress() != null) {
                    String source = String.valueOf(gpsTracker.getLatitude()).concat(",").concat(String.valueOf(gpsTracker.getLongitude()));

                    String destination = String.valueOf(String.valueOf(destinationLocation.getLatitude())).concat(",").concat(String.valueOf(destinationLocation.getLongitude()));

                    if (gpsTracker.getLocation() != null && !tvSource.getText().toString().isEmpty()) {
                        apiServiceGetDistanceMap.getMapDate(source, destination, "driving").enqueue(new Callback<MapData>() {
                            @Override
                            public void onResponse(Call<MapData> call, Response<MapData> response) {

                                if (response.isSuccessful()) {

                                    rowArrayList = (ArrayList<Row>) response.body().getRows();


                                    if (rowArrayList.size() > 0) {

                                        for (int i = 0; i < rowArrayList.size(); i++) {


                                            for (int j = 0; j < rowArrayList.get(i).getElements().size(); j++) {

                                                if (rowArrayList.get(i).getElements().get(j).getDistance() != null) {
                                                    distance = distance + rowArrayList.get(i).getElements().get(j).getDistance().getValue();

                                                }

                                            }

                                        }
                                    }

                                    isRefresh = true;
                                    tvDistance.setText("Travel distance:- ".concat(String.valueOf(distance / 1000).concat(" Km")));

                                }


                            }

                            @Override
                            public void onFailure(Call<MapData> call, Throwable t) {

                            }
                        });

                    }


                }


            } else if (resultCode == PlacePicker.RESULT_ERROR) {
                Toast.makeText(getActivity(), "Places API failure! Check that the API is enabled for your key",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void startIntentService() {
        Intent intent = new Intent(getActivity(), GeocodeAddressIntentService.class);
        intent.putExtra(Constants.RECEIVER, mResultReceiver);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, gpsTracker.getLocation());
        getActivity().startService(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Travelling");
    }

    public void showStopDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        distance = 0;
        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                Intent intent = new Intent(getActivity(), MyService.class);
                getActivity().stopService(intent);


                if (!isMyServiceRunning(MyService.class)) {
                    lltop.setVisibility(View.VISIBLE);
                    llBottom.setVisibility(View.GONE);
                }

                PowerManager pm = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
                PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
                if (wl.isHeld())
                    wl.release();


                WifiManager wm = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                WifiManager.WifiLock wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, "TAG");
                if (wifiLock.isHeld())
                    wifiLock.release();


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

    public boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastclick < DOUBLE_TAP) {
            lastclick = clickTime;
            return true;
        }
        lastclick = clickTime;
        return false;
    }

    private class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {

            // Display the address string
            // or an error message sent from the intent service.
            String msg = resultData.getString(Constants.RESULT_DATA_KEY);


            // Show a toast message if an address was found.
            if (resultCode == Constants.SUCCESS_RESULT) {

                if (msg != null)
                    tvSource.setText(msg);

            }

        }
    }

}
