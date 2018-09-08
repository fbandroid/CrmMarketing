package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.ClientDetail;
import com.crmmarketing.hmt.gsonmodel22.Customer;
import com.crmmarketing.hmt.model.InqMaster;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 14-06-2017.
 */

public class ClientListFragment extends Fragment implements SearchView.OnQueryTextListener {
    private final long DOUBLE_TAP = 1500;
    private Context context;
    private long lastclick;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private RetrofitClient.GetClientList getClientList;
    private String id;
    private ArrayList<Customer> customerArrayList;
    private CustomAdapter customAdapter;
    private TextInputEditText edtSearch;
    private Spinner spType;
    private Button btnSearch;

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
        customerArrayList = new ArrayList<>();
        getClientList = RetrofitClient.getclientlist(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Client List");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_client_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvClientList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);

        spType = view.findViewById(R.id.spSearchType);
        btnSearch = view.findViewById(R.id.btnSearch);
        edtSearch = view.findViewById(R.id.edtSearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtSearch.getText().toString().trim().isEmpty()) {
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                int searchType = spType.getSelectedItemPosition() + 1;

                getClientList.getclientlist(id, String.valueOf(searchType), edtSearch.getText().toString().trim()).enqueue(new Callback<ClientDetail>() {
                    @Override
                    public void onResponse(Call<ClientDetail> call, Response<ClientDetail> response) {

                        progressBar.setVisibility(View.GONE);

                        if (response.isSuccessful()) {

                            customerArrayList = (ArrayList<Customer>) response.body().getCustomers();

                            if (customerArrayList.size() > 0) {

                                customAdapter = new CustomAdapter(customerArrayList);
                                recyclerView.setAdapter(customAdapter);
                            }


                        } else {

                        }

                    }

                    @Override
                    public void onFailure(Call<ClientDetail> call, Throwable t) {

                        progressBar.setVisibility(View.GONE);
                    }
                });


            }
        });


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Client List");
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (customAdapter != null)
            customAdapter.setFilter(filter(customerArrayList, newText));

        return true;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem addItem = menu.findItem(R.id.action_add_employee);

        addItem.setVisible(false);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    // filter customer list
    private List<Customer> filter(List<Customer> models, String query) {
        query = query.toLowerCase().trim();
        final List<Customer> filteredModelList = new ArrayList<>();
        for (Customer model : models) {
            final String text = model.getName().toLowerCase();
            final String empId = model.getCode().toLowerCase();
            final String pan = model.getPan().toLowerCase();
            if (text.contains(query) || empId.contains(query) || pan.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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

        private ArrayList<Customer> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Customer> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_client_list, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvClientCode.setText(mDataSet.get(position).getCode());
            viewHolder.tvClientName.setText(mDataSet.get(position).getName());

        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        void setFilter(List<Customer> empInfo) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(empInfo);
            notifyDataSetChanged();
        }

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView tvClientName;
            private final TextView tvClientCode;


            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Fragment fragment = new ClientDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("detail", mDataSet.get(getAdapterPosition()));
                        fragment.setArguments(bundle);
                        addFragment(fragment, ClientListFragment.this);


                    }
                });

                tvClientName = (TextView) v.findViewById(R.id.tvClientName);
                tvClientCode = (TextView) v.findViewById(R.id.tvClientCode);

            }

        }
    }
}
