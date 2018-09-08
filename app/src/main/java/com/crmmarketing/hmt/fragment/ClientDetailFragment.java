package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.ViewDocumentActivity;
import com.crmmarketing.hmt.gsonmodel22.Customer;

/**
 * Created by USER on 14-06-2017.
 */

public class ClientDetailFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
    private Context context;
    private long lastclick;

    private TextView tvClientName;
    private TextView tvClientMob;
    private TextView tvTSBCode;
    private TextView tvPAN;
    private TextView tvEmail;
    private TextView tvDOB;
    private TextView tvFamilyCode;
    private TextView tvRM;
    private TextView tvAddr;
    private TextView tvMFCode;
    private TextView tvPMSCode;
    private TextView tvINSCode;
    private Button btnViewDoc;
    private Customer customer;


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

        if (getArguments() != null) {
            customer = getArguments().getParcelable("detail");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Client Info");
        return inflater.inflate(R.layout.fragment_client_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvClientName = (TextView) view.findViewById(R.id.tvClientName);
        tvClientMob = (TextView) view.findViewById(R.id.tvClientMob);
        tvTSBCode = (TextView) view.findViewById(R.id.tvTSBCode);
        tvPAN = (TextView) view.findViewById(R.id.tvPAN);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvDOB = (TextView) view.findViewById(R.id.tvDOB);
        tvFamilyCode = (TextView) view.findViewById(R.id.tvFamilyCode);
        tvRM = (TextView) view.findViewById(R.id.tvRM);
        tvAddr = (TextView) view.findViewById(R.id.tvAddr);
        btnViewDoc = (Button) view.findViewById(R.id.btnViewDoc);
        tvMFCode = view.findViewById(R.id.tvMFCode);
        tvPMSCode = view.findViewById(R.id.tvPMSCode);
        tvINSCode = view.findViewById(R.id.tvINSCode);

        if (customer != null) {

            tvClientName.setText(customer.getName());
            tvClientMob.setText(customer.getMobile());
            tvDOB.setText(customer.getDob());
            tvAddr.setText(customer.getAddress());
            tvPAN.setText(customer.getPan());
            tvRM.setText(customer.getRmName());
            tvEmail.setText(customer.getEmail());
            tvTSBCode.setText(customer.getCode());
            tvFamilyCode.setText(customer.getFamilyCode());
            tvMFCode.setText(customer.getMfCode());
            tvPMSCode.setText(customer.getPmscode());
            tvINSCode.setText(customer.getInsCode());


        }

        btnViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewDocumentActivity.class);
                intent.putExtra("cu_id", customer.getCuId());
                startActivity(intent);
            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Client Info");
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
}
