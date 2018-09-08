package com.crmmarketing.hmt.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.adapter.CustomAdapter;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.Datum;
import com.crmmarketing.hmt.gsonmodel22.VolumeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 26-06-2017.
 */

public class CashVolumeFragment extends Fragment implements SearchView.OnQueryTextListener {
    private RecyclerView rvFNO;
    private TextView tvDate;
    private String id;
    private String role;
    private ProgressBar progressBar;
    private RetrofitClient.Volumedata volumedata;
    private ArrayList<Datum> datumArrayList;
    private CustomAdapter customAdapter;
    DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar endCalender = Calendar.getInstance();
            endCalender.set(Calendar.YEAR, year);
            endCalender.set(Calendar.MONTH, monthOfYear);
            endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);


            String myFormat = "dd-MM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


            tvDate.setText(sdf.format(endCalender.getTime()));
//            dob = sdf.format(endCalender.getTime());

            progressBar.setVisibility(View.VISIBLE);
            volumedata.getVolumeData(id, role, "9", sdf.format(endCalender.getTime()))
                    .enqueue(new Callback<VolumeData>() {
                        @Override
                        public void onResponse(Call<VolumeData> call, Response<VolumeData> response) {

                            progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                datumArrayList = (ArrayList<Datum>) response.body().getData();

                                if ( datumArrayList!=null &&  datumArrayList.size() > 0) {

                                    customAdapter = new CustomAdapter(datumArrayList, getActivity(), 9, role);
                                    rvFNO.setAdapter(customAdapter);


                                } else if(datumArrayList!=null) {
                                    customAdapter = new CustomAdapter(datumArrayList, getActivity(), 9, role);
                                    rvFNO.setAdapter(customAdapter);
                                    Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();
                                }

                            }


                        }

                        @Override
                        public void onFailure(Call<VolumeData> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                        }
                    });

        }

    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        volumedata = RetrofitClient.getvolume(Constants.BASE_URL);

        datumArrayList = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_fno, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvFNO = (RecyclerView) view.findViewById(R.id.rvFNO);
        tvDate = (TextView) view.findViewById(R.id.tvDatePick);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvFNO.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        rvFNO.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFNO.addItemDecoration(dividerItemDecoration);

        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (customAdapter != null)
            customAdapter.setFilter(filter(datumArrayList, newText));
        return true;
    }

    private List<Datum> filter(List<Datum> models, String query) {
        query = query.toLowerCase().trim();
        final List<Datum> filteredModelList = new ArrayList<>();
        for (Datum model : models) {
            final String text = model.getName().toLowerCase();
            final String empId = model.getCode().toLowerCase();

            if (text.contains(query) || empId.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
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
}
