package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.ViewTargetActivity;
import com.crmmarketing.hmt.targetmodel.Gettarget;

/**
 * Created by USER on 06-03-2017.
 */

public class TargetMasterDetail extends Fragment {
    private TextView tvName;
    private TextView tvTopEmp;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private Context context;
    private Gettarget gettarget;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getParentFragment() != null) {

            if (getParentFragment().getArguments() != null) {

                if (getParentFragment().getArguments().getParcelable("detail") != null) {

                    gettarget = getParentFragment().getArguments().getParcelable("detail");
                }
            }


        } else {
            gettarget = ((ViewTargetActivity) context).gettarget;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_master_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = (TextView) view.findViewById(R.id.fragment_target_master_tvName);
        tvTopEmp = (TextView) view.findViewById(R.id.fragment_target_master_tvTopEmp);
        tvStartDate = (TextView) view.findViewById(R.id.fragment_target_master_tvStartDate);
        tvEndDate = (TextView) view.findViewById(R.id.fragment_target_master_tvEndDate);


        if(gettarget!=null){

            tvName.setText(gettarget.getTarMaster().getTarChildname());
            tvTopEmp.setText(gettarget.getTarMaster().getTarParentname());
            tvStartDate.setText(gettarget.getTarMaster().getTarStartdate());
            tvEndDate.setText(gettarget.getTarMaster().getTarEnddate());
        }
    }
}
