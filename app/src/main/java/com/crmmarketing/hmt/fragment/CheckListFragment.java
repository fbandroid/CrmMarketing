package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.CheckListAll;
import com.crmmarketing.hmt.GsonModel.CheckListModel;
import com.crmmarketing.hmt.GsonModel.Checklist;
import com.crmmarketing.hmt.GsonModel.ConfirmCheckList;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.MyRes;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 30-05-2017.
 */

public class CheckListFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button btnSend;
    private RetrofitClient.getCheckListAll getCheckList;
    private String id;
    private ArrayList<Checklist> checklistArrayList;
    private boolean isChecked = false;
    private RetrofitClient.Confirmchecklistapi confirmchecklistapi;
    private ProgressDialog progressDialog;
    private Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checklistArrayList = new ArrayList<>();

        confirmchecklistapi = RetrofitClient.confirmchecklist(Constants.BASE_URL);

        getCheckList = RetrofitClient.getCheckList(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_check_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((HomeActivity) context).setTitle("Document CheckList");


        recyclerView = (RecyclerView) view.findViewById(R.id.rvCheckList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnSend = (Button) view.findViewById(R.id.btnSendCheckList);

        showProgressDialog();
        getCheckList.getAllChecklist(id).enqueue(new Callback<CheckListAll>() {
            @Override
            public void onResponse(Call<CheckListAll> call, Response<CheckListAll> response) {

                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                if (response.isSuccessful()) {


                    checklistArrayList = (ArrayList<Checklist>) response.body().getChecklist();

                    if (checklistArrayList != null && checklistArrayList.size() > 0) {
                        CustomAdapter customAdapter = new CustomAdapter((ArrayList<Checklist>) response.body().getChecklist());
                        recyclerView.setAdapter(customAdapter);
                    } else {
                        btnSend.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "No Data Available", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<CheckListAll> call, Throwable t) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmCheckList confirmCheckList = new ConfirmCheckList();
                ArrayList<CheckListModel> checkListModels = new ArrayList<CheckListModel>();

                if (checklistArrayList != null) {
                    for (int i = 0; i < checklistArrayList.size(); i++) {

                        if (checklistArrayList.get(i).isChecked()) {

                            CheckListModel checkListModel = new CheckListModel();
                            checkListModel.setId(checklistArrayList.get(i).getId());

                            checkListModels.add(checkListModel);

                            isChecked = true;

                        }
                    }
                    confirmCheckList.setList(checkListModels);
                }


                if (isChecked) {


                    // call web service for insert

                    showProgressDialog();

                    confirmchecklistapi.confirmCheckList(confirmCheckList).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {


                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {

                                    Toast.makeText(getActivity(), "Successfully Send", Toast.LENGTH_SHORT).show();
                                    getActivity().getFragmentManager().popBackStack();
                                }
                            }

                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), "No Data Selected", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Document CheckList");

    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Checklist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Checklist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_check_list, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            if (mDataSet.get(position).getStatus().equals("0")) {

                viewHolder.tvStatus.setText("pending");

            } else if (mDataSet.get(position).getStatus().equals("1")) {
                viewHolder.tvStatus.setText("confirm");
                viewHolder.checkBox.setEnabled(false);
            }

            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mDataSet.get(position).setChecked(isChecked);


                }
            });

            viewHolder.checkBox.setText(mDataSet.get(position).getRemark());
            viewHolder.tvDate.setText(mDataSet.get(position).getDateTime());
            viewHolder.tvChildname.setText(mDataSet.get(position).getCus_name());

            // Get element from your dataset at this position and replace the contents of the view
            // with that element

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
            private final CheckBox checkBox;
            private final TextView tvChildname;
            private final TextView tvDate;
            private final TextView tvStatus;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

                checkBox = (CheckBox) v.findViewById(R.id.cbChkList);
                tvChildname = (TextView) v.findViewById(R.id.tvInqChildName);
                tvDate = (TextView) v.findViewById(R.id.tvInqDate);
                tvStatus = (TextView) v.findViewById(R.id.tvStatus);


            }


        }
    }
}
