package com.crmmarketing.hmt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;


import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.fragment.ChainInquiryInfoFragment;
import com.crmmarketing.hmt.fragment.EditTeamLeadFragment;
import com.crmmarketing.hmt.fragment.InquiryListInfoFragment;
import com.crmmarketing.hmt.fragment.PersonalInfoFragment;
import com.crmmarketing.hmt.fragment.ProductInfoFragment;
import com.crmmarketing.hmt.fragment.ReferenceInfoFragment;
import com.crmmarketing.hmt.fragment.ReportinquiryFragment;
import com.crmmarketing.hmt.fragment.UploadDocumentFragment;
import com.crmmarketing.hmt.fragment.ViewUploadDocument;
import com.crmmarketing.hmt.model.InqMaster;

import java.util.ArrayList;
import java.util.List;

public class InquirtDetailActivity extends AppCompatActivity {

    public InqMaster inqMaster;
    private String from;
    private String role;

    private boolean isEditable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);

        role = String.valueOf(sharedPref.getString("role", ""));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set Collapsing Toolbar layout to the screen

        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        if (getIntent() != null) {

            if (getIntent().getExtras() != null) {

                if (getIntent().getExtras().getParcelable("detail") != null) {
                    inqMaster = getIntent().getExtras().getParcelable("detail");
                    from = getIntent().getStringExtra("from");
                    if (inqMaster != null) {
                        toolbar.setTitle(inqMaster.getCusInfo().getCusName());
                    }

                }
            }
        }


        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {



        Adapter adapter = new Adapter(getFragmentManager());

        if(inqMaster.getStausInq().equals("4")){
            adapter.addFragment(new PersonalInfoFragment(), "INFO");
            adapter.addFragment(new ProductInfoFragment(), "PRODUCT");
            adapter.addFragment(new ReferenceInfoFragment(), "REFERENCE");
        }
        else {
            adapter.addFragment(new ChainInquiryInfoFragment(), "INQUIRY");
            adapter.addFragment(new PersonalInfoFragment(), "INFO");
            adapter.addFragment(new ProductInfoFragment(), "PRODUCT");
            adapter.addFragment(new ReportinquiryFragment(), "REPORT");

//        if (from != null) {
//            adapter.addFragment(new ReportinquiryFragment(), "REPORT");
//        }


            adapter.addFragment(new ReferenceInfoFragment(), "REFERENCE");
            adapter.addFragment(new ViewUploadDocument(),"DOCUMENT");
            adapter.addFragment(new UploadDocumentFragment(), "UPLOAD");
        }

        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (!role.equals("6")) {

            getMenuInflater().inflate(R.menu.menu_edit, menu);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.edit_inq:
                isEditable = true;

                if (Utils.checkInternetConnection(InquirtDetailActivity.this)) {
                    Intent intent = new Intent(InquirtDetailActivity.this, EditInquiryactivity.class);
                    intent.putExtra("detail", inqMaster);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(InquirtDetailActivity.this, "No Internet", Toast.LENGTH_LONG).show();
                }


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

