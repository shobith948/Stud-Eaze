package com.example.studeaze;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

public class TimeTableDisplay extends AppCompatActivity { //class for time table display
    //user interface elements
    ImageView timetableimage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_time_table_display);

        Paper.init(this);  //used to initialise session of user

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        timetableimage = findViewById(R.id.timetableimage);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String usn = Paper.book().read("usn"); //retrieving student session
        //Gets a DatabaseReference for the database specified child node.
        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference().child("Time_Table");
        DatabaseReference rootref2 = FirebaseDatabase.getInstance().getReference().child("students").child(usn);

        rootref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long sem = (long) snapshot.child("semester").getValue();
                rootref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        table tab =  snapshot.child(String.valueOf(sem)).getValue(table.class);
                        Picasso.get().load(tab.getImageUrl()).into(timetableimage); //loading image into view using Picasso library

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}