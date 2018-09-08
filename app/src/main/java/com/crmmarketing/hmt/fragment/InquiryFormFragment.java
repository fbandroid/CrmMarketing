package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.crmmarketing.hmt.ConfirmUploadActivity;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.adapter.BookAutoCompleteAdapter;
import com.crmmarketing.hmt.adapter.ClientFilteradapter;
import com.crmmarketing.hmt.common.DelayAutoCompleteTextView;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.database.PostsDatabaseHelper;
import com.crmmarketing.hmt.gsonmodel22.ClientDetail;
import com.crmmarketing.hmt.gsonmodel22.Customer;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.InquiryMaster;
import com.crmmarketing.hmt.model.InquiryTransaction;
import com.crmmarketing.hmt.model.ProductInfo;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class InquiryFormFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
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
    private TextInputEditText edtTime;
    private Button btnSubmit;
    private Button btnDOB;
    private TextView tvDOB;
    private TextInputEditText edtAmount;
    private RadioGroup radioGroup;
    private RadioButton rbpending;
    private RadioButton rbConfirm;
    private LinearLayout llFeedback;
    private long lastclick;


    private RadioGroup rgTSBSearch;
    private RadioButton rbTSB;
    private RadioButton rbPAN;
    private RadioButton rbName;
    private TextInputEditText edtTsbcodeSearch;

    private RetrofitClient.SearchByTSBPAN byTSBPAN;


    private String dob;
    private String nomineeDob="";
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

    DatePickerDialog.OnDateSetListener nomineeEndDate = new DatePickerDialog.OnDateSetListener() {

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


            fragmentInquiryFormTvBirthNominee.setText(sdf.format(endCalender.getTime()));
            nomineeDob = sdf.format(endCalender.getTime());

        }

    };


    private AutoCompleteTextView autoCompleteTextView;
    private AutoCompleteTextView autoCustomerName;
    private ArrayList<PrList> productInfoArrayList;
    private RecyclerView recyclerView;
    private ArrayList<PrList> selectedArrayList;
    private CustomAdapter customAdapter;
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
    private String caseof = "0";

    private TextInputEditText fragmentInquiryFormEdtCity;
    private TextInputEditText fragmentInquiryFormEdtZip;
    private TextInputEditText fragmentInquiryFormEdtState;
    private TextInputEditText fragmentInquiryFormEdtOccDetail;

    private TextInputEditText fragmentInquiryFormEdtMotherName;
    private TextInputEditText fragmentInquiryFormEdtFamilyCode;
    private TextInputEditText fragmentInquiryFormEdtBirthPlace;
    private TextInputEditText fragmentInquiryFormEdtNomineeName;
    private TextInputEditText fragmentInquiryFormEdtNomineePAN;
    private TextInputEditText fragmentInquiryFormEdtNomineeAdhar;
    private TextInputEditText fragmentInquiryFormEdtNomineeRelation;

    private Button fragmentInquiryFormBtnDatePickNominee;
    private TextView fragmentInquiryFormTvBirthNominee;


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
        productInfoArrayList = new ArrayList<>();
        selectedArrayList = new ArrayList<>();
        byTSBPAN = RetrofitClient.searchbytsbpan(Constants.BASE_URL);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        role = sharedPref.getString("role", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        b_id = sharedPref.getString("b_id", "");
        if (b_id.equals("0")) {
            b_name = "All";
        } else {
            b_name = sharedPref.getString("b_name", "");
        }


        new getProduct().execute();

        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getActivity().getApplicationContext());
        inqMasterArrayList = postsDatabaseHelper.getAllPosts();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Inquiry Form");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_inquiry_form, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentView = (LinearLayout) view.findViewById(R.id.parent_view);
        edtName = (AutoCompleteTextView) view.findViewById(R.id.fragment_inquiry_form_edtApplicantName);
        edtAddr = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtAddr);
        edtMobile = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtMobileNo);
        edtFeedBackDays = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtFeedbackDay);
        edtEmail = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtEmail);
        edtPhone = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtPhone);
        edtOhterDetail = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtOtherDetail);
        edtRefereeEmail = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtRefereeEmail);
        edtRefereeName = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtRefereeName);
        edtRefereePhone = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtRefereePhone);
        edtSource = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtSource);
        edtOptionalEmail = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtOptionalEmail);
        edtRefCode = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtrefCose);
        edtPAN = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtPAN);
        edtTime = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtTime);
        edtAmount = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtAmount);
        fragmentInquiryFormBtnDatePickNominee = (Button) view.findViewById(R.id.fragment_inquiry_form_btnDatePickNominee);
        fragmentInquiryFormTvBirthNominee = (TextView) view.findViewById(R.id.fragment_inquiry_form_tvBirthNominee);


        spRange = (Spinner) view.findViewById(R.id.fragment_inquiry_form_spRange);
        spOccupation = (Spinner) view.findViewById(R.id.fragment_inquiry_form_spOccupation);
        llFeedback = (LinearLayout) view.findViewById(R.id.llFeedback);
        radioGroup = (RadioGroup) view.findViewById(R.id.rgStatus);
        rbConfirm = (RadioButton) view.findViewById(R.id.rbConfirm);
        rbpending = (RadioButton) view.findViewById(R.id.rbPending);
        tvDOB = (TextView) view.findViewById(R.id.fragment_inquiry_form_tvBirth);
        btnDOB = (Button) view.findViewById(R.id.fragment_inquiry_form_btnDatePick);
        btnSubmit = (Button) view.findViewById(R.id.fragment_inquiry_form_btnSubmit);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_inquiry_form_rvProductAdd);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        rgTSBSearch = (RadioGroup) view.findViewById(R.id.rgTSBSearch);
        rbTSB = (RadioButton) view.findViewById(R.id.rbTSB);
        rbPAN = (RadioButton) view.findViewById(R.id.rbPAN);
        rbName = (RadioButton) view.findViewById(R.id.rbName);

        edtTsbcodeSearch = (TextInputEditText) view.findViewById(R.id.edt_tsbcode_search);
        autoCustomerName = (AutoCompleteTextView) view.findViewById(R.id.autoCustomerName);
        autoCustomerName.setThreshold(3);


        autoCustomerName.setAdapter(new ClientFilteradapter(id, getActivity()));


        autoCustomerName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                autoCustomerName.getText().clear();

                if (parent.getItemAtPosition(position) instanceof Customer) {


                    edtName.setText(((Customer) parent.getItemAtPosition(position)).getName());
                    edtAddr.setText(((Customer) parent.getItemAtPosition(position)).getAddress());
                    edtEmail.setText(((Customer) parent.getItemAtPosition(position)).getEmail());
                    edtMobile.setText(((Customer) parent.getItemAtPosition(position)).getMobile());
                    edtPhone.setText(((Customer) parent.getItemAtPosition(position)).getLandline());
                    edtPAN.setText(((Customer) parent.getItemAtPosition(position)).getPan());
                    tvDOB.setText(((Customer) parent.getItemAtPosition(position)).getDob());
                    edtOptionalEmail.setText(((Customer) parent.getItemAtPosition(position)).getAlternateEmail());
                    fragmentInquiryFormEdtCity.setText(((Customer) parent.getItemAtPosition(position)).getCity());
                    fragmentInquiryFormEdtState.setText(((Customer) parent.getItemAtPosition(position)).getState());
                    fragmentInquiryFormEdtZip.setText(((Customer) parent.getItemAtPosition(position)).getZip());
                    fragmentInquiryFormEdtOccDetail.setText(((Customer) parent.getItemAtPosition(position)).getOccupationDetails());

                }
            }
        });


        fragmentInquiryFormEdtCity = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtCity);
        fragmentInquiryFormEdtZip = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtZip);
        fragmentInquiryFormEdtState = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtState);
        fragmentInquiryFormEdtOccDetail = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtOccDetail);

        fragmentInquiryFormEdtMotherName = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtMotherName);
        fragmentInquiryFormEdtFamilyCode = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtFamilyCode);
        fragmentInquiryFormEdtBirthPlace = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtBirthPlace);
        fragmentInquiryFormEdtNomineeName = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtNomineeName);
        fragmentInquiryFormEdtNomineePAN = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtNomineePAN);
        fragmentInquiryFormEdtNomineeAdhar = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtNomineeAdhar);
        fragmentInquiryFormEdtNomineeRelation = (TextInputEditText) view.findViewById(R.id.fragment_inquiry_form_edtNomineeRelation);


        rgTSBSearch.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rgTSBSearch.getCheckedRadioButtonId()) {

                    case R.id.rbTSB:
                        edtTsbcodeSearch.setVisibility(View.VISIBLE);
                        autoCustomerName.setVisibility(View.GONE);
                        break;
                    case R.id.rbPAN:
                        edtTsbcodeSearch.setVisibility(View.VISIBLE);
                        autoCustomerName.setVisibility(View.GONE);
                        break;

                    case R.id.rbName:
                        edtTsbcodeSearch.setVisibility(View.GONE);
                        autoCustomerName.setVisibility(View.VISIBLE);
                        break;

                }

            }
        });


        edtTsbcodeSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (v.getId() == R.id.edt_tsbcode_search) {

                    if (hasFocus) {


                    } else {
                        Log.e("focus change", "focus changed");


                        String caseoffSearch = "";
                        switch (rgTSBSearch.getCheckedRadioButtonId()) {

                            case R.id.rbTSB:
                                caseoffSearch = "1";
                                break;
                            case R.id.rbPAN:
                                caseoffSearch = "2";
                                break;

                            case R.id.rbName:
                                caseoffSearch = "3";

                        }

                        if (!edtTsbcodeSearch.getText().toString().trim().isEmpty()) {


                            //TODO call search customer service here and set text for basic detail..

                            byTSBPAN.tsbPanvala(id, caseoffSearch, edtTsbcodeSearch.getText().toString().trim())
                                    .enqueue(new Callback<ClientDetail>() {
                                        @Override
                                        public void onResponse(Call<ClientDetail> call, Response<ClientDetail> response) {

                                            if (response.isSuccessful()) {

                                                final ArrayList<Customer> customerArrayList = (ArrayList<Customer>) response.body().getCustomers();

                                                if (customerArrayList.size() > 0) {

                                                    edtName.setText(customerArrayList.get(0).getName());
                                                    edtAddr.setText(customerArrayList.get(0).getAddress());
                                                    edtEmail.setText(customerArrayList.get(0).getEmail());
                                                    edtMobile.setText(customerArrayList.get(0).getMobile());
                                                    edtPhone.setText(customerArrayList.get(0).getLandline());
                                                    edtPAN.setText(customerArrayList.get(0).getPan());
                                                    tvDOB.setText(customerArrayList.get(0).getDob());
                                                    edtOptionalEmail.setText(customerArrayList.get(0).getAlternateEmail());
                                                    fragmentInquiryFormEdtCity.setText(customerArrayList.get(0).getCity());
                                                    fragmentInquiryFormEdtState.setText(customerArrayList.get(0).getState());
                                                    fragmentInquiryFormEdtZip.setText(customerArrayList.get(0).getZip());
                                                    fragmentInquiryFormEdtOccDetail.setText(customerArrayList.get(0).getOccupationDetails());


                                                } else {

                                                    edtName.setText("");
                                                    edtAddr.setText("");
                                                    edtEmail.setText("");
                                                    edtMobile.setText("");
                                                    edtPhone.setText("");
                                                    edtPAN.setText("");
                                                    tvDOB.setText("");
                                                    edtOptionalEmail.setText("");
                                                    fragmentInquiryFormEdtCity.setText("");
                                                    fragmentInquiryFormEdtState.setText("");
                                                    fragmentInquiryFormEdtZip.setText("");
                                                    fragmentInquiryFormEdtOccDetail.setText("");


                                                }

                                            }

                                        }

                                        @Override
                                        public void onFailure(Call<ClientDetail> call, Throwable t) {

                                            edtName.setText("");
                                            edtAddr.setText("");
                                            edtEmail.setText("");
                                            edtMobile.setText("");
                                            edtPhone.setText("");
                                            edtPAN.setText("");
                                            tvDOB.setText("");
                                            edtOptionalEmail.setText("");
                                            fragmentInquiryFormEdtCity.setText("");
                                            fragmentInquiryFormEdtState.setText("");
                                            fragmentInquiryFormEdtZip.setText("");
                                            fragmentInquiryFormEdtOccDetail.setText("");
                                        }
                                    });


                        }
                    }
                }

            }
        });


        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.fragment_inquiry_form_ProductAutoComplete);
        //
        //
        //
        // autoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(getActivity(), productInfoArrayList));
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


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                if (group.getCheckedRadioButtonId() == R.id.rbConfirm) {

                    caseof = "1";

                    llFeedback.setVisibility(View.GONE);
                } else if (group.getCheckedRadioButtonId() == R.id.rbPending) {

                    caseof = "0";
                    llFeedback.setVisibility(View.VISIBLE);

                }
            }
        });

        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getActivity().getApplicationContext());

        final ArrayList<String> stringArrayList = (ArrayList<String>) postsDatabaseHelper.getCusName();
        final String[] array = stringArrayList.toArray(new String[stringArrayList.size()]);
        //edt name autocomplete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.select_dialog_item, array);

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
//                   Toast.makeText(getActivity(),customerFeedBack,Toast.LENGTH_LONG).show();
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


        // set spinner

        ArrayAdapter<CharSequence> adapterRange = ArrayAdapter.createFromResource(getActivity(),
                R.array.range, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapterRange.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spRange.setAdapter(adapterRange);


        ArrayAdapter<CharSequence> adapterOccupation = ArrayAdapter.createFromResource(getActivity(),
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


                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();

            }
        });

        fragmentInquiryFormBtnDatePickNominee.setOnClickListener(new View.OnClickListener() {
                                                                     @Override
                                                                     public void onClick(View v) {

                                                                         DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), nomineeEndDate, Calendar.getInstance()
                                                                                 .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                                                                                 Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                                                                         datePickerDialog.show();

                                                                     }
                                                                 }
        );


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // check validation and insert inquiry data to local and send data to server

                if (isDoubleClick()) {
                    return;
                }

                if (Utils.checkInternetConnection(getActivity())) {

                    showAlertDialog();
                } else {
                    Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                }


//                final String[] transactionId = new String[selectedArrayList.size()];
//                final String customerName = edtName.getText().toString().trim();
//                final String customerAddr = edtAddr.getText().toString().trim();
//                final String customerMobile = edtMobile.getText().toString().trim();
//                final String customerEmail = edtEmail.getText().toString().trim();
//                final String customerLandLine = edtPhone.getText().toString().trim();
//                final String customerOccupation = spOccupation.getSelectedItem().toString().trim();
//                final String customerOptionalEmail = edtOptionalEmail.getText().toString().trim();
//                if (dob != null) {
//                    final String customerBirth = dob.trim();
//                }
//                String customerFeedBack = "";
//                if (!edtFeedBackDays.getText().toString().trim().isEmpty()) {
//                    customerFeedBack = edtFeedBackDays.getText().toString().trim();
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.DATE, Integer.parseInt(customerFeedBack));
//                    customerFeedBack = cal.getTime().toString();
//
//                }
//
//
//                final String customerOther = edtOhterDetail.getText().toString().trim();
//                final String customerRefereeName = edtRefereeName.getText().toString().trim();
//                final String customerRefereeEmail = edtRefereeEmail.getText().toString().trim();
//                final String customerRefereePhone = edtRefereePhone.getText().toString().trim();
//                final String customerSource = edtSource.getText().toString().trim();
//                final String customerRange = spRange.getSelectedItem().toString().trim();
//                final String customerRefCode = edtRefCode.getText().toString().trim();
//
//                if (customerName.isEmpty()) {
//
//
//                    Utils.showSnackBar(parentView, "Please enter customer name", getActivity());
//                    edtName.requestFocus();
//
//
//                } else if (customerAddr.isEmpty()) {
//
//
//                    Utils.showSnackBar(parentView, "Please enter customer Address", getActivity());
//                    edtAddr.requestFocus();
//                } else if (customerMobile.isEmpty() || customerMobile.length() < 10) {
//
//
//                    Utils.showSnackBar(parentView, "Please enter customer Mobile No", getActivity());
//                    edtMobile.requestFocus();
//                } else if (!Utils.isValidEmail(customerEmail)) {
//
//
//                    Utils.showSnackBar(parentView, "Please enter customer Email", getActivity());
//                    edtEmail.requestFocus();
//                } else if (!edtPAN.getText().toString().trim().isEmpty() && !Utils.isValidPAN(edtPAN.getText().toString().trim())) {
//                    Utils.showSnackBar(parentView, "Please enter Valid PAN NO", getActivity());
//                    edtPAN.requestFocus();
//                } else if (tvDOB.getText().toString().trim().isEmpty()) {
//
//                    Utils.showSnackBar(parentView, "Please enter customer BirhtDate", getActivity());
//                    tvDOB.requestFocus();
//                } else if (selectedArrayList.size() < 1) {
//
//                    Utils.showSnackBar(parentView, "Please select One Product", getActivity());
//                    autoCompleteTextView.requestFocus();
//                } else if (customerFeedBack.isEmpty()) {
//
//
//                    Utils.showSnackBar(parentView, "Please enter FeedBack time", getActivity());
//                    edtFeedBackDays.requestFocus();
//
//
//                } else if (!customerRefereeEmail.isEmpty() && !Utils.isValidEmail(customerRefereeEmail)) {
//
//                    Utils.showSnackBar(parentView, "Please enter Valid Email", getActivity());
//                    edtRefereeEmail.requestFocus();
//
//                } else {
//
//
//                    if (Utils.isTimeAutomatic(getActivity())) {
//
//                        final User user = new User();
//                        user.setUserName(customerName);
//                        user.setUserAddr(customerAddr);
//                        user.setUserBirth(tvDOB.getText().toString().trim());
//                        user.setUserMobileNo(customerMobile);
//                        user.setUserEmail(customerEmail);
//                        user.setOccupation(customerOccupation);
//                        user.setUserRange(customerRange);
//                        user.setUserLandlineNo(customerLandLine);
//                        user.setRefereeEmail(customerRefereeEmail);
//                        user.setRefereeMobile(customerRefereePhone);
//                        user.setRefereeName(customerRefereeName);
//                        user.setSourceName(customerSource);
//                        user.setUserComment(customerOther);
//                        user.setUserOptionalEmail(customerOptionalEmail);
//
//                        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getActivity().getApplicationContext());
//
//                        final long cus_id = postsDatabaseHelper.addOrUpdateUser(user);
//
//                        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", Context.MODE_PRIVATE);
//                        String username = sharedPref.getString("user_name", null);
//                        int id = sharedPref.getInt("id", 0);
//                        showProgressDialog();
//                        if (cus_id != -1) {
//                            user.setUserId(String.valueOf(cus_id));
//
//
//                            final InquiryMaster inquiryMaster = new InquiryMaster();
//                            inquiryMaster.setKEY_INQUIRY_MASTER_CUSTOMER_ID_FK(String.valueOf(cus_id));
//                            inquiryMaster.setKEY_INQUIRY_MASTER_FEEDBACK_TIME(customerFeedBack);
//                            inquiryMaster.setKEY_INQUIRY_MASTER_INVEST_RANGE(customerRange);
//                            inquiryMaster.setKEY_INQUIRY_MASTER_TIMESTAMP(String.valueOf(Calendar.getInstance().getTimeInMillis()));
//                            inquiryMaster.setKEY_INQUIRY_MASTER_USER_ID_FK(username);
//                            inquiryMaster.setKEY_INQUIRY_MASTER_INVEST_RANGE(customerRange);
//                            inquiryMaster.setKEY_INQUIRY_MASTER_ID(String.valueOf(inq_mas_id));
//
//
//                            inq_mas_id = postsDatabaseHelper.addOrUpdateInquiryMaster(inquiryMaster);
//
//                            if (inq_mas_id != -1) {
//                                inquiryMaster.setKEY_INQUIRY_MASTER_ID(String.valueOf(inq_mas_id));
//
//                                for (int i = 0; i < selectedArrayList.size(); i++) {
//                                    final InquiryTransaction inquiryTransaction = new InquiryTransaction();
//                                    inquiryTransaction.setKEY_INQUIRY_TRANSACTION_INQ_MASTER_ID_FK(String.valueOf(inq_mas_id));
//                                    inquiryTransaction.setKEY_INQUIRY_TRANSACTION_PRODUCT_ID(selectedArrayList.get(i).getPrdId());
//                                    inquiryTransaction.setKEY_INQUIRY_TRANSACTION_TIMESTAMP(String.valueOf(Calendar.getInstance().getTimeInMillis()));
//                                    final long transaction_id = postsDatabaseHelper.addOrUpdateInquiryTransaction(inquiryTransaction);
//                                    if (transaction_id != -1) {
//
//                                        inquiryTransaction.setKEY_INQUIRY_TRANSACTION_ID(String.valueOf(transaction_id));
//                                        transactionId[i] = String.valueOf(transaction_id);
//                                    }
//                                }
//
//
//                            }
//
//                        }
//
//                        // service call
//
//                        try {
//                            final JSONObject topJsonObject = new JSONObject();
//
//                            final JSONObject jsonObject = new JSONObject();
//
//
//                            final JSONObject personal_info_json = new JSONObject();
//                            personal_info_json.put("cus_name", customerName);
//                            personal_info_json.put("cus_addr", customerAddr);
//                            personal_info_json.put("cus_mobile", customerMobile);
//                            personal_info_json.put("cus_land", customerLandLine);
//                            personal_info_json.put("cus_email", customerEmail);
//                            personal_info_json.put("cus_alt_email", customerOptionalEmail);
//                            personal_info_json.put("cus_birth", dob);
//                            personal_info_json.put("cus_occupation", customerOccupation);
//
//
//                            JSONObject product_info = new JSONObject();
//
//                            final JSONArray jsonArray = new JSONArray();
//                            for (int i = 0; i < selectedArrayList.size(); i++) {
//                                final JSONObject product_info_json = new JSONObject();
//                                product_info_json.put("pr_id", selectedArrayList.get(i).getPrdId());
//                                product_info_json.put("pr_name", selectedArrayList.get(i).getPrdName());
//                                //   product_info_json.put("pr_category", selectedArrayList.get(i).getProductCategory());
//                                jsonArray.put(i, product_info_json);
//                            }
//                            product_info.put("pr_list", jsonArray);
//
//                            final JSONObject feedback_info_json = new JSONObject();
//                            feedback_info_json.put("feed_date", customerFeedBack);
//                            feedback_info_json.put("feed_remark", customerSource);
//
//                            final JSONObject ref_info_json = new JSONObject();
//                            ref_info_json.put("ref_name", customerRefereeName);
//                            ref_info_json.put("ref_email", customerRefereeEmail);
//                            ref_info_json.put("ref_phone", customerRefereePhone);
//                            ref_info_json.put("ref_code", customerRefCode);
//                            ref_info_json.put("ref_other", customerOther);
//
//
//                            jsonObject.put("personal_info", personal_info_json);
//                            jsonObject.put("product_detail", product_info);
//                            jsonObject.put("feedback_info", feedback_info_json);
//                            jsonObject.put("ref_info", ref_info_json);
//                            jsonObject.put("emp_id", String.valueOf(id));
//                            jsonObject.put("br_id",b_id);
//                            jsonObject.put("br_name",b_name);
//                            jsonObject.put("pr_range", customerRange);
//                            topJsonObject.put("inquiry", jsonObject);
//
//                            Log.e("json web",topJsonObject.toString());
//                            //insert inquiry
//
//                            if(Utils.checkInternetConnection(getActivity())){
//                                new InsertInquiry(topJsonObject, String.valueOf(inq_mas_id), transactionId).execute();
//
//                            }
//                            else {
//                                if(getActivity()!=null){
//
//                                    Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } else {
//                        startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
//                        Toast.makeText(getActivity(), "Please make time and date Automatic", Toast.LENGTH_LONG).show();
//                    }
//
//
//                }


            }
        });


    }

    // asynch task for insert inquiry to server

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
        final String customerCity = fragmentInquiryFormEdtCity.getText().toString().trim();
        final String customerZip = fragmentInquiryFormEdtZip.getText().toString().trim();
        final String customerState = fragmentInquiryFormEdtState.getText().toString().trim();
        final String customerOccDeatail = fragmentInquiryFormEdtOccDetail.getText().toString();
        final String customerMotherName = fragmentInquiryFormEdtMotherName.getText().toString().trim();
        final String customerFamilyCode = fragmentInquiryFormEdtFamilyCode.getText().toString().trim();
        final String customerBirthPlace = fragmentInquiryFormEdtBirthPlace.getText().toString().trim();
        final String customerNomineeName = fragmentInquiryFormEdtNomineeName.getText().toString().trim();
        final String customerNomineePAN = fragmentInquiryFormEdtNomineePAN.getText().toString().trim();
        final String customerNomineeRelation = fragmentInquiryFormEdtNomineeRelation.getText().toString().trim();
        final String customerNomineeAdhar = fragmentInquiryFormEdtNomineeAdhar.getText().toString().trim();
        final String customerNomineeDOB=fragmentInquiryFormTvBirthNominee.getText().toString().trim();


        if (dob != null) {
            final String customerBirth = dob.trim();
        }
        String customerFeedBack = "";
        if (!edtFeedBackDays.getText().toString().trim().isEmpty()) {
            customerFeedBack = edtFeedBackDays.getText().toString().trim();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, Integer.parseInt(customerFeedBack));
            customerFeedBack = cal.getTime().toString();

        }

        final String feedTime = edtTime.getText().toString().trim();


        final String customerOther = edtOhterDetail.getText().toString().trim();
        final String customerRefereeName = edtRefereeName.getText().toString().trim();
        final String customerRefereeEmail = edtRefereeEmail.getText().toString().trim();
        final String customerRefereePhone = edtRefereePhone.getText().toString().trim();
        final String customerSource = edtSource.getText().toString().trim();
        final String customerRange = edtAmount.getText().toString().trim();
        final String customerRefCode = edtRefCode.getText().toString().trim();

        if (customerName.isEmpty()) {


            Utils.showSnackBar(parentView, "Please enter customer name", getActivity());
            edtName.requestFocus();


        } else if (customerAddr.isEmpty()) {


            Utils.showSnackBar(parentView, "Please enter customer Address", getActivity());
            edtAddr.requestFocus();
        } else if (customerCity.isEmpty()) {
            Utils.showSnackBar(parentView, "Please enter City Name", getActivity());
            fragmentInquiryFormEdtCity.requestFocus();
        } else if (customerZip.isEmpty() || customerZip.length() < 6) {
            Utils.showSnackBar(parentView, "Please enter zip code", getActivity());
            fragmentInquiryFormEdtZip.requestFocus();
        } else if (customerState.isEmpty()) {
            Utils.showSnackBar(parentView, "Please enter state name", getActivity());
            fragmentInquiryFormEdtState.requestFocus();
        } else if (customerMobile.isEmpty() || customerMobile.length() < 10) {


            Utils.showSnackBar(parentView, "Please enter customer Mobile No", getActivity());
            edtMobile.requestFocus();
        } else if (!Utils.isValidEmail(customerEmail)) {


            Utils.showSnackBar(parentView, "Please enter customer Email", getActivity());
            edtEmail.requestFocus();
        } else if (!customerOptionalEmail.isEmpty() && !Utils.isValidEmail(customerOptionalEmail)) {

            Utils.showSnackBar(parentView, "Please enter valid Email", getActivity());
            edtOptionalEmail.requestFocus();
        } else if (!edtPAN.getText().toString().trim().isEmpty() && !Utils.isValidPAN(edtPAN.getText().toString().trim())) {
            Utils.showSnackBar(parentView, "Please enter Valid PAN NO", getActivity());
            edtPAN.requestFocus();
        } else if (tvDOB.getText().toString().trim().isEmpty()) {

            Utils.showSnackBar(parentView, "Please enter customer BirhtDate", getActivity());
            tvDOB.requestFocus();
        } else if (selectedArrayList.size() < 1) {

            Utils.showSnackBar(parentView, "Please select One Product", getActivity());
            autoCompleteTextView.requestFocus();
        } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbPending && edtFeedBackDays.getText().toString().trim().isEmpty()) {


            Utils.showSnackBar(parentView, "Please enter FeedBack time", getActivity());
            edtFeedBackDays.requestFocus();


        } else if (!customerRefereeEmail.isEmpty() && !Utils.isValidEmail(customerRefereeEmail)) {

            Utils.showSnackBar(parentView, "Please enter Valid Email", getActivity());
            edtRefereeEmail.requestFocus();

        } else {


            if (Utils.isTimeAutomatic(getActivity())) {

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
                user.setCity(customerCity);
                user.setOccdetail(customerOccDeatail);
                user.setZip(customerZip);
                user.setState(customerState);
                user.setUserComment(customerOther);
                user.setUserOptionalEmail(customerOptionalEmail);

                PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getActivity().getApplicationContext());

                final long cus_id = postsDatabaseHelper.addOrUpdateUser(user);

                SharedPreferences sharedPref = getActivity().getSharedPreferences("my", Context.MODE_PRIVATE);
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
                    personal_info_json.put("cus_birth", dob);
                    personal_info_json.put("pan", customerPAN);
                    personal_info_json.put("city", customerCity);
                    personal_info_json.put("state", customerState);
                    personal_info_json.put("occ_detail", customerOccDeatail);
                    personal_info_json.put("zip", customerZip);
                    personal_info_json.put("cus_occupation", customerOccupation);
                    personal_info_json.put("mother", customerMotherName);
                    personal_info_json.put("b_place", customerBirthPlace);
                    personal_info_json.put("family", customerFamilyCode);
                    personal_info_json.put("nom_name", customerNomineeName);
                    personal_info_json.put("nom_pan", customerNomineePAN);
                    personal_info_json.put("nom_adhar", customerNomineeAdhar);
                    personal_info_json.put("nom_rel", customerNomineeRelation);
                    personal_info_json.put("nom_dob",nomineeDob);


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

                    if (radioGroup.getCheckedRadioButtonId() == R.id.rbPending) {
                        feedback_info_json.put("feed_date", customerFeedBack);
                        feedback_info_json.put("feed_time", feedTime);
                        feedback_info_json.put("feed_remark", customerSource);
                    } else {
                        feedback_info_json.put("feed_date", "");
                        feedback_info_json.put("feed_time", "");
                        feedback_info_json.put("feed_remark", customerSource);
                    }


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
                    jsonObject.put("pr_range", customerRange);
                    if (caseof != null) {
                        jsonObject.put("case", caseof);
                    }


                    topJsonObject.put("inquiry", jsonObject);


                    //gson to make json from java object

                    if (Utils.checkInternetConnection(getActivity())) {
                        new InsertInquiry(topJsonObject, String.valueOf(inq_mas_id), transactionId).execute();

                    } else {

                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                Toast.makeText(getActivity(), "Please make time and date Automatic", Toast.LENGTH_LONG).show();
            }


        }


    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    private void replaceFragment(Fragment toAdd, Fragment current) {
        getFragmentManager().beginTransaction()
                .replace(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();

    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
//            recyclerView.setAdapter(customAdapter);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

//            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
//            recyclerView.setAdapter(customAdapter);
        }

    }


//    private void dummyData() {
//        for (int i = 1; i < 7; i++) {
//
//            final ProductInfo productInfo = new ProductInfo();
//            productInfo.setProductName("pr" + i);
//            productInfo.setProductId(String.valueOf(i));
//            productInfoArrayList.add(productInfo);
//        }
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit_form) {

            if (isDoubleClick()) {
                return true;
            }
            if (Utils.checkInternetConnection(getActivity())) {
                showAlertDialog();
            } else {
                Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
            }


            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.inquiry_form_menu, menu);


    }

    public void showAlertDialog() {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());


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
        android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
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
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();

                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSADDInquiry(getActivity()).postInquiry(jsonObject.toString());
                progressDialog.dismiss();

                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                Utils.showSnackBar(parentView, "No Internet", getActivity());
                progressDialog.dismiss();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();

                Utils.showSnackBar(parentView, "No Internet", getActivity());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String success;
            String status;
            String inq_id;
            String cu_id;
            progressDialog.dismiss();
            // after web service call do parsing here and update ui
            try {
                if (response != null) {
                    JSONObject jsonObject22 = new JSONObject(response);
                    success = jsonObject22.optString("msg");
                    status = jsonObject22.optString("status");
                    inq_id = jsonObject22.optString("inq_id");
                    cu_id = jsonObject22.optString("cu_id");

                    if (success.equalsIgnoreCase("true")) {


                        getFragmentManager().popBackStack(InquiryListInfoFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        PostsDatabaseHelper postsDatabaseHelper = PostsDatabaseHelper.getInstance(getActivity().getApplicationContext());
                        postsDatabaseHelper.updateStatus("1", masterId, childId);

                        Toast.makeText(getActivity(), "Successfully inserted", Toast.LENGTH_LONG).show();


                        if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()) != null) {


                            if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()));
                            }


                        } else if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()) != null) {
                            // replaceFragment(new TeamLeaderDashboardFragment(),InquiryFormFragment.this);

                            if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()));
                            }

                        } else if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()) != null) {
                            // replaceFragment(new MarketingExecutiveDashboard(),InquiryFormFragment.this);
//
                            if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()));
                            }

                        }

                        if (status.equals("1")) {
                            Intent intent = new Intent(getActivity(), ConfirmUploadActivity.class);
                            intent.putExtra("inq_id", inq_id);
                            intent.putExtra("cu_id", cu_id);
                            startActivity(intent);
                        }


                    } else {

                        // getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                        if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()) != null) {

                            //replaceFragment(new AdminDashBoard(),InquiryFormFragment.this);
                            if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()));
                            }


                        } else if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()) != null) {

                            //replaceFragment(new TeamLeaderDashboardFragment(),InquiryFormFragment.this);
                            if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()));
                            }

                        } else if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()) != null) {

                            //replaceFragment(new MarketingExecutiveDashboard(),InquiryFormFragment.this);
                            if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()).isResumed()) {
                                addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()));
                            }

                        }
                        Toast.makeText(getActivity(), "Error while inserting", Toast.LENGTH_LONG).show();
                    }
                } else {

                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                    if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()) != null) {

                        // replaceFragment(new AdminDashBoard(),InquiryFormFragment.this);
                        if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()).isResumed()) {
                            addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()));
                        }


                    } else if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()) != null) {

                        // replaceFragment(new TeamLeaderDashboardFragment(),InquiryFormFragment.this);
                        if (getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()).isResumed()) {
                            addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(TeamLeaderDashboardFragment.class.getSimpleName()));
                        }

                    } else if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()) != null) {

                        // replaceFragment(new MarketingExecutiveDashboard(),InquiryFormFragment.this);
                        if (getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()).isResumed()) {
                            addFragment(new InquiryListInfoFragment(), getFragmentManager().findFragmentByTag(MarketingExecutiveDashboard.class.getSimpleName()));
                        }

                    }
                    Toast.makeText(getActivity(), "Error while inserting", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }


            //  parseResponce();


        }

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
                    autoCompleteTextView.setAdapter(new BookAutoCompleteAdapter(getActivity(), productInfoArrayList));

                }

            } else {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_LONG).show();
                }

            }
//


            if (getActivity() != null) {
                progressDialog.dismiss();
            }

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

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final TextView textViewCat;
            private final ImageView ivDelete;

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
