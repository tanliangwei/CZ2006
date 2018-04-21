package com.recyclingsg.app.boundary;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.recyclingsg.app.boundary.CashForTrashTabFragment;
import com.recyclingsg.app.boundary.SecondHandTabFragment;
import com.recyclingsg.app.boundary.eWasteTabFragment;

import java.util.ArrayList;

/**
 * Created by Howard on 13/4/2018.
 */

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SimpleFragmentPagerAdap";
    private String tabTitles[] =new String[]{"Cash For Trash", "eWaste", "Second Hand Goods"};
    private int PAGECOUNT = 3;
    private CashForTrashTabFragment cftFragment;
    private eWasteTabFragment ewFragment;
    private SecondHandTabFragment shFragment;


    private Context mContext;

    public SimpleFragmentPagerAdapter (Context context, FragmentManager fm){
        super(fm);
        mContext = context;

    }

    public void compileTtpList(ArrayList<String> trashTypes, ArrayList<String> trashNames, ArrayList<Double> trashPrices, ArrayList<String>trashUnits){
        if (cftFragment.isChecked()){
            Log.d(TAG, "compileTtpList: cft is checked");
            trashTypes.add("Cash For Trash");
            cftFragment.compileCFTposts();
            trashNames.addAll(cftFragment.getTrashNames());
            trashPrices.addAll(cftFragment.getTrashPrices());
            trashUnits.addAll(cftFragment.getTrashUnits());
        }
        if (ewFragment.isChecked()) {
            Log.d(TAG, "compileTtpList: ewaste is checked");
            trashTypes.add("eWaste");
        }
        if (shFragment.isCheck()) {
            Log.d(TAG, "compileTtpList: shfragment is checked");
            trashTypes.add("Second Hand Goods");
        }

    }


    public CharSequence getPageTitle(int position){
        return tabTitles[position];



    }


    @Override
    public Fragment getItem(int position) {
        if ( position == 0 )
            return cftFragment = CashForTrashTabFragment.newInstance(position+1);
        else if (position == 1)
            return ewFragment = new eWasteTabFragment();
        else if (position == 2)
            return shFragment = new SecondHandTabFragment();
        return null;
    }

    @Override
    public int getCount() {
        return PAGECOUNT;
    }
}
