package com.example.syncscrolltabsexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by ll298lee on 7/29/15.
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mText;
        public ViewHolder(View itemView) {
            super(itemView);
            mText = (TextView)itemView.findViewById(R.id.text);
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case 0:
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.view_header_placeholder, viewGroup, false));


            default:
                return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_layout, viewGroup, false));

        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position != 0) {
            ViewHolder vh = (ViewHolder) holder;
            vh.mText.setText("item: " + position);
        }

    }

    @Override
    public int getItemCount() {
        return 200;
    }

    @Override
    public int getItemViewType(int position){
        if(position==0){
            return 0;
        }else{
            return 1;
        }

    }
}
