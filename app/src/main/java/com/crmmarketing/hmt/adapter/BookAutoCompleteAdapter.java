package com.crmmarketing.hmt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.ProductInfo;
import com.crmmarketing.hmt.poductmodel.PrList;

import java.util.ArrayList;
import java.util.List;

public class BookAutoCompleteAdapter extends BaseAdapter implements Filterable {


    private Context mContext;
    private List<PrList> resultList = new ArrayList<PrList>();
    private ArrayList<PrList> productInfoArrayList;

    public BookAutoCompleteAdapter(Context context, ArrayList<PrList> productInfoArrayList) {
        mContext = context;
        this.productInfoArrayList = productInfoArrayList;
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public PrList getItem(int index) {
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
        ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).getPrdName());
        ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).getPcatName());
        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<PrList> books = findBooks(mContext, constraint.toString());


                    // Assign the data to the FilterResults
                    filterResults.values = books;
                    filterResults.count = books.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    resultList = (List<PrList>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<PrList> findBooks(Context context, String bookTitle) {
        // GoogleBooksProtocol is a wrapper for the Google Books API

        ArrayList<PrList> arrayList = new ArrayList<>();

        for (PrList model : productInfoArrayList) {
            final String text = model.getPrdName().toLowerCase().trim();
            final String empId = model.getPrdPrice().toLowerCase();
            final String cat = model.getPcatName().toLowerCase();
            if (text.contains(bookTitle.trim()) || empId.contains(bookTitle.trim()) || cat.contains(bookTitle.trim())) {
                arrayList.add(model);
            }
        }


        return arrayList;
    }
}