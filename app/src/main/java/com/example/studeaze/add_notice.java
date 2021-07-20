package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SimpleTimeZone;

public class add_notice extends AppCompatActivity {
    TextView notice_head,notice_desc;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);

        notice_head = findViewById(R.id.notice_head);
        notice_desc = findViewById(R.id.notice_desc);
        button = findViewById(R.id.add_notice_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadNotice();
            }
        });

    }

    private void uploadNotice() {
        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final String noticeId = saveCurrentDate+""+saveCurrentTime;

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                HashMap<String, Object> studentsdataMap = new HashMap<>();
                studentsdataMap.put("date", saveCurrentDate);
                studentsdataMap.put("time", saveCurrentTime);
                studentsdataMap.put("note_head", notice_head.getText().toString());
                studentsdataMap.put("note_desc", notice_desc.getText().toString());


                RootRef.child("notice").child(noticeId).updateChildren(studentsdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(add_notice.this,"updated sucessfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(add_notice.this,admin.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(add_notice.this, "Network Error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(add_notice.this, "Try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}