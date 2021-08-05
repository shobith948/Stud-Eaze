package com.example.studeaze;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class attendance_recycler extends AppCompatActivity {
    RecyclerView a_recyclerView;
    DatabaseReference rootref;
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_recycler);
        TextView textView = (TextView) findViewById(R.id.T_text_name_attendence);
        Paper.init(this);
        usn = Paper.book().read("usn");

        String s= "Attendence View";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,10, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.home_btn), 0, 10, 0);// set color
        TextView tv= (TextView) findViewById(R.id.T_text_name_attendence);
        tv.setText(ss1);

        a_recyclerView = findViewById(R.id.attendance_recycler);
        a_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("Marks").child(usn);
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<marks> options =
                new FirebaseRecyclerOptions.Builder<marks>()
                        .setQuery(rootref, marks.class)
                        .build();

        FirebaseRecyclerAdapter<marks, get_attendance> adapter =
                new FirebaseRecyclerAdapter<marks, get_attendance>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull get_attendance holder, int position, @NonNull marks model) {
                        holder.usn.setText("" + model.getSubCode());
                        holder.attendance.setText("" + model.getAttendance()+"%");
                    }

                    @NonNull
                    @Override
                    public get_attendance onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item, parent, false);
                        return new get_attendance(view);
                    }
                };

        a_recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}