package com.example.studeaze;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class NoticeViewHolder extends RecyclerView.ViewHolder { //class for notice view holder
    //user interface elements
    public TextView date, time,headng, descrpt;

    //constructor
    public NoticeViewHolder(View itemView)
    {
        super(itemView);

        //Finds a view that was identified by the android:id XML attribute.
        date = (TextView) itemView.findViewById(R.id.date);
        time = (TextView) itemView.findViewById(R.id.time);
        headng = (TextView) itemView.findViewById(R.id.head);
        descrpt = itemView.findViewById(R.id.desc);
    }
}