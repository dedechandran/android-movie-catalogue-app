package com.example.myfavoritemovie;

import android.view.View;

public class CustomOnClickListener implements View.OnClickListener {
    private int position;
    private OnItemClickListener onItemClickListener;

    public CustomOnClickListener(int position, OnItemClickListener onItemClickListener) {
        this.position = position;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        onItemClickListener.onItemClicked(v,position);
    }

    public interface OnItemClickListener{
        void onItemClicked(View v,int position);
    }
}
