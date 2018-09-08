package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;

/**
 * Created by USER on 07-02-2017.
 */

public class AddEmployeeFragment extends Fragment implements View.OnClickListener {


    private Context context;
    private Spinner spRole;
    private TextInputEditText edtName;
    private TextInputEditText edtMobile;
    private TextInputEditText edtPhone;
    private TextInputEditText edtAddr;
    private Button btnDateOfBirth;
    private TextView tvDateOfBirth;
    private Button btnSubmit;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Add Employee");
        return inflater.inflate(R.layout.fragment_add_employee, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        spRole = (Spinner) view.findViewById(R.id.fragment_add_employee_spRole);
        edtName = (TextInputEditText) view.findViewById(R.id.fragment_add_employee_edtFullName);
        edtAddr = (TextInputEditText) view.findViewById(R.id.fragment_add_employee_edtAddr);
        edtMobile = (TextInputEditText) view.findViewById(R.id.fragment_add_employee_edtMobileNo);
        edtPhone = (TextInputEditText) view.findViewById(R.id.fragment_add_employee_edtPhone);
        btnDateOfBirth = (Button) view.findViewById(R.id.fragment_add_employee_btnDateOfBirth);
        tvDateOfBirth = (TextView) view.findViewById(R.id.fragment_add_employee_tvDateOfBirth);
        btnSubmit = (Button) view.findViewById(R.id.fragment_add_employee_btnSubmit);


        btnDateOfBirth.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Add Employee");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.fragment_add_employee_btnDateOfBirth:
                break;

            case R.id.fragment_add_employee_btnSubmit:

//                if (getTargetFragment() != null) {
//                    Intent intent = new Intent();
//                    intent.putExtra("add", "add");
//                   // getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
//
//                }
                break;
        }
    }
}
