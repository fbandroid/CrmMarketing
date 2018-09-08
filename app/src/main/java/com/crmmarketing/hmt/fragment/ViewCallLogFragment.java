package com.crmmarketing.hmt.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;

/**
 * Created by USER on 04-02-2017.
 */

public class ViewCallLogFragment extends Fragment {


    private Context context;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCallHistory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_call_log, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void getCallHistory() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        String[] projection = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.NUMBER,
                CallLog.Calls.DATE,
                CallLog.Calls.DURATION,
                CallLog.Calls.TYPE,

        };


        Cursor c = getActivity().getContentResolver().query(CallLog.Calls.CONTENT_URI, projection, CallLog.Calls.NUMBER + " = ? ",
                new String[]{"02772234177"}, CallLog.Calls.DATE + " DESC");

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            do {
                String callerID = c.getString(c.getColumnIndex(CallLog.Calls._ID));
                String callerNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                long callDateandTime = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                long callDuration = c.getLong(c.getColumnIndex(CallLog.Calls.DURATION));
                int callType = c.getInt(c.getColumnIndex(CallLog.Calls.TYPE));
                if (callType == CallLog.Calls.INCOMING_TYPE) {
                    //incoming call
                } else if (callType == CallLog.Calls.OUTGOING_TYPE) {
                    //outgoing call
                    Log.e("outgoing call..", callerNumber);
                    Log.e("duration..", String.valueOf(callDuration));
                } else if (callType == CallLog.Calls.MISSED_TYPE) {
                    //missed call
                }
            } while (c.moveToNext());

        }
        if (c != null) {
            c.close();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Sales Executive");
    }
}
