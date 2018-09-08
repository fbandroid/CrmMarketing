package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 22-08-2017.
 */

public class ECSApproveFragment extends Fragment {

    private Context context;
    private String userName;
    private String id;
    private String role;
    private String branchId;

    private ProgressDialog progressDialog;
    private Spinner spType;
    private EditText edtRemark;
    private Button btnSubmit;

    private String inq_id;
    private RetrofitClient.ECSAction ecsAction;


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

            inq_id = getArguments().getString("inq_id");
        }

        ecsAction = RetrofitClient.setApproveEcs(Constants.BASE_URL);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branchId = sharedPref.getString("branch", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ecs_approve, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((HomeActivity) context).setTitle("ECS Approve");
        spType = (Spinner) view.findViewById(R.id.spType);
        edtRemark = (EditText) view.findViewById(R.id.edtRemark);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!edtRemark.getText().toString().trim().isEmpty()) {

                    showProgressDialog();
                    ecsAction.setEcsAction(id, inq_id, edtRemark.getText().toString().trim(), String.valueOf(spType.getSelectedItemPosition() + 1))
                            .enqueue(new Callback<MyRes>() {
                                @Override
                                public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }

                                    if (response.isSuccessful()) {
                                        if (response.body().getMsg().equalsIgnoreCase("true")) {
                                            Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();

                                            Fragment fragment = getFragmentManager().findFragmentByTag(OperationExFragment.class.getSimpleName());
                                            Fragment fragment1 = getFragmentManager().findFragmentByTag(OperationTLFragment.class.getSimpleName());
                                            Fragment fragment2 = getFragmentManager().findFragmentByTag(OperationMEFragment.class.getSimpleName());

                                            if (fragment != null) {
                                                getFragmentManager().popBackStack(OperationExFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            } else if (fragment1 != null) {
                                                getFragmentManager().popBackStack(OperationTLFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            } else if (fragment2 != null) {
                                                getFragmentManager().popBackStack(OperationMEFragment.class.getSimpleName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                            }


                                        } else {
                                            Toast.makeText(getActivity(), "Not Successfully updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<MyRes> call, Throwable t) {
                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }

                                    Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("ECS Approve");
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
