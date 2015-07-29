package com.example.syncscrolltabsexample;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExampleFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExampleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExampleFragment extends Fragment implements  ScrollTabHolder{

    private static final String ARG_PARAM1 = "param1";



    private int mTabPosition=-1;


    private OnFragmentInteractionListener mListener;

    protected ScrollTabHolder mScrollTabHolder;

    private LinearLayoutManager mLayoutManager;
    private ItemAdapter mAdapter;
    private RecyclerView mListView;
    private int mScrollY = 0;




    public static ExampleFragment newInstance(int param1) {
        ExampleFragment fragment = new ExampleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    public ExampleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTabPosition = getArguments().getInt(ARG_PARAM1);

        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_example, container, false);

        mListView = (RecyclerView)v.findViewById(R.id.item_list);


        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mListView.setLayoutManager(mLayoutManager);
        mAdapter = new ItemAdapter();
        mListView.setAdapter(mAdapter);


        mListView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;

                if ( mTabPosition>=0 &&mScrollTabHolder != null && mAdapter != null && mAdapter.getItemCount() > 0) {


                    mScrollTabHolder.onScroll(null, mScrollY, 0, 0, mTabPosition);
                }
            }
        });


        return v;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
        mScrollTabHolder = scrollTabHolder;
    }

    @Override
    public void adjustScroll(int scrollHeight, boolean isSticky) {
        Resources r = getResources();
        if (isSticky && mLayoutManager.findFirstVisibleItemPosition() >= 1) {

            return;
        }else if(isSticky){

            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 229-52, r.getDisplayMetrics());


            mScrollY = (int)px;
            mLayoutManager.scrollToPositionWithOffset(0, -(int)px);
            return;
        }
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 229, r.getDisplayMetrics());
        mScrollY = (int)px - scrollHeight;
        mLayoutManager.scrollToPositionWithOffset(0, -((int)px - scrollHeight));
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name

    }



}
