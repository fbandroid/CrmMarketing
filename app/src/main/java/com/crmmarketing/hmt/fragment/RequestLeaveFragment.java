package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.LeaveDate;
import com.crmmarketing.hmt.gsonmodel22.LeaveRequest;
import com.crmmarketing.hmt.model.MyRes;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 26-06-2017.
 */

/**
 * D.L --> 1
 * C.L-->2
 */


public class RequestLeaveFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
    Calendar endCalender;
    private Context context;
    private TextView tvStartDate;
    private TextView tvEndDate;
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            endCalender = Calendar.getInstance();
            endCalender.set(Calendar.YEAR, year);
            endCalender.set(Calendar.MONTH, monthOfYear);
            endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


            tvEndDate.setText(sdf.format(endCalender.getTime()));
//            dob = sdf.format(endCalender.getTime());


        }

    };
    private Spinner spLeave;
    private EditText edtDesc;
    private Button btnRequest;
    private long lastclick;
    private String id;
    private String userName;
    private String role;
    private String branch;
    private Calendar initCalender;
    private Calendar startCalender;
    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            startCalender = Calendar.getInstance();
            startCalender.set(Calendar.YEAR, year);
            startCalender.set(Calendar.MONTH, monthOfYear);
            startCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabelStart("start");


        }


    };
    private ProgressDialog progressDialog;
    private RetrofitClient.RequestLeave requestLEave;
    private CheckBox checkBox;
    private EditText editText;
    private String type = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branch = sharedPref.getString("branch", "");
        initCalender = Calendar.getInstance();
        startCalender = Calendar.getInstance();
        requestLEave = RetrofitClient.requestLeave(Constants.BASE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Request Leave");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_request_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
        editText = (EditText) view.findViewById(R.id.edtNoOfDay);
        spLeave = (Spinner) view.findViewById(R.id.spLeave);
        edtDesc = (EditText) view.findViewById(R.id.edtDesc);
        btnRequest = (Button) view.findViewById(R.id.btnRequest);
        checkBox = (CheckBox) view.findViewById(R.id.cbEmergency);
        checkBox.setChecked(true);

        spLeave.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (parent.getItemAtPosition(position).toString()) {

                    case "D.L":
                        type = "1";
                        break;
                    case "C.L":
                        type = "2";
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                tvStartDate.setText("");
            }
        });


        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Utils.isTimeAutomatic(getActivity())) {

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDate, initCalender
                            .get(Calendar.YEAR), initCalender.get(Calendar.MONTH),
                            initCalender.get(Calendar.DAY_OF_MONTH));

                    if (checkBox.isChecked()) {
                        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    } else {

                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DAY_OF_MONTH, 3);

                        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                    }


                    datePickerDialog.show();
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }

            }
        });


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }

                final ArrayList<LeaveDate> leaveDateArrayList = new ArrayList<>();
                final LeaveRequest leaveRequest = new LeaveRequest();


                if (Utils.checkInternetConnection(getActivity())) {
                    if (tvStartDate.getText().toString().trim().isEmpty()) {

                        Toast.makeText(getActivity(), "Select start date", Toast.LENGTH_SHORT).show();
                    } else if (tvEndDate.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getActivity(), "Select end date", Toast.LENGTH_SHORT).show();
                    } else if (endCalender.before(startCalender)) {
                        Toast.makeText(getActivity(), "Select end date greater than start date", Toast.LENGTH_SHORT).show();
                    } else if (edtDesc.getText().toString().trim().isEmpty()) {
                        edtDesc.requestFocus();
                        Toast.makeText(getActivity(), "Enter discription", Toast.LENGTH_SHORT).show();
                    } else {
                        //call ws for request leave


//                            for (int i = 0; i < Integer.parseInt(editText.getText().toString().trim()); i++) {
//
//                                Calendar calendar = Calendar.getInstance();
//                                calendar.setTime(startCalender.getTime());
//                                calendar.add(Calendar.DAY_OF_MONTH, i);
//                                LeaveDate leaveDate = new LeaveDate();
//                                leaveDate.setDate(calendar.getTime().toString());
//                                leaveDateArrayList.add(leaveDate);
//
//
//                            }
//
//                            leaveRequest.setLeaveDate(leaveDateArrayList);
//                            leaveRequest.setId(id);
//                            leaveRequest.setReason(edtDesc.getText().toString().trim());
//                            leaveRequest.setDay(editText.getText().toString().trim());
//
//                            Log.e("josn", new Gson().toJson(leaveRequest));

                        showProgressDialog();

                        requestLEave.requestleave(id, role, tvStartDate.getText().toString(), tvEndDate.getText().toString(),
                                edtDesc.getText().toString().trim(),type

                        ).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        Toast.makeText(getActivity(), "Leave submitted..", Toast.LENGTH_SHORT).show();
                                        getActivity().getFragmentManager().popBackStack();
                                    } else {
                                        Toast.makeText(getActivity(), "Leave not  submitted..", Toast.LENGTH_SHORT).show();
                                        getActivity().getFragmentManager().popBackStack();
                                    }
                                } else {
                                    getActivity().getFragmentManager().popBackStack();
                                }
                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                Toast.makeText(getActivity(), "Leave not  submitted..", Toast.LENGTH_SHORT).show();
                                getActivity().getFragmentManager().popBackStack();

                            }
                        });


                    }
                }

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Request Leave");
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

    private void updateLabelStart(String which) {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (which.equalsIgnoreCase("start")) {
            tvStartDate.setText(sdf.format(startCalender.getTime()));
        } else if (which.equalsIgnoreCase("end")) {


        }

    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_view, menu);
        MenuItem item = menu.findItem(R.id.view_leave);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.view_leave) {
            addFragment(new ViewLeaveFragment(), RequestLeaveFragment.this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }
}
