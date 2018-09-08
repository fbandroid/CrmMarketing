package com.crmmarketing.hmt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwdActivity extends AppCompatActivity {
    private TextInputEditText edtOld;
    private TextInputEditText edtNew;
    private TextInputEditText edtConf;
    private Button btnSubmit;
    private RetrofitClient.APIServiceChangePwd apiServiceChangePwd;
    private String a_id;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);


        apiServiceChangePwd = RetrofitClient.changePwd(Constants.BASE_URL);

        edtOld = (TextInputEditText) findViewById(R.id.activity_change_edtOld);
        edtNew = (TextInputEditText) findViewById(R.id.activity_change_edtNew);
        edtConf = (TextInputEditText) findViewById(R.id.activity_change_edtNewCon);

        final SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);
        a_id = String.valueOf(sharedPref.getInt("id", -1));

        btnSubmit = (Button) findViewById(R.id.activity_forgot_btnSubmit);



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String old = edtOld.getText().toString().trim();
                String newPwd = edtNew.getText().toString().trim();
                String newConf = edtConf.getText().toString().trim();


                if (!edtOld.getText().toString().trim().isEmpty()) {

                    if (!edtNew.getText().toString().trim().isEmpty() && !edtConf.getText().toString().trim().isEmpty()) {


                        if (edtNew.getText().toString().trim().equals(edtConf.getText().toString().trim())) {

                            //TODO call change


                            if (Utils.checkInternetConnection(ChangePwdActivity.this)) {

                                showProgressDialog();


                                apiServiceChangePwd.changePwd(a_id,old,newPwd).enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {


                                        if(response.body().getMsg().equalsIgnoreCase("true")){


                                            sharedPref.edit().putString("user_name", null).apply();
                                            sharedPref.edit().putString("token", null).apply();
                                            sharedPref.edit().putString("b_id", null).apply();
                                            sharedPref.edit().putInt("id", -1).apply();
                                            sharedPref.edit().putString("name", null).apply();


                                            Toast.makeText(ChangePwdActivity.this,"Changed Successfully",Toast.LENGTH_LONG).show();
                                            finishAffinity();
                                            Intent intent=new Intent(getApplicationContext(),SplashActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }

                                        else if(response.body().getMsg().equalsIgnoreCase("false")){
                                            Toast.makeText(ChangePwdActivity.this,"Not changed",Toast.LENGTH_LONG).show();

                                        }



                                       Log.e("response..",response.body().getMsg());

                                        if(progressDialog.isShowing()){

                                           progressDialog.dismiss();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {

                                    }
                                });



                            } else {
                                Toast.makeText(ChangePwdActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                            }


                        } else {
                            Toast.makeText(ChangePwdActivity.this, "Enter correct password", Toast.LENGTH_LONG).show();
                            edtConf.requestFocus();
                        }

                    } else {

                        if (edtNew.getText().toString().trim().isEmpty()) {
                            Toast.makeText(ChangePwdActivity.this, "Enter New password", Toast.LENGTH_LONG).show();
                            edtNew.requestFocus();

                        } else {
                            Toast.makeText(ChangePwdActivity.this, "ReEnter password", Toast.LENGTH_LONG).show();
                            edtConf.requestFocus();
                        }


                    }


                } else {
                    edtOld.requestFocus();
                    Toast.makeText(ChangePwdActivity.this, "Enter valid password", Toast.LENGTH_LONG).show();
                }


            }
        });


    }


    public void showProgressDialog() {
        progressDialog = new ProgressDialog(ChangePwdActivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
