package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.Event;
import com.crmmarketing.hmt.gsonmodel22.EventList;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventAttendanceFragment extends Fragment {

    private final long DOUBLE_TAP = 1500;
    private Context context;
    private long lastclick;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RetrofitClient.EventList eventList;

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
        eventList = RetrofitClient.geteventlist(Constants.BASE_URL);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Event");
        return inflater.inflate(R.layout.fragment_event_attd, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvEventList);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);


        eventList.getEventList().enqueue(new Callback<EventList>() {
            @Override
            public void onResponse(Call<EventList> call, Response<EventList> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    final ArrayList<Event> events = (ArrayList<Event>) response.body().getEvents();

                    if (events.size() > 0) {
                        final CustomAdapter customAdapter = new CustomAdapter(events);
                        recyclerView.setAdapter(customAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<EventList> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Event");
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

        private ArrayList<Event> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Event> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_event_list, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element

            viewHolder.tvEventName.setText(mDataSet.get(position).getName());
            viewHolder.tvEventdate.setText(mDataSet.get(position).getEventDate());

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

            private final TextView tvEventName;
            private final TextView tvEventdate;
            private final ImageView ivInvite;
            private final ImageView ivTake;

            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

                tvEventName = (TextView) v.findViewById(R.id.tvEventName);
                tvEventdate = (TextView) v.findViewById(R.id.tvDate);
                ivInvite = (ImageView) v.findViewById(R.id.ivInvite);
                ivTake=(ImageView) v.findViewById(R.id.ivAttandance);

                ivInvite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new InviteCustomerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", mDataSet.get(getAdapterPosition()).getId());
                        fragment.setArguments(bundle);
                        addFragment(fragment, EventAttendanceFragment.this);
                    }
                });

                ivTake.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new EventDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", mDataSet.get(getAdapterPosition()).getId());
                        bundle.putString("event_date", mDataSet.get(getAdapterPosition()).getEventDate());
                        fragment.setArguments(bundle);
                        addFragment(fragment, EventAttendanceFragment.this);
                    }
                });
            }

        }
    }
}
