package com.example.studeaze;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class add_notice extends AppCompatActivity {
    private TextView notice_head,notice_desc;
    Button button;
    private static int LOADING_DIALOG = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);
        notice_head = findViewById(R.id.notice_head);
        notice_desc = findViewById(R.id.notice_desc);
        button = findViewById(R.id.add_notice_btn);
        final loading_dialog loading_dialog = new loading_dialog(add_notice.this);


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
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
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
                HashMap<String, Object> noticedataMap = new HashMap<>();
                noticedataMap.put("date", saveCurrentDate);
                noticedataMap.put("time", saveCurrentTime);
                noticedataMap.put("note_head", notice_head.getText().toString());
                noticedataMap.put("note_desc", notice_desc.getText().toString());


                RootRef.child("notice").child(noticeId).updateChildren(noticedataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String notice_h = notice_head.getText().toString();
                        String notice_d= notice_desc.getText().toString();

                            if(TextUtils.isEmpty(notice_h))
                            {
                                Toast.makeText(add_notice.this, "Notice subject cannot be empty....", Toast.LENGTH_SHORT).show();
                            }
                            else
                            if(TextUtils.isEmpty(notice_d))
                            {
                                Toast.makeText(add_notice.this, "Notice description cannot be empty....", Toast.LENGTH_SHORT).show();
                            }
                            else
                        if (task.isSuccessful()) {
                            Toast.makeText(add_notice.this,"Updated Successfully",Toast.LENGTH_SHORT).show();
                            loading_dialog.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(add_notice.this, "Notice added sucessfully", Toast.LENGTH_SHORT).show();
                                    loading_dialog.dismissDialog();
                                    Intent intent = new Intent(add_notice.this, admin.class);
                                    startActivity(intent);
                                }
                            },LOADING_DIALOG);
                        }
                            else{
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