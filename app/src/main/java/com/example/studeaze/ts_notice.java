package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ts_notice extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference rootref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ts_notice);

        recyclerView = findViewById(R.id.notice_display);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        rootref = FirebaseDatabase.getInstance().getReference().child("notice");
    }
    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<notice> options =
                new FirebaseRecyclerOptions.Builder<notice>()
                        .setQuery(rootref, notice.class)
                        .build();

        FirebaseRecyclerAdapter<notice, get_notice> adapter =
                new FirebaseRecyclerAdapter<notice, get_notice>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull get_notice holder, final int position, @NonNull final notice model) {
                        holder.date.setText("Date: " + model.getDate());
                        holder.time.setText("Time: " + model.getTime());
                        holder.headng.setText("" + model.getNote_head());
                        holder.descrpt.setText("" + model.getNote_desc());

                    }

                    @NonNull
                    @Override
                    public get_notice onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item, parent, false);
                        return new get_notice(view);
                    }
                };

        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}