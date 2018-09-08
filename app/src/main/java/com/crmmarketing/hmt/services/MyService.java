package com.crmmarketing.hmt.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.MapData;
import com.crmmarketing.hmt.gsonmodel22.Row;
import com.crmmarketing.hmt.model.MyRes;
import com.crmmarketing.hmt.model.Travel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    private static final String TAG = "gpstest";
    private static final int LOCATION_INTERVAL = 3000;
    private static final float LOCATION_DISTANCE = 5;
    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER),
    };
    Location mLastLocation;
    private LocationManager mLocationManager = null;
    private double lat;
    private double log;
    private RetrofitClient.APIServiceGetDistanceMap apiServiceGetDistanceMap;
    private RetrofitClient.MakeMyTrip makeMyTrip;
    private String id;
    private String t_id;
    private boolean isStop = false;
    private double srclat;
    private double srclog;
    private double destlat;
    private double destlog;
    private double total;

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");

        lat = intent.getDoubleExtra("lat", 0);
        log = intent.getDoubleExtra("log", 0);

        if (mLastLocation != null) {
            mLastLocation.setLatitude(0);
            mLastLocation.setLongitude(0);
        }

        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");

        PowerManager pm = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "TAG");
        wl.acquire(720000000);

        WifiManager wm = (WifiManager) getApplicationContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiManager.WifiLock wifiLock = wm.createWifiLock(WifiManager.WIFI_MODE_FULL, "TAG");
        wifiLock.acquire();


        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("my", Context.MODE_PRIVATE);
        String username = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        t_id = sharedPref.getString("t_id", "");

        initializeLocationManager();
        apiServiceGetDistanceMap = RetrofitClient.getDistanceMap(Constants.MAP_URL);
        makeMyTrip = RetrofitClient.makeTrip(Constants.BASE_URL);
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[1]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }
        try {
            mLocationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    mLocationListeners[0]);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        super.onDestroy();


        if (mLocationManager != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                try {
                    mLocationManager.removeUpdates(mLocationListeners[i]);
                } catch (Exception ex) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex);
                }
            }


        }
    }

    private void initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager");
        if (mLocationManager == null) {
            mLocationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > LOCATION_INTERVAL;
        boolean isSignificantlyOlder = timeDelta < -LOCATION_INTERVAL;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 10;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    private class LocationListener implements android.location.LocationListener {


        public LocationListener(String provider) {
            Log.e(TAG, "LocationListener " + provider);
            mLastLocation = new Location(provider);
//            mLastLocation.setLatitude(lat);
//            mLastLocation.setLongitude(log);
        }

        @Override
        public void onLocationChanged(final Location location) {
            Log.e(TAG, "onLocationChanged: " + location);

            if (mLastLocation != null) {
                String source = String.valueOf(mLastLocation.getLatitude()).concat(",").concat(String.valueOf(mLastLocation.getLongitude()));


                String destination = String.valueOf(String.valueOf(location.getLatitude())).concat(",").concat(String.valueOf(location.getLongitude()));
                apiServiceGetDistanceMap.getMapDate(source, destination, "driving").enqueue(new Callback<MapData>() {
                    @Override
                    public void onResponse(Call<MapData> call, Response<MapData> response) {
                        ArrayList<Row> rowArrayList = new ArrayList<Row>();
                        if (response.isSuccessful()) {

                            rowArrayList = (ArrayList<Row>) response.body().getRows();
                            double distance = 0;


                            if (rowArrayList.size() > 0) {

                                for (int i = 0; i < rowArrayList.size(); i++) {


                                    for (int j = 0; j < rowArrayList.get(i).getElements().size(); j++) {

                                        if (rowArrayList.get(i).getElements().get(j).getDistance() != null) {
                                            distance = distance + rowArrayList.get(i).getElements().get(j).getDistance().getValue();

                                        }

                                    }

                                }

                                Log.e("source..lat", "" + mLastLocation.getLatitude());
                                Log.e("source..log", "" + mLastLocation.getLongitude());
                                Log.e("destination..lat", "" + location.getLatitude());
                                Log.e("destination..log", "" + location.getLongitude());
                                Log.e("distance:", "" + distance / 1000);

                                final double dist = distance / 1000;

                                //call service to send location

                                srclat = mLastLocation.getLatitude();
                                srclog = mLastLocation.getLongitude();
                                destlat = mLastLocation.getLatitude();
                                destlog = mLastLocation.getLongitude();
                                total = dist;

                                Travel travel = new Travel();
                                travel.setId(id);
                                travel.setTid(t_id);
                                travel.setSrcLat(String.valueOf(mLastLocation.getLatitude()));
                                travel.setSrcLog(String.valueOf(mLastLocation.getLongitude()));
                                travel.setDestLat(String.valueOf(location.getLatitude()));
                                travel.setDestLog(String.valueOf(location.getLongitude()));
                                travel.setDist(String.valueOf(dist));

                                // check for accuracy of location
                                Log.e("meter", String.valueOf(distance));
                                double speed = distance / (location.getTime() - mLastLocation.getTime());

                                if (isBetterLocation(location, mLastLocation) && speed > 3 && distance < 300) {
                                    if (mLastLocation.getLatitude() != 0 && mLastLocation.getLongitude() != 0 && location.getLatitude() != 0 && location.getLongitude() != 0) {

                                        if (mLastLocation.getLatitude() != location.getLatitude() && mLastLocation.getLongitude() != location.getLongitude()) {
                                            makeMyTrip.makeTrip(id, t_id, String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()),
                                                    String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), String.valueOf(dist)).enqueue(new Callback<MyRes>() {
                                                @Override
                                                public void onResponse(Call<MyRes> call, Response<MyRes> response) {


                                                }

                                                @Override
                                                public void onFailure(Call<MyRes> call, Throwable t) {

                                                }
                                            });


                                        }


                                    }
                                }

                                mLastLocation.set(location);
                            }


                        }


                    }

                    @Override
                    public void onFailure(Call<MapData> call, Throwable t) {
                        mLastLocation.set(location);
                    }
                });

            }


        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.e(TAG, "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.e(TAG, "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.e(TAG, "onStatusChanged: " + provider);
        }
    }


}