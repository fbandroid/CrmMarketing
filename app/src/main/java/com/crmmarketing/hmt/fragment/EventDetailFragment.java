package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.EventDetail;
import com.crmmarketing.hmt.gsonmodel22.Eventcustomer;
import com.crmmarketing.hmt.gsonmodel22.Present;
import com.crmmarketing.hmt.gsonmodel22.Present_;
import com.crmmarketing.hmt.model.MyRes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class EventDetailFragment extends Fragment implements android.support.v7.widget.SearchView.OnQueryTextListener {

    private final long DOUBLE_TAP = 1500;
    private ArrayList<Eventcustomer> eventcustomerArrayList;
    private Context context;
    private long lastclick;
    private RetrofitClient.EventDetailList eventDetailList;
    private RecyclerView rvEventDetail;
    private Button btnTakeAttd;
    private ProgressBar progressBar;
    private String id;
    private String eventId;
    private String eventDate;
    private RetrofitClient.TakeAttd takeAttd;
    private ProgressDialog progressDialog;
    private CustomAdapter customAdapter;

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
        eventDetailList = RetrofitClient.geteventdetaillist(Constants.BASE_URL);
        takeAttd = RetrofitClient.takeattd(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));

        if (getArguments() != null) {

            eventId = getArguments().getString("event_id");
            eventDate = getArguments().getString("event_date");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Event Attendance");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvEventDetail = (RecyclerView) view.findViewById(R.id.rvEventDetail);
        btnTakeAttd = (Button) view.findViewById(R.id.btnTakeAttd);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        rvEventDetail.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        eventDetailList.getEventDetailCustomeList(id, eventId).enqueue(new Callback<EventDetail>() {
            @Override
            public void onResponse(Call<EventDetail> call, Response<EventDetail> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    eventcustomerArrayList = (ArrayList<Eventcustomer>) response.body().getEventcustomers();

                    if (eventcustomerArrayList.size() > 0) {

                        for (int j = 0; j < eventcustomerArrayList.size(); j++) {
                            if (eventcustomerArrayList.get(j).getAttendance().equals("2")) {
                                eventcustomerArrayList.get(j).setChecked(true);
                            }
                        }


                         customAdapter = new CustomAdapter(eventcustomerArrayList);
                        rvEventDetail.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No Data available", Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<EventDetail> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Data available", Toast.LENGTH_SHORT).show();

            }
        });

        btnTakeAttd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDoubleClick()) {
                    return;
                }
                final Present present = new Present();
                final ArrayList<Present_> present_s = new ArrayList<>();
                for (int i = 0; i < eventcustomerArrayList.size(); i++) {


                    if (eventcustomerArrayList.get(i).isChecked()) {

                        final Present_ present_ = new Present_();
                        present_.setId(eventcustomerArrayList.get(i).getCuId());
                        present_s.add(present_);

                    }
                }

                present.setEventId(eventId);
                present.setEventDate(eventDate);
                present.setPresent(present_s);


                if (present_s.size() == 0) {
            Toast.makeText(getActivity(), "Please select at least one", Toast.LENGTH_SHORT).show();
            return;
        } else {

            Log.e("json", new Gson().toJson(present).toString());
            // process to add attendance

            showProgressDialog();

            takeAttd.putattd(present).enqueue(new Callback<MyRes>() {
                @Override
                public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    if (response.isSuccessful()) {

                        if (response.body().getMsg().equalsIgnoreCase("true")) {
                            Toast.makeText(getActivity(), "Successfully saved", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(getActivity(), "Failed to save", Toast.LENGTH_SHORT).show();
                            getFragmentManager().popBackStack();
                        }

                    }


                }

                @Override
                public void onFailure(Call<MyRes> call, Throwable t) {

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    if (getActivity() != null)
                        Toast.makeText(getActivity(), "Connection error", Toast.LENGTH_SHORT).show();

                    getFragmentManager().popBackStack();
                }
            });


        }


    }
});


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Event Attendance");
    }

    public boolean isDoubleClick() {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastclick < DOUBLE_TAP) {
            lastclick = clickTime;
            return true;
        }
        lastclick = clickTime;
        return false;
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (customAdapter != null)
            customAdapter.setFilter(filter(eventcustomerArrayList, newText));

        return true;
    }

    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Eventcustomer> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Eventcustomer> dataSet) {
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

            if (mDataSet.get(position).getAttendance().equals("2")) {
                viewHolder.cbPA.setEnabled(false);
            } else {
                viewHolder.cbPA.setEnabled(true);
            }


            viewHolder.cbPA.setOnCheckedChangeListener(null);

            viewHolder.cbPA.setChecked(mDataSet.get(position).isChecked());

            viewHolder.cbPA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mDataSet.get(position).setChecked(isChecked);

                }
            });

            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvClientName.setText(mDataSet.get(position).getName());
            viewHolder.tvClientMob.setText(mDataSet.get(position).getCode());

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
            private final CheckBox cbPA;


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

            }

        }

        void setFilter(List<Eventcustomer> empInfo) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(empInfo);
            notifyDataSetChanged();
        }
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



    private List<Eventcustomer> filter(List<Eventcustomer> models, String query) {
        query = query.toLowerCase().trim();
        final List<Eventcustomer> filteredModelList = new ArrayList<>();
        for (Eventcustomer model : models) {
            final String text = model.getName().toLowerCase();
            final String empId = model.getCode().toLowerCase();
            if (text.contains(query) || empId.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
