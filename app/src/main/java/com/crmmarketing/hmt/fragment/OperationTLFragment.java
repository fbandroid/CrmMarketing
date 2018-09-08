package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.FNOActivity;
import com.crmmarketing.hmt.GsonModel.Counter;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.NetworkChangeReceiver;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by USER on 10-07-2017.
 */

public class OperationTLFragment extends Fragment {
    private final long DOUBLE_TAP = 1500;
    private CardView cvViewTarget;
    private CardView cvInquirylist;
    private CardView cvAttendance;
    private String id;
    private String userName;
    private String role;
    private String branch;
    private BroadcastReceiver broadcastReceiver;
    private long lastclick;
    private TextView tvCounter;
    private TextView tvCounterToday;
    private TextView tvCounterCompleted;
    private RetrofitClient.getCounterInfo getCounterInfo;
    private Context context;
    private RetrofitClient.setChecklist checklist;
    private CardView fragmentTlDashboardCvExpence;
    private CardView fragmentTlDashboardCvEventAttendance;
    private CardView fragmentAdminDashboardCvRequestLeave;
    private CardView fragmentTlDashboardCvClient;
    private CardView fragmentAdminDashboardCvVolume;
    private CardView fragmentAdminDashboardCvEventReport;



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

        checklist = RetrofitClient.setCheck(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);

        getCounterInfo = RetrofitClient.getCounter(Constants.BASE_URL);

        broadcastReceiver = new NetworkChangeReceiver();
        HandlerThread handlerThread = new HandlerThread("ht");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);


        getActivity().registerReceiver(
                broadcastReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION), null, handler);

        if (getArguments() != null && getArguments().getString("id") != null && getArguments().getString("username") != null) {

            id = getArguments().getString("id");
            userName = getArguments().getString("username");
            role = getArguments().getString("role");

        }

        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branch = sharedPref.getString("branch", "");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("DashBoard");
        return inflater.inflate(R.layout.fragment_op_tl, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvViewTarget = (CardView) view.findViewById(R.id.fragment_me_dashboard_cvViewTarget);
        cvInquirylist = (CardView) view.findViewById(R.id.fragment_me_dashboard_cvInquiry);
        cvAttendance = (CardView) view.findViewById(R.id.fragment_me_dashboard_cvAttendance);

        fragmentTlDashboardCvExpence = (CardView) view.findViewById(R.id.fragment_tl_dashboard_cvExpence);
        fragmentTlDashboardCvEventAttendance = (CardView) view.findViewById(R.id.fragment_tl_dashboard_cvEventAttendance);
        fragmentAdminDashboardCvRequestLeave = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvRequestLeave);
        fragmentTlDashboardCvClient = (CardView) view.findViewById(R.id.fragment_tl_dashboard_cvClient);
        fragmentAdminDashboardCvVolume = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvVolume);
        fragmentAdminDashboardCvEventReport = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvEventReport);





        tvCounter = (TextView) view.findViewById(R.id.tvCounterTotal);

        cvViewTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }

                addFragment(new ViewCallLogFragment(), OperationTLFragment.this);
            }
        });


        cvInquirylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }

                InquiryListInfoFragment inquiryListInfoFragment = new InquiryListInfoFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("id", id);
                inquiryListInfoFragment.setArguments(bundle);

                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(inquiryListInfoFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        cvViewTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                ViewTargetFragment viewTargetFragment = new ViewTargetFragment();
                final Bundle bundle44 = new Bundle();
                bundle44.putString("id", id);
                viewTargetFragment.setArguments(bundle44);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(viewTargetFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        cvAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }
                AttandaceMasterFragment attandaceMasterFragment = new AttandaceMasterFragment();
                final Bundle bundle55 = new Bundle();
                bundle55.putString("id", id);
                attandaceMasterFragment.setArguments(bundle55);
                if (Utils.checkInternetConnection(getActivity())) {


                    if (Utils.isLocationEnabled(getActivity())) {
                        addFragment(attandaceMasterFragment, OperationTLFragment.this);


                    } else {

                        Toast.makeText(getActivity(), "Please enable Location", Toast.LENGTH_LONG).show();
                        Intent viewIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(viewIntent);
                    }


                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });


        fragmentTlDashboardCvEventAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                EventAttendanceFragment eventAttendanceFragment = new EventAttendanceFragment();
                final Bundle bundle77 = new Bundle();
                bundle77.putString("id", id);
                eventAttendanceFragment.setArguments(bundle77);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(eventAttendanceFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        fragmentTlDashboardCvExpence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                ExpenseRequestFragment expenseRequestFragment = new ExpenseRequestFragment();
                final Bundle bundle88 = new Bundle();
                bundle88.putString("id", id);
                expenseRequestFragment.setArguments(bundle88);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(expenseRequestFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        fragmentAdminDashboardCvRequestLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                RequestLeaveFragment requestLeaveFragment = new RequestLeaveFragment();
                final Bundle bundle10 = new Bundle();
                bundle10.putString("id", id);
                requestLeaveFragment.setArguments(bundle10);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(requestLeaveFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        fragmentTlDashboardCvClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                ClientListFragment clientListFragment = new ClientListFragment();
                final Bundle bundle10 = new Bundle();
                bundle10.putString("id", id);
                clientListFragment.setArguments(bundle10);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(clientListFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        fragmentAdminDashboardCvEventReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                EventReportFragment eventReportFragment = new EventReportFragment();
                final Bundle bundle10 = new Bundle();
                bundle10.putString("id", id);
                eventReportFragment.setArguments(bundle10);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(eventReportFragment, OperationTLFragment.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        fragmentAdminDashboardCvVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }


                if (Utils.checkInternetConnection(getActivity())) {
                    Intent intent = new Intent(getActivity(), FNOActivity.class);
                    startActivity(intent);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }


    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("DashBoard");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        //call counter api

        getCounterInfo.getCounter(id, role, branch).enqueue(new Callback<Counter>() {
            @Override
            public void onResponse(Call<Counter> call, Response<Counter> response) {

                if (response.isSuccessful()) {

                    tvCounter.setText(response.body().getTotal());

                }

            }

            @Override
            public void onFailure(Call<Counter> call, Throwable t) {

            }
        });


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
}
