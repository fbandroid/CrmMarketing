package com.crmmarketing.hmt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.Datum;
import com.crmmarketing.hmt.model.MyRes;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class ViewVolumeDetailActivity extends AppCompatActivity {


    private ScrollView topContainer;
    private TextView tvName;
    private TextView tvCRPR;
    private TextInputLayout txtInRemark;
    private TextInputEditText edtRemark;
    private TextInputLayout txtInNoOfDays;
    private Button btnSubmitFollowup;
    private ScrollView BottomContainer;
    private TextView tvStatus;
    private TextView tvNameOfCustomer;
    private TextView tvTSBCode;
    private TextView tvRemark;
    private TextInputEditText edtFollowRemark;
    private RadioGroup rgStatus;
    private RadioButton rbComplete;
    private RadioButton rbCancel;
    private Button btnUpdateFollowUp;
    private Datum datum;
    private TextInputEditText edtNoofDay;
    private ProgressBar progressBar;
    private int type;

    private RetrofitClient.CheckFollowUPStatus checkFollowUPStatus;
    private RetrofitClient.SubmitFollowUp submitFollowUp;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_volume_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar = findViewById(R.id.progressBar);

        checkFollowUPStatus = RetrofitClient.checkStatus(Constants.BASE_URL);
        submitFollowUp = RetrofitClient.submitFollowUp(Constants.BASE_URL);

        if (getIntent() != null) {

            datum = getIntent().getParcelableExtra("data");
            type = getIntent().getIntExtra("type", 0);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        topContainer = findViewById(R.id.topContainer);
        tvName = findViewById(R.id.tvName);
        tvCRPR = findViewById(R.id.tvCRPR);
        txtInRemark = findViewById(R.id.txtInRemark);
        edtRemark = findViewById(R.id.edtRemark);
        txtInNoOfDays = findViewById(R.id.txtInNoOfDays);
        btnSubmitFollowup = findViewById(R.id.btnSubmitFollowup);
        BottomContainer = findViewById(R.id.BottomContainer);
        edtNoofDay = findViewById(R.id.edtNoOfDay);
        tvStatus = findViewById(R.id.tvStatus);
        tvNameOfCustomer = findViewById(R.id.tvNameOfCustomer);
        tvTSBCode = findViewById(R.id.tvTSBCode);
        tvRemark = findViewById(R.id.tvRemark);
        edtFollowRemark = findViewById(R.id.edtFollowRemark);
        rgStatus = findViewById(R.id.rgStatus);
        rbComplete = findViewById(R.id.rbComplete);
        rbCancel = findViewById(R.id.rbCancel);
        btnUpdateFollowUp = findViewById(R.id.btnUpdateFollowUp);


        if (datum != null) {

            checkFollowUPStatus.checkfollowUpStatus(datum.getId(), String.valueOf(type)).enqueue(new Callback<MyRes>() {
                @Override
                public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                    if (response.isSuccessful()) {

                        progressBar.setVisibility(View.GONE);

                        if (response.body().getStatus().equals("0")) {

                            topContainer.setVisibility(View.VISIBLE);
                            BottomContainer.setVisibility(View.GONE);

                            tvName.setText(datum.getName());

                            if (datum.getCr_amount() != null && !datum.getCr_amount().isEmpty()) {
                                tvCRPR.setText("Credit Amount: " + datum.getCr_amount());
                            } else if (datum.getAging_debit() != null && !datum.getAging_debit().isEmpty()) {

                                tvCRPR.setText("Aging Debit: " + datum.getAging_debit());
                            }

                            submitFollowUp();


                        } else if (response.body().getStatus().equals("1")) {
                            topContainer.setVisibility(View.GONE);
                            BottomContainer.setVisibility(View.VISIBLE);
                            viewFollowUp("1");
                        } else if (response.body().getStatus().equals("2")) {
                            topContainer.setVisibility(View.GONE);
                            BottomContainer.setVisibility(View.VISIBLE);
                            viewFollowUp("2");
                        } else if (response.body().getStatus().equals("3")) {
                            topContainer.setVisibility(View.GONE);
                            BottomContainer.setVisibility(View.VISIBLE);
                            viewFollowUp("3");
                        }

                    }

                }

                @Override
                public void onFailure(Call<MyRes> call, Throwable t) {

                    Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                    finish();
                }
            });


        }


    }


    private void submitFollowUp() {

        btnSubmitFollowup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String remark = edtRemark.getText().toString().trim();
                String noOfDay = edtNoofDay.getText().toString().trim();


                if (!remark.isEmpty() && !noOfDay.isEmpty()) {

                    // TODO submit follow up

                    showProgressDialog();

                    submitFollowUp.submitFollow(datum.getId(), String.valueOf(type),
                            remark, noOfDay, "", "1").enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {
                                    Toast.makeText(ViewVolumeDetailActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
                                    finish();
                                } else {
                                    Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                                    finish();
                                }


                            } else {
                                Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                                finish();
                            }


                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            if (progressDialog != null && progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });

                }

            }
        });


    }


    private void viewFollowUp(String status) {

        switch (status) {

            case "1":

                tvNameOfCustomer.setText(datum.getName());
                tvTSBCode.setText(datum.getCode());
                if (datum.getCr_amount() != null && !datum.getCr_amount().isEmpty()) {
                    tvRemark.setText("Credit Amount: " + datum.getCr_amount());
                } else if (datum.getAging_debit() != null && !datum.getAging_debit().isEmpty()) {

                    tvRemark.setText("Aging Debit: " + datum.getAging_debit());
                }


                btnUpdateFollowUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showProgressDialog();


                        String status;

                        if (rgStatus.getCheckedRadioButtonId() == R.id.rbComplete) {

                            status = "2"; // complete

                        } else {
                            status = "3"; // canceled
                        }


                        submitFollowUp.submitFollow(datum.getId(), String.valueOf(type),
                                edtFollowRemark.getText().toString().trim(), "", status, "2").enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        Toast.makeText(ViewVolumeDetailActivity.this, "Successfully added", Toast.LENGTH_LONG).show();
                                        finish();
                                    } else {
                                        Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                                        finish();
                                    }


                                } else {
                                    Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                                    finish();
                                }


                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                if (progressDialog != null && progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(ViewVolumeDetailActivity.this, "Connection error", Toast.LENGTH_LONG).show();
                                finish();
                            }
                        });


                    }
                });


                break;

            case "2":
                tvNameOfCustomer.setText(datum.getName());
                tvTSBCode.setText(datum.getCode());
                if (datum.getCr_amount() != null && !datum.getCr_amount().isEmpty()) {
                    tvRemark.setText("Credit Amount: " + datum.getCr_amount());
                } else if (datum.getAging_debit() != null && !datum.getAging_debit().isEmpty()) {

                    tvRemark.setText("Aging Debit: " + datum.getAging_debit());
                }

                // rgStatus.setVisibility(View.GONE);
                rbComplete.setChecked(true);
                rbComplete.setEnabled(false);
                rbCancel.setEnabled(false);
                edtFollowRemark.setVisibility(View.GONE);
                btnUpdateFollowUp.setVisibility(View.GONE);
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("Completed..");


                break;

            case "3":
                tvNameOfCustomer.setText(datum.getName());
                tvTSBCode.setText(datum.getCode());
                if (datum.getCr_amount() != null && !datum.getCr_amount().isEmpty()) {
                    tvRemark.setText("Credit Amount: " + datum.getCr_amount());
                } else if (datum.getAging_debit() != null && !datum.getAging_debit().isEmpty()) {

                    tvRemark.setText("Aging Debit: " + datum.getAging_debit());
                }

                // rgStatus.setVisibility(View.GONE);
                btnUpdateFollowUp.setVisibility(View.GONE);
                edtFollowRemark.setVisibility(View.GONE);
                rbCancel.setChecked(true);
                rbComplete.setEnabled(false);
                rbCancel.setEnabled(false);
                tvStatus.setVisibility(View.VISIBLE);
                tvStatus.setText("Canceled..");
                break;


        }


    }


    public void showProgressDialog() {
        progressDialog = new ProgressDialog(ViewVolumeDetailActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


}
