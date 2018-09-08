package com.crmmarketing.hmt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.services.SynchToServer;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public final String TAG = NetworkChangeReceiver.class.getSimpleName();

    public void register(NetworkChangeCallback callback) {
        callback.updateNetworkInfo();
    }


    @Override
    public void onReceive(final Context context, final Intent intent) {

        Log.v(TAG, "");


        if (getConnectivityType(context)) {

            if (!Utils.isMyServiceRunning(context, "com.crmmarketing.hmt.services.SynchToServer")) {
                Intent intentService = new Intent(context, SynchToServer.class);
                context.startService(intentService);
            }


        }


    }

    public boolean getConnectivityType(Context context) {
        boolean connectionStatus = false;
        // Get an instance of ConnectivityManager
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //If connectivity object is not null
        if (cm != null) {
            //Get network info - connection status
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {

                    connectionStatus = true;

                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    connectionStatus = true;
                }
            } else {
                connectionStatus = false;
            }
        }
        return connectionStatus;
    }
}