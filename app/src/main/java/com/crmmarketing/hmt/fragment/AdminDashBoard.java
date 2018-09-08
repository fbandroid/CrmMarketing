package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.FNOActivity;
import com.crmmarketing.hmt.GsonModel.Branch;
import com.crmmarketing.hmt.GsonModel.Counter;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.NetworkChangeCallback;
import com.crmmarketing.hmt.NetworkChangeReceiver;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.database.PostsDatabaseHelper;
import com.crmmarketing.hmt.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 24-01-2017.
 */

public class AdminDashBoard extends Fragment implements View.OnClickListener, NetworkChangeCallback {


    private final long DOUBLE_TAP = 1500;
    private CardView cvInquiry;
    private CardView cvFinalLead;
    private CardView cvPerformanceAnalysis;
    private CardView cvListOfEmployee;
    private CardView cvAddProduct;
    private CardView cvAttendance;
    private Context context;
    private BroadcastReceiver broadcastReceiver;
    private String userName;
    private String id;
    private String role;
    private String branch;
    private ArrayList<Branch> branchArrayList;
    private long lastclick;
    private TextView tvCounter;
    private TextView tvCounterToday;
    private TextView tvCounterCompleted;

    private CardView fragmentAdminDashboardCvClient;
    private CardView fragmentAdminDashboardCvEventAttendance;
    private CardView fragmentAdminDashboardCvVolume;
    private CardView fragmentAdminDashboardCvRequestLeave;
    private CardView fragmentAdminDashboardCvEventReport;
    private CardView fragmentAdminDashboardCvApproveLeave;





    private RetrofitClient.getCounterInfo getCounterInfo;

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
        setRetainInstance(true);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);


        getCounterInfo = RetrofitClient.getCounter(Constants.BASE_URL);

        broadcastReceiver = new NetworkChangeReceiver();

        HandlerThread handlerThread = new HandlerThread("ht");
        handlerThread.start();
        Looper looper = handlerThread.getLooper();
        Handler handler = new Handler(looper);


        if (getArguments() != null) {
            branchArrayList = getArguments().getParcelableArrayList("branch");
        }


        if (getArguments() != null && getArguments().getString("id") != null && getArguments().getString("username") != null) {

            id = getArguments().getString("id");
            userName = getArguments().getString("username");
            role = getArguments().getString("role");
            branch = getArguments().getString("branch");

        }


        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branch = sharedPref.getString("branch", "");


        getActivity().registerReceiver(
                broadcastReceiver,
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION), null, handler);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "restore", AdminDashBoard.this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("DashBoard");
        return inflater.inflate(R.layout.fragment_admin_dashboard, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cvInquiry = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvInquiry);
        cvFinalLead = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvFinalLead);
        cvPerformanceAnalysis = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvPerformanceAnalysis);
        cvListOfEmployee = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvListOfExe);
        cvAddProduct = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvAddProduct);
        cvAttendance = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvAttendance);
        fragmentAdminDashboardCvVolume = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvVolume);


        fragmentAdminDashboardCvClient = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvClient);
        fragmentAdminDashboardCvEventAttendance = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvEventAttendance);
        fragmentAdminDashboardCvRequestLeave = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvRequestLeave);
        fragmentAdminDashboardCvEventReport = (CardView)view. findViewById(R.id.fragment_admin_dashboard_cvEventReport);
        fragmentAdminDashboardCvApproveLeave = (CardView) view.findViewById(R.id.fragment_admin_dashboard_cvApproveLeave);



        tvCounter = (TextView) view.findViewById(R.id.tvCounterTotal);
        tvCounterToday = (TextView) view.findViewById(R.id.tvCounterToday);
        tvCounterCompleted = (TextView) view.findViewById(R.id.tvCounterCompleted);


        cvInquiry.setOnClickListener(this);
        cvFinalLead.setOnClickListener(this);
        cvPerformanceAnalysis.setOnClickListener(this);
        cvListOfEmployee.setOnClickListener(this);
        cvAddProduct.setOnClickListener(this);
        cvAttendance.setOnClickListener(this);
        fragmentAdminDashboardCvClient.setOnClickListener(this);
        fragmentAdminDashboardCvEventAttendance.setOnClickListener(this);
        fragmentAdminDashboardCvVolume.setOnClickListener(this);
        fragmentAdminDashboardCvRequestLeave.setOnClickListener(this);
        fragmentAdminDashboardCvEventReport.setOnClickListener(this);
        fragmentAdminDashboardCvApproveLeave.setOnClickListener(this);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().getFragment(savedInstanceState, "restore");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fragment_admin_dashboard_cvInquiry:


                if (isDoubleClick()) {
                    return;
                }


                InquiryListInfoFragment inquiryListInfoFragment = new InquiryListInfoFragment();
                final Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putParcelableArrayList("branch", branchArrayList);
                inquiryListInfoFragment.setArguments(bundle);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(inquiryListInfoFragment, AdminDashBoard.this);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.fragment_admin_dashboard_cvFinalLead:
                if (isDoubleClick()) {
                    return;
                }

                TodaysFollowUpFragment todaysFollowUpFragment = new TodaysFollowUpFragment();
                final Bundle bundle22 = new Bundle();
                bundle22.putString("id", id);
                todaysFollowUpFragment.setArguments(bundle22);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(todaysFollowUpFragment, AdminDashBoard.this);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.fragment_admin_dashboard_cvPerformanceAnalysis:
                if (isDoubleClick()) {
                    return;
                }
                CompletedFollowUpListFragment completedFollowUpListFragment = new CompletedFollowUpListFragment();
                final Bundle bundle33 = new Bundle();
                bundle33.putString("id", id);
                completedFollowUpListFragment.setArguments(bundle33);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(completedFollowUpListFragment, AdminDashBoard.this);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;
            case R.id.fragment_admin_dashboard_cvListOfExe:
                if (isDoubleClick()) {
                    return;
                }
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(new ListOfTeamLeaderFragment(), AdminDashBoard.this);
                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.fragment_admin_dashboard_cvAttendance:
                if (isDoubleClick()) {
                    return;
                }
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(new AttandaceMasterFragment(), AdminDashBoard.this);

                } else {
                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }


                break;
            case R.id.fragment_admin_dashboard_cvAddProduct:
                if (isDoubleClick()) {
                    return;
                }
                ViewTargetFragment viewTargetFragment = new ViewTargetFragment();
                final Bundle bundle44 = new Bundle();
                bundle44.putString("id", id);
                viewTargetFragment.setArguments(bundle44);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(viewTargetFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.fragment_admin_dashboard_cvClient:
                if (isDoubleClick()) {
                    return;
                }

                ClientListFragment clientListFragment = new ClientListFragment();
                final Bundle bundle55 = new Bundle();
                bundle55.putString("id", id);
                clientListFragment.setArguments(bundle55);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(clientListFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.fragment_admin_dashboard_cvEventAttendance:
                if (isDoubleClick()) {
                    return;
                }

                EventAttendanceFragment eventAttendanceFragment = new EventAttendanceFragment();
                final Bundle bundle66 = new Bundle();
                bundle66.putString("id", id);
                eventAttendanceFragment.setArguments(bundle66);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(eventAttendanceFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.fragment_admin_dashboard_cvVolume:

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
                break;

            case R.id.fragment_admin_dashboard_cvRequestLeave:
                if (isDoubleClick()) {
                    return;
                }

                RequestLeaveFragment requestLeaveFragment = new RequestLeaveFragment();
                final Bundle bundle77 = new Bundle();
                bundle77.putString("id", id);
                requestLeaveFragment.setArguments(bundle77);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(requestLeaveFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.fragment_admin_dashboard_cvEventReport:
                if (isDoubleClick()) {
                    return;
                }

                EventReportFragment eventReportFragment = new EventReportFragment();
                final Bundle bundle88 = new Bundle();
                bundle88.putString("id", id);
                eventReportFragment.setArguments(bundle88);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(eventReportFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
                break;

            case R.id.fragment_admin_dashboard_cvApproveLeave:

                if (isDoubleClick()) {
                    return;
                }

                ApproveLeaveFragment approveLeaveFragment = new ApproveLeaveFragment();
                final Bundle bundle99 = new Bundle();
                bundle99.putString("id", id);
                approveLeaveFragment.setArguments(bundle99);
                if (Utils.checkInternetConnection(getActivity())) {
                    addFragment(approveLeaveFragment, AdminDashBoard.this);
                } else {

                    if (getActivity() != null) {
                        Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }

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
    public void updateNetworkInfo() {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        context.unregisterReceiver(broadcastReceiver);
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

    @Override
    public void onResume() {
        super.onResume();

        //call counter api

        getCounterInfo.getCounter(id, role, branch).enqueue(new Callback<Counter>() {
            @Override
            public void onResponse(Call<Counter> call, Response<Counter> response) {

                if (response.isSuccessful()) {

                    tvCounter.setText(response.body().getTotal());
                    tvCounterToday.setText(response.body().getToday());
                    tvCounterCompleted.setText(response.body().getCompleted());
                }

            }

            @Override
            public void onFailure(Call<Counter> call, Throwable t) {

            }
        });


    }
}


