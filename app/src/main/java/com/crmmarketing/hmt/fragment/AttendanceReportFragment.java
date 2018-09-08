package com.crmmarketing.hmt.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.AttendanceSummary;
import com.crmmarketing.hmt.GsonModel.Attendancelist;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 15-05-2017.
 */

public class AttendanceReportFragment extends Fragment {


    private Spinner spMonth;
    private Spinner spEmployee;
    private RecyclerView recyclerView;
    private TextView tvEmptyView;
    private String uid;
    private List<Attendancelist> attendanceSummaryArrayList;
    private RetrofitClient.APIServicegetAttendanceSummary apiServicegetAttendanceSummary;
    private CustomAdapter customAdapter;
    private ProgressBar progressBar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        uid = String.valueOf(sharedPref.getInt("id", 0));

        apiServicegetAttendanceSummary = RetrofitClient.getAttendanceSummary(Constants.BASE_URL);
        attendanceSummaryArrayList = new ArrayList<>();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance_report, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spMonth = (Spinner) view.findViewById(R.id.spMonth);
        spEmployee = (Spinner) view.findViewById(R.id.spEmployee);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvAttendance);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        tvEmptyView = (TextView) view.findViewById(R.id.empty_view);


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

                apiServicegetAttendanceSummary.getAttendanceSummary(uid, String.valueOf(spMonth.getSelectedItemPosition()), Constants.root).enqueue(new Callback<AttendanceSummary>() {
                    @Override
                    public void onResponse(Call<AttendanceSummary> call, Response<AttendanceSummary> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response != null) {

                            attendanceSummaryArrayList = response.body().getAttendancelist();


                        }


                        if (attendanceSummaryArrayList.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                            tvEmptyView.setVisibility(View.GONE);
                            customAdapter = new CustomAdapter((ArrayList<Attendancelist>) attendanceSummaryArrayList);
                            recyclerView.setAdapter(customAdapter);

                        } else {
                            recyclerView.setVisibility(View.GONE);
                            tvEmptyView.setVisibility(View.VISIBLE);
                            Toast.makeText(getActivity(), "No data available ", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AttendanceSummary> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "Internal error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    // call network for get attendance summary


    //custom adapter for showing attendance summary

    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Attendancelist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Attendancelist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_attendance_summary_data, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.tvEmpName.setText(mDataSet.get(position).getUName());
            viewHolder.tvBranchName.setText(mDataSet.get(position).getBranch());
            viewHolder.tvPresentDay.setText(mDataSet.get(position).getTotaldays());

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
            private final TextView tvEmpName;
            private final TextView tvBranchName;
            private final TextView tvPresentDay;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO network call for detail attendance

                        Fragment fragment = new AttendanceDetailReportFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("id", mDataSet.get(getAdapterPosition()).getUId());
                        bundle.putString("name", mDataSet.get(getAdapterPosition()).getUName());
                        bundle.putString("month", String.valueOf(spMonth.getSelectedItemPosition()));

                        fragment.setArguments(bundle);


                        addFragment(fragment, AttendanceReportFragment.this);


                    }
                });
                tvEmpName = (TextView) v.findViewById(R.id.tvEmpName);
                tvBranchName = (TextView) v.findViewById(R.id.tvMobile);
                tvPresentDay = (TextView) v.findViewById(R.id.tvPresentDay);
            }


        }
    }

}
