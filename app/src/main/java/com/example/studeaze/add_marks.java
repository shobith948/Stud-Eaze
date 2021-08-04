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
    private EditText Adusn, Adc1, Adc2, Adc3, Adattendance;
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
        Adattendance = findViewById(R.id.attendance);
        AddMarksButton = findViewById(R.id.update);

        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                teacherNameDisplay(TeacherSubKey, TeacherPasswordKey);
            }
        }


        AddMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usn = Adusn.getText().toString();
                String c1 = Adc1.getText().toString();
                String c2 = Adc2.getText().toString();
                String c3 = Adc3.getText().toString();
                String avg = String.valueOf(Math.round((Float.parseFloat(c1) + Float.parseFloat(c2) + Float.parseFloat(c3))/3));
                String attendance = Adattendance.getText().toString();

                if(Float.parseFloat(c1) <= 50 && Float.parseFloat(c2) <= 50 && Float.parseFloat(c3) <= 50 && Float.parseFloat(avg) <= 50) {
                    UpdateMarks(attendance, usn, c1, c2, c3, avg);
                }
                else {
                    Toast.makeText(add_marks.this, "Entered marks value cannot be greater than 50", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void UpdateMarks(final String attendance, final String usn, final String c1, final String c2, final String c3, final String avg) {
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
                marksdataMap.put("attendance", attendance);
                marksdataMap.put("SubCode", SubCode);

                RootRef.child(usn).child(SubCode).updateChildren(marksdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(add_marks.this, "Details of USN " + usn + "added successfully", Toast.LENGTH_SHORT).show();

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

    private void teacherNameDisplay(final String subcode, final String t_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(subcode).exists()) {
                    students teachersData = dataSnapshot.child("teachers").child(subcode).getValue(students.class);

                    if (teachersData.getsubcode().equals(subcode)) {
                        if (teachersData.getPassword().equals(t_pass)) {
                            String t_subcode = teachersData.getsubcode();
                            sub_display.setText(t_subcode);
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