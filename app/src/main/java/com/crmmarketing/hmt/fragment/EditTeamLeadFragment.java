package com.crmmarketing.hmt.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.EmployeeInfo;


public class EditTeamLeadFragment extends Fragment {

    private Button btnSave;
    private TextInputEditText edtName;
    private TextInputEditText edtId;
    private TextInputEditText edtAddr;
    private TextInputEditText edtMobile;
    private EmployeeInfo employeeInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            if (getArguments().getParcelable("detail") != null) {


                employeeInfo = getArguments().getParcelable("detail");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_team_lead, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = (Button) view.findViewById(R.id.row_fragment_team_lead_btnSave);
        edtName = (TextInputEditText) view.findViewById(R.id.row_fragment_team_lead_edtName);
        edtAddr = (TextInputEditText) view.findViewById(R.id.row_fragment_team_lead_edtAddr);
        edtId = (TextInputEditText) view.findViewById(R.id.row_fragment_team_lead_edtId);
        edtMobile = (TextInputEditText) view.findViewById(R.id.row_fragment_team_lead_edtMobile);


        if (employeeInfo != null) {

            edtName.setText(employeeInfo.getEmpName());
            edtId.setText(employeeInfo.getEmpNo());
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack(ListOfTeamLeaderFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
                if (getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()).isResumed()) {
                    addFragment(new ListOfTeamLeaderFragment(), getFragmentManager().findFragmentByTag(AdminDashBoard.class.getSimpleName()));
                }
            }
        });
    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }
}
