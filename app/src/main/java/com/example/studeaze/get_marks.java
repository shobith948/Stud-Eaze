package com.example.studeaze;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class get_marks extends RecyclerView.ViewHolder {
    public TextView usn,c1,c2, c3,avg;


    public get_marks(View itemView)
    {
        super(itemView);

        usn = (TextView) itemView.findViewById(R.id.usn_head);
        c1 = (TextView) itemView.findViewById(R.id.test1);
        c2 = (TextView) itemView.findViewById(R.id.test2);
        c3 = (TextView) itemView.findViewById(R.id.test3);
        avg = (TextView) itemView.findViewById(R.id.avg);
    }
}