package com.crmmarketing.hmt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.crmmarketing.hmt.Utils.Utils;
import com.crmmarketing.hmt.fragment.CashVolumeFragment;
import com.crmmarketing.hmt.fragment.CommodityVolume;
import com.crmmarketing.hmt.fragment.CrDataFragment;
import com.crmmarketing.hmt.fragment.CurrancyVolumeFragment;
import com.crmmarketing.hmt.fragment.DrDataFragment;
import com.crmmarketing.hmt.fragment.FnoFragment;
import com.crmmarketing.hmt.fragment.IPOFragment;
import com.crmmarketing.hmt.fragment.NCDEXFragment;
import com.crmmarketing.hmt.fragment.NSEFragment;
import com.crmmarketing.hmt.fragment.PrData;

import java.util.ArrayList;
import java.util.List;

public class FNOActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fno);


        SharedPreferences sharedPref = getSharedPreferences("my", MODE_PRIVATE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Volume Data");

        // Set Collapsing Toolbar layout to the screen

        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));


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

        adapter.addFragment(new CashVolumeFragment(), "Total-turnOver");
//        adapter.addFragment(new NSEFragment(), "NSE");
//        adapter.addFragment(new FnoFragment(), "FNO volume");
//        adapter.addFragment(new CommodityVolume(), "MCX");
//        adapter.addFragment(new NCDEXFragment(), "NCDEX");
//        adapter.addFragment(new CurrancyVolumeFragment(), "Currancy volume");

//        if (from != null) {
//            adapter.addFragment(new ReportinquiryFragment(), "REPORT");
//        }


        adapter.addFragment(new DrDataFragment(), "Dr-Pr Data");
        adapter.addFragment(new CrDataFragment(), "Cr Data");
//        adapter.addFragment(new PrData(), "Pr Data");
//        adapter.addFragment(new IPOFragment(), "IPO");


        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
