package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
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

public class AddNoticeActivity extends AppCompatActivity { //class for the admin to add notice
    //user interface elements
    private TextView notice_head,notice_desc;
    Button button;

    private static int LOADING_DIALOG = 5000; //variable to store 5000 value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //onCreate is called when activity is started, to perform initialisation
        setContentView(R.layout.activity_add_notice);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        notice_head = findViewById(R.id.notice_head);
        notice_desc = findViewById(R.id.notice_desc);
        button = findViewById(R.id.add_notice_btn);
        final LoadingDialogAcitivity loading_dialog = new LoadingDialogAcitivity(AddNoticeActivity.this);

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadNotice();
            }
        });

    }

    //Function to upload notice into firebase
    private void uploadNotice() {
        //To get current date and time in required format
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final String noticeId = saveCurrentDate+""+saveCurrentTime; //setting notice id as current date combined with current time

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node.

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                HashMap<String, Object> noticedataMap = new HashMap<>(); //Hashmap to store the data into notice child node in firebase
                noticedataMap.put("date", saveCurrentDate);
                noticedataMap.put("time", saveCurrentTime);
                noticedataMap.put("note_head", notice_head.getText().toString());
                noticedataMap.put("note_desc", notice_desc.getText().toString());

                //Creating/Updating child node
                RootRef.child("notice").child(noticeId).updateChildren(noticedataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String notice_h = notice_head.getText().toString();
                        String notice_d= notice_desc.getText().toString();

                        if(TextUtils.isEmpty(notice_h))
                        {
                            Toast.makeText(AddNoticeActivity.this, "Notice subject cannot be empty....", Toast.LENGTH_SHORT).show();
                        }
                        else if(TextUtils.isEmpty(notice_d))
                        {
                            Toast.makeText(AddNoticeActivity.this, "Notice description cannot be empty....", Toast.LENGTH_SHORT).show();
                        }
                        else if (task.isSuccessful()) {  //checking if updating task is successful or not
                            Toast.makeText(AddNoticeActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                            LoadingDialogAcitivity.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //A toast is a view containing a quick little message for the user.
                                    Toast.makeText(AddNoticeActivity.this, "Notice added successfully", Toast.LENGTH_SHORT).show();
                                    LoadingDialogAcitivity.dismissDialog();
                                    Intent intent = new Intent(AddNoticeActivity.this, AdminActivity.class);
                                    startActivity(intent);
                                }
                            },LOADING_DIALOG);
                        }
                        else {
                            Toast.makeText(AddNoticeActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                            Toast.makeText(AddNoticeActivity.this, "Try again", Toast.LENGTH_SHORT).show();
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