package com.crmmarketing.hmt.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.GsonModel.AttendanceHistory22;
import com.crmmarketing.hmt.GsonModel.Attendancehistory;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.AttendanceDetail;
import com.crmmarketing.hmt.gsonmodel22.Attendancelist;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AttendanceDetailReportFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView tvTotal;
    private TextView tvMonth;
    private TextView tvEmpName;
    private ProgressBar progressBar;
    private String id;
    private String month;
    private String name;
    private String monthName;
    private RetrofitClient.APIServicegetAttendanceDetail apiServicegetAttendanceDetail;
    private RetrofitClient.APIServicegetAttendanceHistory apiServicegetAttendanceHistory;
    private ArrayList<Attendancelist> attendancelistArrayList;
    private CustomAdapter customAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        attendancelistArrayList = new ArrayList<>();

        if (getArguments() != null) {

            id = getArguments().getString("id");
            name = getArguments().getString("name");
            month = getArguments().getString("month");
        }


        apiServicegetAttendanceDetail = RetrofitClient.getAttendanceDetail(Constants.BASE_URL);
        apiServicegetAttendanceHistory = RetrofitClient.getAttendanceHistory(Constants.BASE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attendance_report_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.rvAttendanceDetail);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        tvMonth = (TextView) view.findViewById(R.id.tvMonth);
        tvEmpName = (TextView) view.findViewById(R.id.tvEmpName);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        tvEmpName.setText(name);

        for (int i = 0; i < getResources().getStringArray(R.array.month).length; i++) {

            if (Integer.parseInt(month) == i) {
                monthName = getResources().getStringArray(R.array.month)[i];
            }

        }

        tvMonth.setText(monthName);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        progressBar.setVisibility(View.VISIBLE);

        apiServicegetAttendanceDetail.getAttendanceSummary(id, month, Constants.root).enqueue(new Callback<AttendanceDetail>() {
            @Override
            public void onResponse(Call<AttendanceDetail> call, Response<AttendanceDetail> response) {

                progressBar.setVisibility(View.GONE);

                if (response != null) {

                    attendancelistArrayList = (ArrayList<Attendancelist>) response.body().getAttendancelist();


                    if (attendancelistArrayList.size() > 0) {

                        customAdapter = new CustomAdapter(attendancelistArrayList);
                        recyclerView.setAdapter(customAdapter);

                    } else {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "No data Available", Toast.LENGTH_SHORT).show();
                        }
                    }


                }


            }

            @Override
            public void onFailure(Call<AttendanceDetail> call, Throwable t) {

                if (getActivity() != null) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "Internal error", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    // TODO call network  for attendance detail punch in and punch out

    //TODO custom adapter for showing report in  Recycler view


    private void addFragment(Fragment toAdd, Fragment current) {

        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    void showAttendanceHistory(ArrayList<Attendancehistory> attendancehistories) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_attendance_history, null);
        dialogBuilder.setView(dialogView);

        String[] splitedPunchIn = attendancehistories.get(0).getPunchin().split("\\s+");

        TextView tvDate = (TextView) dialogView.findViewById(R.id.tvDate);

        tvDate.setText(splitedPunchIn[0]);


        RecyclerView recyclerView = (RecyclerView) dialogView.findViewById(R.id.rvAtthistory);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        CustomAdapter22 customAdapter22 = new CustomAdapter22(attendancehistories);
        recyclerView.setAdapter(customAdapter22);

        dialogBuilder.show();


    }

    class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
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
                    .inflate(R.layout.row_attendance_report_detail_data, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            String[] splitedPunchIn = mDataSet.get(position).getPunchin().split("\\s+");
            String[] splitedPunchOut = mDataSet.get(position).getPunchout().split("\\s+");

            viewHolder.tvDate.setText(splitedPunchIn[0]);
            viewHolder.tvPunchIn.setText(splitedPunchIn[1]);
            viewHolder.tvPunchOut.setText(splitedPunchOut[1]);
            viewHolder.tvTotal.setText(mDataSet.get(position).getTotalhour());


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

            private final TextView tvDate;
            private final TextView tvPunchIn;
            private final TextView tvPunchOut;
            private final TextView tvTotal;

            public ViewHolder(View v) {
                super(v);

                tvDate = (TextView) v.findViewById(R.id.tvDate);
                tvPunchIn = (TextView) v.findViewById(R.id.tvInTime);
                tvPunchOut = (TextView) v.findViewById(R.id.tvOutTime);
                tvTotal = (TextView) v.findViewById(R.id.tvTotal);


                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO network call for detail attendance


                        apiServicegetAttendanceHistory.getAttendanceSummary(id, tvDate.getText().toString(), Constants.root).enqueue(new Callback<AttendanceHistory22>() {
                            @Override
                            public void onResponse(Call<AttendanceHistory22> call, Response<AttendanceHistory22> response) {

                                ArrayList<Attendancehistory> attendancehistoryArrayList = new ArrayList<Attendancehistory>();

                                if (response != null) {

                                    attendancehistoryArrayList = (ArrayList<Attendancehistory>) response.body().getAttendancehistory();


                                    if (attendancehistoryArrayList.size() > 0) {

                                        showAttendanceHistory(attendancehistoryArrayList);


                                    } else {
                                        if (getActivity() != null) {
                                            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }


                            }

                            @Override
                            public void onFailure(Call<AttendanceHistory22> call, Throwable t) {
                                if (getActivity() != null) {
                                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }
                });


            }


        }
    }

    class CustomAdapter22 extends RecyclerView.Adapter<CustomAdapter22.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Attendancehistory> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter22(ArrayList<Attendancehistory> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_attendance_history, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            String[] splitedPunchIn = mDataSet.get(position).getPunchin().split("\\s+");
            String[] splitedPunchOut = mDataSet.get(position).getPunchout().split("\\s+");

            viewHolder.tvPunchIn.setText(splitedPunchIn[1]);
            viewHolder.tvPunchOut.setText(splitedPunchOut[1]);

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


            private final TextView tvPunchIn;
            private final TextView tvPunchOut;


            public ViewHolder(View v) {
                super(v);


                tvPunchIn = (TextView) v.findViewById(R.id.tvPunchIn);
                tvPunchOut = (TextView) v.findViewById(R.id.tvPunchOut);


                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // TODO network call for detail attendance


                    }
                });


            }


        }
    }


}
