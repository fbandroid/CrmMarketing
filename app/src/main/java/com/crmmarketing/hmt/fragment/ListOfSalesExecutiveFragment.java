package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.GsonModel.UserDetail;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.adapter.SalesExecutiveListAdapter;
import com.crmmarketing.hmt.adapter.TeamLeaderListAdapter;
import com.crmmarketing.hmt.common.CropCircleTransformation;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.EmployeeInfo;
import com.crmmarketing.hmt.webservice.WSInquiry;
import com.dpizarro.autolabel.library.AutoLabelUI;
import com.dpizarro.autolabel.library.AutoLabelUISettings;
import com.dpizarro.autolabel.library.Label;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by USER on 27-01-2017.
 */

public class ListOfSalesExecutiveFragment extends Fragment implements SearchView.OnQueryTextListener {

    private Context context;
    private LinearLayout linearLayout;
    private Dialog meDialog;
    private ArrayList<User> userArrayList;
    private Button btnAddMe;
    private ProgressDialog progressDialog;
    private String uid;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = getActivity();
    }

    private RecyclerView recyclerView;
    private RecyclerView recyclerView22;
    private AutoLabelUI mAutoLabel;
    private SalesExecutiveListAdapter salesExecutiveListAdapter;
    private SalesExecutiveSelectAdapter salesExecutiveSelectAdapter;
    private ArrayList<User> employeeInfoArrayList;
    private ArrayList<String> checkedArray;
    private ArrayList<String> recyclerSelectArray;
    private ArrayList<Integer> posArray;
    private TeamLeaderListAdapter teamLeaderListAdapter;
    private TextView tvName;
    private TextView tvDesg;
    private TextView tvMob;
    private User user;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
        employeeInfoArrayList = new ArrayList<>();
        userArrayList = new ArrayList<>();
        checkedArray = new ArrayList<>();
        recyclerSelectArray = new ArrayList<>();
        posArray = new ArrayList<>();

        if (getArguments() != null) {

             user= getArguments().getParcelable("detail");
            uid=user.getUId();

        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Sales Executive");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_list_of_sales_exe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAutoLabel = (AutoLabelUI) view.findViewById(R.id.row_fragment_team_lead_autoLable);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_of_salesexe_rv);
        btnAddMe = (Button) view.findViewById(R.id.row_fragment_team_lead_btnAddMe);
        linearLayout = (LinearLayout) view.findViewById(R.id.header_team_lead);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(true);
        tvName= (TextView) view.findViewById(R.id.row_fragment_team_lead_tvName);
        tvMob= (TextView) view.findViewById(R.id.row_fragment_team_lead_tvPhone);
        tvDesg= (TextView) view.findViewById(R.id.row_admin_dashboard_tvId);


        if(user!=null){
            tvName.setText(user.getUName());
            tvMob.setText(user.getUContact());
            tvDesg.setText(user.getPost());
        }


        new LoginCheckTask().execute();


        btnAddMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAutoLabel.getLabels() != null) {

                    if (mAutoLabel.getLabels().size() > 0) {

                        for (int i = 0; i < mAutoLabel.getLabels().size(); i++) {
                            Log.e("labels..", "" + mAutoLabel.getLabel(i).getText());
                        }


                    }
                }
            }
        });

        AutoLabelUISettings autoLabelUISettings = new AutoLabelUISettings.Builder()
                .withIconCross(R.drawable.ic_clear_grey_600_24dp)
                .withBackgroundResource(R.drawable.bg_round_corner)
                .withLabelsClickables(false)
                .withShowCross(true)
                .withTextColor(android.R.color.black)
                .withLabelPadding(R.dimen.dimen_10)
                .build();

        mAutoLabel.setSettings(autoLabelUISettings);
        mAutoLabel.setOnRemoveLabelListener(new AutoLabelUI.OnRemoveLabelListener() {
            @Override
            public void onRemoveLabel(View view, int position) {

                employeeInfoArrayList.get(position).setChecked(false);
                salesExecutiveSelectAdapter.notifyDataSetChanged();
                checkedArray.remove((String) employeeInfoArrayList.get(position).getUName());

                Log.e("remove lable", "" + position);
            }
        });

        mAutoLabel.setOnLabelClickListener(new AutoLabelUI.OnLabelClickListener() {
            @Override
            public void onClickLabel(View v) {
                Toast.makeText(getActivity(), ((Label) v).getText(), Toast.LENGTH_LONG).show();
            }
        });

        mAutoLabel.setOnLabelsEmptyListener(new AutoLabelUI.OnLabelsEmptyListener() {
            @Override
            public void onLabelsEmpty() {


                btnAddMe.setVisibility(View.GONE);


            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "restore", ListOfSalesExecutiveFragment.this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("activity", "recreated");
        if (savedInstanceState != null) {
            getFragmentManager().getFragment(savedInstanceState, "restore");
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Sales Executive");

        if (mAutoLabel.getLabels().size() > 0) {

            btnAddMe.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);


        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
// Do something when collapsed
                        salesExecutiveListAdapter.setFilter(employeeInfoArrayList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
// Do something when expanded

                        return true; // Return true to expand action view
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_employee) {
           // openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        linearLayout.setVisibility(View.VISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        final List<User> filteredModelList = filter(employeeInfoArrayList, newText);

        salesExecutiveListAdapter.setFilter(filteredModelList);
        return true;
    }


    private List<User> filter(List<User> models, String query) {
        query = query.toLowerCase().trim();
        final List<User> filteredModelList = new ArrayList<>();
        for (User model : models) {
            final String text = model.getUName().toLowerCase();
            final String empId = model.getUContact();
            if (text.contains(query) || empId.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "salesexe");
    }


    public class SalesExecutiveSelectAdapter extends RecyclerView.Adapter<SalesExecutiveSelectAdapter.ViewHolder> {

        private static final String TAG = "ServiceAdapter";

        private ArrayList<User> mDataSet;
        private Context context;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvEmpNmae;
            private final TextView tvEmpid;
            private final ImageView ivProfile;
            private final CheckBox checkBox;


            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                        // addFragment(new TeamLeadDeatilFragment(),((AppCompatActivity)context).getFragmentManager().findFragmentByTag(ListOfTeamLeaderFragment.class.getSimpleName()));

                    }
                });
                tvEmpNmae = (TextView) v.findViewById(R.id.row_admin_dashboard_tvName);
                tvEmpid = (TextView) v.findViewById(R.id.row_admin_dashboard_tvId);
                ivProfile = (ImageView) v.findViewById(R.id.row_admin_dashboard_ivprofile);
                checkBox = (CheckBox) v.findViewById(R.id.chk);


            }

            TextView getTvEmpNmae() {
                return tvEmpNmae;
            }

            TextView getTvEmpid() {
                return tvEmpid;
            }

            ImageView getIvProfile() {
                return ivProfile;
            }

            CheckBox getCheckBox() {
                return checkBox;
            }
        }

        // END_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public SalesExecutiveSelectAdapter(ArrayList<User> dataSet, Context context) {
            this.mDataSet = dataSet;
            this.context = context;
        }

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_custom_spinner, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            //  holder.getCheckBox().setOnCheckedChangeListener(null);

            if (employeeInfoArrayList.get(position).isChecked()) {

                holder.getCheckBox().setChecked(true);

            } else {
                holder.getCheckBox().setChecked(false);
            }


            holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    mDataSet.get(holder.getAdapterPosition()).setChecked(isChecked);
                }
            });

            holder.getTvEmpNmae().setText(mDataSet.get(position).getUName());
            holder.getTvEmpid().setText(mDataSet.get(position).getUUsername());
            Glide
                    .with(context)
                    .fromResource().load(R.drawable.profile)
                    .bitmapTransform(new CropCircleTransformation(context))
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade()
                    .into(holder.getIvProfile());

        }


        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)

        private void addFragment(Fragment toAdd, Fragment current) {

            ((AppCompatActivity) context).getFragmentManager().beginTransaction()
                    .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                    .addToBackStack(null)
                    .hide(current).commitAllowingStateLoss();
        }


        void setFilter(List<User> empInfo) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(empInfo);
            notifyDataSetChanged();
        }


    }

    private void openDialog() {


        TextInputEditText textInputEditText;
        final Button btnSubmit;
        final Button btnCancel;
        salesExecutiveSelectAdapter = new SalesExecutiveSelectAdapter(employeeInfoArrayList, getActivity());


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View subView = inflater.inflate(R.layout.dialog_melist, null);

        meDialog = new Dialog(getActivity(), R.style.AppCompactDialog);
        meDialog.setTitle("Select Marketing Executive.");
        meDialog.setContentView(subView);
        meDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {


                if (mAutoLabel.getLabels().size() > 0) {

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnAddMe.setVisibility(View.VISIBLE);
                        }
                    }, 500);


                }
            }
        });


        btnSubmit = (Button) subView.findViewById(R.id.dialog_melist_ok);


        textInputEditText = (TextInputEditText) subView.findViewById(R.id.dialog_melist_tvSearch);
        recyclerView22 = (RecyclerView) subView.findViewById(R.id.dialog_melist_rv);
        recyclerView22.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView22.setNestedScrollingEnabled(true);
        recyclerView22.setAdapter(salesExecutiveSelectAdapter);
        meDialog.show();

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                final List<User> filteredModelList = filter(employeeInfoArrayList, s.toString());

                salesExecutiveSelectAdapter.setFilter(filteredModelList);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (employeeInfoArrayList != null) {

                    recyclerSelectArray.clear();
                    posArray.clear();

                    Handler handler2 = new Handler();
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            for (int i = 0; i < mAutoLabel.getLabels().size(); i++) {
                                mAutoLabel.removeLabel(mAutoLabel.getLabels().get(i).getText());
                                mAutoLabel.clear();


                            }


                        }
                    }, 100);


                    Handler handler33 = new Handler();
                    handler33.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < employeeInfoArrayList.size(); i++) {

                                if (employeeInfoArrayList.get(i).isChecked()) {


                                    recyclerSelectArray.add(employeeInfoArrayList.get(i).getUName());
                                    posArray.add(i);


                                    boolean isAdded = mAutoLabel.addLabel(employeeInfoArrayList.get(i).getUName(), i);
                                    if (isAdded) {

                                        employeeInfoArrayList.get(i).setChecked(true);
                                        salesExecutiveSelectAdapter.notifyDataSetChanged();
                                    }


                                }

                            }


                            meDialog.dismiss();
                            meDialog.cancel();
                        }
                    }, 200);

//


                }


            }


        });

    }



    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String url;
        private String response;

        LoginCheckTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.get_emp_list);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();
                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSInquiry().getEmpList(url, uid, Constants.root);
                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();




            } catch (IllegalArgumentException e) {



            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;

            if (getActivity() != null && progressDialog.isShowing()) {

                progressDialog.dismiss();
            }


            if(response!=null){

                UserDetail userDetail=new Gson().fromJson(response,UserDetail.class);
                userArrayList= (ArrayList<User>) userDetail.getUsers();


                if(userArrayList.size()>0 && getActivity()!=null){

                    salesExecutiveListAdapter=new SalesExecutiveListAdapter(userArrayList,getActivity());
                    recyclerView.setAdapter(salesExecutiveListAdapter);
                }
                else {

                    if(getActivity()!=null){
                        Toast.makeText(getActivity(),"No employee",Toast.LENGTH_LONG).show();
                    }
                }

            }



        }


        public void showProgressDialog() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

    }

}
