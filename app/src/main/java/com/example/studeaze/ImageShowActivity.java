package com.example.studeaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImageShowActivity extends AppCompatActivity {//Image show activity class

    //user interface elements
    private RecyclerView recyclerView;
    private ArrayList<ImageModel> list;
    private ImageAdapter adapter;

    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");  //Gets a DatabaseReference for the database specified child node.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_image_show);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new ImageAdapter(this , list);
        recyclerView.setAdapter(adapter);
        //Adding model to list
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ImageModel model = dataSnapshot.getValue(ImageModel.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
