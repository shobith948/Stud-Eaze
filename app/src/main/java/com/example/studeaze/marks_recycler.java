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

public class marks_recycler extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference rootref;
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_recycler);

        Paper.init(this);
        usn = Paper.book().read("usn");

        recyclerView = findViewById(R.id.marks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("Marks").child(usn);
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<marks> options =
                new FirebaseRecyclerOptions.Builder<marks>()
                        .setQuery(rootref, marks.class)
                        .build();

        FirebaseRecyclerAdapter<marks, get_marks> adapter =
                new FirebaseRecyclerAdapter<marks, get_marks>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull get_marks holder, int position, @NonNull marks model) {
                        holder.usn.setText("" + model.getSubCode());
                        holder.c1.setText("" + model.getc1() +"/50");
                        holder.c2.setText("" + model.getC2() +"/50");
                        holder.c3.setText("" + model.getC3() +"/50");
                        holder.avg.setText("" + model.getAvg() +"/50");
                    }

                    @NonNull
                    @Override
                    public get_marks onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_item, parent, false);
                        return new get_marks(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}