package com.crmmarketing.hmt.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.common.RetrofitClient;
import com.crmmarketing.hmt.cons.Constants;
import com.crmmarketing.hmt.gsonmodel22.ClientDetail;
import com.crmmarketing.hmt.gsonmodel22.Customer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by USER on 15-06-2017.
 */

public class ClientFilteradapter extends BaseAdapter implements Filterable {

    private Context mContext;
    private RetrofitClient.SearchByTSBPAN byTSBPAN;
    private List<Customer> resultList = new ArrayList<Customer>();
    private ArrayList<Customer> customerArrayList = new ArrayList<>();
    private String id;

    public ClientFilteradapter(String id, Context context) {
        mContext = context;

        byTSBPAN = RetrofitClient.searchbytsbpan(Constants.BASE_URL);
        this.id = id;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Customer getItem(int index) {
        return resultList.get(index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.simple_dropdown_item_2line, parent, false);
        }
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).getName());
        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getAddress());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<Customer> books = findBooks(mContext, constraint.toString());


                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<Customer>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    private List<Customer> findBooks(Context context, String bookTitle) {
        // GoogleBooksProtocol is a wrapper for the Google Books API

        byTSBPAN.tsbPanvala(id, "3", bookTitle).enqueue(new Callback<ClientDetail>() {
            @Override
            public void onResponse(Call<ClientDetail> call, Response<ClientDetail> response) {
                if (response.isSuccessful()) {

                    customerArrayList = (ArrayList<Customer>) response.body().getCustomers();
                }
            }

            @Override
            public void onFailure(Call<ClientDetail> call, Throwable t) {

            }
        });


        return customerArrayList;
    }
}
