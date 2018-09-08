package com.crmmarketing.hmt;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.Branch;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.fragment.AdminDashBoard;
import com.crmmarketing.hmt.fragment.DealerFragment;
import com.crmmarketing.hmt.fragment.ECSApprovalListFragment;
import com.crmmarketing.hmt.fragment.MarketingExecutiveDashboard;
import com.crmmarketing.hmt.fragment.MiniAdminDashboard;
import com.crmmarketing.hmt.fragment.OperationExFragment;
import com.crmmarketing.hmt.fragment.OperationMEFragment;
import com.crmmarketing.hmt.fragment.OperationTLFragment;
import com.crmmarketing.hmt.fragment.TeamLeadDeatilFragment;
import com.crmmarketing.hmt.fragment.TeamLeaderDashboardFragment;
import com.crmmarketing.hmt.fragment.TravellingFragment;
import com.crmmarketing.hmt.model.MyRes;
import com.crmmarketing.hmt.services.CheckStatus;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * role 1: approve leave
     * role 2: approve leave
     * role 6: approve leave
     */

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 420;

    private DrawerLayout drawer;
    private Toolbar toolbar;
    private String username;
    private String role;
    private String empid;
    private String name;
    private String u_id;
    private ArrayList<Branch> branchArrayList;
    private RetrofitClient.APIServiceLogOut apiServiceLogOut;
    private ProgressDialog progressDialog;
    private AlarmManager alarmManager;
    private boolean shouldRationalpermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.e("oncreate", "oncreate");

        checkPermission();

        apiServiceLogOut = RetrofitClient.logOut(Constants.BASE_URL);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().clear();


        SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);
        name = sharedPref.getString("name", null);
        u_id = String.valueOf(sharedPref.getInt("id", -1));


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(HomeActivity.this, CheckStatus.class);
//        intent.setAction("status");
//        intent.putExtra("id",u_id);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                SystemClock.elapsedRealtime(),
//                30 * 1000, PendingIntent.getService(HomeActivity.this, 0, intent, 0));


        if (getIntent() != null) {

            branchArrayList = getIntent().getParcelableArrayListExtra("branch");

            if (getIntent().getStringExtra("role") != null) {
                role = getIntent().getStringExtra("role");
                username = getIntent().getStringExtra("user_name");
                empid = getIntent().getStringExtra("id");
                switch (role) {
                    case "1":

                        AdminDashBoard adminDashBoard = new AdminDashBoard();
                        final Bundle bundle = new Bundle();
                        bundle.putString("id", empid);
                        bundle.putString("role", role);
                        bundle.putString("username", username);
                        bundle.putParcelableArrayList("branch", branchArrayList);
                        adminDashBoard.setArguments(bundle);

                        replaceFragment(adminDashBoard, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;
                    case "2":

                        MiniAdminDashboard miniAdminDashboard = new MiniAdminDashboard();
                        final Bundle bundle22 = new Bundle();
                        bundle22.putString("id", empid);
                        bundle22.putString("role", role);
                        bundle22.putString("username", username);
                        miniAdminDashboard.setArguments(bundle22);

                        replaceFragment(miniAdminDashboard, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;
                    case "3":
                        TeamLeaderDashboardFragment teamLeaderDashboardFragment = new TeamLeaderDashboardFragment();
                        final Bundle bundle33 = new Bundle();
                        bundle33.putString("id", empid);
                        bundle33.putString("role", role);
                        bundle33.putString("username", username);
                        teamLeaderDashboardFragment.setArguments(bundle33);

                        replaceFragment(teamLeaderDashboardFragment, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;
                    case "4":
                        MarketingExecutiveDashboard marketingExecutiveDashboard = new MarketingExecutiveDashboard();
                        final Bundle bundle44 = new Bundle();
                        bundle44.putString("id", empid);
                        bundle44.putString("role", role);
                        bundle44.putString("username", username);
                        marketingExecutiveDashboard.setArguments(bundle44);
                        replaceFragment(marketingExecutiveDashboard, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;

                    case "5":
                        //TODO dealer

                        DealerFragment dealerFragment = new DealerFragment();
                        final Bundle bundle55 = new Bundle();
                        bundle55.putString("id", empid);
                        bundle55.putString("role", role);
                        bundle55.putString("username", username);
                        dealerFragment.setArguments(bundle55);
                        replaceFragment(dealerFragment, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;


                    case "6":
                        //TODO operation EX admin
                        OperationExFragment operationExFragment = new OperationExFragment();
                        final Bundle bundle66 = new Bundle();
                        bundle66.putString("id", empid);
                        bundle66.putString("role", role);
                        bundle66.putString("username", username);
                        operationExFragment.setArguments(bundle66);
                        replaceFragment(operationExFragment, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;

                    case "7":
                        //TODO operation TL
                        OperationTLFragment operationTLFragment = new OperationTLFragment();
                        final Bundle bundle77 = new Bundle();
                        bundle77.putString("id", empid);
                        bundle77.putString("role", role);
                        bundle77.putString("username", username);
                        operationTLFragment.setArguments(bundle77);
                        replaceFragment(operationTLFragment, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;

                    case "8":
                        //TODO operation ME
                        OperationMEFragment operationMEFragment = new OperationMEFragment();
                        final Bundle bundle88 = new Bundle();
                        bundle88.putString("id", empid);
                        bundle88.putString("role", role);
                        bundle88.putString("username", username);
                        operationMEFragment.setArguments(bundle88);
                        replaceFragment(operationMEFragment, role);
                        navigationView.inflateMenu(R.menu.activity_home_admin_drawer);
                        break;
                }
            }
        }


        //// TODO: 27-02-2017  drawer header

        View mHeaderView = navigationView.getHeaderView(0);
        if (mHeaderView != null && name != null) {
            TextView tvName = (TextView) mHeaderView.findViewById(R.id.header_nav_tvName);
            TextView tvEmail = (TextView) mHeaderView.findViewById(R.id.header_nav_tvEmail);
            tvName.setText(name);
        }


        //  navigationView.getMenu().getItem(0).setChecked(true);
        //  MenuItem item =  navigationView.getMenu().getItem(0);
        //  navigationView.setCheckedItem(0);
        //  onNavigationItemSelected(item);


        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {

                if (getFragmentManager().getBackStackEntryCount() > 0) {

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        });
                    }


                } else {
                    //show hamburger

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                        toggle.syncState();
                        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                drawer.openDrawer(GravityCompat.START);
                            }
                        });
                    }

                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {


            if (Utils.checkInternetConnection(HomeActivity.this)) {

                showProgressDialog();

                apiServiceLogOut.logOut(u_id).enqueue(new Callback<MyRes>() {
                    @Override
                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (response.body().getMsg().equalsIgnoreCase("true")) {
                            //TODO call logout and redirect to login activity

                            SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);
                            sharedPref.edit().putBoolean(username, false).apply();
                            sharedPref.edit().putString("user_name", null).apply();
                            sharedPref.edit().putString("token", null).apply();
                            sharedPref.edit().putString("b_id", null).apply();
                            sharedPref.edit().putInt("id", -1).apply();
                            sharedPref.edit().putString("name", null).apply();

                            finishAffinity();
                            Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
                            startActivity(intent);
                            finish();
                        } else {

                            Toast.makeText(HomeActivity.this, "Error while logout", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<MyRes> call, Throwable t) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();

                            Toast.makeText(HomeActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }


        } else if (id == R.id.action_change_pwd) {
            Intent intent22 = new Intent(HomeActivity.this, ChangePwdActivity.class);
            startActivity(intent22);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action


            switch (role) {

                case "1":
                    AdminDashBoard adminDashBoard = new AdminDashBoard();
                    final Bundle bundle = new Bundle();
                    bundle.putString("id", empid);
                    bundle.putString("role", role);
                    bundle.putString("username", username);
                    bundle.putParcelableArrayList("branch", branchArrayList);
                    adminDashBoard.setArguments(bundle);
                    replaceFragment(adminDashBoard, role);
                    break;
                case "2":
                    MiniAdminDashboard miniAdminDashboard = new MiniAdminDashboard();
                    final Bundle bundle22 = new Bundle();
                    bundle22.putString("id", empid);
                    bundle22.putString("role", role);
                    bundle22.putString("username", username);
                    miniAdminDashboard.setArguments(bundle22);
                    replaceFragment(miniAdminDashboard, role);
                    break;
                case "3":
                    TeamLeaderDashboardFragment teamLeaderDashboardFragment = new TeamLeaderDashboardFragment();
                    final Bundle bundle33 = new Bundle();
                    bundle33.putString("id", empid);
                    bundle33.putString("role", role);
                    bundle33.putString("username", username);
                    teamLeaderDashboardFragment.setArguments(bundle33);

                    replaceFragment(teamLeaderDashboardFragment, role);
                    break;
                case "4":
                    MarketingExecutiveDashboard marketingExecutiveDashboard = new MarketingExecutiveDashboard();
                    final Bundle bundle44 = new Bundle();
                    bundle44.putString("id", empid);
                    bundle44.putString("role", role);
                    bundle44.putString("username", username);
                    marketingExecutiveDashboard.setArguments(bundle44);
                    replaceFragment(marketingExecutiveDashboard, role);
                    break;

                case "5":
                    //TODO dealer

                    DealerFragment dealerFragment = new DealerFragment();
                    final Bundle bundle55 = new Bundle();
                    bundle55.putString("id", empid);
                    bundle55.putString("role", role);
                    bundle55.putString("username", username);
                    dealerFragment.setArguments(bundle55);
                    replaceFragment(dealerFragment, role);
                    break;


                case "6":
                    //TODo Operation ex

                    OperationExFragment operationExFragment = new OperationExFragment();
                    final Bundle bundle66 = new Bundle();
                    bundle66.putString("id", empid);
                    bundle66.putString("role", role);
                    bundle66.putString("username", username);
                    operationExFragment.setArguments(bundle66);
                    replaceFragment(operationExFragment, role);
                    break;

                case "7":
                    //TODO operation TL
                    OperationTLFragment operationTLFragment = new OperationTLFragment();
                    final Bundle bundle77 = new Bundle();
                    bundle77.putString("id", empid);
                    bundle77.putString("role", role);
                    bundle77.putString("username", username);
                    operationTLFragment.setArguments(bundle77);
                    replaceFragment(operationTLFragment, role);

                    break;

                case "8":
                    //TODO operation ME
                    OperationMEFragment operationMEFragment = new OperationMEFragment();
                    final Bundle bundle88 = new Bundle();
                    bundle88.putString("id", empid);
                    bundle88.putString("role", role);
                    bundle88.putString("username", username);
                    operationMEFragment.setArguments(bundle88);
                    replaceFragment(operationMEFragment, role);

                    break;


            }


        } else if (id == R.id.nav_gallery) {

            ECSApprovalListFragment ecsApprovalListFragment = new ECSApprovalListFragment();
            final Bundle bundle88 = new Bundle();
            bundle88.putString("id", empid);
            bundle88.putString("role", role);
            bundle88.putString("username", username);
            ecsApprovalListFragment.setArguments(bundle88);
            replaceFragment(ecsApprovalListFragment, role);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment toReplace, String role) {


        switch (role) {

            case "1":
                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(AdminDashBoard.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(AdminDashBoard.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()))
                            .commit();
                }


                break;
            case "2":


                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(MiniAdminDashboard.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(MiniAdminDashboard.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(MiniAdminDashboard.class.getSimpleName()))
                            .commit();
                }


                break;
            case "3":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(TeamLeaderDashboardFragment.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(TeamLeaderDashboardFragment.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()))
                            .commit();
                }

                break;
            case "4":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(MarketingExecutiveDashboard.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(MarketingExecutiveDashboard.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()))
                            .commit();
                }

                break;


            case "5":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(DealerFragment.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(DealerFragment.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(DealerFragment.class.getSimpleName()))
                            .commit();
                }

                break;


            case "6":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(OperationExFragment.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(OperationExFragment.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(OperationExFragment.class.getSimpleName()))
                            .commit();
                }

                break;

            case "7":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(OperationTLFragment.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(OperationTLFragment.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(OperationTLFragment.class.getSimpleName()))
                            .commit();
                }

                break;


            case "8":

                if (toReplace.getClass().getSimpleName().equalsIgnoreCase(OperationMEFragment.class.getSimpleName())) {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .commit();
                } else {


                    for (int i = 0; i < getFragmentManager().getBackStackEntryCount(); i++) {

                        getFragmentManager().popBackStack();
                    }

                    getFragmentManager().beginTransaction()
                            .replace(R.id.content_home, toReplace, toReplace.getClass().getSimpleName())
                            .addToBackStack(OperationMEFragment.class.getSimpleName())
                            .hide(getFragmentManager().findFragmentByTag(OperationMEFragment.class.getSimpleName()))
                            .commit();
                }

                break;


        }


    }


    public void setToolBarTitle(String title) {
        if (getSupportActionBar() != null) {
            toolbar.setTitle(title);

        }
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment fragment = getFragmentManager().findFragmentByTag(TravellingFragment.class.getSimpleName());

        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);


    }


    private void checkPermission() {


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(HomeActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                shouldRationalpermission = true;

                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                shouldRationalpermission = true;

                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        Toast.makeText(HomeActivity.this, "Please enable all permission", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                        finishAffinity();

                    }


                    if (ContextCompat.checkSelfPermission(HomeActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {

                        Toast.makeText(HomeActivity.this, "Please enable all permission", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);


                        finishAffinity();

                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    if (!shouldRationalpermission) {

                        Toast.makeText(HomeActivity.this, "Please enable all permission", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                    }

                    finishAffinity();


                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
