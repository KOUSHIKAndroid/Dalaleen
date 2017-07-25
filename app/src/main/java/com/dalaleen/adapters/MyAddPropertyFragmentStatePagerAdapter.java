package com.dalaleen.adapters;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.LinkedList;

/**
 * Created by su on 4/13/17.
 */

public class MyAddPropertyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    LinkedList<Fragment> listItems;

    public MyAddPropertyFragmentStatePagerAdapter(FragmentManager fm, LinkedList<Fragment> list) {
        super(fm);
        listItems = list;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        super.restoreState(state, loader);
    }

    @Override
    public Fragment getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }
}
