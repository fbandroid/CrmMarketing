package com.crmmarketing.hmt.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crmmarketing.hmt.InquirtDetailActivity;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.InqChild;
import com.crmmarketing.hmt.model.InqMaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 17-02-2017.
 */

public class ProductInfoFragment extends Fragment {


    private RecyclerView recyclerView;
    private TextView tvProductRange;
    private TextView tvProductRemark;
    private TextView tvFeedBack;
    private TextView tvStatus;
    private Context context;
    private InqMaster inqMaster;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getParentFragment() != null) {

            if (getParentFragment().getArguments() != null) {

                if (getParentFragment().getArguments().getParcelable("detail") != null) {

                    inqMaster = getParentFragment().getArguments().getParcelable("detail");
                }
            }

        } else {
            inqMaster = ((InquirtDetailActivity) context).inqMaster;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_product_info_rv);
        recyclerView.setNestedScrollingEnabled(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(dividerItemDecoration);
        tvFeedBack = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtFeedbackDay);
        tvProductRange = (TextView) view.findViewById(R.id.fragment_inquiry_form_spRange);
        tvProductRemark = (TextView) view.findViewById(R.id.fragment_inquiry_form_edtRemark);
        tvStatus= (TextView) view.findViewById(R.id.fragment_inquiry_form_edtStatus);

        if (inqMaster != null) {
            tvProductRange.setText(inqMaster.getInqInvestRange());
            tvFeedBack.setText(inqMaster.getInqFeedbackTime());
            tvProductRemark.setText(inqMaster.getInqRemark());
            if(inqMaster.getStausInq().equals("2")){

                tvStatus.setTextColor(getResources().getColor(R.color.dark_red));
               tvStatus.setText("canceled");


            }
            else if(inqMaster.getStausInq().equals("1")){
                tvStatus.setTextColor(getResources().getColor(R.color.dark_green));
                tvStatus.setText("confirm");
            }

            else if(inqMaster.getStausInq().equals("0")){
                tvStatus.setTextColor(getResources().getColor(R.color.blue));
                tvStatus.setText("pending");
            }

            recyclerView.setAdapter(new CustomAdapter((ArrayList<InqChild>) inqMaster.getInqChild()));
        }

    }


    /**
     * Provide views to RecyclerView with data from mDataSet.
     */
    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        private ArrayList<InqChild> mDataSet;

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final TextView textView22;

            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                textView = (TextView) v.findViewById(R.id.row_product_name);
                textView22 = (TextView) v.findViewById(R.id.row_product_id);

            }

            TextView getTextView() {
                return textView;
            }
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(ArrayList<InqChild> dataSet) {
            this.mDataSet = dataSet;
        }

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_product_view, viewGroup, false);

            return new ViewHolder(v);
        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.getTextView().setText(mDataSet.get(position).getInqChildproductName());

        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }
}


