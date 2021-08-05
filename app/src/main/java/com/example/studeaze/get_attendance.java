package com.example.studeaze;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class get_attendance extends RecyclerView.ViewHolder { //class for attendance view holder
    //user interface elements
    public TextView usn, attendance;

    //constructor
    public get_attendance(View itemView)
    {
        super(itemView);

        //Finds a view that was identified by the android:id XML attribute.
        usn = (TextView) itemView.findViewById(R.id.a_usn_head);
        attendance = (TextView) itemView.findViewById(R.id.attendance);
    }
}