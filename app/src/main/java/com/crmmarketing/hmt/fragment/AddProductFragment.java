package com.crmmarketing.hmt.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.crmmarketing.hmt.HomeActivity;
import com.crmmarketing.hmt.R;


public class AddProductFragment extends Fragment {

    private Context context;
    private Spinner spCategoty;
    private TextInputEditText edtProductName;
    private TextInputEditText edtProductPrice;
    private TextInputEditText edtProductDesc;
    private Button btnSubmit;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((HomeActivity) context).setTitle("Add product");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_product, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spCategoty = (Spinner) view.findViewById(R.id.fragment_add_product_spCategory);
        edtProductName = (TextInputEditText) view.findViewById(R.id.fragment_add_product_edtProductName);
        edtProductPrice = (TextInputEditText) view.findViewById(R.id.fragment_add_product_edtProductPrice);
        edtProductDesc = (TextInputEditText) view.findViewById(R.id.fragment_add_product_edtProductDesc);
        btnSubmit = (Button) view.findViewById(R.id.fragment_add_product_btnSubmit);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        ((HomeActivity) context).setTitle("Add product");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_product, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_product) {
            Toast.makeText(getActivity(), "add product", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
