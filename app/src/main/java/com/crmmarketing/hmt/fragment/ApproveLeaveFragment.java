package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.ApprLeave;
import com.crmmarketing.hmt.gsonmodel22.Approveleavelist;
import com.crmmarketing.hmt.model.MyRes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class ApproveLeaveFragment extends Fragment {

    private Context context;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RetrofitClient.ViewApproveListLeave viewApproveListLeave;
    private RetrofitClient.ApproveLeave approveLeave;
    private RetrofitClient.EditLeave editLeave;
    private CustomAdapter customAdapter;
    private ProgressDialog progressDialog;

    private String uid;
    private String role;

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

        viewApproveListLeave = RetrofitClient.getApprLeave(Constants.BASE_URL);
        approveLeave = RetrofitClient.setApprove(Constants.BASE_URL);
        editLeave = RetrofitClient.editLeaveReq(Constants.BASE_URL);
        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        uid = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Approve Leave");
        return inflater.inflate(R.layout.fragment_approve_leave, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvLeave);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);


        viewApproveListLeave.getLeavelist(uid, role).enqueue(new Callback<ApprLeave>() {
            @Override
            public void onResponse(Call<ApprLeave> call, Response<ApprLeave> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    final ArrayList<Approveleavelist> approveleavelists = (ArrayList<Approveleavelist>) response.body().getApproveleavelist();

                    if (approveleavelists.size() > 0) {

                        customAdapter = new CustomAdapter(approveleavelists);
                        recyclerView.setAdapter(customAdapter);
                    } else {

                        if (getActivity() != null)
                            Toast.makeText(getActivity(), "No data available", Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onFailure(Call<ApprLeave> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Approve Leave");
    }

    public void showAlertDialog(final String id) {
        android.support.v7.app.AlertDialog.Builder dialogBuilder = new android.support.v7.app.AlertDialog.Builder(getActivity());


        dialogBuilder.setMessage("Are you sure to continue");
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                showProgressDialog();

                approveLeave.setApproveLeave(id, role).enqueue(new Callback<MyRes>() {
                    @Override
                    public void onResponse(Call<MyRes> call, Response<MyRes> response) {

                        progressBar.setVisibility(View.GONE);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        if (response.isSuccessful()) {

                            switch (response.body().getMsg()) {

                                case "1":
                                    if (getActivity() != null) {
                                        Toast.makeText(getActivity(), "Successfully approved", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    }

                                    break;

                                case "2":
                                    if (getActivity() != null) {
                                        Toast.makeText(getActivity(), "Please approve leave by mini admin first", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    }
                                    break;

                                case "3":
                                    if (getActivity() != null) {
                                        Toast.makeText(getActivity(), "You already approve leave.Contact Main admin for approval..", Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();
                                    }
                                    break;


                            }

                        }


                    }

                    @Override
                    public void onFailure(Call<MyRes> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        if (getActivity() != null)
                            Toast.makeText(getActivity(), "Not approved..", Toast.LENGTH_SHORT).show();

                    }
                });


            }


        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass


            }
        });
        android.support.v7.app.AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void showChangeLangDialog(String start, String end, String id, String type) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.row_edit_leave, null);
        final TextView tvDuration = (TextView) dialogView.findViewById(R.id.tvDuration);
        final TextView tvStart = (TextView) dialogView.findViewById(R.id.tvStart);
        final TextView tvEnd = (TextView) dialogView.findViewById(R.id.tvEnd);
        final EditText edtRemark = (EditText) dialogView.findViewById(R.id.edtRemark);
        final Spinner spType = (Spinner) dialogView.findViewById(R.id.spType);
        final Calendar startCalender = Calendar.getInstance();
        final Calendar endCalender = Calendar.getInstance();
        final String leaveId = id;
        final String typeof = type;

        tvDuration.setText(start.concat(" to ").concat(end));

        if (typeof.equals("1")) {

            spType.setSelection(getIndex(spType, "D.L"));


        } else if (typeof.equals("2")) {

            spType.setSelection(getIndex(spType, "C.L"));
        }

        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                startCalender.set(Calendar.YEAR, year);
                startCalender.set(Calendar.MONTH, monthOfYear);
                startCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                tvStart.setText(sdf.format(startCalender.getTime()));


            }


        };

        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub

                endCalender.set(Calendar.YEAR, year);
                endCalender.set(Calendar.MONTH, monthOfYear);
                endCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);


                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);


                tvEnd.setText(sdf.format(endCalender.getTime()));
//            dob = sdf.format(endCalender.getTime());


            }

        };


        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });

        tvEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, Calendar.getInstance()
                        .get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show();
            }
        });


        dialogBuilder.setView(dialogView);


        dialogBuilder.setTitle("Edit date and time");
        dialogBuilder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();


            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        final AlertDialog b = dialogBuilder.create();
        b.setCancelable(false);
        b.show();

        Button theButton = b.getButton(DialogInterface.BUTTON_POSITIVE);
        theButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String type = "";

                if (spType.getSelectedItem().toString().equals("D.L")) {
                    type = "1";

                } else if (spType.getSelectedItem().toString().equals("C.L")) {
                    type = "2";
                }


                if (tvStart.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Select start date", Toast.LENGTH_SHORT).show();

                } else if (tvEnd.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Select end date", Toast.LENGTH_SHORT).show();

                } else if (endCalender.before(startCalender)) {
                    Toast.makeText(getActivity(), "end date should greater than start date", Toast.LENGTH_SHORT).show();

                } else {

                    b.dismiss();
                    showProgressDialog();

                    editLeave.editLeave(leaveId, tvStart.getText().toString(), tvEnd.getText().toString(),
                            edtRemark.getText().toString().trim(), type).enqueue(new Callback<MyRes>() {
                        @Override
                        public void onResponse(Call<MyRes> call, Response<MyRes> response) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }


                            if (response.isSuccessful()) {

                                if (response.body().getMsg().equalsIgnoreCase("true")) {
                                    Toast.makeText(getActivity(), "Edited successfully", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();
                                } else {
                                    Toast.makeText(getActivity(), "Not edited", Toast.LENGTH_SHORT).show();
                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<MyRes> call, Throwable t) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            Toast.makeText(getActivity(), "Not edited", Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }
        });
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<Approveleavelist> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<Approveleavelist> dataSet) {
            mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_approve_leave, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.tvName.setText(mDataSet.get(position).getUName());
            viewHolder.tvReason.setText(mDataSet.get(position).getReason());
            viewHolder.tvdate.setText(mDataSet.get(position).getStart().concat(" to ").concat(mDataSet.get(position).getEnd()));

            if (mDataSet.get(position).getType().equals("1")) {
                viewHolder.tvType.setText("D.L");
            } else if (mDataSet.get(position).getType().equals("2")) {
                viewHolder.tvType.setText("C.L");
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
            private final TextView tvName;
            private final TextView tvdate;
            private final TextView tvReason;
            private final TextView tvType;
            private final ImageView ivEdit;
            private Button btnApprove;


            public ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                tvName = (TextView) v.findViewById(R.id.tvName);
                tvdate = (TextView) v.findViewById(R.id.tvDuration);
                tvReason = (TextView) v.findViewById(R.id.tvDesc);
                btnApprove = (Button) v.findViewById(R.id.btnApprove);
                ivEdit = (ImageView) v.findViewById(R.id.ivEdit);
                tvType = (TextView) v.findViewById(R.id.tvType);


                btnApprove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showAlertDialog(mDataSet.get(getAdapterPosition()).getId());
                    }
                });

                ivEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showChangeLangDialog(mDataSet.get(getAdapterPosition()).getStart(),
                                mDataSet.get(getAdapterPosition()).getEnd(), mDataSet.get(getAdapterPosition()).getId(), mDataSet.get(getAdapterPosition()).getType());

                    }
                });

            }


        }
    }

}
