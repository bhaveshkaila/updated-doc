package com.musica.cortador.ui.fragment;


import android.content.ComponentName;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.musica.cortador.R;
import com.musica.cortador.adapter.TabPagerAdapter;
import com.musica.cortador.listner.FloatingBtnClickListner;
import com.musica.cortador.services.ServiceMovies;
import com.musica.cortador.tabs.SlidingTabLayout;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements MaterialTabListener, View.OnClickListener {

    private static final int JOB_ID = 100;
    private static final long POLL_FREQUENCY = 30000;
    private  SlidingTabLayout mTabs;
    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private TabPagerAdapter mTabAdapter;


    //tag associated with the FAB menu button that sorts by name
    private static final String TAG_SORT_NAME = "sortName";
    //tag associated with the FAB menu button that sorts by date
    private static final String TAG_SORT_DATE = "sortDate";
    //tag associated with the FAB menu button that sorts by ratings
    private static final String TAG_SORT_RATINGS = "sortRatings";
    private JobScheduler mJobScheduler;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mJobScheduler=JobScheduler.getInstance(getActivity());
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        mTabHost=(MaterialTabHost)view.findViewById(R.id.materialTabHost);
        mViewPager=(ViewPager)view.findViewById(R.id.tabViewPager);



        setUpTabs();

        setUpFloatingActionBtn();

//        buildJob();
        return view;
    }

    public void setUpFloatingActionBtn(){

        ImageView imageView=new ImageView(getActivity());
        imageView.setImageResource(R.drawable.ic_launcher);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(getActivity())
                .setContentView(imageView)
                .build();

        //define the icons for the sub action buttons
        ImageView iconSortName = new ImageView(getActivity());
        iconSortName.setImageResource(R.drawable.ic_launcher);
        ImageView iconSortDate = new ImageView(getActivity());
        iconSortDate.setImageResource(R.drawable.ic_launcher);
        ImageView iconSortRatings = new ImageView(getActivity());
        iconSortRatings.setImageResource(R.drawable.ic_launcher);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        SubActionButton btnSortByName = itemBuilder.setContentView(iconSortName).build();
        SubActionButton btnSortByDate = itemBuilder.setContentView(iconSortDate).build();
        SubActionButton btnSortByRating = itemBuilder.setContentView(iconSortRatings).build();

        btnSortByName.setTag(TAG_SORT_NAME);
        btnSortByDate.setTag(TAG_SORT_DATE);
        btnSortByRating.setTag(TAG_SORT_RATINGS);

        btnSortByName.setOnClickListener(this);
        btnSortByDate.setOnClickListener(this);
        btnSortByRating.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .addSubActionView(btnSortByName)
                .addSubActionView(btnSortByDate)
                .addSubActionView(btnSortByRating)
                .attachTo(actionButton)
                .build();
    }

    public void setUpTabs(){



        mTabAdapter=new TabPagerAdapter(getActivity(),getActivity().getSupportFragmentManager(),getFragments());
        mViewPager.setAdapter(mTabAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mTabHost.setSelectedNavigationItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        // insert all tabs from pagerAdapter data
        for (int i = 0; i <mTabAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setText(mTabAdapter.getPageTitle(i))
                            .setTabListener(this)
            );
        }
    }
    private void buildJob() {
        //attach the job ID and the name of the Service that will work in the background
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(getActivity(), ServiceMovies.class));
        //set periodic polling that needs net connection and works across device reboots
        builder.setPeriodic(POLL_FREQUENCY)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);
        mJobScheduler.schedule(builder.build());
    }

    private List<Fragment> getFragments(){
        List<Fragment> list=new ArrayList<>();

        FromMeFragment from=new FromMeFragment();
        FriendsFragment friends=new FriendsFragment();
        MessagesFragment messages=new MessagesFragment();

        list.add(from);
        list.add(messages);
        list.add(friends);

        return list;
    }
    @Override
    public void onTabSelected(MaterialTab materialTab) {

        Log.d("TAG",""+materialTab.toString());
        mViewPager.setCurrentItem(materialTab.getPosition());

    }

    @Override
    public void onTabReselected(MaterialTab materialTab) {

    }

    @Override
    public void onTabUnselected(MaterialTab materialTab) {

    }

    @Override
    public void onClick(View v) {
        Fragment fragment = (Fragment) mTabAdapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        if (fragment instanceof FloatingBtnClickListner) {

            if (v.getTag().equals(TAG_SORT_NAME)) {
                //call the sort by name method on any Fragment that implements sortlistener
                ((FloatingBtnClickListner) fragment).onSortByName();
            }
            if (v.getTag().equals(TAG_SORT_DATE)) {
                //call the sort by date method on any Fragment that implements sortlistener
                ((FloatingBtnClickListner) fragment).onSortByDate();
            }
            if (v.getTag().equals(TAG_SORT_RATINGS)) {
                //call the sort by ratings method on any Fragment that implements sortlistener
                ((FloatingBtnClickListner) fragment).onSortByRatting();
            }
        }
    }
}
