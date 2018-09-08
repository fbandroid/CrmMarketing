package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel33.EcsList;
import com.crmmarketing.hmt.gsonmodel33.Getinquiry;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ECSApprovalListFragment extends Fragment {

    private Context context;
    private RecyclerView rvList;
    private String userName;
    private String id;
    private String role;
    private String branchId;
    private CustomAdapter customAdapter;
    private ProgressBar progressBar;
    private ArrayList<Getinquiry> getinquiryArrayList;
    private RetrofitClient.GetECSList getECSList;

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

        getinquiryArrayList = new ArrayList<>();
        getECSList = RetrofitClient.ecslistOf(Constants.BASE_URL);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branchId = sharedPref.getString("branch", "");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("ECS List");
        return inflater.inflate(R.layout.fragment_ecs_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvList = (RecyclerView) view.findViewById(R.id.rvList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvList.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.addItemDecoration(dividerItemDecoration);

        getECSList.getecslist(id, role, branchId).enqueue(new Callback<EcsList>() {
            @Override
            public void onResponse(Call<EcsList> call, Response<EcsList> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    getinquiryArrayList = (ArrayList<Getinquiry>) response.body().getGetinquiry();

                    if (getinquiryArrayList!=null && getinquiryArrayList.size() > 0) {
                        customAdapter = new CustomAdapter(getinquiryArrayList);
                        rvList.setAdapter(customAdapter);
                    } else {
                        customAdapter = new CustomAdapter(new ArrayList<Getinquiry>());
                        rvList.setAdapter(customAdapter);
                    }

                }

            }

            @Override
            public void onFailure(Call<EcsList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("ECS List");
    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Getinquiry> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Getinquiry> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_ecs_list, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvCusName.setText(mDataSet.get(position).getCusInfo().getName());
            viewHolder.tvInqDate.setText(mDataSet.get(position).getInqMaster().getInqDatetime());
            viewHolder.tvEmpName.setText(mDataSet.get(position).getInqMaster().getInqChildname());

            if (mDataSet.get(position).getInqMaster().getEcsStatus()!=null &&mDataSet.get(position).getInqMaster().getEcsStatus().equals("1")) {
                viewHolder.tvStatus.setText("Approved");
            } else if(mDataSet.get(position).getInqMaster().getEcsStatus()!=null &&mDataSet.get(position).getInqMaster().getEcsStatus().equals("2")) {
                viewHolder.tvStatus.setText("Rejected");
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
            private TextView tvCusName;
            private TextView tvInqDate;
            private Button btnAction;
            private TextView tvEmpName;
            private TextView tvStatus;


            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                tvCusName = (TextView) v.findViewById(R.id.tvCusName);
                tvInqDate = (TextView) v.findViewById(R.id.tvInqDate);
                btnAction = (Button) v.findViewById(R.id.btnAction);
                tvStatus = (TextView) v.findViewById(R.id.tvStatus);
                btnAction.setVisibility(View.GONE);
                tvEmpName = (TextView) v.findViewById(R.id.tvEmpName);

                if (role.equals("6") || role.equals("7") || role.equals("8")) {
                    btnAction.setVisibility(View.VISIBLE);

                } else {
                    btnAction.setVisibility(View.GONE);
                }


                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putString("inq_id", mDataSet.get(getAdapterPosition()).getInqMaster().getInqId());
                        Fragment fragment = new ECSApproveFragment();
                        fragment.setArguments(bundle);

                        addFragment(fragment, ECSApprovalListFragment.this);

                    }
                });


            }


        }
    }
}
