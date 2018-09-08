package com.crmmarketing.hmt.fragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.InvitedCustomer;
import com.crmmarketing.hmt.gsonmodel22.Report;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class InvitedCustomerFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RetrofitClient.InvitedCustomer invitedCustomer;
    private String id;
    private String event_id;
    private CustomAdapter customAdapter;
    private ArrayList<Report> reports;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reports=new ArrayList<>();

        invitedCustomer = RetrofitClient.invitedCustomer(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);

        id = String.valueOf(sharedPref.getInt("id", 0));

        if (getArguments() != null) {

            event_id = getArguments().getString("event_id");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_invited_customer, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvInvitedList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        invitedCustomer.getInvitedCustomer(id, event_id).enqueue(new Callback<InvitedCustomer>() {
            @Override
            public void onResponse(Call<InvitedCustomer> call, Response<InvitedCustomer> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                  reports = (ArrayList<Report>) response.body().getReports();


                    if (reports != null && reports.size() > 0) {

                        customAdapter = new CustomAdapter(reports);
                        recyclerView.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onFailure(Call<InvitedCustomer> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (customAdapter != null)
            customAdapter.setFilter(filter(reports, newText));

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem addItem = menu.findItem(R.id.action_add_employee);

        addItem.setVisible(false);

        final android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Report> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Report> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_event_detail, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            viewHolder.tvClientName.setText(mDataSet.get(position).getName());
            viewHolder.tvClientMob.setText(mDataSet.get(position).getCode());
            viewHolder.tvCode.setText(mDataSet.get(position).getMobile());

            if (mDataSet.get(position).getAttendance().equals("2")) {
                viewHolder.tvPresent.setText("Present");
            } else {
                viewHolder.tvPresent.setText("");
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
            private final TextView tvClientName;
            private final TextView tvClientMob;
            private final TextView tvCode;
            private final CheckBox cbPA;
            private final TextView tvPresent;


            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                tvClientName = (TextView) v.findViewById(R.id.tvClientName);
                tvClientMob = (TextView) v.findViewById(R.id.tvClientMob);
                cbPA = (CheckBox) v.findViewById(R.id.cbPA);
                tvCode = (TextView) v.findViewById(R.id.tvTSBCode);
                tvPresent = (TextView) v.findViewById(R.id.tvPresent);
                tvCode.setVisibility(View.VISIBLE);
                tvPresent.setVisibility(View.VISIBLE);


                cbPA.setVisibility(View.GONE);

            }

        }

        void setFilter(List<Report> empInfo) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(empInfo);
            notifyDataSetChanged();
        }
    }

    private List<Report> filter(List<Report> models, String query) {
        query = query.toLowerCase().trim();
        final List<Report> filteredModelList = new ArrayList<>();
        for (Report model : models) {
            final String text = model.getName().toLowerCase();
            final String empId = model.getCode().toLowerCase();
            final String pan = model.getPan().toLowerCase();
            if (text.contains(query) || empId.contains(query) || pan.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
