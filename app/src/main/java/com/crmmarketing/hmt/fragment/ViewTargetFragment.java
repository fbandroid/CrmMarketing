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
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.ViewTargetActivity;
import com.crmmarketing.hmt.targetmodel.Example;
import com.crmmarketing.hmt.targetmodel.Gettarget;
import com.crmmarketing.hmt.webservice.WSInquiry;
import com.google.gson.Gson;

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
 * Created by USER on 04-03-2017.
 */

public class ViewTargetFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView recyclerView;
    private String userName;
    private String id;
    private String role;
    private int selectedPos = 0;
    private ProgressDialog progressDialog;
    private int smallWidth;
    private FrameLayout frameLayout;
    private SalesExecutiveSelectSingleChoiceAdapter salesExecutiveSelectSingleChoiceAdapter;
    private ArrayList<Gettarget> inqMasterArrayList;
    private Context context;
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

        if (getArguments() != null && getArguments().getString("id") != null && getArguments().getString("username") != null) {

            id = getArguments().getString("id");
            userName = getArguments().getString("username");
            role = getArguments().getString("role");

        } else {
            SharedPreferences sharedPref = getActivity().getSharedPreferences("my", MODE_PRIVATE);
            userName = sharedPref.getString("user_name", null);
            id = String.valueOf(sharedPref.getInt("id", 0));

        }
        Configuration config = getResources().getConfiguration();
        smallWidth = config.smallestScreenWidthDp;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("View Target");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_view_target, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = (FrameLayout) view.findViewById(R.id.info_container);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_view_target_rv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);

        // call web service to fetch data
        new LoginCheckTask().execute();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem item = menu.findItem(R.id.action_search);
        final MenuItem item1=menu.findItem(R.id.action_add_employee);
        item1.setVisible(false);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return true;

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (salesExecutiveSelectSingleChoiceAdapter != null) {

            final List<Gettarget> filteredModelList = filter(inqMasterArrayList, newText);

            salesExecutiveSelectSingleChoiceAdapter.setFilter(filteredModelList);

        }

        return true;
    }


    private List<Gettarget> filter(List<Gettarget> models, String query) {
        query = query.toLowerCase().trim();
        final List<Gettarget> filteredModelList = new ArrayList<>();
        for (Gettarget model : models) {
            final String text = model.getTarMaster().getTarChildname().toLowerCase();
            final String empId = model.getTarMaster().getTarStartdate().toLowerCase();
            final String  enddate=model.getTarMaster().getTarEnddate().toLowerCase();
            if (text.contains(query) || empId.contains(query) || enddate.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    public class SalesExecutiveSelectSingleChoiceAdapter extends RecyclerView.Adapter<SalesExecutiveSelectSingleChoiceAdapter.ViewHolder> {

        private static final String TAG = "ServiceAdapter";
        private final List<Gettarget> filteredUserList;
        public int mSelectedItem = -1;
        private ArrayList<Gettarget> mDataSet;
        private Context context;


        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public SalesExecutiveSelectSingleChoiceAdapter(ArrayList<Gettarget> dataSet, Context context) {
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
                    .inflate(R.layout.row_fragment_view_target, viewGroup, false);
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


            holder.getTvFrame().setText(mDataSet.get(position).getTarMaster().getTarChildname().toUpperCase().substring(0, 1));

            holder.getTvEmpNmae().setText(mDataSet.get(position).getTarMaster().getTarChildname());
            holder.getTvDate().setText(mDataSet.get(position).getTarMaster().getTarStartdate().concat(" to ").concat(mDataSet.get(position).getTarMaster().getTarEnddate()));

//            holder.getTvEmpName().setText(mDataSet.get(position).getEmpName());
//            holder.getTvParentName().setText(mDataSet.get(position).getParentName());
            StringBuilder stringBuilder = new StringBuilder();

            if (mDataSet.get(position).getTarChild() != null) {
                for (int i = 0; i < mDataSet.get(position).getTarChild().size(); i++) {

                    stringBuilder.append(mDataSet.get(position).getTarChild().get(i).getProductname().concat(" ,"));
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

        void setFilter(List<Gettarget> empInfo) {
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
            private final TextView tvDate;
            private final FrameLayout frameLayout;
            private final TextView tvFrame;
            private final TextView tvProduct;


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
                            Intent intent = new Intent(getActivity(), ViewTargetActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }


                        // addFragment(new TeamLeadDeatilFragment(),((AppCompatActivity)context).getFragmentManager().findFragmentByTag(ListOfTeamLeaderFragment.class.getSimpleName()));

                    }
                });
                tvEmpNmae = (TextView) v.findViewById(R.id.fragment_target_list_tvCusName);
                tvDate = (TextView) v.findViewById(R.id.fragment_target_list_tvDate);

                frameLayout = (FrameLayout) v.findViewById(R.id.frame_layout);
                tvFrame = (TextView) v.findViewById(R.id.tvFrame);
                tvProduct = (TextView) v.findViewById(R.id.fragment_target_list_tvProduct);
//                tvEmpName = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvEmpName);
//                tvParentName = (TextView) v.findViewById(R.id.fragment_inquiry_list_tvTopEmp);

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


            FrameLayout getFrameLayout() {
                return frameLayout;
            }

            TextView getTvFrame() {
                return tvFrame;
            }

            TextView getTvProduct() {
                return tvProduct;
            }


        }


    }


    private class LoginCheckTask extends AsyncTask<String, Void, String> {

        private String response;
        private String url;

        LoginCheckTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = getString(R.string.view_tar_api);
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                // response = new WSLogin(getActivity(),getResources().getString(R.string.login_url),username,password).postLoginData();
                // response=new WSLogin().postLoginData(getString(R.string.login_url),loginJSON(username,password));
                response = new WSInquiry().getTargetList(url, id);
                Log.e("Response", response);
            } catch (IOException e) {
                e.printStackTrace();

//                Utils.showSnackBar(parentView, e.getMessage(), getActivity());
                getFragmentManager().popBackStack();


            } catch (IllegalArgumentException e) {

                e.printStackTrace();
//                Utils.showSnackBar(parentView, e.getMessage(), getActivity());

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
            if (response != null) {


                //gson parse start
//
                Gson gson = new Gson();

                Example restaurantObject = gson.fromJson(response, Example.class);
                inqMasterArrayList = (ArrayList<Gettarget>) restaurantObject.getGettarget();

                //gson parse end


            }

//
//                    Collections.sort(inqMasterArrayList, new Comparator<InqMaster>() {
//                        @Override
//                        public int compare(InqMaster inqMaster11, InqMaster  inqMaster22)
//                        {
//
//                            return  inqMaster11.getCusInfo().getU_name().compareTo(inqMaster22.getCusInfo().getU_name());
//                        }
//                    });


            if (inqMasterArrayList!=null && inqMasterArrayList.size()>0) {
                salesExecutiveSelectSingleChoiceAdapter = new SalesExecutiveSelectSingleChoiceAdapter(inqMasterArrayList, getActivity());
                recyclerView.setAdapter(salesExecutiveSelectSingleChoiceAdapter);
            }
            else {
                if(getActivity()!=null){
                    Toast.makeText(getActivity(),"No Target available",Toast.LENGTH_LONG).show();
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
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        ((HomeActivity) context).setTitle("View Target");
    }

}
