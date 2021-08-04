package com.example.studeaze;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        Paper.init(this);
        usn = Paper.book().read("usn");

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
                        holder.attendance.setText("" + model.getAttendance());
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