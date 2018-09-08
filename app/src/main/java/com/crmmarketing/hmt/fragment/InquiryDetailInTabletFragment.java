package com.crmmarketing.hmt.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;


import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crmmarketing.hmt.R;
import com.crmmarketing.hmt.model.InqMaster;
import com.crmmarketing.hmt.targetmodel.Gettarget;
import com.crmmarketing.hmt.targetmodel.TarChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 23-02-2017.
 */

public class InquiryDetailInTabletFragment extends Fragment {
    public InqMaster inqMaster;
    private Gettarget gettarget;
    private PersonalInfoFragment personalInfoFragment;
    private ProductInfoFragment productInfoFragment;
    private ReferenceInfoFragment referenceInfoFragment;
    private ReportinquiryFragment reportinquiryFragment;
    private TargetMasterDetail targetMasterDetail;
    private TargetChildDetail targetChildDetail;
    private String from;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        personalInfoFragment = new PersonalInfoFragment();
        productInfoFragment = new ProductInfoFragment();
        referenceInfoFragment = new ReferenceInfoFragment();
        reportinquiryFragment = new ReportinquiryFragment();
        targetMasterDetail = new TargetMasterDetail();
        targetChildDetail = new TargetChildDetail();

        if (getArguments() != null) {

            if (getArguments().getParcelable("detail") != null) {

                from = getArguments().getString("from");

                if(getArguments().getParcelable("detail") instanceof InqMaster){

                    inqMaster=getArguments().getParcelable("detail");
                }
                else if(getArguments().getParcelable("detail") instanceof  Gettarget){
                    gettarget=getArguments().getParcelable("detail");
                }


            }


            if (inqMaster != null) {

                productInfoFragment.setArguments(getArguments());
                personalInfoFragment.setArguments(getArguments());
                referenceInfoFragment.setArguments(getArguments());
                reportinquiryFragment.setArguments(getArguments());

            } else if (gettarget != null) {
                targetMasterDetail.setArguments(getArguments());
                targetChildDetail.setArguments(getArguments());

            }


        }
    }


    private void setupViewPager(ViewPager viewPager) {


        Adapter adapter = new Adapter(getChildFragmentManager());

        if (inqMaster != null) {

            adapter.addFragment(personalInfoFragment, "INFO");
            adapter.addFragment(productInfoFragment, "PRODUCT");
            if (from != null) {
                adapter.addFragment(reportinquiryFragment, "REPORT");
            }
            adapter.addFragment(referenceInfoFragment, "REFERENCE");
            viewPager.setAdapter(adapter);

        } else if (gettarget != null) {

            adapter.addFragment(targetMasterDetail, "TARGET");
            adapter.addFragment(targetChildDetail, "PRODUCT");
            viewPager.setAdapter(adapter);
        }


    }

    class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        Adapter(android.app.FragmentManager manager) {
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inquiry_detail_tablet, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
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


}




