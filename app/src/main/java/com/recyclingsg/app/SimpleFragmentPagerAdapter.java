package com.recyclingsg.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Howard on 13/4/2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public SimpleFragmentPagerAdapter (Context context, FragmentManager fm){
        super(fm);
        mContext = context;

    }

    public CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "Cash For Trash";
            case 1:
                return "eWaste";
            case 2:
                return "General Waste";
            default:
                return null;



        }


    }


    @Override
    public Fragment getItem(int position) {
        if ( position == 0 )
            return new CashForTrashTabFragment();
        else if (position == 1)
            return new eWasteFragment();
        else if (position == 2)
            return new GeneralWasteFragment();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
