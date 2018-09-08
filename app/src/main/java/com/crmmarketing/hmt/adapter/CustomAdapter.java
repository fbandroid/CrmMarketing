package com.crmmarketing.hmt.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.gsonmodel22.Datum;

import java.util.ArrayList;
import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    private ArrayList<Datum> mDataSet;
    private Context context;
    private int type;
    private String role;

    private OnVolumeReportClickListener onVolumeReportClickListener;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(ArrayList<Datum> dataSet, Context context, int type, String role) {
        mDataSet = dataSet;
        this.context = context;
        this.type = type;
        this.role = role;
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_fragment_fno, viewGroup, false);

        return new ViewHolder(v);
    }

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {


        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.tvName.setText(mDataSet.get(position).getName());
        viewHolder.tvCode.setText(mDataSet.get(position).getCode());

        switch (type) {

            case 1:

                viewHolder.tv11.setText("Trading");
                viewHolder.tv12.setText("Delivery");
                viewHolder.tv13.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getTrading());
                viewHolder.tv22.setText(mDataSet.get(position).getDelivery());
                viewHolder.tv23.setText(mDataSet.get(position).getTotal());


                if (role.equals("1")) {
                    viewHolder.tv14.setText("Brokerage");
                    viewHolder.tv24.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv14.setText("");
                    viewHolder.tv24.setText("");
                }


                break;
            case 2:
                viewHolder.tv11.setText("Fut. Trd. Val.");
                viewHolder.tv12.setText("Opt. Trd. Val.");
                viewHolder.tv13.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getFut_trd_val());
                viewHolder.tv22.setText(mDataSet.get(position).getOpt_trd_val());
                viewHolder.tv23.setText(mDataSet.get(position).getTotal());

                if (role.equals("1")) {
                    viewHolder.tv14.setText("Brokerage");
                    viewHolder.tv24.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv14.setText("");
                    viewHolder.tv24.setText("");
                }

                break;
            case 3:

                viewHolder.tv11.setText("Fut. Trd. Val.");
                viewHolder.tv12.setText("Opt Trd. Val");
                viewHolder.tv13.setText("Cl.'d Out Val.");
                viewHolder.tv14.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getFut_trd_val());
                viewHolder.tv22.setText(mDataSet.get(position).getOpt_trd_val());
                viewHolder.tv23.setText(mDataSet.get(position).getCld_out_val());
                viewHolder.tv24.setText(mDataSet.get(position).getTotal());

                if (role.equals("1")) {
                    viewHolder.tv31.setText("Brokerage");
                    viewHolder.tv41.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv31.setText("");
                    viewHolder.tv41.setText("");
                }
                break;
            case 4:
                viewHolder.tv11.setText("Fut. Trd. Val.");
                viewHolder.tv12.setText("Opt. Trd. Val.");
                viewHolder.tv13.setText("Cl.'d Out Val");
                viewHolder.tv14.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getFut_trd_val());
                viewHolder.tv22.setText(mDataSet.get(position).getOpt_trd_val());
                viewHolder.tv23.setText(mDataSet.get(position).getCld_out_val());
                viewHolder.tv24.setText(mDataSet.get(position).getTotal());

                if (role.equals("1")) {
                    viewHolder.tv31.setText("Brokerage");
                    viewHolder.tv41.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv31.setText("");
                    viewHolder.tv41.setText("");
                }

                break;
            case 5:
                viewHolder.tv11.setText("Ledger\n Real Time");
                viewHolder.tv12.setText("Stock BHC");
                viewHolder.tv13.setText("Cover BHC");

                viewHolder.tv21.setText(mDataSet.get(position).getLedger());
                viewHolder.tv22.setText(mDataSet.get(position).getStock());
                viewHolder.tv23.setText(mDataSet.get(position).getCover());
                break;
            case 6:
                viewHolder.tv11.setText("Ledger\n Real Time");
                viewHolder.tv12.setText("Stock BHC");
                viewHolder.tv13.setText("Cover BHC");

                viewHolder.tv21.setText(mDataSet.get(position).getLedger());
                viewHolder.tv22.setText(mDataSet.get(position).getStock());
                viewHolder.tv23.setText(mDataSet.get(position).getCover());
                break;
            case 7:
                viewHolder.tv11.setText("Segment");
                viewHolder.tv12.setText("PendingBalance");

                viewHolder.tv21.setText(mDataSet.get(position).getSegment());
                viewHolder.tv22.setText(mDataSet.get(position).getPending());

                break;
            case 8:
                viewHolder.tv11.setText("IPO");
                viewHolder.tv12.setText("lot");
                viewHolder.tv13.setText("Amount");

                viewHolder.tv21.setText(mDataSet.get(position).getIpo());
                viewHolder.tv22.setText(mDataSet.get(position).getLot());
                viewHolder.tv23.setText(mDataSet.get(position).getAmount());
                break;

            case 9:

                viewHolder.tv11.setText("Trading");
                viewHolder.tv12.setText("Delivery");
                viewHolder.tv13.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getTrading());
                viewHolder.tv22.setText(mDataSet.get(position).getDelivery());
                viewHolder.tv23.setText(mDataSet.get(position).getTotal());


                if (role.equals("1")) {
                    viewHolder.tv14.setText("Brokerage");
                    viewHolder.tv24.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv14.setText("");
                    viewHolder.tv24.setText("");
                }

                break;

            case 10:
                viewHolder.tv11.setText("Aging Debit");
                viewHolder.tv12.setText("Permanent DP");
                viewHolder.tv13.setText("Total");
                //viewHolder.tv14.setText("Total");

                viewHolder.tv21.setText(mDataSet.get(position).getAging_debit());
                viewHolder.tv22.setText(mDataSet.get(position).getParmenant_dp());
                viewHolder.tv23.setText(mDataSet.get(position).getTotal());
                // viewHolder.tv24.setText(mDataSet.get(position).getTotal());

                if (role.equals("1")) {
                    viewHolder.tv31.setText("Brokerage");
                    viewHolder.tv41.setText(mDataSet.get(position).getBrokerage());
                } else {
                    viewHolder.tv31.setText("");
                    viewHolder.tv41.setText("");
                }
                break;

            case 11:
                viewHolder.tv11.setText("Credit Amount");
                viewHolder.tv21.setText(mDataSet.get(position).getCr_amount());

                break;

        }


    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    public void setFilter(List<Datum> empInfo) {
        mDataSet = new ArrayList<>();
        mDataSet.addAll(empInfo);
        notifyDataSetChanged();
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public  class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv11;
        private final TextView tv12;
        private final TextView tv13;
        private final TextView tv14;
        private final TextView tv21;
        private final TextView tv22;
        private final TextView tv23;
        private final TextView tv24;
        private final TextView tv31;
        private final TextView tv41;

        private final TextView tvName;
        private final TextView tvCode;

//        private final TextView tvCrAmount;
//        private final TextView tvAgingDebit;
//        private final TextView tvPermanantDp;


        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                       if(onVolumeReportClickListener!=null){
                          onVolumeReportClickListener.onVolumeClick(mDataSet.get(getAdapterPosition()));
                       }


                }
            });
            tv11 = (TextView) v.findViewById(R.id.tv11);
            tv12 = (TextView) v.findViewById(R.id.tv12);
            tv13 = (TextView) v.findViewById(R.id.tv13);
            tv14 = (TextView) v.findViewById(R.id.tv14);
            tv21 = (TextView) v.findViewById(R.id.tv21);
            tv22 = (TextView) v.findViewById(R.id.tv22);
            tv23 = (TextView) v.findViewById(R.id.tv23);
            tv24 = (TextView) v.findViewById(R.id.tv24);
            tv31 = (TextView) v.findViewById(R.id.tv31);
            tv41 = (TextView) v.findViewById(R.id.tv41);

            tvName = (TextView) v.findViewById(R.id.tvCusName);
            tvCode = (TextView) v.findViewById(R.id.tvTSBCode);


        }

    }

    public void setOnVolumeClickListener(OnVolumeReportClickListener onVolumeClickListener){
        this.onVolumeReportClickListener = onVolumeClickListener;
    }
}