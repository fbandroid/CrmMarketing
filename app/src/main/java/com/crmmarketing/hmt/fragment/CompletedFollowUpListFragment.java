package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.model.CusInfo;
import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.webservice.WSInquiry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by USER on 02-03-2017.
 */

public class CompletedFollowUpListFragment extends Fragment implements SearchView.OnQueryTextListener {

    private Context context;
    private int smallWidth;

    private FrameLayout frameLayout;
    private int selectedPos = 0;
    private RecyclerView recyclerView;
    private String response;
    private ProgressDialog progressDialog;
    private LinearLayout parentView;
    private Button btnViewMore;
    private ArrayList<InqMaster> inqMasterArrayList;
    private String userName;
    private String branchId;
    private String id;
    private String role;
    private SalesExecutiveSelectSingleChoiceAdapter salesExecutiveSelectSingleChoiceAdapter;

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
        Configuration config = getResources().getConfiguration();
        smallWidth = config.smallestScreenWidthDp;
        if (getArguments() != null && getArguments().getString("id") != null && getArguments().getString("username") != null) {

            id = getArguments().getString("id");
            userName = getArguments().getString("username");
            role = getArguments().getString("role");

        } else {
            SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
            userName = sharedPref.getString("user_name", null);
            id = String.valueOf(sharedPref.getInt("id", 0));
            role = sharedPref.getString("role", "");
            branchId = sharedPref.getString("branch", "");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Completed Follow up");
        return inflater.inflate(R.layout.fragment_completed_follow_up, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        frameLayout = (FrameLayout) view.findViewById(R.id.info_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_completed_follow_up_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        new LoginCheckTask().execute();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        ((HomeActivity) context).setTitle("Completed Follow up");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem item1 = menu.findItem(R.id.action_add_employee);
        item1.setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }

    private List<InqMaster> filter(List<InqMaster> models, String query) {
        query = query.toLowerCase().trim();
        final List<InqMaster> filteredModelList = new ArrayList<>();
        for (InqMaster model : models) {
            final String text = model.getCusInfo().getCusName().toLowerCase();
            final String empId = model.getCusInfo().getCusMobile();

            if (text.contains(query) || empId.contains(query)) {
                filteredModelList.add(model);
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

    public class SalesExecutiveSelectSingleChoiceAdapter extends RecyclerView.Adapter<SalesExecutiveSelectSingleChoiceAdapter.ViewHolder> {

        private static final String TAG = "ServiceAdapter";
        private final List<InqMaster> filteredUserList;
        public int mSelectedItem = -1;
        private ArrayList<InqMaster> mDataSet;
        private Context context;


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

//            holder.getRb().setChecked(position == mSelectedItem);

//            Glide
//                    .with(context)
//                    .fromResource().load(R.drawable.girlprofile)
//                    .bitmapTransform(new CropCircleTransformation(context))
//                    .placeholder(R.mipmap.ic_launcher)
//                    .crossFade()
//                    .into(holder.getIvProfile());
            if (smallWidth >= 720 && frameLayout != null) {
                // holder.itemView.setSelected(selectedPos == position);

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


            if (mDataSet.get(position).getStausInq().equals("2")) {

                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_red));
                holder.getTvStatus().setText("canceled");


            } else if (mDataSet.get(position).getStausInq().equals("1")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.dark_green));
                holder.getTvStatus().setText("confirm");
            } else if (mDataSet.get(position).getStausInq().equals("0")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.blue));
                holder.getTvStatus().setText("pending");
            } else if (mDataSet.get(position).getStausInq().equals("3")) {
                holder.getTvStatus().setTextColor(getResources().getColor(R.color.darkPink));
                holder.getTvStatus().setText("pending\n with confirm");
            }

            holder.getTvFrame().setText(mDataSet.get(position).getCusInfo().getCusName().toUpperCase().substring(0, 1));

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
            private final TextView tvTSB;

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
                tvTSB = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvTSB);
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

            TextView getTvParentName() {
                return tvParentName;
            }


            TextView getTvStatus() {
                return tvStatus;
            }


        }


    }

    private class LoginCheckTask extends AsyncTask<String, Void, String> {


        private String url;

        LoginCheckTask() {

            url = getString(R.string.completed_api);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();

                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSInquiry().getFollowUpInquiry(url, id, role, branchId);

                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();
                progressDialog.dismiss();
                //Utils.showSnackBar(parentView, "No Internet", getActivity());
                getFragmentManager().popBackStack();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();
                //Utils.showSnackBar(parentView,"No Internet", getActivity());

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            String isValid = null;
            String role = null;
            String username = null;

            inqMasterArrayList = new ArrayList<>();
            int id = 0;

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
//                    inqMasterArrayListgson = (ArrayList<Getinquiry>) restaurantObject.getGetinquiry();

//
                    //gson parse end


                    //manual parsing
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
                        inqMaster.setInqRemark(inquiryMasterJson.optString("inq_remark"));
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

                            inqChildArrayList.add(inqChild);


                        }

                        inqMaster.setCusInfo(cusInfo);
                        inqMaster.setInqChild(inqChildArrayList);
                        inqMasterArrayList.add(inqMaster);


                    }


                    Collections.sort(inqMasterArrayList, new Comparator<InqMaster>() {
                        @Override
                        public int compare(InqMaster inqMaster11, InqMaster inqMaster22) {

                            return inqMaster11.getEmpName().compareTo(inqMaster22.getEmpName());
                        }
                    });


                    if (inqMasterArrayList != null && inqMasterArrayList.size() > 0) {
                        salesExecutiveSelectSingleChoiceAdapter = new SalesExecutiveSelectSingleChoiceAdapter(inqMasterArrayList, getActivity());
                        recyclerView.setAdapter(salesExecutiveSelectSingleChoiceAdapter);
                    } else {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity(), "No Inquiry Available", Toast.LENGTH_LONG).show();

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
