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

public class MarksAdapter extends AppCompatActivity { //class for marks recycler
    RecyclerView recyclerView;
    DatabaseReference rootref;
    String usn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_marks_adapter);

        Paper.init(this);  //used to initialise session of user
        usn = Paper.book().read("usn");

        recyclerView = findViewById(R.id.marks_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("Marks").child(usn);  //Gets a DatabaseReference for the database specified child node.
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<MarksModel> options =
                new FirebaseRecyclerOptions.Builder<MarksModel>()
                        .setQuery(rootref, MarksModel.class)
                        .build();
        //Firebase recycler adapter
        FirebaseRecyclerAdapter<MarksModel, MarksViewHolder> adapter =
                new FirebaseRecyclerAdapter<MarksModel, MarksViewHolder>(options) {
                    @Override
                    //Bind view
                    protected void onBindViewHolder(@NonNull MarksViewHolder holder, int position, @NonNull MarksModel model) {
                        holder.usn.setText("" + model.getSubCode());
                        holder.c1.setText("" + model.getc1() +"/50");
                        holder.c2.setText("" + model.getC2() +"/50");
                        holder.c3.setText("" + model.getC3() +"/50");
                        holder.avg.setText("" + model.getAvg() +"/50");
                    }

                    @NonNull
                    @Override
                    //Create view holder
                    public MarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.marks_item, parent, false);
                        return new MarksViewHolder(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}