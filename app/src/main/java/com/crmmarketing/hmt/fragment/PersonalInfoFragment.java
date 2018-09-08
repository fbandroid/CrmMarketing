package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;

import java.util.ArrayList;

/**
 * Created by USER on 17-02-2017.
 */

public class PersonalInfoFragment extends Fragment {


    private LinearLayout parentView;
    private TextView edtName;
    private TextView edtEmail;
    private TextView edtMobile;
    private TextView edtPhone;
    private TextView edtAddr;
    private TextView edtFeedBackDays;
    private TextView edtOhterDetail;
    private TextView edtRefereeName;
    private TextView edtRefereeEmail;
    private TextView edtRefereePhone;
    private TextView edtSource;
    private TextView edtOptionalEmail;
    private TextView edtRefCode;
    private TextView tvDOB;
    private String dob;
    private TextView tvFamily;
    private TextView tvTsb;
    private TextView tvMother;
    private RecyclerView recyclerView;
    private ArrayList<InqChild> selectedArrayList;
    private TextView spRange;
    private TextView spOccupation;
    private Context context;
    private InqMaster inqMaster;


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
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // parentView = (LinearLayout) view.findViewById(R.id.parent_view);
        edtName = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtApplicantName);
        edtAddr = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtAddr);
        edtMobile = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtMobileNo);
        edtFeedBackDays = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtFeedbackDay);
        edtEmail = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtEmail);
        edtName = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtApplicantName);
        edtPhone = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtPhone);
        edtOhterDetail = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtOtherDetail);
        edtRefereeEmail = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtRefereeEmail);
        edtRefereeName = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtRefereeName);
        edtRefereePhone = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtRefereePhone);
        edtSource = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtSource);
        edtOptionalEmail = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtOptionalEmail);
        edtRefCode = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtrefCose);
        spRange = (TextView) view.findViewById(R.id.fragment_inquiry_form_spRange);
        spOccupation = (TextView) view.findViewById(R.id.fragment_inquiry_form_spOccupation);
        tvDOB = (TextView) view.findViewById(R.id.fragment_inquiry_form_tvBirth);

        tvFamily = (TextView) view.findViewById(R.id.tvFamilyCode);
        tvMother = (TextView) view.findViewById(R.id.tvMotherName);
        tvTsb = (TextView) view.findViewById(R.id.tvTSBCode);


        if (inqMaster != null) {

            edtName.setText(inqMaster.getCusInfo().getCusName());
            edtAddr.setText(inqMaster.getCusInfo().getCusAddr());
            edtMobile.setText(inqMaster.getCusInfo().getCusMobile());
            edtEmail.setText(inqMaster.getCusInfo().getCusEmail());
            edtPhone.setText(inqMaster.getCusInfo().getCusLand());
            spOccupation.setText(inqMaster.getCusInfo().getCusOccupation());
            tvDOB.setText(inqMaster.getCusInfo().getCusDob());
            tvMother.setText(inqMaster.getCusInfo().getCusMother());
            tvFamily.setText(inqMaster.getCusInfo().getCusFamily());
            tvTsb.setText(inqMaster.getCusInfo().getCusTSB());
            //spRange.setText(inqMaster.getInqInvestRange());
            //edtFeedBackDays.setText(inqMaster.getInqFeedbackTime());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());
//            edtName.setText(inqMaster.getCusInfo().getCusName());

        }

    }
}
