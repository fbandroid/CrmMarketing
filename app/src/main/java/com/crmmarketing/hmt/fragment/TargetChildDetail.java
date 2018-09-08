package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.ViewTargetActivity;
import com.crmmarketing.hmt.targetmodel.Gettarget;

import java.util.ArrayList;



public class TargetChildDetail extends Fragment {

    private RecyclerView recyclerView;
    private Context context;
    private Gettarget gettarget;
    private ProgressBar progressBar;


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

                    gettarget = getParentFragment().getArguments().getParcelable("detail");
                }
            }


        } else {
            gettarget = ((ViewTargetActivity) context).gettarget;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_target_child_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar= (ProgressBar) view.findViewById(R.id.progressBar_cyclic);
        recyclerView = (RecyclerView) view.findViewById(R.id.fragment_target_child_detail_rv);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(dividerItemDecoration);
        if (gettarget != null) {

            recyclerView.setAdapter(new CustomAdapter(gettarget));
        }


    }

    public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
        private static final String TAG = "CustomAdapter";

        Gettarget mDataSet;
        SparseBooleanArray sparseBooleanArray=new SparseBooleanArray();

        // BEGIN_INCLUDE(recyclerViewSampleViewHolder)

        /**
         * Initialize the dataset of the Adapter.
         *
         * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
         */
        public CustomAdapter(Gettarget dataSet) {
            this.mDataSet = dataSet;
        }
        // END_INCLUDE(recyclerViewSampleViewHolder)

        // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view.
            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.row_fragment_target_child_detail, viewGroup, false);

            return new ViewHolder(v);
        }

        // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


            // Get element from your dataset at this position and replace the contents of the view
            // with that element
            viewHolder.getTvPrName().setText(mDataSet.getTarChild().get(position).getProductname());
            viewHolder.getTvPrQty().setText("Price:-".concat(mDataSet.getTarChild().get(position).getQuantity()));
            // viewHolder.getTvStatus().setText(mDataSet.getTarChild().get(position).getStatus());

            viewHolder.getBtnViewMore().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO call web service statistic


                    viewHolder.getFrameLayout().removeAllViews();
                    View inflatedLayout= getActivity().getLayoutInflater().inflate(R.layout.row_statistic, null, false);

                    if(!sparseBooleanArray.get(viewHolder.getAdapterPosition())){
                        viewHolder.getFrameLayout().addView(inflatedLayout);
                        sparseBooleanArray.put(viewHolder.getAdapterPosition(),true);
                    }else {
                        viewHolder.getFrameLayout().removeAllViews();
                        sparseBooleanArray.put(viewHolder.getAdapterPosition(),false);
                    }






                }
            });

        }
        // END_INCLUDE(recyclerViewOnCreateViewHolder)

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataSet.getTarChild().size();
        }
        // END_INCLUDE(recyclerViewOnBindViewHolder)

        /**
         * Provide a reference to the type of views that you are using (custom ViewHolder)
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView tvPrName;
            private final TextView tvPrQty;
            private final TextView tvStatus;
            private final Button btnViewMore;
            private final FrameLayout frameLayout
                    ;

            ViewHolder(View v) {
                super(v);
                // Define click listener for the ViewHolder's View.
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


                tvPrName = (TextView) v.findViewById(R.id.row_fragment_target_child_detail_tvPrName);
                tvPrQty = (TextView) v.findViewById(R.id.row_fragment_target_child_detail_tvQty);
                tvStatus = (TextView) v.findViewById(R.id.row_fragment_target_child_detail_tvStatus);
                btnViewMore= (Button) v.findViewById(R.id.row_fragment_target_child_detail_btnViewMore);
                frameLayout= (FrameLayout) v.findViewById(R.id.flContainer);


            }

            TextView getTvPrName() {
                return tvPrName;
            }

            TextView getTvPrQty() {
                return tvPrQty;
            }

            TextView getTvStatus() {
                return tvStatus;
            }

            FrameLayout getFrameLayout(){
                return frameLayout;
            }

            Button getBtnViewMore(){return  btnViewMore;}
        }
    }


   private class getStatistic extends AsyncTask<Void,Void,Void>{

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           progressBar.setVisibility(View.VISIBLE);
       }

       @Override
       protected Void doInBackground(Void... params) {
           return null;
       }


       @Override
       protected void onPostExecute(Void aVoid) {
           super.onPostExecute(aVoid);
           progressBar.setVisibility(View.GONE);
       }
   }

}
