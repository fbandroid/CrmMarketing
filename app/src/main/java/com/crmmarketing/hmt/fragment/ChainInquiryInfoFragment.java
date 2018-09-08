package com.crmmarketing.hmt.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.ChainInquiry;
import com.crmmarketing.hmt.GsonModel.Chaindatum;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.MyRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ChainInquiryInfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private RetrofitClient.APIServicegetChainInq apiServicegetChainInq;
    private ProgressBar progressBar;
    private String inqId;
    private InqMaster inqMaster;
    private Context context;
    private ArrayList<Chaindatum> chainArrayList;
    private CustomAdapter customadapter;
    private LinearLayout llBottom;
    private CheckBox cbApprove;
    private Button btnApprove;
    private String role;
    private RetrofitClient.ApproveByOperation approveByOperation;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        role = sharedPref.getString("role", null);

        approveByOperation = RetrofitClient.approveInq(Constants.BASE_URL);

        context = getActivity();
        chainArrayList = new ArrayList<>();

        if (getParentFragment() != null) {

            if (getParentFragment().getArguments() != null) {

                if (getParentFragment().getArguments().getParcelable("detail") != null) {

                    inqMaster = getParentFragment().getArguments().getParcelable("detail");
                }
            }


        } else {
            inqMaster = ((InquirtDetailActivity) context).inqMaster;
        }

        if (inqMaster != null) {

            inqId = inqMaster.getInqMasterId();
        }

        apiServicegetChainInq = RetrofitClient.getInqChain(Constants.BASE_URL);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chain_info_inquiry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_chain_info_rv);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        llBottom = (LinearLayout) view.findViewById(R.id.llBottom);
        cbApprove = (CheckBox) view.findViewById(R.id.cbApprove);
        btnApprove = (Button) view.findViewById(R.id.btnApprove);

        cbApprove.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });


        btnApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbApprove.isChecked()) {

                    showAlertDialog(inqId);

                }
            }
        });

        if (role.equals("6") || role.equals("7") || role.equals("8")) {
            llBottom.setVisibility(View.VISIBLE);
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);

        progressBar.setVisibility(View.VISIBLE);

        apiServicegetChainInq.checkInqByDate(inqId).enqueue(new Callback<ChainInquiry>() {
            @Override
            public void onResponse(Call<ChainInquiry> call, Response<ChainInquiry> response) {

                progressBar.setVisibility(View.GONE);
                if (response != null) {

                    chainArrayList = (ArrayList<Chaindatum>) response.body().getChaindata();


                    if (chainArrayList.size() > 0) {

                        customadapter = new CustomAdapter(chainArrayList);
                        recyclerView.setAdapter(customadapter);
                    } else {
                        Toast.makeText(getActivity(), "No Inquiry History", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<ChainInquiry> call, Throwable t) {

            }
        });


    }

    public void showAlertDialog(final String inqId) {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());


        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                showProgressDialog();

                approveByOperation.approvebyop(inqId).enqueue(new Callback<MyRes>() {
                    @Override
                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.isSuccessful()) {
                            if (response.body().getMsg().equalsIgnoreCase("true")) {

                                if (getActivity() != null) {
                                    Toast.makeText(getActivity(), "Successfully Approved", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                    getFragmentManager().popBackStack();

                                }


                            } else {
                                Toast.makeText(getActivity(), "Not Successfully Approved", Toast.LENGTH_SHORT).show();

                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<MyRes> call, Throwable t) {
                        Toast.makeText(getActivity(), "Not Successfully Approved", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                });

            }


        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass


            }
        });
        android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

        private ArrayList<Chaindatum> chaindata;

        CustomAdapter(ArrayList<Chaindatum> chaindata) {
            this.chaindata = chaindata;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chain_inq_fragment, parent, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.tvBranch.setText(chaindata.get(position).getInqBranchname());
            holder.tvInqDate.setText(chaindata.get(position).getInqDate());
            holder.tvFeedBackDate.setText(chaindata.get(position).getInqFeedbacktime());
            holder.tvPrList.setText(chaindata.get(position).getProducts());
            holder.tvRemark.setText(chaindata.get(position).getInqRemark());


            if (chaindata.get(position).getInq_status().equals("2")) {

                holder.tvStatus.setTextColor(getResources().getColor(R.color.dark_red));
                holder.tvStatus.setText("canceled");


            } else if (chaindata.get(position).getInq_status().equals("0")) {
                holder.tvStatus.setTextColor(getResources().getColor(R.color.blue));
                holder.tvStatus.setText("pending");
            } else if (chaindata.get(position).getInq_status().equals("1")) {
                holder.tvStatus.setTextColor(getResources().getColor(R.color.dark_green));
                holder.tvStatus.setText("confirm");
            } else if (chaindata.get(position).getInq_status().equals("3")) {
                holder.tvStatus.setTextColor(getResources().getColor(R.color.darkPink));
                holder.tvStatus.setText("pending\n with confirm");
            }


        }

        @Override
        public int getItemCount() {
            return chaindata.size();
        }


        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvInqDate;
            private final TextView tvFeedBackDate;
            private final TextView tvPrList;
            private final TextView tvBranch;
            private final TextView tvRemark;
            private final TextView tvStatus;


            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                });


                tvBranch = (TextView) v.findViewById(R.id.tvBranchName);
                tvInqDate = (TextView) v.findViewById(R.id.tvInqDate);
                tvFeedBackDate = (TextView) v.findViewById(R.id.tvInqFeedBackDate);
                tvRemark = (TextView) v.findViewById(R.id.tvRemark);
                tvPrList = (TextView) v.findViewById(R.id.tvPrList);
                tvStatus = (TextView) v.findViewById(R.id.tvStatus);


            }


        }

    }
}
