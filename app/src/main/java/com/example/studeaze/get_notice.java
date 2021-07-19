package com.example.studeaze;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class get_notice extends RecyclerView.ViewHolder {
    public TextView date, time,headng, descrpt;


    public get_notice(View itemView)
    {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.date);
        time = (TextView) itemView.findViewById(R.id.time);
        headng = (TextView) itemView.findViewById(R.id.head);
        descrpt = itemView.findViewById(R.id.desc);
    }
}
