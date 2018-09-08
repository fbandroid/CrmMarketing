package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.crmmarketing.hmt.GsonModel.Branch;
import com.crmmarketing.hmt.GsonModel.BranchList;
import com.crmmarketing.hmt.GsonModel.Example;
import com.crmmarketing.hmt.GsonModel.Getinquiry;
import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.LoginActivity;
import com.crmmarketing.hmt.OnLoadMoreListener;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.adapter.SalesExecutiveListAdapter;
import com.crmmarketing.hmt.adapter.TeamLeaderListAdapter;
import com.crmmarketing.hmt.common.CircularTextView;
import com.crmmarketing.hmt.common.CropCircleTransformation;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.model.CusInfo;
import com.crmmarketing.hmt.model.EmployeeInfo;
import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.model.InquiryMaster;
import com.crmmarketing.hmt.webservice.WSInquiry;
import com.crmmarketing.hmt.webservice.WSLogin;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class InquiryListInfoFragment extends Fragment implements SearchView.OnQueryTextListener {

    private final long DOUBLE_TAP = 1500;
    private Dialog meDialog;
    private int smallWidth;
    private ArrayList<EmployeeInfo> employeeInfoArrayList;
    private FrameLayout frameLayout;
    private int selectedPos = 0;
    private RecyclerView recyclerView;
    private String response;
    private ProgressDialog progressDialog;
    private LinearLayout parentView;
    private Button btnViewMore;
    private ArrayList<InqMaster> inqMasterArrayList;
    private ArrayList<Getinquiry> inqMasterArrayListgson;
    private SalesExecutiveSelectSingleChoiceAdapter salesExecutiveSelectSingleChoiceAdapter;
    private Context context;
    private String userName;
    private String id;
    private String role;
    private TextView tvFilter;
    private Spinner tvSort;
    private RetrofitClient.getBranchList getBranchList;
    private ArrayList<Branch> branchArrayList;
    private String[] branchlist;
    private String branchId;
    private OnLoadMoreListener onLoadMoreListener;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;
    private long lastclick;


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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        employeeInfoArrayList = new ArrayList<>();
        inqMasterArrayList = new ArrayList<>();
        inqMasterArrayListgson = new ArrayList<>();
        branchArrayList = new ArrayList<>();

        SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
        userName = sharedPref.getString("user_name", null);
        id = String.valueOf(sharedPref.getInt("id", 0));
        role = String.valueOf(sharedPref.getString("role", ""));
        branchId = sharedPref.getString("branch", "");

        getBranchList = RetrofitClient.getBranchList(Constants.BASE_URL);

        if (getArguments() != null) {
            branchArrayList = getArguments().getParcelableArrayList("branch");

        }


        if (getArguments() != null && getArguments().getString("id") != null && getArguments().getString("username") != null) {

            id = getArguments().getString("id");
            userName = getArguments().getString("username");
            role = getArguments().getString("role");

        } else {
//            SharedPreferences sharedPref22 = getActivity().getSharedPreferences("my", MODE_PRIVATE);
//            userName = sharedPref.getString("user_name", null);
//            id = String.valueOf(sharedPref.getInt("id", 0));
//            role = getArguments().getString("role");

        }

        Configuration config = getResources().getConfiguration();
        smallWidth = config.smallestScreenWidthDp;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("InquiryList");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_inquiry_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvFilter = (TextView) view.findViewById(R.id.fragment_inquiry_list_tvFilter);
        tvSort = (Spinner) view.findViewById(R.id.fragment_inquiry_list_tvSort);
        parentView = (LinearLayout) view.findViewById(R.id.parent_view);
        frameLayout = (FrameLayout) view.findViewById(R.id.info_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_inquiry_list_rv);

        if (role.equals("1")) {
            tvSort.setVisibility(View.VISIBLE);
        }

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);

        /**
         *     load more recyclerview edit here
         *     ToDO load more
         *
         */
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = ((LinearLayoutManager) recyclerView.getLayoutManager()).getItemCount();
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });


        tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), tvFilter);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.filter_pop_up, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {


                        switch (item.getItemId()) {

                            case R.id.filter_All:
                                if (inqMasterArrayList != null && salesExecutiveSelectSingleChoiceAdapter != null) {
                                    salesExecutiveSelectSingleChoiceAdapter.setFilter(inqMasterArrayList);
                                }
                                break;

                            case R.id.filter_pending:
                                if (inqMasterArrayList != null && salesExecutiveSelectSingleChoiceAdapter != null) {


                                    final List<InqMaster> filteredModelList = filterPending(inqMasterArrayList, "0");
                                    salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);
                                }
                                break;
                            case R.id.filter_confirm:

                                if (inqMasterArrayList != null && salesExecutiveSelectSingleChoiceAdapter != null) {


                                    final List<InqMaster> filteredModelList = filterPending(inqMasterArrayList, "1");
                                    salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);
                                }

                                break;

                            case R.id.filter_cancel:

                                if (inqMasterArrayList != null && salesExecutiveSelectSingleChoiceAdapter != null) {
                                    final List<InqMaster> filteredModelList = filterPending(inqMasterArrayList, "2");
                                    salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);
                                }

                                break;
                            case R.id.filter_date:
                                showChangeLangDialog();
                                break;
                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }

        });

        // inflate spinner prlist


        // call web service to fetch data
        new LoginCheckTask().execute();


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem addItem = menu.findItem(R.id.action_add_employee);

        if (role.equals("6") || role.equals("7") || role.equals("8")) {
            addItem.setVisible(true);

        }

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_employee) {

            if (Utils.checkInternetConnection(getActivity())) {

                if (isDoubleClick()) {
                    return true;
                }

                addFragment(new InquiryFormFragment(), InquiryListInfoFragment.this);
            } else {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "No Internet", Toast.LENGTH_LONG).show();
                }
            }


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void dummyList() {
        for (int i = 0; i < 20; i++) {

            final EmployeeInfo employeeInfo = new EmployeeInfo();
            employeeInfo.setEmpName("Team Leader" + i);
            employeeInfo.setEmpNo(String.valueOf(i));
            employeeInfoArrayList.add(employeeInfo);
        }
    }


       private List<InqMaster> filter(List<InqMaster> models, String query) {
        query = query.toLowerCase().trim();
        final List<InqMaster> filteredModelList = new ArrayList<>();
        for (InqMaster model : models) {
            final String text = model.getCusInfo().getCusName().toLowerCase();
            final String empId = model.getCusInfo().getCusMobile();
            final String tsbcode=model.getCusInfo().getCusTSB().toLowerCase();
            if (text.contains(query) || empId.contains(query) || tsbcode.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    // TODO inq master status not defined clearly..
    private List<InqMaster> filterPending(List<InqMaster> models, String query) {
        query = query.trim();
        final List<InqMaster> filteredModelList = new ArrayList<>();
        for (InqMaster model : models) {
            final String text = model.getStausInq();

            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }


    //TODO filter date
    private List<InqMaster> filterDate(List<InqMaster> models, Calendar startDate, Calendar endDate) {

        final List<InqMaster> filteredModelList = new ArrayList<>();
        for (InqMaster model : models) {
            final String text = model.getInqFeedbackTime();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = format.parse(text);
                Calendar tocompare = Utils.toCalendar(date);
                startDate.set(Calendar.HOUR_OF_DAY, 0);
                startDate.set(Calendar.MINUTE, 0);
                startDate.set(Calendar.SECOND, 0);
                startDate.set(Calendar.MILLISECOND, 0);
                System.out.println(date);
                if ((tocompare.equals(startDate) || tocompare.after(startDate)) && (tocompare.equals(endDate) || tocompare.before(endDate))) {
                    filteredModelList.add(model);
                }
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        return filteredModelList;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (salesExecutiveSelectSingleChoiceAdapter != null) {

            final List<InqMaster> filteredModelList = filter(inqMasterArrayList, newText);

            salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);

        }

        return true;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
        }
    }

    private void addFragment(Fragment toAdd, Fragment current) {


        getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    private void addV4Fragment(Fragment toAdd, Fragment current) {
        getFragmentManager().beginTransaction()
                .add(R.id.info_container, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commit();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("hidden called", "TeamLead");
        ((HomeActivity) context).setTitle("InquiryList");
    }

    public void showChangeLangDialog() {

        final TextView tvStartDate;
        final TextView tvEndDate;
        final Calendar[] initCalender = new Calendar[1];
        final Calendar[] startCalender = new Calendar[1];
        final Calendar[] endCalender = new Calendar[1];

        initCalender[0] = Calendar.getInstance();


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.time_picker_dialog, null);
        tvStartDate = (TextView) dialogView.findViewById(R.id.time_picker_dialog_tvStartDate);
        tvEndDate = (TextView) dialogView.findViewById(R.id.time_picker_dialog_tvEndDate);
        dialogBuilder.setView(dialogView);


        final DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                startCalender[0] = Calendar.getInstance();
                startCalender[0].set(Calendar.YEAR, year);
                startCalender[0].set(Calendar.MONTH, monthOfYear);
                startCalender[0].set(Calendar.DAY_OF_MONTH, dayOfMonth);

                if (startCalender[0].after(endCalender[0])) {

//                     endCalender.set(Calendar.MONTH,monthOfYear);
//                     endCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth+1);
//                     endCalender.set(Calendar.YEAR,year);
                    endCalender[0] = startCalender[0];

                    //

                    String myFormat = "dd/MM/yy"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    tvEndDate.setText(sdf.format(endCalender[0].getTime()));


                }

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvStartDate.setText(sdf.format(startCalender[0].getTime()));
            }


            //
        };


        final DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                endCalender[0] = Calendar.getInstance();
                endCalender[0].set(Calendar.YEAR, year);
                endCalender[0].set(Calendar.MONTH, monthOfYear);
                endCalender[0].set(Calendar.DAY_OF_MONTH, dayOfMonth);

//            if(endCalender.before(startCalender) && endCalender.after(Calendar.getInstance()) ){
//
//                startCalender.set(Calendar.MONTH,monthOfYear);
//                startCalender.set(Calendar.DAY_OF_MONTH,dayOfMonth-1);
//                updateLabelStart("start");
//            }

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                tvEndDate.setText(sdf.format(endCalender[0].getTime()));
            }

        };


        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Utils.isTimeAutomatic(getActivity())) {
                    if (startCalender[0] != null) {

                        initCalender[0] = startCalender[0];
                    }


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), startDate, initCalender[0]
                            .get(Calendar.YEAR), initCalender[0].get(Calendar.MONTH),
                            initCalender[0].get(Calendar.DAY_OF_MONTH));
                    //datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }


            }
        });


        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Utils.isTimeAutomatic(getActivity())) {

                    if (endCalender[0] != null) {

                        initCalender[0] = endCalender[0];
                    }

                    if (startCalender[0] != null) {
                        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), endDate, initCalender[0]
                                .get(Calendar.YEAR), initCalender[0].get(Calendar.MONTH),
                                initCalender[0].get(Calendar.DAY_OF_MONTH));
                        datePickerDialog.getDatePicker().setMinDate(startCalender[0].getTimeInMillis());

                        if (startCalender[0] != null) {
                            datePickerDialog.show();
                        }
                    }


                } else {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);
                }
            }
        });

        dialogBuilder.setTitle("Select start date and end date");
        dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();


                if (!tvStartDate.getText().toString().isEmpty() && !tvEndDate.getText().toString().isEmpty()) {


                    if (inqMasterArrayList != null && salesExecutiveSelectSingleChoiceAdapter != null) {

                        List<InqMaster> inqMasterList = filterDate(inqMasterArrayList, startCalender[0], endCalender[0]);
                        salesExecutiveSelectSingleChoiceAdapter.setFilter(inqMasterList);
                    }


                }
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
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

    public class SalesExecutiveSelectSingleChoiceAdapter extends RecyclerView.Adapter<SalesExecutiveSelectSingleChoiceAdapter.ViewHolder> {

        private static final String TAG = "ServiceAdapter";
        private final List<InqMaster> filteredUserList;
        public int mSelectedItem = -1;
        private ArrayList<InqMaster> mDataSet;
        private Context context;
        private Calendar currentCalender = Calendar.getInstance();


        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public SalesExecutiveSelectSingleChoiceAdapter(ArrayList<InqMaster> dataSet, Context context) {
            this.mDataSet = dataSet;
            this.context = context;
            this.filteredUserList = new ArrayList<>();

        }


        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_fragment_inquiry_list, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            Calendar calendar = Calendar.getInstance();

            boolean isConfirmed = false;
            boolean isPending = false;
            SparseBooleanArray sparseBooleanArrayCanceled = new SparseBooleanArray();
            for (InqChild inqChild : mDataSet.get(position).getInqChild()) {

                if (inqChild.getInqChildStatus().equals("1")) {
                    isConfirmed = true;
                    break;
                } else if (inqChild.getInqChildStatus().equals("2")) {
                    sparseBooleanArrayCanceled.put(Integer.valueOf(inqChild.getInqChildId()), true);
                } else if (inqChild.getInqChildStatus().equals("0")) {
                    isPending = true;
                }


            }


            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // here set the pattern as you date in string was containing like date/month/year
                Date d = sdf.parse(mDataSet.get(position).getInqFeedbackTime());
                calendar.setTime(d);
                calendar.add(Calendar.DAY_OF_YEAR, 5);


                if (currentCalender.getTime().after(calendar.getTime()) && mDataSet.get(position).getStausInq().equals("0")) {
                    holder.getTvStatus().setCompoundDrawablesWithIntrinsicBounds(R.drawable.caretdown2, 0, 0, 0);

                } else {
                    holder.getTvStatus().setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                }


            } catch (ParseException ex) {
                // handle parsing exception if date string was different from the pattern applying into the SimpleDateFormat contructor
            }


//            holder.getRb().setChecked(position == mSelectedItem);

//            Glide
//                    .with(context)
//                    .fromResource().load(R.drawable.girlprofile)
//                    .bitmapTransform(new CropCircleTransformation(context))
//                    .placeholder(R.mipmap.ic_launcher)
//                    .crossFade()
//                    .into(holder.getIvProfile());
            if (smallWidth >= 720 && frameLayout != null) {
                //holder.itemView.setSelected(selectedPos == position);

                if (selectedPos == 0) {

                    Fragment fragment = new InquiryDetailInTabletFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("detail", mDataSet.get(0));
                    fragment.setArguments(bundle);
                    getChildFragmentManager().beginTransaction().replace(R.id.info_container, fragment, fragment.getClass().getSimpleName()).commit();

                }

                if (selectedPos == position) {
                    // Here I am just highlighting the background

                    holder.itemView.setBackgroundColor(getResources().getColor(R.color.hilight));
                } else {
                    holder.itemView.setBackgroundColor(Color.TRANSPARENT);
                }

            }

//            if (sparseBooleanArrayCanceled.size() == mDataSet.get(position).getInqChild().size()) {
//
//                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_red));
//                holder.getTvStatus().setText("canceled");
//
//
//            } else if (isPending) {
//                holder.getTvStatus().setTextColor(getResources().getColor(R.color.blue));
//                holder.getTvStatus().setText("pending");
//            }
//            else if(isConfirmed){
//                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_green));
//                holder.getTvStatus().setText("confirm");
//            }


            if (mDataSet.get(position).getStausInq().equals("2")) {

                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_red));
                holder.getTvStatus().setText("canceled");


            } else if (mDataSet.get(position).getStausInq().equals("0")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.blue));
                holder.getTvStatus().setText("pending");
            } else if (mDataSet.get(position).getStausInq().equals("1")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_green));
                holder.getTvStatus().setText("confirm");
            } else if (mDataSet.get(position).getStausInq().equals("3")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.darkPink));
                holder.getTvStatus().setText("pending\n with confirm");
            }
            else if(mDataSet.get(position).getStausInq().equals("4")){
                holder.getTvStatus().setText("Approved\n By Opr.");
            }


            if (mDataSet.get(position).getInqFeedbackTime().equals(android.text.format.DateFormat.format("yyyy-MM-dd", Calendar.getInstance().getTime()))) {

                holder.getIvImageView().setVisibility(View.VISIBLE);
                holder.getIvImageView().setBackgroundResource(R.drawable.ic_notifications_active_blue_600_24dp);
                // holder.getIvImageView().startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.shake));


            } else {
                //holder.getIvImageView().clearAnimation();
                holder.getIvImageView().setVisibility(View.GONE);
            }


            holder.getTvFrame().setText(mDataSet.get(position).getCusInfo().getCusName().toUpperCase().substring(0, 1));
            holder.tvCounter.setText(mDataSet.get(position).getInqCounter());
            holder.getTvEmpNmae().setText(mDataSet.get(position).getCusInfo().getCusName());

            holder.getTvDate().setText(mDataSet.get(position).getInqFeedbackTime());

            holder.getTvPhone().setText(mDataSet.get(position).getCusInfo().getCusMobile());
            holder.getTvEmpName().setText(mDataSet.get(position).getEmpName());
            holder.getTvParentName().setText(mDataSet.get(position).getParentName());
            holder.tvTSB.setText(mDataSet.get(position).getCusInfo().getCusTSB());
            StringBuilder stringBuilder = new StringBuilder();

            if (mDataSet.get(position).getInqChild() != null) {
                for (int i = 0; i < mDataSet.get(position).getInqChild().size(); i++) {

                    stringBuilder.append(mDataSet.get(position).getInqChild().get(i).getInqChildproductName().concat(" ,"));
                }
                holder.getTvProduct().setText(stringBuilder.toString());
            }


            GradientDrawable bgShape = (GradientDrawable) holder.getFrameLayout().getBackground();

            int[] rainbow = context.getResources().getIntArray(R.array.color_array);
            bgShape.setColor(rainbow[position % rainbow.length]);

        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }

        void setFilter(List<InqMaster> empInfo) {
            mDataSet = new ArrayList<>();
            mDataSet.addAll(empInfo);
            notifyDataSetChanged();
        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvEmpNmae;
            private final TextView tvPhone;
            private final TextView tvDate;
            private final FrameLayout frameLayout;
            private final TextView tvFrame;
            private final TextView tvProduct;
            private final TextView tvEmpName;
            private final TextView tvParentName;
            private final TextView tvStatus;
            private final ImageView ivImageView;
            private final TextView tvTSB;
            private final CircularTextView tvCounter;

            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");

                        if (smallWidth >= 720) {

                            if (frameLayout != null) {


                                Fragment fragment = new InquiryDetailInTabletFragment();
                                Bundle bundle = new Bundle();
                                bundle.putParcelable("detail", mDataSet.get(getAdapterPosition()));
                                // bundle.putString("from",InquiryListInfoFragment.class.getSimpleName());
                                fragment.setArguments(bundle);
                                getChildFragmentManager().beginTransaction().replace(R.id.info_container, fragment, fragment.getClass().getSimpleName()).commit();

                                notifyItemChanged(selectedPos);
                                selectedPos = getLayoutPosition();
                                notifyItemChanged(selectedPos);

                            }


                        } else {

                            Fragment fragment = new InquiryMasterDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("detail", mDataSet.get(getAdapterPosition()));
                            fragment.setArguments(bundle);
                            Intent intent = new Intent(getActivity(), InquirtDetailActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }


                        // addFragment(new TeamLeadDeatilFragment(),((AppCompatActivity)context).getFragmentManager().findFragmentByTag(ListOfTeamLeaderFragment.class.getSimpleName()));

                    }
                });
                tvEmpNmae = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvCusName);
                tvPhone = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvCusPhone);
                tvDate = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvDate);
                frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout);
                tvFrame = (TextView) v.findViewById(R.id.tvFrame);
                tvProduct = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvProduct);
                tvEmpName = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvEmpName);
                tvParentName = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvTopEmp);
                tvStatus = (TextView) v.findViewById(R.id.row_fragmen_inquiry_list_tvStatus);
                ivImageView = (ImageView) v.findViewById(R.id.fragment_inquiry_list_ivAlert);
                tvCounter = (CircularTextView) v.findViewById(R.id.tvCounter);
                tvTSB = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvTSB);
                tvCounter.setSolidColor("#10D25A");


//                rb = (RadioButton) v.findViewById(R.id.rb);
//                rb.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mSelectedItem = getAdapterPosition();
//                        notifyDataSetChanged();
//                    }
//                });


            }

            TextView getTvEmpNmae() {
                return tvEmpNmae;
            }

            TextView getTvDate() {
                return tvDate;
            }

            TextView getTvPhone() {
                return tvPhone;
            }

            FrameLayout getFrameLayout() {
                return frameLayout;
            }

            TextView getTvFrame() {
                return tvFrame;
            }

            TextView getTvProduct() {
                return tvProduct;
            }

            TextView getTvEmpName() {
                return tvEmpName;
            }

            TextView getTvStatus() {
                return tvStatus;
            }

            TextView getTvParentName() {
                return tvParentName;
            }

            ImageView getIvImageView() {
                return ivImageView;
            }
        }


    }

    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String url;

        LoginCheckTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.inq_list);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();
                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSInquiry().getAllPostInquuiry(url, id, role, branchId);
                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Utils.showSnackBar(parentView, "No Internet", getActivity());
                getFragmentManager().popBackStack();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();
                Utils.showSnackBar(parentView, "No Internet", getActivity());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;

            //get branch
            getBranchList.branch().enqueue(new Callback<BranchList>() {
                @Override
                public void onResponse(Call<BranchList> call, Response<BranchList> response) {

                    if (salesExecutiveSelectSingleChoiceAdapter != null) {
                        salesExecutiveSelectSingleChoiceAdapter.setFilter(inqMasterArrayList);
                    }


                    branchArrayList = (ArrayList<Branch>) response.body().getBranch();

                    if (branchArrayList != null && branchArrayList.size() > 0) {

                        Branch branch = new Branch();
                        branch.setName("All");
                        branch.setId("0");
                        branchArrayList.add(0, branch);
                        branchlist = new String[branchArrayList.size()];
                        for (int i = 0; i < branchArrayList.size(); i++) {


                            branchlist[i] = branchArrayList.get(i).getName();

                        }


                        if (branchlist != null && getActivity() != null) {
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, branchlist);

                            // Drop down layout style - list view with radio button
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                            // attaching data adapter to spinner
                            tvSort.setAdapter(dataAdapter);

                            tvSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                                    if (inqMasterArrayList != null) {
                                        ArrayList<InqMaster> filteredModelList = new ArrayList<InqMaster>();
                                        for (int i = 0; i < inqMasterArrayList.size(); i++) {


                                            if (inqMasterArrayList.get(i).getBranchName().equals(parent.getItemAtPosition(position))) {


                                                filteredModelList.add(inqMasterArrayList.get(i));


                                            } else if (parent.getItemAtPosition(position).equals("All")) {
                                                filteredModelList = inqMasterArrayList;
                                                break;
                                            }


                                        }

                                        if (salesExecutiveSelectSingleChoiceAdapter != null)
                                            salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);
                                    }


                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        }


                    }

                }

                @Override
                public void onFailure(Call<BranchList> call, Throwable t) {

                }
            });


            inqMasterArrayList = new ArrayList<>();


            if (getActivity() != null && progressDialog.isShowing()) {

                progressDialog.dismiss();
            }
            // after web service call do parsing here and update ui
            try {
                if (response != null) {


                    //gson parse start
//
//                    Gson gson = new Gson();
//
//                    Example restaurantObject = gson.fromJson(response, Example.class);
//                    inqMasterArrayListgson= (ArrayList<Getinquiry>) restaurantObject.getGetinquiry();
//
                    //gson parse end
                    JSONObject jsonObject = new JSONObject(response);


                    JSONArray jsonArray = jsonObject.getJSONArray("getinquiry");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        JSONObject inquiryMasterJson = jsonObject1.getJSONObject("inq_master");
                        JSONObject cus_infoJson = jsonObject1.getJSONObject("cus_info");
                        JSONArray inq_child_json = jsonObject1.getJSONArray("inq_child");


//                        inquiryMasterJson.optString("inq_id");
//                        inquiryMasterJson.optString("inq_datetime");
//                        inquiryMasterJson.optString("inq_userid");
//                        inquiryMasterJson.optString("inq_custid");
//                        inquiryMasterJson.optString("inq_investrange");
//                        inquiryMasterJson.optString("inq_feedbacktime");
//                        inquiryMasterJson.optString("inq_status");

                        final InqMaster inqMaster = new InqMaster();
                        inqMaster.setInqMasterId(inquiryMasterJson.optString("inq_id"));
                        inqMaster.setInqEmpId(inquiryMasterJson.optString("inq_userid"));
                        inqMaster.setInqMasterStatus(inquiryMasterJson.optString("inq_status"));
                        inqMaster.setInqCusId(inquiryMasterJson.optString("inq_custid"));
                        inqMaster.setInqFeedbackTime(inquiryMasterJson.optString("inq_feedbacktime"));
                        inqMaster.setInqInvestRange(inquiryMasterJson.optString("inq_investrange"));
                        inqMaster.setInqMasterTimeStamp(inquiryMasterJson.optString("inq_datetime"));
                        inqMaster.setEmpName(inquiryMasterJson.optString("inq_childname"));
                        inqMaster.setParentName(inquiryMasterJson.optString("inq_parentname"));
                        inqMaster.setBranchName(inquiryMasterJson.optString("inq_branchname"));
                        inqMaster.setBranchId(inquiryMasterJson.optString("inq_branchid"));
                        inqMaster.setInqRemark(inquiryMasterJson.optString("inq_remark"));
                        inqMaster.setFeedTime(inquiryMasterJson.optString("inq_feed_time"));
                        inqMaster.setStausInq(inquiryMasterJson.optString("inq_status"));
                        inqMaster.setInqCounter(inquiryMasterJson.optString("inq_counter"));

//
//                        cus_infoJson.optString("cu_id");
//                        cus_infoJson.optString("name");
//                        cus_infoJson.optString("occupation");
//                        cus_infoJson.optString("source_detail");
//                        cus_infoJson.optString("referee_name");
//                        cus_infoJson.optString("referee_code");
//                        cus_infoJson.optString("referee_email");
//                        cus_infoJson.optString("referee_mobile");
//                        cus_infoJson.optString("email");
//                        cus_infoJson.optString("alternate_email");
//                        cus_infoJson.optString("mobile");
//                        cus_infoJson.optString("landline");
//                        cus_infoJson.optString("dob");
//                        cus_infoJson.optString("address");
//                        cus_infoJson.optString("comments");


                        final CusInfo cusInfo = new CusInfo();
                        cusInfo.setCusId(cus_infoJson.optString("cu_id"));
                        cusInfo.setCusName(cus_infoJson.optString("name"));
                        cusInfo.setCusOccupation(cus_infoJson.optString("occupation"));
                        cusInfo.setCusRefName(cus_infoJson.optString("referee_name"));
                        cusInfo.setCusRefEmail(cus_infoJson.optString("referee_email"));
                        cusInfo.setCusRefMobile(cus_infoJson.optString("referee_mobile"));
                        cusInfo.setCusRefCode(cus_infoJson.optString("referee_code"));
                        cusInfo.setCusEmail(cus_infoJson.optString("email"));
                        cusInfo.setCusMobile(cus_infoJson.getString("mobile"));
                        cusInfo.setCusDob(cus_infoJson.optString("dob"));
                        cusInfo.setCusAddr(cus_infoJson.optString("address"));
                        cusInfo.setCusLand(cus_infoJson.optString("landline"));
                        cusInfo.setCusOptEmail(cus_infoJson.optString("alternate_email"));
                        cusInfo.setCusRefOther(cus_infoJson.optString("comments"));
                        cusInfo.setU_name(cus_infoJson.optString("u_name"));
                        cusInfo.setParent_name(cus_infoJson.optString("parent_name"));
                        cusInfo.setCusMother(cus_infoJson.optString("mother_name"));
                        cusInfo.setCusFamily(cus_infoJson.optString("family_code"));
                        cusInfo.setCusTSB(cus_infoJson.optString("code"));
                        cusInfo.setNomineeName(cus_infoJson.optString("nominee_name"));
                        cusInfo.setNomineePAN(cus_infoJson.optString("nominee_pan"));

                        final ArrayList<InqChild> inqChildArrayList = new ArrayList<>();
                        for (int j = 0; j < inq_child_json.length(); j++) {

                            JSONObject jsonObject2 = inq_child_json.getJSONObject(j);
                            jsonObject2.optString("Int_id");
                            jsonObject2.optString("inquiryid");
                            jsonObject2.optString("productid");
                            jsonObject2.optString("quantity");
                            jsonObject2.optString("updateddatetime");
                            jsonObject2.optString("status");


                            final InqChild inqChild = new InqChild();
                            inqChild.setInqChildId(jsonObject2.optString("Int_id"));
                            inqChild.setInqMasterId(jsonObject2.optString("inquiryid"));
                            inqChild.setInqChildProductId(jsonObject2.optString("productid"));
                            inqChild.setInqChildproductName(jsonObject2.optString("productname"));
                            inqChild.setInqChildQty(jsonObject2.optString("quantity"));
                            inqChild.setInqChildTimeStamp(jsonObject2.optString("updateddatetime"));
                            inqChild.setInqChildStatus(jsonObject2.optString("status"));
                            inqChild.setInqCatId(jsonObject2.optString("categoryid"));
                            inqChild.setInqCatName(jsonObject2.optString("categoryname"));

                            inqChildArrayList.add(inqChild);


                        }

                        inqMaster.setCusInfo(cusInfo);
                        inqMaster.setInqChild(inqChildArrayList);
                        inqMasterArrayList.add(inqMaster);


                    }


//                    Collections.sort(inqMasterArrayList, new Comparator<InqMaster>() {
//                        @Override
//                        public int compare(InqMaster inqMaster11, InqMaster inqMaster22) {
//
//                            return inqMaster11.getEmpName().compareTo(inqMaster22.getEmpName());
//                        }
//                    });


                    if (inqMasterArrayList != null && inqMasterArrayList.size() > 0) {
                        salesExecutiveSelectSingleChoiceAdapter = new SalesExecutiveSelectSingleChoiceAdapter(inqMasterArrayList, getActivity());
                        recyclerView.setAdapter(salesExecutiveSelectSingleChoiceAdapter);
                    } else {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "No inquiry available", Toast.LENGTH_LONG).show();
                        }
                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();

                if (getActivity() != null) {

                    Toast.makeText(getActivity(), "No Inquiry Available", Toast.LENGTH_LONG).show();
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



