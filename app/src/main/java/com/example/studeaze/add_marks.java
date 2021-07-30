package com.example.studeaze;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;

import io.paperdb.Paper;

public class add_marks extends AppCompatActivity {
    private Button AddMarksButton;
    private TextView sub_display;
    private EditText Adusn, Adc1, Adc2, Adc3, Adavg, Adattendence;
    String SubCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_marks);

        Paper.init(this);

        sub_display = findViewById(R.id.subcode_marks);
        Adusn = findViewById(R.id.usn_head);
        Adc1 = findViewById(R.id.test1);
        Adc2 = findViewById(R.id.test2);
        Adc3 = findViewById(R.id.test3);
        Adavg = findViewById(R.id.avg);
        Adattendence = findViewById(R.id.attendence);
        AddMarksButton = findViewById(R.id.update);

        String UserSubcodeKey = Paper.book().read("subcode");
        String UserPasswordKey = Paper.book().read("password");

        if(UserSubcodeKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserSubcodeKey) && !TextUtils.isEmpty(UserPasswordKey)){
                teacherNameDisplay(UserSubcodeKey, UserPasswordKey);
            }
        }


        AddMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usn = Adusn.getText().toString();
                String c1 = Adc1.getText().toString();
                String c2 = Adc2.getText().toString();
                String c3 = Adc3.getText().toString();
                String avg = Adavg.getText().toString();
                String attendence = Adattendence.getText().toString();

                UpdateMarks(attendence, usn, c1, c2, c3, avg);

            }
        });
    }

    private void UpdateMarks(final String attendence, final String usn, final String c1, final String c2, final String c3, final String avg) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Marks");
        SubCode = Paper.book().read("subcode");

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> marksdataMap = new HashMap<>();
                marksdataMap.put("usn", usn);
                marksdataMap.put("c1", c1);
                marksdataMap.put("c2", c2);
                marksdataMap.put("c3", c3);
                marksdataMap.put("avg", avg);
                marksdataMap.put("attendence", attendence);
                marksdataMap.put("SubCode", SubCode);

                RootRef.child(usn).child(SubCode).updateChildren(marksdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(add_marks.this, "Details of usn " + usn + "added successfull", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(add_marks.this, teach_dash.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(add_marks.this, "Network Error", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(add_marks.this, "Try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void teacherNameDisplay(String subcode, String s_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("students").child(subcode).exists()) {
                    students studentsData = dataSnapshot.child("students").child(subcode).getValue(students.class);

                    if (studentsData.getsubcode().equals(subcode)) {
                        if (studentsData.getPassword().equals(s_pass)) {
                            String s_name = studentsData.getsubcode();
                            sub_display.setText(s_name);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}