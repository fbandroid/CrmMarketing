package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.LeaveHistoryList;
import com.crmmarketing.hmt.gsonmodel22.Leavehistory;
import com.crmmarketing.hmt.model.MyRes;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ViewLeaveFragment extends Fragment {

    private Context context;
    private RecyclerView rvLeave;
    private Spinner spMonth;
    private RetrofitClient.ViewLeave viewLeave;
    private ProgressBar progressBar;
    private String uid;
    private String role;
    private RetrofitClient.CancelLeave cancelLeave;
    private ProgressDialog progressDialog;


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
        viewLeave = RetrofitClient.viewleave(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        uid = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        cancelLeave = RetrofitClient.cancelleaveOf(Constants.BASE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ((HomeActivity) context).setTitle("View Leave");
        return inflater.inflate(R.layout.fragment_view_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spMonth = (Spinner) view.findViewById(R.id.spMonth);
        rvLeave = (RecyclerView) view.findViewById(R.id.rvLeave);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvLeave.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        rvLeave.addItemDecoration(dividerItemDecoration);
        rvLeave.setLayoutManager(new LinearLayoutManager(getActivity()));


        int month = Calendar.getInstance().get(Calendar.MONTH);


        for (int i = 0; i < 12; i++) {

            if (i == month) {

                spMonth.setSelection(i);
            }
        }

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                progressBar.setVisibility(View.VISIBLE);

                viewLeave.getLeavelist(uid, String.valueOf(spMonth.getSelectedItemPosition()), role).enqueue(new Callback<LeaveHistoryList>() {
                    @Override
                    public void onResponse(Call<LeaveHistoryList> call, Response<LeaveHistoryList> response) {


                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {

                            final ArrayList<Leavehistory> leavehistoryArrayList = (ArrayList<Leavehistory>) response.body().getLeavehistory();

                            if (leavehistoryArrayList.size() > 0) {

                                CustomAdapter customAdapter = new CustomAdapter(leavehistoryArrayList);
                                rvLeave.setAdapter(customAdapter);
                            } else {
                                CustomAdapter customAdapter = new CustomAdapter(leavehistoryArrayList);
                                rvLeave.setAdapter(customAdapter);
                                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LeaveHistoryList> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewLeave.getLeavelist(uid, String.valueOf(spMonth.getSelectedItemPosition()), role).enqueue(new Callback<LeaveHistoryList>() {
            @Override
            public void onResponse(Call<LeaveHistoryList> call, Response<LeaveHistoryList> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    final ArrayList<Leavehistory> leavehistoryArrayList = (ArrayList<Leavehistory>) response.body().getLeavehistory();

                    if (leavehistoryArrayList.size() > 0) {

                        CustomAdapter customAdapter = new CustomAdapter(leavehistoryArrayList);
                        rvLeave.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LeaveHistoryList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("View Leave");
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Leavehistory> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Leavehistory> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_view_leave, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvName.setText(mDataSet.get(position).getUName());
            viewHolder.tvdate.setText("From- ".concat(mDataSet.get(position).getLeaveDate()));
            viewHolder.tvEndDate.setText("To- ".concat(mDataSet.get(position).getEndDate()));
            viewHolder.tvReason.setText(mDataSet.get(position).getReason());

            if (mDataSet.get(position).getType().equals("1")) {
                viewHolder.tvType.setText("D.L");
            } else if (mDataSet.get(position).getType().equals("2")) {
                viewHolder.tvType.setText("C.L");
            }

            switch (mDataSet.get(position).getStatus()) {

                case "0":
                    viewHolder.tvStatus.setText("pending");
                    viewHolder.ivDelete.setVisibility(View.VISIBLE);
                    break;
                case "1":
                    viewHolder.tvStatus.setText("Approved");
                    viewHolder.ivDelete.setVisibility(View.GONE);
                    break;
                case "2":
                    viewHolder.tvStatus.setText("Canceled");
                    viewHolder.ivDelete.setVisibility(View.GONE);
                    break;
            }

        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvName;
            private final TextView tvdate;
            private final TextView tvEndDate;
            private final TextView tvReason;
            private final TextView tvStatus;
            private final ImageView ivDelete;
            private final TextView tvType;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                tvName = (TextView) v.findViewById(R.id.tvName);
                tvdate = (TextView) v.findViewById(R.id.tvDate);
                tvReason = (TextView) v.findViewById(R.id.tvReason);
                tvStatus = (TextView) v.findViewById(R.id.tvStatus);
                tvEndDate = (TextView) v.findViewById(R.id.tvEndDate);
                ivDelete = (ImageView) v.findViewById(R.id.ivCancel);
                tvType = (TextView) v.findViewById(R.id.tvType);

                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showProgressDialog();
                        cancelLeave.cancelleave(mDataSet.get(getAdapterPosition()).getId()).enqueue(new Callback<MyRes>() {
                            @Override
                            public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }

                                if (response.isSuccessful()) {

                                    if (response.body().getMsg().equalsIgnoreCase("true")) {
                                        Toast.makeText(getActivity(), "Leave cancled successfully", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    } else {
                                        Toast.makeText(getActivity(), "Not successfully canceled", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    }
                                }

                            }

                            @Override
                            public void onFailure(Call<MyRes> call, Throwable t) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                if (getActivity() != null) {
                                    Toast.makeText(getActivity(), "Not successfully canceled", Toast.LENGTH_SHORT).show();

                                }
                                getFragmentManager().popBackStack();
                            }
                        });

                    }
                });

            }


        }
    }
}
