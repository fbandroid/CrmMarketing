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

import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.InqMaster;

/**
 * Created by USER on 17-02-2017.
 */

public class ReferenceInfoFragment extends Fragment {
    private InqMaster inqMaster;
    private Context context;
    private TextView tvRefName;
    private TextView tvRefEmail;
    private TextView tvRefphone;
    private TextView tvRefCode;
    private TextView tvRefOther;
    private TextView fragmentReferenceInfoTvNomineeName;
    private TextView fragmentReferenceInfoTvNomineePAN;


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

                    inqMaster = getParentFragment().getArguments().getParcelable("detail");
                }
            }


        } else {
            inqMaster = ((InquirtDetailActivity) context).inqMaster;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reference_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvRefName = (TextView) view.findViewById(R.id.fragment_reference_info_tvName);
        tvRefCode = (TextView) view.findViewById(R.id.fragment_reference_info_tvRefCode);
        tvRefEmail = (TextView) view.findViewById(R.id.fragment_reference_info_tvEmail);
        tvRefphone = (TextView) view.findViewById(R.id.fragment_reference_info_tvPhone);
        tvRefOther = (TextView) view.findViewById(R.id.fragment_reference_info_tvOther);

        fragmentReferenceInfoTvNomineeName = (TextView) view.findViewById(R.id.fragment_reference_info_tvNomineeName);
        fragmentReferenceInfoTvNomineePAN = (TextView) view.findViewById(R.id.fragment_reference_info_tvNomineePAN);


        if (inqMaster != null) {

            tvRefName.setText(inqMaster.getCusInfo().getCusRefName());
            tvRefEmail.setText(inqMaster.getCusInfo().getCusRefEmail());
            tvRefphone.setText(inqMaster.getCusInfo().getCusRefMobile());
            fragmentReferenceInfoTvNomineeName.setText(inqMaster.getCusInfo().getNomineeName());
            fragmentReferenceInfoTvNomineePAN.setText(inqMaster.getCusInfo().getNomineePAN());

        }

    }
}
