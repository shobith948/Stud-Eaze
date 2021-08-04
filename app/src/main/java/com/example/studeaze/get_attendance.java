package com.example.studeaze;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class get_attendance extends RecyclerView.ViewHolder {
    public TextView usn, attendance;

    public get_attendance(View itemView)
    {
        super(itemView);

        usn = (TextView) itemView.findViewById(R.id.a_usn_head);
        attendance = (TextView) itemView.findViewById(R.id.attendance);
    }
}