package com.crmmarketing.hmt;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.adapter.BookAutoCompleteAdapter;
import com.crmmarketing.hmt.database.PostsDatabaseHelper;
import com.crmmarketing.hmt.fragment.AdminDashBoard;
import com.crmmarketing.hmt.fragment.InquiryListInfoFragment;
import com.crmmarketing.hmt.fragment.MarketingExecutiveDashboard;
import com.crmmarketing.hmt.fragment.TeamLeaderDashboardFragment;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.InquiryMaster;
import com.crmmarketing.hmt.model.InquiryTransaction;
import com.crmmarketing.hmt.model.User;
import com.crmmarketing.hmt.poductmodel.Example;
import com.crmmarketing.hmt.poductmodel.PrList;
import com.crmmarketing.hmt.webservice.WSADDInquiry;
import com.crmmarketing.hmt.webservice.WSInquiry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class EditInquiryactivity extends AppCompatActivity {

    private Context context;
    private LinearLayout parentView;
    private AutoCompleteTextView edtName;
    private TextInputEditText edtEmail;
    private TextInputEditText edtMobile;
    private TextInputEditText edtPhone;
    private TextInputEditText edtAddr;
    private TextInputEditText edtFeedBackDays;
    private TextInputEditText edtOhterDetail;
    private TextInputEditText edtRefereeName;
    private TextInputEditText edtRefereeEmail;
    private TextInputEditText edtRefereePhone;
    private TextInputEditText edtSource;
    private TextInputEditText edtOptionalEmail;
    private TextInputEditText edtRefCode;
    private TextInputEditText edtPAN;
    private RadioGroup radioGroup;
    private RadioButton rbpending;
    private RadioButton rbConfirm;
    private RadioButton rbCancel;
    private RadioButton rbPwithC;
    private String caseOf;

    private Button btnSubmit;
    private Button btnDOB;
    private TextView tvDOB;
    private CustomAdapter customAdapter;
    private String dob;
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar endCalender = Calendar.getInstance();
            endCalender.set(Calendar.YEAR, year);
            endCalender.set(Calendar.MONTH, monthOfYear);
            endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


            tvDOB.setText(sdf.format(endCalender.getTime()));
            dob = sdf.format(endCalender.getTime());

        }

    };
    private AutoCompleteTextView autoCompleteTextView;
    private ArrayList<PrList> productInfoArrayList;
    private RecyclerView recyclerView;
    private ArrayList<PrList> selectedArrayList;

    private Spinner spRange;
    private Spinner spOccupation;
    private ProgressDialog progressDialog;
    private long inq_mas_id = -1;
    private Spinner spProduct;
    private ArrayList<InqMaster> inqMasterArrayList;
    private HashMap<String, List<InqMaster>> hashMap;
    private String role;
    private String id;
    private String b_id;
    private String b_name;
    private InqMaster inqmaster;
    private TextInputEditText edtTime;
    private TextInputEditText edtAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_inquiryactivity);
        productInfoArrayList = new ArrayList<>();
        selectedArrayList = new ArrayList<>();

        if (getIntent() != null) {

            inqmaster = getIntent().getParcelableExtra("detail");


        }


        SharedPreferences sharedPref = EditInquiryactivity.this.getSharedPreferences("my", MODE_PRIVATE);
        role = sharedPref.getString("role", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        b_id = sharedPref.getString("b_id", "");
        if (b_id.equals("0")) {
            b_name = "All";
        } else {
            b_name = sharedPref.getString("b_name", "");
        }


        new getProduct().execute();

        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(EditInquiryactivity.this.getApplicationContext());
        inqMasterArrayList = postsDatabaseHelper.getAllPosts();


        parentView = (LinearLayout) findViewById(R.id.parent_view);
        edtName = (AutoCompleteTextView) findViewById(R.id.fragment_inquiry_form_edtApplicantName);
        edtAddr = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtAddr);
        edtMobile = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtMobileNo);
        edtFeedBackDays = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtFeedbackDay);
        edtEmail = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtEmail);
        edtPhone = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtPhone);
        edtOhterDetail = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtOtherDetail);
        edtRefereeEmail = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtRefereeEmail);
        edtRefereeName = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtRefereeName);
        edtRefereePhone = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtRefereePhone);
        edtSource = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtSource);
        edtOptionalEmail = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtOptionalEmail);
        edtRefCode = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtrefCose);
        edtPAN = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtPAN);
        edtTime = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtTime);
        edtAmount = (TextInputEditText) findViewById(R.id.fragment_inquiry_form_edtAmount);

        radioGroup = (RadioGroup) findViewById(R.id.rgStatus);
        rbConfirm = (RadioButton) findViewById(R.id.rbConfirm);
        rbpending = (RadioButton) findViewById(R.id.rbPending);
        rbCancel = (RadioButton) findViewById(R.id.rbCancel);
        rbPwithC = (RadioButton) findViewById(R.id.rbPwithC);
        spRange = (Spinner) findViewById(R.id.fragment_inquiry_form_spRange);
        spOccupation = (Spinner) findViewById(R.id.fragment_inquiry_form_spOccupation);
        tvDOB = (TextView) findViewById(R.id.fragment_inquiry_form_tvBirth);
        btnDOB = (Button) findViewById(R.id.fragment_inquiry_form_btnDatePick);
        btnSubmit = (Button) findViewById(R.id.fragment_inquiry_form_btnSubmit);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (group.getCheckedRadioButtonId() == R.id.rbPending) {

                    caseOf = "0";

                    edtAmount.setEnabled(true);
                    recyclerView.setVisibility(View.VISIBLE);
                    autoCompleteTextView.setEnabled(true);
                } else if (group.getCheckedRadioButtonId() == R.id.rbConfirm) {

                    edtAmount.setEnabled(false);
                    recyclerView.setVisibility(View.GONE);
                    autoCompleteTextView.setEnabled(false);

                    caseOf = "1";
                } else if (group.getCheckedRadioButtonId() == R.id.rbCancel) {

                    edtAmount.setEnabled(false);
                    recyclerView.setVisibility(View.GONE);
                    autoCompleteTextView.setEnabled(false);

                    caseOf = "2";
                } else if (group.getCheckedRadioButtonId() == R.id.rbPwithC) {

                    edtAmount.setEnabled(false);
                    recyclerView.setVisibility(View.GONE);
                    autoCompleteTextView.setEnabled(false);

                    caseOf = "3";
                }

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.checkInternetConnection(EditInquiryactivity.this)) {
                    showAlertDialog();
                } else {
                    Toast.makeText(EditInquiryactivity.this, "No Internet", Toast.LENGTH_LONG).show();
                }


            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.fragment_inquiry_form_rvProductAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(EditInquiryactivity.this, LinearLayoutManager.HORIZONTAL, false));


        if (inqmaster != null) {

            edtName.setText(inqmaster.getCusInfo().getCusName());
            edtAddr.setText(inqmaster.getCusInfo().getCusAddr());
            edtEmail.setText(inqmaster.getCusInfo().getCusEmail());
            edtMobile.setText(inqmaster.getCusInfo().getCusMobile());
            tvDOB.setText(inqmaster.getCusInfo().getCusDob());
            edtFeedBackDays.setText(inqmaster.getInqFeedbackTime());
            edtTime.setText(inqmaster.getFeedTime());
            edtSource.setText(inqmaster.getInqRemark());
            edtAmount.setText(inqmaster.getInqInvestRange());
            edtRefereeName.setText(inqmaster.getCusInfo().getCusRefName());
            edtRefereeEmail.setText(inqmaster.getCusInfo().getCusRefEmail());
            edtRefereePhone.setText(inqmaster.getCusInfo().getCusRefMobile());


        }

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.fragment_inquiry_form_ProductAutoComplete);
        //
        //
        //
        // autoCompleteTextsetAdapter(new BookAutoCompleteAdapter(EditInquiryactivity.this, productInfoArrayList));
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PrList productInfo = (PrList) adapterView.getItemAtPosition(position);
                if (!productInfo.isAdded()) {
                    selectedArrayList.add(productInfo);
                    customAdapter = new CustomAdapter(selectedArrayList, productInfoArrayList);
                    recyclerView.setAdapter(customAdapter);
                    autoCompleteTextView.getText().clear();
                } else {
                    autoCompleteTextView.getText().clear();
                }

            }
        });


        if (inqmaster.getStausInq().equals("1") || inqmaster.getStausInq().equals("2") || inqmaster.getStausInq().equals("3")) {
//            radioGroup.setVisibility(View.VISIBLE);


            if (inqmaster.getStausInq().equals("1")) {
                rbConfirm.setChecked(true);

            } else if (inqmaster.getStausInq().equals("2")) {
                rbCancel.setChecked(true);


            } else if (inqmaster.getStausInq().equals("0")) {
                rbpending.setChecked(true);
            } else if (inqmaster.getStausInq().equals("3")) {
                rbPwithC.setChecked(true);
            }


            edtAmount.setEnabled(false);
            autoCompleteTextView.setEnabled(false);
            recyclerView.setVisibility(View.GONE);


        }


        final ArrayList<String> stringArrayList = (ArrayList<String>) postsDatabaseHelper.getCusName();
        final String[] array = stringArrayList.toArray(new String[stringArrayList.size()]);
        //edt name autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (EditInquiryactivity.this, android.R.layout.select_dialog_item, array);

        edtName.setThreshold(2);
        edtName.setAdapter(adapter);

        edtName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String username = (String) parent.getItemAtPosition(position);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!edtMobile.getText().toString().trim().isEmpty() && !edtName.getText().toString().trim().isEmpty()) {

                    if (inqMasterArrayList != null) {
                        for (int i = 0; i < inqMasterArrayList.size(); i++) {

                            if (edtName.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusName()) && edtMobile.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusMobile()) && edtEmail.getText().toString().trim().equals(inqMasterArrayList.get(i).getCusInfo().getCusEmail())) {

                                edtAddr.setText(inqMasterArrayList.get(i).getCusInfo().getCusAddr());
                                edtPhone.setText(inqMasterArrayList.get(i).getCusInfo().getCusLand());
                                break;
                            }
                        }
                    }


                }
            }
        });

        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtMobile.getText().toString().trim().isEmpty() && !edtName.getText().toString().trim().isEmpty()) {

                    if (inqMasterArrayList != null) {
                        for (int i = 0; i < inqMasterArrayList.size(); i++) {

                            if (edtName.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusName()) && edtMobile.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusMobile()) && edtEmail.getText().toString().trim().equals(inqMasterArrayList.get(i).getCusInfo().getCusEmail())) {

                                edtAddr.setText(inqMasterArrayList.get(i).getCusInfo().getCusAddr());
                                edtPhone.setText(inqMasterArrayList.get(i).getCusInfo().getCusLand());
                                break;
                            }
                        }
                    }


                }
            }
        });

        edtFeedBackDays.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String customerFeedBack = "";
//               if(!edtFeedBackDays.getText().toString().trim().isEmpty()){
//                   customerFeedBack = edtFeedBackDays.getText().toString().trim();
//                   Calendar cal = Calendar.getInstance();
//                   cal.add(Calendar.DATE, Integer.parseInt(customerFeedBack));
//                   customerFeedBack=cal.getTime().toString();
//
//                   Toast.makeText(EditInquiryactivity.this,customerFeedBack,Toast.LENGTH_LONG).show();
//               }
            }
        });


        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!edtMobile.getText().toString().trim().isEmpty() && !edtName.getText().toString().trim().isEmpty()) {

                    if (inqMasterArrayList != null) {
                        for (int i = 0; i < inqMasterArrayList.size(); i++) {

                            if (edtName.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusName()) && edtMobile.getText().toString().equals(inqMasterArrayList.get(i).getCusInfo().getCusMobile()) && edtEmail.getText().toString().trim().equals(inqMasterArrayList.get(i).getCusInfo().getCusEmail())) {

                                edtAddr.setText(inqMasterArrayList.get(i).getCusInfo().getCusAddr());
                                edtPhone.setText(inqMasterArrayList.get(i).getCusInfo().getCusLand());
                                break;
                            }
                        }
                    }


                }


            }
        });


        ArrayAdapter<CharSequence> adapterRange = ArrayAdapter.createFromResource(EditInquiryactivity.this,
                R.array.range, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spRange.setAdapter(adapterRange);


        ArrayAdapter<CharSequence> adapterOccupation = ArrayAdapter.createFromResource(EditInquiryactivity.this,
                R.array.occupation, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterOccupation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spOccupation.setAdapter(adapterOccupation);


        spRange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spOccupation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(EditInquiryactivity.this, endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit_form) {
            XYZFormSubmit();
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(EditInquiryactivity.this);
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void XYZFormSubmit() {


        final String[] transactionId = new String[selectedArrayList.size()];
        final String customerName = edtName.getText().toString().trim();
        final String customerAddr = edtAddr.getText().toString().trim();
        final String customerMobile = edtMobile.getText().toString().trim();
        final String customerEmail = edtEmail.getText().toString().trim();
        final String customerLandLine = edtPhone.getText().toString().trim();
        final String customerOccupation = spOccupation.getSelectedItem().toString().trim();
        final String customerOptionalEmail = edtOptionalEmail.getText().toString().trim();
        final String customerPAN = edtPAN.getText().toString().trim();
        if (dob != null) {
            final String customerBirth = dob.trim();
        }
//        String customerFeedBack = "";
//        if (!edtFeedBackDays.getText().toString().trim().isEmpty()) {
//            customerFeedBack = edtFeedBackDays.getText().toString().trim();
//            Calendar cal = Calendar.getInstance();
//            cal.add(Calendar.DATE, Integer.parseInt(customerFeedBack));
//            customerFeedBack = cal.getTime().toString();
//
//        }

        final String feedTime = edtTime.getText().toString().trim();
        final String customerOther = edtOhterDetail.getText().toString().trim();
        final String customerRefereeName = edtRefereeName.getText().toString().trim();
        final String customerRefereeEmail = edtRefereeEmail.getText().toString().trim();
        final String customerRefereePhone = edtRefereePhone.getText().toString().trim();
        final String customerSource = edtSource.getText().toString().trim();
        final String customerRange = edtAmount.getText().toString().trim();
        final String customerRefCode = edtRefCode.getText().toString().trim();
        final String customerFeedBack = edtFeedBackDays.getText().toString().trim();

        if (customerName.isEmpty()) {


            Utils.showSnackBar(parentView, "Please enter customer name", EditInquiryactivity.this);
            edtName.requestFocus();


        } else if (customerAddr.isEmpty()) {


            Utils.showSnackBar(parentView, "Please enter customer Address", EditInquiryactivity.this);
            edtAddr.requestFocus();
        } else if (customerMobile.isEmpty() || customerMobile.length() < 10) {


            Utils.showSnackBar(parentView, "Please enter customer Mobile No", EditInquiryactivity.this);
            edtMobile.requestFocus();
        } else if (!Utils.isValidEmail(customerEmail)) {


            Utils.showSnackBar(parentView, "Please enter customer Email", EditInquiryactivity.this);
            edtEmail.requestFocus();
        } else if (!customerOptionalEmail.isEmpty() && !Utils.isValidEmail(customerOptionalEmail)) {

            Utils.showSnackBar(parentView, "Please enter valid Email", EditInquiryactivity.this);
            edtOptionalEmail.requestFocus();
        } else if (!edtPAN.getText().toString().trim().isEmpty() && !Utils.isValidPAN(edtPAN.getText().toString().trim())) {
            Utils.showSnackBar(parentView, "Please enter Valid PAN NO", EditInquiryactivity.this);
            edtPAN.requestFocus();
        } else if (tvDOB.getText().toString().trim().isEmpty()) {

            Utils.showSnackBar(parentView, "Please enter customer BirhtDate", EditInquiryactivity.this);
            tvDOB.requestFocus();
        } else if (selectedArrayList.size() < 1) {

            Utils.showSnackBar(parentView, "Please select One Product", EditInquiryactivity.this);
            autoCompleteTextView.requestFocus();
        } else try {
            if (!checkYYYYmmDD(customerFeedBack)) {


                Utils.showSnackBar(parentView, "Please enter Date in dd-mm-yyyy format", EditInquiryactivity.this);
                edtFeedBackDays.requestFocus();


            } else if (!customerRefereeEmail.isEmpty() && !Utils.isValidEmail(customerRefereeEmail)) {

                Utils.showSnackBar(parentView, "Please enter Valid Email", EditInquiryactivity.this);
                edtRefereeEmail.requestFocus();

            } else {


                if (Utils.isTimeAutomatic(EditInquiryactivity.this)) {

                    final User user = new User();
                    user.setUserName(customerName);
                    user.setUserAddr(customerAddr);
                    user.setUserBirth(tvDOB.getText().toString().trim());
                    user.setUserMobileNo(customerMobile);
                    user.setUserEmail(customerEmail);
                    user.setOccupation(customerOccupation);
                    user.setUserRange(customerRange);
                    user.setUserLandlineNo(customerLandLine);
                    user.setRefereeEmail(customerRefereeEmail);
                    user.setRefereeMobile(customerRefereePhone);
                    user.setRefereeName(customerRefereeName);
                    user.setSourceName(customerSource);
                    user.setPan(customerPAN);
                    user.setUserComment(customerOther);

                    user.setUserOptionalEmail(customerOptionalEmail);

                    PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(EditInquiryactivity.this.getApplicationContext());

                    final long cus_id = postsDatabaseHelper.addOrUpdateUser(user);

                    SharedPreferences sharedPref = EditInquiryactivity.this.getSharedPreferences("my", Context.MODE_PRIVATE);
                    String username = sharedPref.getString("user_name", null);
                    int id = sharedPref.getInt("id", 0);
                    showProgressDialog();
                    if (cus_id != -1) {
                        user.setUserId(String.valueOf(cus_id));


                        final InquiryMaster inquiryMaster = new InquiryMaster();
                        inquiryMaster.setKEY_INQUIRY_MASTER_CUSTOMER_ID_FK(String.valueOf(cus_id));
                        inquiryMaster.setKEY_INQUIRY_MASTER_FEEDBACK_TIME(customerFeedBack);
                        inquiryMaster.setKEY_INQUIRY_MASTER_INVEST_RANGE(customerRange);
                        inquiryMaster.setKEY_INQUIRY_MASTER_TIMESTAMP(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                        inquiryMaster.setKEY_INQUIRY_MASTER_USER_ID_FK(username);
                        inquiryMaster.setKEY_INQUIRY_MASTER_INVEST_RANGE(customerRange);
                        inquiryMaster.setKEY_INQUIRY_MASTER_ID(String.valueOf(inq_mas_id));
                        inquiryMaster.setFeedTime(feedTime);


                        inq_mas_id = postsDatabaseHelper.addOrUpdateInquiryMaster(inquiryMaster);

                        if (inq_mas_id != -1) {
                            inquiryMaster.setKEY_INQUIRY_MASTER_ID(String.valueOf(inq_mas_id));

                            for (int i = 0; i < selectedArrayList.size(); i++) {
                                final InquiryTransaction inquiryTransaction = new InquiryTransaction();
                                inquiryTransaction.setKEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK(String.valueOf(inq_mas_id));
                                inquiryTransaction.setKEY_INQUIRY_TRANSACTION_PRODUCT_ID(selectedArrayList.get(i).getPrdId());
                                inquiryTransaction.setKEY_INQUIRY_TRANSACTION_TIMESTAMP(String.valueOf(Calendar.getInstance().getTimeInMillis()));
                                final long transaction_id = postsDatabaseHelper.addOrUpdateInquiryTransaction(inquiryTransaction);
                                if (transaction_id != -1) {

                                    inquiryTransaction.setKEY_INQUIRY_TRANSACTION_ID(String.valueOf(transaction_id));
                                    transactionId[i] = String.valueOf(transaction_id);
                                }
                            }


                        }

                    }

                    // service call

                    try {
                        final JSONObject topJsonObject = new JSONObject();

                        final JSONObject jsonObject = new JSONObject();


                        final JSONObject personal_info_json = new JSONObject();
                        personal_info_json.put("cus_name", customerName);
                        personal_info_json.put("cus_addr", customerAddr);
                        personal_info_json.put("cus_mobile", customerMobile);
                        personal_info_json.put("cus_land", customerLandLine);
                        personal_info_json.put("cus_email", customerEmail);
                        personal_info_json.put("cus_alt_email", customerOptionalEmail);
                        personal_info_json.put("cus_birth", tvDOB.getText().toString().trim());
                        personal_info_json.put("pan", customerPAN);
                        personal_info_json.put("cu_id", inqmaster.getCusInfo().getCusId());
                        personal_info_json.put("cus_occupation", customerOccupation);


                        JSONObject product_info = new JSONObject();

                        final JSONArray jsonArray = new JSONArray();
                        for (int i = 0; i < selectedArrayList.size(); i++) {
                            final JSONObject product_info_json = new JSONObject();
                            product_info_json.put("pr_id", selectedArrayList.get(i).getPrdId());
                            product_info_json.put("pr_name", selectedArrayList.get(i).getPrdName());
                            product_info_json.put("cat_id", selectedArrayList.get(i).getPrdCategoryid());
                            product_info_json.put("cat_name", selectedArrayList.get(i).getPcatName());
                            // product_info_json.put("pr_name", selectedArrayList.get(i).getProductName());
                            //   product_info_json.put("pr_category", selectedArrayList.get(i).getProductCategory());
                            jsonArray.put(i, product_info_json);
                        }
                        product_info.put("pr_list", jsonArray);


                        final JSONObject feedback_info_json = new JSONObject();
                        feedback_info_json.put("feed_date", customerFeedBack);
                        feedback_info_json.put("feed_remark", customerSource);
                        feedback_info_json.put("feed_time", feedTime);

                        final JSONObject ref_info_json = new JSONObject();
                        ref_info_json.put("ref_name", customerRefereeName);
                        ref_info_json.put("ref_email", customerRefereeEmail);
                        ref_info_json.put("ref_phone", customerRefereePhone);
                        ref_info_json.put("ref_code", customerRefCode);
                        ref_info_json.put("ref_other", customerOther);


                        jsonObject.put("personal_info", personal_info_json);
                        jsonObject.put("product_detail", product_info);
                        jsonObject.put("feedback_info", feedback_info_json);
                        jsonObject.put("ref_info", ref_info_json);
                        jsonObject.put("emp_id", String.valueOf(id));
                        jsonObject.put("br_id", b_id);
                        jsonObject.put("br_name", b_name);
                        jsonObject.put("inq_id", inqmaster.getInqMasterId());
                        jsonObject.put("pr_range", customerRange);
                        jsonObject.put("case", caseOf);

                        topJsonObject.put("inquiry", jsonObject);


                        //gson to make json from java object

                        if (Utils.checkInternetConnection(EditInquiryactivity.this)) {
                            new InsertInquiry(topJsonObject, String.valueOf(inq_mas_id), transactionId).execute();

                        } else {

                            if (EditInquiryactivity.this != null) {
                                Toast.makeText(EditInquiryactivity.this, "No Internet", Toast.LENGTH_LONG).show();
                            }
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                    Toast.makeText(EditInquiryactivity.this, "Please make time and date Automatic", Toast.LENGTH_LONG).show();
                }


            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(EditInquiryactivity.this, "Please enter date in yyyy-mm-dd", Toast.LENGTH_LONG).show();
            edtFeedBackDays.requestFocus();
        }


    }

    boolean checkYYYYmmDD(String feedBackDate) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
        String after = df.format(df.parse(feedBackDate));
        if (after.equals(feedBackDate)) {
            // validated
            return true;
        } else {
            return false;
        }
    }

    public void showAlertDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(EditInquiryactivity.this);


        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                XYZFormSubmit();

            }


        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass


            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private class getProduct extends AsyncTask<String, Void, String> {

        private String response = null;
        private String url;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.pr_list);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = new WSInquiry().getInquiry(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            Gson gson = new Gson();

            if (response != null) {

                Example example = gson.fromJson(response, Example.class);
                productInfoArrayList = (ArrayList<PrList>) example.getPrList();
                if (productInfoArrayList != null) {
                    autoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(EditInquiryactivity.this, productInfoArrayList));


                    for (int i = 0; i < inqmaster.getInqChild().size(); i++) {

                        for (int j = 0; j < productInfoArrayList.size(); j++) {

                            if (inqmaster.getInqChild().get(i).getInqChildProductId().equals(productInfoArrayList.get(j).getPrdId())) {

                                selectedArrayList.add(productInfoArrayList.get(j));
                            }
                        }


                    }


                    customAdapter = new CustomAdapter(selectedArrayList, productInfoArrayList);
                    recyclerView.setAdapter(customAdapter);
                }

            } else {
                if (EditInquiryactivity.this != null) {
                    Toast.makeText(EditInquiryactivity.this, "Unable to connect", Toast.LENGTH_LONG).show();
                }

            }
//


            if (EditInquiryactivity.this != null) {
                progressDialog.dismiss();
            }

        }
    }

    private class InsertInquiry extends AsyncTask<String, Void, String> {

        String response = null;
        private JSONObject jsonObject;
        private String masterId;
        private String[] childId;

        InsertInquiry(JSONObject jsonObject, String masterId, String[] transactionId) {

            this.jsonObject = jsonObject;
            this.masterId = masterId;
            this.childId = transactionId;
            Log.e("inquiry", jsonObject.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(EditInquiryactivity.this,getResources().getString(R.string.login_url),username,password).postLoginData();

                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSADDInquiry(EditInquiryactivity.this).editInquiry(jsonObject.toString());
                progressDialog.dismiss();

                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                Utils.showSnackBar(parentView, "No Internet", EditInquiryactivity.this);
                progressDialog.dismiss();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();

                Utils.showSnackBar(parentView, "No Internet", EditInquiryactivity.this);


            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String success;
            progressDialog.dismiss();
            // after web service call do parsing here and update ui
            try {
                if (response != null) {
                    JSONObject jsonObject22 = new JSONObject(response);
                    success = jsonObject22.optString("msg");


                    if (success.equalsIgnoreCase("true")) {

                        Toast.makeText(EditInquiryactivity.this, "Edited Successfully", Toast.LENGTH_LONG).show();

                        finish();


                    }
                } else {

                    Toast.makeText(EditInquiryactivity.this, "internal error", Toast.LENGTH_LONG).show();
                    finish();


                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


            //  parseResponce();


        }


    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<PrList> mDataSet;
        private ArrayList<PrList> sourceArrayList;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<PrList> dataSet, ArrayList<PrList> sourceArrayList) {
            mDataSet = dataSet;
            this.sourceArrayList = sourceArrayList;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_chip_layout, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            // Get element from your dataset at this position and replace the contents of the view
            // with that element

            mDataSet.get(position).setAdded(true);
            viewHolder.getTextView().setText(mDataSet.get(position).getPrdName());
            viewHolder.textViewCat.setText(mDataSet.get(position).getPcatName());

        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        public void setFilter(ArrayList<PrList> prLists) {
            this.sourceArrayList = prLists;
            notifyDataSetChanged();


        }

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final ImageView ivDelete;
            private final TextView textViewCat;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    }
                });
                textView = (TextView) v.findViewById(R.id.row_chip_tvName);
                ivDelete = (ImageView) v.findViewById(R.id.row_chip_ivDelete);
                textViewCat = (TextView) v.findViewById(R.id.row_chip_tvCatName);

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sourceArrayList.get(sourceArrayList.indexOf(mDataSet.get(getAdapterPosition()))).setAdded(false);
                        mDataSet.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                });
            }

            public TextView getTextView() {
                return textView;
            }

            ImageView getIvDelete() {
                return ivDelete;
            }
        }
    }


}
