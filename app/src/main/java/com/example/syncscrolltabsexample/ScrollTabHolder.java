package com.example.syncscrolltabsexample;

import android.widget.AbsListView;

/**
 * Created by ll298lee on 7/29/15.
 */
public interface ScrollTabHolder {
    void adjustScroll(int scrollHeight, boolean isSticky);

    void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition);
}
