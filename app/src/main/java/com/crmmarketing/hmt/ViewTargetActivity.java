package com.crmmarketing.hmt;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.crmmarketing.hmt.fragment.TargetChildDetail;
import com.crmmarketing.hmt.fragment.TargetMasterDetail;
import com.crmmarketing.hmt.targetmodel.Gettarget;

import java.util.ArrayList;
import java.util.List;

public class ViewTargetActivity extends AppCompatActivity {
    public Gettarget gettarget;
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


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
                    gettarget = getIntent().getExtras().getParcelable("detail");
                    from=getIntent().getStringExtra("from");
                    if (gettarget != null) {
                        toolbar.setTitle(gettarget.getTarMaster().getTarChildname());
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

                Log.e("page scroll", "detail");
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("page selected", "detail");

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                Log.e("state changed", "detail");
            }
        });

    }


    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getFragmentManager());
        adapter.addFragment(new TargetMasterDetail(), "TARGET");
        adapter.addFragment(new TargetChildDetail(), "PRODUCT");

        viewPager.setAdapter(adapter);
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



}
