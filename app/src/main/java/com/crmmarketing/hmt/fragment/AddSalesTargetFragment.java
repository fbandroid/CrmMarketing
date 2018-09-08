package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.dpizarro.autolabel.library.AutoLabelUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddSalesTargetFragment extends Fragment implements AdapterView.OnItemClickListener {

    private Button btnStartDate;
    private Button btnEndDate;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private Calendar initCalender;
    private Calendar startCalender;
    private Calendar endCalender;
    private AutoCompleteTextView autoCompleteTextView;
    private AutoLabelUI autoLabelUI;
    private String[] products;


    private Context context;

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
        initCalender = Calendar.getInstance();
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        products = new String[]{
                "Equity Shares", " Life Insurance Policies", "Mutual Funds", "Money Market Funds"
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_target, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnStartDate = (Button) view.findViewById(R.id.fragment_add_target_btnStartDate);
        btnEndDate = (Button) view.findViewById(R.id.fragment_add_target_btnEndDate);
        tvStartDate = (TextView) view.findViewById(R.id.fragment_add_target_tvStartDate);
        tvEndDate = (TextView) view.findViewById(R.id.fragment_add_target_tvEndDate);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.auto_complete_tv);
        // autoLabelUI= (AutoLabelUI) view.findViewById(R.id.label_view);


//        autoLabelUI.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
//            @Override
//            public void onRemoveLabel(View view, int position) {
//
//            }
//        });


        //TODO web service for task set for selected item and dynamic item loading

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_dropdown_item_1line,products);
//
//
//        autoCompleteTextView.setAdapter(adapter);
//        autoCompleteTextView.setOnItemClickListener(this);

        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Utils.isTimeAutomatic(getActivity())) {
                    if (startCalender != null) {

                        initCalender = startCalender;
                    }


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDate, initCalender
                            .get(Calendar.YEAR), initCalender.get(Calendar.MONTH),
                            initCalender.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }


            }
        });

        btnEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isTimeAutomatic(getActivity())) {

                    if (endCalender != null) {

                        initCalender = endCalender;
                    }

                    if (startCalender != null) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, initCalender
                                .get(Calendar.YEAR), initCalender.get(Calendar.MONTH),
                                initCalender.get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMinDate(startCalender.getTimeInMillis());

                        if (startCalender != null) {
                            datePickerDialog.show();
                        }
                    }


                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }


            }
        });
    }


    DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            startCalender = Calendar.getInstance();
            startCalender.set(Calendar.YEAR, year);
            startCalender.set(Calendar.MONTH, monthOfYear);
            startCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            if (startCalender.after(endCalender)) {

//                     endCalender.set(Calendar.MONTH,monthOfYear);
//                     endCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth+1);
//                     endCalender.set(Calendar.YEAR,year);
                endCalender = startCalender;

                updateLabelStart("end");
            }

            updateLabelStart("start");
        }

    };


    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            endCalender = Calendar.getInstance();
            endCalender.set(Calendar.YEAR, year);
            endCalender.set(Calendar.MONTH, monthOfYear);
            endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

//            if(endCalender.before(startCalender) && endCalender.after(Calendar.getInstance()) ){
//
//                startCalender.set(Calendar.MONTH,monthOfYear);
//                startCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth-1);
//                updateLabelStart("start");
//            }
            updateLabelStart("end");
        }

    };


    private void updateLabelStart(String which) {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (which.equalsIgnoreCase("start")) {
            tvStartDate.setText(sdf.format(startCalender.getTime()));
        } else if (which.equalsIgnoreCase("end")) {
            tvEndDate.setText(sdf.format(endCalender.getTime()));

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        boolean isAlready = false;
        Log.e("item selected", (String) parent.getAdapter().getItem(position));


//            for(int i=0;i<autoLabelUI.getLabels().size();i++){
//
//                if(autoLabelUI.getLabels().get(i).getText().equals((String) parent.getAdapter().getItem(position))){
//                    isAlready=true;
//                    break;
//                }
//            }
//              if(!isAlready){
//                autoLabelUI.addLabel((String) parent.getAdapter().getItem(position));
//            }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("TeamLeader");
    }


}



