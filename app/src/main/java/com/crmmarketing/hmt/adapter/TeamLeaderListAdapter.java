package com.crmmarketing.hmt.adapter;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.crmmarketing.hmt.GsonModel.User;
import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.CropCircleTransformation;
import com.crmmarketing.hmt.fragment.ListOfTeamLeaderFragment;
import com.crmmarketing.hmt.fragment.TeamLeadDeatilFragment;
import com.crmmarketing.hmt.model.EmployeeInfo;

import java.util.ArrayList;
import java.util.List;


public class TeamLeaderListAdapter extends RecyclerView.Adapter<TeamLeaderListAdapter.ViewHolder> {

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
        private final TextView tvDesg;
        private final TextView tvDep;


        ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                    final Fragment fragment = new TeamLeadDeatilFragment();
                    final Bundle bundle = new Bundle();
                    bundle.putParcelable("detail", mDataSet.get(getAdapterPosition()));
                    fragment.setArguments(bundle);
                    addFragment(fragment, ((AppCompatActivity) context).getFragmentManager().findFragmentByTag(ListOfTeamLeaderFragment.class.getSimpleName()));

                }
            });
            tvEmpNmae = (TextView) v.findViewById(R.id.row_admin_dashboard_tvName);
            tvEmpid = (TextView) v.findViewById(R.id.row_admin_dashboard_tvId);
            ivProfile = (ImageView) v.findViewById(R.id.row_admin_dashboard_ivprofile);
            tvDesg= (TextView) v.findViewById(R.id.row_admin_dashboard_tvDesignation);
            tvDep= (TextView) v.findViewById(R.id.row_admin_dashboard_tvDepartment);

            tvDep.setVisibility(View.VISIBLE);

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

    }

    // END_INCLUDE(recyclerViewSampleViewHolder)

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public TeamLeaderListAdapter(ArrayList<User> dataSet, Context context) {
        this.mDataSet = dataSet;
        this.context = context;
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_list_of_teamlead, viewGroup, false);

        return new ViewHolder(v);
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getTvEmpNmae().setText(mDataSet.get(position).getUName());
        viewHolder.getTvEmpid().setText(mDataSet.get(position).getUUsername());
        viewHolder.tvDesg.setText(mDataSet.get(position).getPost());

        Glide
                .with(context)
                .fromResource().load(R.drawable.profile)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.mipmap.ic_launcher)
                .crossFade()
                .into(viewHolder.getIvProfile());


        // viewHolder.getImageViewLogo().setImageDrawable(context.getResources().getDrawable(mDataSet.get(position).getDrawableId()));
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);

        Glide.clear(holder.getIvProfile());

    }

    private void addFragment(Fragment toAdd, Fragment current) {

        ((AppCompatActivity) context).getFragmentManager().beginTransaction()
                .add(R.id.content_home, toAdd, toAdd.getClass().getSimpleName())
                .addToBackStack(toAdd.getClass().getSimpleName())
                .hide(current).commitAllowingStateLoss();
    }


    public void setFilter(List<User> empInfo) {
        mDataSet = new ArrayList<>();
        mDataSet.addAll(empInfo);
        notifyDataSetChanged();
    }


}
