package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.adapter.TeamLeaderListAdapter;
import com.crmmarketing.hmt.model.EmployeeInfo;


public class TeamLeadDeatilFragment extends Fragment {
    private Context context;
    private ImageButton btnView;
    private ImageButton btnEdit;
    private ImageButton btnDelete;
    private User employeeInfo;
    private TextView tvName;
    private TextView tvMObile;
    private TextView tvAddr;
    private TextView tvDesg;

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


        if (getArguments() != null) {

            if (getArguments().getParcelable("detail") != null) {

                employeeInfo = getArguments().getParcelable("detail");

            }
        }

        if (employeeInfo != null) {
            ((HomeActivity) context).setTitle(employeeInfo.getUName());
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_team_lead_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnView = (ImageButton) view.findViewById(R.id.row_fragment_team_lead_btnView);
        btnEdit = (ImageButton) view.findViewById(R.id.row_fragment_team_lead_btnEdit);
        btnDelete = (ImageButton) view.findViewById(R.id.row_fragment_team_lead_btnDelete);
        tvName = (TextView) view.findViewById(R.id.row_fragment_team_lead_tvName);
        tvAddr = (TextView) view.findViewById(R.id.row_fragment_team_lead_tvAddress);

        tvMObile = (TextView) view.findViewById(R.id.row_fragment_team_lead_tvPhone);

        tvDesg = (TextView) view.findViewById(R.id.row_admin_dashboard_tvId);


        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utils.checkInternetConnection(getActivity())){
                    addFragment(new ListOfSalesExecutiveFragment(), TeamLeadDeatilFragment.this);
                }
                else {

                    if(getActivity()!=null){
                        Toast.makeText(getActivity(),"No Internet",Toast.LENGTH_LONG).show();
                    }
                }



            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // addFragment(new EditTeamLeadFragment(), TeamLeadDeatilFragment.this);
            }
        });


        if (employeeInfo != null) {

            tvName.setText(employeeInfo.getUName());
            tvDesg.setText(employeeInfo.getPost());
            tvMObile.setText(employeeInfo.getUContact());
            tvAddr.setText(employeeInfo.getUAddress());

        }


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "restore", TeamLeadDeatilFragment.this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            getFragmentManager().getFragment(savedInstanceState, "restore");
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle(employeeInfo.getUName());
    }

    private void addFragment(Fragment toAdd, Fragment current) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("detail", employeeInfo);
        toAdd.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(null)
                .hide(current).commit();
    }

}
