package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.GsonModel.UserDetail;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class TravellingReportFragment extends Fragment {

    private Spinner spMonth;
    private RecyclerView rvTravelList;
    private Context context;
    private CustomAdapter customAdapter;
    private ArrayList<User> userArrayList;
    private RetrofitClient.EmpList empList;
    private String uid;
    private ProgressBar progressBar;


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
        userArrayList = new ArrayList<>();
        empList = RetrofitClient.getEmpList(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        uid = String.valueOf(sharedPref.getInt("id", 0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Travelling Report");
        return inflater.inflate(R.layout.fragment_travel_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spMonth = (Spinner) view.findViewById(R.id.spMonth);
        rvTravelList = (RecyclerView) view.findViewById(R.id.rvTravelList);
        rvTravelList.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

//        empList.getemplist(id, Constants.root).enqueue(new Callback<UserDetail>() {
//            @Override
//            public void onResponse(Call<UserDetail> call, Response<UserDetail> response) {
//
//                progressBar.setVisibility(View.GONE);
//
//                if (response.isSuccessful()) {
//
//                    userArrayList = (ArrayList<User>) response.body().getUsers();
//
//                    for(int i=0;i<userArrayList.size();i++){
//                        if(userArrayList.get(i).getUId().equals("1")){
//                            userArrayList.remove(i);
//                            break;
//                        }
//                    }
//
//                    if (userArrayList.size() > 0) {
//
//                        customAdapter = new CustomAdapter(userArrayList);
//                        rvTravelList.setAdapter(customAdapter);
//                    } else {
//                        Fragment fragment = new TravellingReportDetailFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("u_id", id);
//                        bundle.putString("month", String.valueOf(spMonth.getSelectedItemPosition()));
//                        fragment.setArguments(bundle);
//                        addFragment(fragment, TravellingReportFragment.this);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<UserDetail> call, Throwable t) {
//
//                progressBar.setVisibility(View.GONE);
//            }
//        });

        int month = Calendar.getInstance().get(Calendar.MONTH);


        for (int i = 0; i < 12; i++) {

            if (i == month) {

                spMonth.setSelection(i);
            }
        }

        spMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Fragment fragment = new TravellingReportDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("u_id", uid);
                bundle.putString("month", String.valueOf(spMonth.getSelectedItemPosition()));
                fragment.setArguments(bundle);
                addFragment(fragment, TravellingReportFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Travelling Report");
    }

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<User> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<User> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_travel_report, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvEmpName.setText(mDataSet.get(position).getUName());
            viewHolder.tvBranchName.setText(mDataSet.get(position).getPost());

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

            private final TextView tvEmpName;
            private final TextView tvBranchName;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new TravellingReportDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("u_id", mDataSet.get(getAdapterPosition()).getUId());
                        bundle.putString("month", String.valueOf(spMonth.getSelectedItemPosition()));
                        fragment.setArguments(bundle);
                        addFragment(fragment, TravellingReportFragment.this);


                    }
                });

                tvBranchName = (TextView) v.findViewById(R.id.tvBranchName);
                tvEmpName = (TextView) v.findViewById(R.id.tvEmpName);

            }


        }
    }
}
