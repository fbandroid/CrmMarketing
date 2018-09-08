package com.crmmarketing.hmt;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.Branch;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.database.PostsDatabaseHelper;
import com.crmmarketing.hmt.model.User;
import com.crmmarketing.hmt.webservice.WSLogin;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 420;
    private TextInputEditText edtName;
    private TextInputEditText edtPwd;
    private Button button;
    private ProgressDialog progressDialog;
    private ScrollView parentView;
    private String response;
    private Spinner spBranchName;
    private ArrayList<Branch> branchArrayList;
    private String[] branchName;
    private String b_id;
    private String b_name;
    private String token;
    private HashMap<String, Boolean> permissionHashMap = new HashMap<String, Boolean>();
    private boolean shouldRationalpermission=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtName = (TextInputEditText) findViewById(R.id.username);
        edtPwd = (TextInputEditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.btnLogin);
        spBranchName = (Spinner) findViewById(R.id.spBranch);
        parentView = (ScrollView) findViewById(R.id.parent);


        permissionHashMap.put(Manifest.permission.ACCESS_FINE_LOCATION, false);
        permissionHashMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);

        checkPermission();

        if (getIntent() != null) {

            branchArrayList = getIntent().getParcelableArrayListExtra("branch");

            if (branchArrayList != null && branchArrayList.size() > 0) {

                branchName = new String[branchArrayList.size()];

                for (int i = 0; i < branchArrayList.size(); i++) {


                    branchName[i] = branchArrayList.get(i).getName();


                }

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_spinner_item, branchName);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // attaching data adapter to spinner
                spBranchName.setAdapter(dataAdapter);

            }

        }

        spBranchName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                for (int i = 0; i < branchArrayList.size(); i++) {

                    if (parent.getItemAtPosition(position).toString().equals(branchArrayList.get(i).getName())) {

                        b_id = branchArrayList.get(i).getId();
                        b_name = branchArrayList.get(i).getName();
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        User user=new User();
//        user.setUserName("admin");
//        user.setPwd("123456");
//        PostsDatabaseHelper postsDatabaseHelper=PostsDatabaseHelper.getInstance(getApplicationContext());
//        postsDatabaseHelper.addOrUpdateUser(user);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //TODO check validation and login web service for get login as admin or normal user

                isValidLogin(edtName.getText().toString().trim(), edtPwd.getText().toString().trim());
            }
        });


    }


    boolean isValidLogin(String username, String password) {

        if (username.trim().isEmpty()) {
            edtName.requestFocus();
            Utils.showSnackBar(parentView, "Please enter valid UserName.", LoginActivity.this);
            return false;
        } else if (password.trim().isEmpty()) {
            edtPwd.requestFocus();
            Utils.showSnackBar(parentView, "Please enter valid password.", LoginActivity.this);
            return false;
        } else {


            if (Utils.checkInternetConnection(LoginActivity.this)) {
                new LoginCheckTask(username, password).execute(getResources().getString(R.string.login_url));

            } else {
                Toast.makeText(LoginActivity.this, "No Internet", Toast.LENGTH_LONG).show();
            }

            return true;

        }


    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void checkPermission() {


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||

                ContextCompat.checkSelfPermission(LoginActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                shouldRationalpermission=true;

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                shouldRationalpermission=true;

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            permissionHashMap.put(Manifest.permission.ACCESS_FINE_LOCATION, true);
            permissionHashMap.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
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

                    if (ContextCompat.checkSelfPermission(LoginActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)){

                        Toast.makeText(LoginActivity.this,"Please enable all permission",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);

                        finishAffinity();

                    }



                    if (ContextCompat.checkSelfPermission(LoginActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED && !ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)){

                        Toast.makeText(LoginActivity.this,"Please enable all permission",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);


                        finishAffinity();

                    }

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    if(!shouldRationalpermission){

                        Toast.makeText(LoginActivity.this,"Please enable all permission",Toast.LENGTH_LONG).show();
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

    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String username;
        private String password;
        private String url;

        LoginCheckTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.login_url);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();

                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSLogin().postFormBody(username, password, b_id, Constants.root, url);

                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Utils.showSnackBar(parentView, "No Internet", LoginActivity.this);


            } catch (IllegalArgumentException e) {

                e.printStackTrace();
                Utils.showSnackBar(parentView, "No Internet", LoginActivity.this);

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;
            String name = null;
            String branch = null;
            int id = 0;

            // after web service call do parsing here and update ui
            try {
                if (response != null) {
                    Log.e("log from web", response);
                    JSONObject jsonObject = new JSONObject(response);
                    isValid = jsonObject.optString("msg");
                    username = jsonObject.optString("username");
                    name = jsonObject.optString("name");
                    role = jsonObject.optString("role");
                    id = jsonObject.optInt("id");
                    token = jsonObject.optString("token");
                    branch = jsonObject.optString("branch");
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            if (isValid != null) {

                if (isValid.equalsIgnoreCase("true")) {
                    SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean(edtName.getText().toString().trim(), true);
                    editor.putString("user_name", edtName.getText().toString().trim());
                    editor.putInt("id", id);
                    editor.putString("role", role);
                    editor.putString("b_id", b_id);
                    editor.putString("b_name", b_name);
                    editor.putString("token", token);
                    editor.putString("name", name);
                    editor.putString("branch", branch);
                    editor.apply();


                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("role", role);
                    intent.putExtra("username", username);
                    intent.putExtra("name", name);
                    intent.putParcelableArrayListExtra("branch", branchArrayList);
                    intent.putExtra("id", id);

                    startActivity(intent);
                    finish();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                        }
                    }, 200);


                } else if (isValid.equals("404")) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Toast.makeText(LoginActivity.this, "Not authorized", Toast.LENGTH_LONG).show();
                } else if (isValid.equalsIgnoreCase("false")) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    Toast.makeText(LoginActivity.this, "Invalid credential", Toast.LENGTH_LONG).show();
                }


            } else {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

            }


            //  parseResponce();


        }

    }


}
