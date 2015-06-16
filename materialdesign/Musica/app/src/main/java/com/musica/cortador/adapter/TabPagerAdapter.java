package com.musica.cortador.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;
import android.widget.Switch;

import com.musica.cortador.R;
import com.musica.cortador.ui.fragment.FriendsFragment;
import com.musica.cortador.ui.fragment.FromMeFragment;
import com.musica.cortador.ui.fragment.HomeFragment;
import com.musica.cortador.ui.fragment.MessagesFragment;
import com.musica.cortador.ui.fragment.SecondFragment;

import java.util.List;

/**
 * Created by Bhavesh.Kaila on 20-May-15.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {

    // Sparse array to keep track of registered fragments in memory
    private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();


    int icons[]={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
    String[] tabs={"FROM ME","MESSAGE","FRIENDS"};

    private Context mContext;
    private List<Fragment> mFragmentList;


    public TabPagerAdapter(Context context,FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.mContext=context;
        mFragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
//        SecondFragment fragment=SecondFragment.getInstance(position);
        Fragment fragment=mFragmentList.get(position);
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        Drawable drawable=mContext.getResources().getDrawable(icons[position]);
//        drawable.setBounds(0,0,36,36);
//        ImageSpan imageSpan=new ImageSpan(drawable);
//        SpannableString spannableString=new SpannableString(" ");
//        spannableString.setSpan(imageSpan,0,spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    // Register the fragment when the item is instantiated
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    // Unregister when the item is inactive
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    // Returns the fragment for the position (if instantiated)
    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }
}
