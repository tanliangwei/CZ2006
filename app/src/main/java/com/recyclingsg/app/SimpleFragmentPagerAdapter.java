package com.recyclingsg.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Howard on 13/4/2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] =new String[]{"Cash For Trash", "eWaste", "General Waste"};
    private int PAGECOUNT = 3;


    private Context mContext;

    public SimpleFragmentPagerAdapter (Context context, FragmentManager fm){
        super(fm);
        mContext = context;

    }




    public CharSequence getPageTitle(int position){
        return tabTitles[position];



    }


    @Override
    public Fragment getItem(int position) {
        if ( position == 0 )
            return CashForTrashTabFragment.newInstance(position+1);
        else if (position == 1)
            return new eWasteTabFragment();
        else if (position == 2)
            return new SecondHandTabFragment();
        return null;
    }

    @Override
    public int getCount() {
        return PAGECOUNT;
    }
}
