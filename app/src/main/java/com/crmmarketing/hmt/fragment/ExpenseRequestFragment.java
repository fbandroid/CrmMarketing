package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 14-06-2017.
 */

public class ExpenseRequestFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
    String dob;
    private Context context;
    private long lastclick;
    private ProgressDialog progressDialog;
    private Spinner spExpenseReason;
    private EditText edtDesc;
    private EditText edtExpense;
    private Button btnRequest;
    private RetrofitClient.RequestExpense requestExpense;
    private String id;
    private String caseOff = "";
    private TextView tvDOB;


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
        requestExpense = RetrofitClient.requestExpense(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ((HomeActivity) context).setTitle("Request expense");
        return inflater.inflate(R.layout.fragment_expense_request, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spExpenseReason = (Spinner) view.findViewById(R.id.spExpenseReason);
        edtDesc = (EditText) view.findViewById(R.id.edtDesc);
        edtExpense = (EditText) view.findViewById(R.id.edtExpense);
        btnRequest = (Button) view.findViewById(R.id.btnRequest);
        tvDOB = (TextView) view.findViewById(R.id.tvDOB);

        tvDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        spExpenseReason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
                        caseOff = "1";

                        break;
                    case 1:
                        caseOff = "1";

                        break;
                    case 2:
                        caseOff = "1";

                        break;
                    case 3:
                        caseOff = "1";

                        break;
                    case 4:
                        caseOff = "2";

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isDoubleClick()) return;
                //TODO call webservice

                if (edtExpense.getText().toString().trim().isEmpty()) {

                    edtExpense.requestFocus();
                    Toast.makeText(getActivity(), "Enter amount", Toast.LENGTH_SHORT).show();

                } else if (edtDesc.getText().toString().trim().isEmpty()) {
                    edtDesc.requestFocus();
                    Toast.makeText(getActivity(), "Enter description", Toast.LENGTH_SHORT).show();

                } else if (tvDOB.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Select date", Toast.LENGTH_SHORT).show();
                } else {
                    // call service to insert

                    showProgressDialog();

                    requestExpense.requestexpense(id, edtExpense.getText().toString().trim(),
                            caseOff, edtDesc.getText().toString().trim(), tvDOB.getText().toString(),
                            spExpenseReason.getSelectedItem().toString()
                    ).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {
                                    getFragmentManager().popBackStack();
                                    Toast.makeText(getActivity(), "Successfully Requested", Toast.LENGTH_SHORT).show();
                                } else {
                                    getFragmentManager().popBackStack();
                                    Toast.makeText(getActivity(), "Not Successfully Requested", Toast.LENGTH_SHORT).show();

                                }
                            } else {
                                getFragmentManager().popBackStack();
                            }


                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            getFragmentManager().popBackStack();
                            Toast.makeText(getActivity(), "Not Successfully Requested", Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Request expense");
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


    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
