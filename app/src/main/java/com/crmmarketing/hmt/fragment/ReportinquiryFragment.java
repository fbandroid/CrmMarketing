package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.MyRes;
import com.crmmarketing.hmt.webservice.WSADDInquiry;
import com.crmmarketing.hmt.webservice.WSInquiry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 28-02-2017.
 */

public class ReportinquiryFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
    private TextView tvName;
    private TextView tvPhone;
    private TextInputEditText edtRemark;
    private TextInputEditText edtDay;
    private TextView tvDate;
    private Spinner spStatus;
    private Button brnSubmit;
    private String dateOf;
    private InqMaster inqMaster;
    private Context context;
    private AlertDialog.Builder builder;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog22;
    private HashMap<String, String> hashMap;
    private String empId;
    private long lastclick;
    private String role;
    private RetrofitClient.setChecklist checklist;

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
        hashMap = new HashMap<>();
        checklist = RetrofitClient.setCheck(Constants.BASE_URL);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);

        empId = String.valueOf(sharedPref.getInt("id", 0));
        role = sharedPref.getString("role", "");

        for (int i = 0; i < getResources().getStringArray(R.array.status).length; i++) {

            hashMap.put(String.valueOf(i), getResources().getStringArray(R.array.status)[i]);

        }
        if (getParentFragment() != null) {

            if (getParentFragment().getArguments() != null) {

                if (getParentFragment().getArguments().getParcelable("detail") != null) {

                    inqMaster = getParentFragment().getArguments().getParcelable("detail");

                }
            }


        } else {
            inqMaster = ((InquirtDetailActivity) context).inqMaster;
        }

    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure to update status?");
// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button


                // submit inquiry json
                try {
                    final JSONObject topJsonObject = new JSONObject();

                    final JSONObject jsonObject = new JSONObject();


                    final JSONObject personal_info_json = new JSONObject();
                    personal_info_json.put("cus_name", inqMaster.getCusInfo().getCusName());
                    personal_info_json.put("cus_addr", inqMaster.getCusInfo().getCusAddr());
                    personal_info_json.put("cus_mobile", inqMaster.getCusInfo().getCusMobile());
                    personal_info_json.put("cus_land", inqMaster.getCusInfo().getCusLand());
                    personal_info_json.put("cus_email", inqMaster.getCusInfo().getCusEmail());
                    personal_info_json.put("cus_alt_email", inqMaster.getCusInfo().getCusOptEmail());
                    personal_info_json.put("cus_birth", inqMaster.getCusInfo().getCusDob());

                    personal_info_json.put("cus_occupation", inqMaster.getCusInfo().getCusOccupation());


                    JSONObject product_info = new JSONObject();

                    final JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < inqMaster.getInqChild().size(); i++) {
                        final JSONObject product_info_json = new JSONObject();
                        product_info_json.put("pr_id", inqMaster.getInqChild().get(i).getInqChildProductId());
                        product_info_json.put("pr_name", inqMaster.getInqChild().get(i).getInqChildproductName());
                        product_info_json.put("cat_id", inqMaster.getInqChild().get(i).getInqCatId());
                        product_info_json.put("cat_name", inqMaster.getInqChild().get(i).getInqCatName());
                        // product_info_json.put("pr_name", selectedArrayList.get(i).getProductName());
                        //   product_info_json.put("pr_category", selectedArrayList.get(i).getProductCategory());
                        jsonArray.put(i, product_info_json);
                    }
                    product_info.put("pr_list", jsonArray);

                    final JSONObject feedback_info_json = new JSONObject();


                    feedback_info_json.put("feed_date", dateOf);
                    feedback_info_json.put("feed_time", inqMaster.getFeedTime());
                    feedback_info_json.put("feed_remark", edtRemark.getText().toString().trim());


                    final JSONObject ref_info_json = new JSONObject();
                    ref_info_json.put("ref_name", inqMaster.getCusInfo().getCusRefName());
                    ref_info_json.put("ref_email", inqMaster.getCusInfo().getCusRefEmail());
                    ref_info_json.put("ref_phone", inqMaster.getCusInfo().getCusRefMobile());
                    ref_info_json.put("ref_code", inqMaster.getCusInfo().getCusRefCode());
                    ref_info_json.put("ref_other", inqMaster.getCusInfo().getCusRefOther());


                    jsonObject.put("personal_info", personal_info_json);
                    jsonObject.put("product_detail", product_info);
                    jsonObject.put("feedback_info", feedback_info_json);
                    jsonObject.put("ref_info", ref_info_json);
                    jsonObject.put("emp_id", empId);
                    jsonObject.put("br_id", inqMaster.getBranchId());
                    jsonObject.put("br_name", inqMaster.getBranchName());
                    jsonObject.put("pr_range", inqMaster.getInqInvestRange());
                    jsonObject.put("inq_id", inqMaster.getInqMasterId());
                    jsonObject.put("case", String.valueOf(spStatus.getSelectedItemId()));


                    final JSONObject jsonObject12 = new JSONObject();
                    jsonObject12.put("inq_id", inqMaster.getInqMasterId());
                    jsonObject12.put("remark", edtRemark.getText().toString().trim());
                    if (spStatus.getSelectedItemId() == 1) {
                        jsonObject12.put("status", String.valueOf("2"));
                    } else {
                        jsonObject12.put("status", String.valueOf(spStatus.getSelectedItemId()));
                    }

                    jsonObject12.put("status", String.valueOf(spStatus.getSelectedItemId()));
                    jsonObject12.put("date", dateOf);
                    jsonObject.put("report", jsonObject12);
                    topJsonObject.put("inquiry", jsonObject);

                    Log.e("report", jsonObject.toString().trim());


                    //gson to make json from java object

                    new LoginCheckTask(jsonObject.toString()).execute();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                    final JSONObject jsonObject12 = new JSONObject();
//                    jsonObject12.put("inq_id", inqMaster.getInqMasterId());
//                    jsonObject12.put("remark", edtRemark.getText().toString().trim());
////                    if (spStatus.getSelectedItemId() == 1) {
////                        jsonObject12.put("status", String.valueOf("2"));
////                    } else {
////                        jsonObject12.put("status", String.valueOf(spStatus.getSelectedItemId()));
////                    }
//
//                    jsonObject12.put("status",String.valueOf(spStatus.getSelectedItemId()));
//                    jsonObject12.put("date", dateOf);
//                    jsonObject.put("report", jsonObject12);
//                    Log.e("report", jsonObject.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }


            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Set other dialog properties


// Create the AlertDialog


        return inflater.inflate(R.layout.fragment_report_inquiry, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvName = (TextView) view.findViewById(R.id.fragment__report_inquiry_tvName);
        tvPhone = (TextView) view.findViewById(R.id.fragment__report_inquiry_tvPhone);
        tvDate = (TextView) view.findViewById(R.id.fragment__report_inquiry_tvDate);
        edtRemark = (TextInputEditText) view.findViewById(R.id.fragment_report_edtRemark);
        edtDay = (TextInputEditText) view.findViewById(R.id.fragment__report_inquiry_edtDay);
        spStatus = (Spinner) view.findViewById(R.id.fragment__report_inquiry_spStatus);
        brnSubmit = (Button) view.findViewById(R.id.fragment_report_btnSubmit);

        if (role.equals("6") || role.equals("7") || role.equals("8")) {
            spStatus.setVisibility(View.GONE);
            edtDay.setVisibility(View.GONE);
            tvDate.setVisibility(View.GONE);
        }

        if (inqMaster != null) {

            tvName.setText(inqMaster.getCusInfo().getCusName());
            tvPhone.setText(inqMaster.getCusInfo().getCusMobile());

        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.status, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spStatus.setAdapter(adapter);

        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2 || position == 1) {
                    edtDay.getText().clear();
                    edtDay.setVisibility(View.GONE);
                } else {
                    edtDay.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtDay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtDay.getText().toString().trim().isEmpty()) {
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE, Integer.parseInt(edtDay.getText().toString().trim()));
                    dateOf = cal.getTime().toString();
                    tvDate.setText(dateOf);
                } else {
                    dateOf = null;
                    tvDate.setText("");
                }
            }
        });


        brnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) {
                    return;
                }


                if (role.equals("6")) {
                    // role 6 process
                    progressDialog22 = new ProgressDialog(getActivity());

                    if (!edtRemark.getText().toString().trim().isEmpty()) {


                        progressDialog22.show();
                        checklist.getCounter(inqMaster.getInqMasterId(), empId, edtRemark.getText().toString().trim())
                                .enqueue(new Callback<MyRes>() {
                                    @Override
                                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                        if (progressDialog22.isShowing()) {
                                            progressDialog22.dismiss();
                                        }

                                        if (response.isSuccessful()) {

                                            Toast.makeText(getActivity(), "Successfully remark", Toast.LENGTH_SHORT).show();
                                            getActivity().finish();


                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyRes> call, Throwable t) {
                                        if (progressDialog22.isShowing()) {
                                            progressDialog22.dismiss();
                                        }
                                        getFragmentManager().popBackStack();
                                    }
                                });


                    }


                } else {
                    if (!edtRemark.getText().toString().trim().isEmpty()) {

                        if (spStatus.getSelectedItemId() == 2 || spStatus.getSelectedItemId() == 1) {


                            updateReport();


                        } else {


                            if (Utils.isTimeAutomatic(getActivity())) {

                                if (dateOf != null) {
                                    tvDate.setText(dateOf);
                                    updateReport();
                                } else {
                                    Toast.makeText(getActivity(), "Please enter day", Toast.LENGTH_LONG).show();
                                    edtDay.requestFocus();
                                }

                            } else {
                                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);

                            }

                        }


                    } else {
                        edtRemark.requestFocus();
                        Toast.makeText(getActivity(), "Please enter remark", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });

    }


    private void updateReport() {

        AlertDialog dialog = builder.create();
        dialog.show();

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

    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String response;
        private String json;
        private String url;


        LoginCheckTask(String json) {
            this.json = json;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.remark_api);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();

                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));

                response = new WSADDInquiry(getActivity()).addReamrk(json, url);
                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();


                getFragmentManager().popBackStack();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();


            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;


            int id = 0;
            if (getActivity() != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

            // after web service call do parsing here and update ui
            try {
                if (response != null) {

                    JSONObject jsonObject = new JSONObject(response);

                    isValid = jsonObject.optString("msg");

                    if (isValid.equalsIgnoreCase("true")) {

                        if (getActivity() != null) {
                            getActivity().finish();
                            Toast.makeText(getActivity(), "Successfully Reported", Toast.LENGTH_SHORT).show();
                        }

                    } else {

                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Internal error", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        }


                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();

                getActivity().finish();
            }


        }


        public void showProgressDialog() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }
}
