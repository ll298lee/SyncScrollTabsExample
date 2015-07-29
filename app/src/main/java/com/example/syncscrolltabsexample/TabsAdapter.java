package com.example.syncscrolltabsexample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.view.ViewGroup;

/**
 * Created by ll298lee on 7/29/15.
 */
public class TabsAdapter extends FragmentPagerAdapter {
    private ScrollTabHolder mListener;
    private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;


    public TabsAdapter(FragmentManager fm){
        super(fm);
        mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();

    }


    @Override
    public Fragment getItem(int position) {
        return ExampleFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public String getPageTitle(int index) {
        return "TAB "+index;

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ExampleFragment fragment = (ExampleFragment) super.instantiateItem(container, position);

        mScrollTabHolders.put(position, fragment);
        if (mListener != null) {
            fragment.setScrollTabHolder(mListener);
        }

        return fragment;
    }

    public void setTabHolderScrollingContent(ScrollTabHolder listener) {
        mListener = listener;
    }



    public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
        return mScrollTabHolders;
    }
}
