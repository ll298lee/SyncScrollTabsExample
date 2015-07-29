package com.example.syncscrolltabsexample;

import android.content.res.Resources;
import android.graphics.Point;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, ScrollTabHolder, ExampleFragment.OnFragmentInteractionListener{
    private ViewPager mTabViewPager;
    private SlidingTabLayout mTabsIndicator;
    private TabsAdapter mTabsAdapter;
    private ImageView mInfoBackground;

    private RelativeLayout mHeader;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHeader = (RelativeLayout) findViewById(R.id.scroll_header);
        mInfoBackground =(ImageView)findViewById(R.id.info_background);

        mTabViewPager = (ViewPager) findViewById(R.id.tab_pager);
        mTabsIndicator = (SlidingTabLayout) findViewById(R.id.tabs);

        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.tabs_header_height);

        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 52, r.getDisplayMetrics());
        mMinHeaderTranslation = -getResources().getDimensionPixelSize(R.dimen.tabs_header_height) + (int)px;


        mTabsAdapter = new TabsAdapter(getSupportFragmentManager());

        mTabViewPager.setAdapter(mTabsAdapter);
        mTabViewPager.setAdapter(mTabsAdapter);
        mTabsIndicator.setCustomTabView(R.layout.sliding_tab_layout_equal_space, R.id.tab_text);
        mTabsIndicator.setViewPager(mTabViewPager);
        //mTabsIndicator.setDividerColors(getResources().getColor(R.color.color19));
        //mTabsIndicator.setSelectedIndicatorColors(getResources().getColor(R.color.color12));
        mTabsAdapter.setTabHolderScrollingContent(this);
        mTabsIndicator.setOnPageChangeListener(this);



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;


        float px2 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 177, r.getDisplayMetrics());
        Picasso.with(this)
                .load("http://pic.pimg.tw/iamayuan/1366429240-3498232206.jpg")
                .placeholder(R.color.color12)
                .resize(width, (int)px2)
                .centerCrop()
                .into(mInfoBackground);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int currentItem = mTabViewPager.getCurrentItem();
        if (positionOffsetPixels > 0) {
            SparseArrayCompat< ScrollTabHolder > tabHolderScrollingContent = mTabsAdapter.getScrollTabHolders();

            ScrollTabHolder fragmentContent;
            if (position < currentItem) {
                // Revealed the previous page
                fragmentContent = tabHolderScrollingContent.valueAt(position);
            } else {
                // Revealed the next page
                fragmentContent = tabHolderScrollingContent.valueAt(position + 1);
            }
            int translationY = getTranslationY();
            if(fragmentContent!=null) {
                fragmentContent.adjustScroll(mHeader.getHeight() + translationY, translationY <= mMinHeaderTranslation);
            }


        }
    }

    @Override
    public void onPageSelected(int position) {
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mTabsAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        int translationY = getTranslationY();
        if(currentHolder!=null) {
            currentHolder.adjustScroll(mHeader.getHeight() + translationY, translationY <= mMinHeaderTranslation);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void adjustScroll(int scrollHeight, boolean isSticky) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {
        if (mTabViewPager.getCurrentItem() == pagePosition) {
            int scrollY;
            if(view!=null){
                scrollY = getScrollY(view);
            }else{
                scrollY = firstVisibleItem;
            }

            if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB) {
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                mInfoBackground.setTranslationY(-Math.max(-scrollY, mMinHeaderTranslation)/2);
            }else{
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mHeader.getLayoutParams();
                params.topMargin = Math.max(-scrollY, mMinHeaderTranslation);
                mHeader.setLayoutParams(params);
            }
        }

    }

    private int getTranslationY(){
        if (android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.HONEYCOMB){
            return (int)mHeader.getTranslationY();
        }else{
            return ((RelativeLayout.LayoutParams)mHeader.getLayoutParams()).topMargin;
        }
    }

    public int getScrollY(AbsListView view) {
        View c = view.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = view.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mHeaderHeight;
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }
}
