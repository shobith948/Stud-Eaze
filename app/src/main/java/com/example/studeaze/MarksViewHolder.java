package com.example.studeaze;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class MarksViewHolder extends RecyclerView.ViewHolder { //class for marks view holder
    //user interface elements
    public TextView usn,c1,c2, c3,avg;

    //constructor
    public MarksViewHolder(View itemView)
    {
        super(itemView);

        //Finds a view that was identified by the android:id XML attribute.
        usn = (TextView) itemView.findViewById(R.id.usn_head);
        c1 = (TextView) itemView.findViewById(R.id.test1);
        c2 = (TextView) itemView.findViewById(R.id.test2);
        c3 = (TextView) itemView.findViewById(R.id.test3);
        avg = (TextView) itemView.findViewById(R.id.avg);
    }
}