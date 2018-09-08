package com.crmmarketing.hmt.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.TravelReport;
import com.crmmarketing.hmt.gsonmodel22.Traveldatum;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 20-06-2017.
 */

public class TravellingReportDetailFragment extends Fragment {

    private RecyclerView rvTravelDetail;
    private String uid;
    private String month;
    private RetrofitClient.TravelReport travelReport;
    private ProgressBar progressBar;
    private CustomAdapter customAdapter;
    private ArrayList<Traveldatum> traveldatumArrayList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            uid = getArguments().getString("u_id");
            month = getArguments().getString("month");
        }
        travelReport = RetrofitClient.getTravelReport(Constants.BASE_URL);
        traveldatumArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_travel_report_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTravelDetail = (RecyclerView) view.findViewById(R.id.rvTravelDetail);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvTravelDetail.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        rvTravelDetail.addItemDecoration(dividerItemDecoration);
        rvTravelDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        travelReport.gettravelreport(uid, month).enqueue(new Callback<TravelReport>() {
            @Override
            public void onResponse(Call<TravelReport> call, Response<TravelReport> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    traveldatumArrayList = (ArrayList<Traveldatum>) response.body().getTraveldata();
                    customAdapter = new CustomAdapter(traveldatumArrayList);
                    rvTravelDetail.setAdapter(customAdapter);

                }
            }

            @Override
            public void onFailure(Call<TravelReport> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Traveldatum> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Traveldatum> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_travel_report_detail, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvDate.setText(mDataSet.get(position).getDate());
            viewHolder.tvSrc.setText(mDataSet.get(position).getSrc());
            viewHolder.tvDest.setText(mDataSet.get(position).getDest());
            viewHolder.tvTotalKm.setText(mDataSet.get(position).getTotaldistance());
            viewHolder.tvUser.setText(mDataSet.get(position).getUName());

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
            private TextView tvDate;
            private TextView tvSrc;
            private TextView tvDest;
            private TextView tvTotalKm;
            private TextView tvUser;


            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                tvDate = (TextView) v.findViewById(R.id.tvDate);
                tvSrc = (TextView) v.findViewById(R.id.tvSrc);
                tvDest = (TextView) v.findViewById(R.id.tvDest);
                tvTotalKm = (TextView) v.findViewById(R.id.tvTotalKm);
                tvUser = (TextView) v.findViewById(R.id.tvUser);

            }


        }
    }
}
