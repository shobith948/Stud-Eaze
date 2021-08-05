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

public class AttendanceAdapter extends AppCompatActivity {
    //user interface elements
    RecyclerView a_recyclerView;
    DatabaseReference rootref;
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_attendance_adapter);

        Paper.init(this);  //used to initialise session of user
        usn = Paper.book().read("usn");

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        a_recyclerView = findViewById(R.id.attendance_recycler);
        a_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("Marks").child(usn); //Gets a DatabaseReference for the database specified child node.
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MarksModel> options =
                new FirebaseRecyclerOptions.Builder<MarksModel>()
                        .setQuery(rootref, MarksModel.class)
                        .build();
        //Firebase recycler adapter used to set the text in holder
        FirebaseRecyclerAdapter<MarksModel, AttendanceViewHolder> adapter =
                new FirebaseRecyclerAdapter<MarksModel, AttendanceViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position, @NonNull MarksModel model) {
                        holder.usn.setText("" + model.getSubCode());
                        holder.attendance.setText("" + model.getAttendance());
                    }

                    @NonNull
                    @Override
                    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_item, parent, false);
                        return new AttendanceViewHolder(view);
                    }
                };

        a_recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}