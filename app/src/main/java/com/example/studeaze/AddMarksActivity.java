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
import java.util.Objects;

import io.paperdb.Paper;

public class AddMarksActivity extends AppCompatActivity { //class for the teacher to add marks and attendance
    //user interface elements
    private Button AddMarksButton;
    private TextView sub_display;
    private EditText Adusn, Adc1, Adc2, Adc3, Adattendance;
    String SubCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_add_marks);

        Paper.init(this); //used to initialise session of user

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        sub_display = findViewById(R.id.subcode_marks);
        Adusn = findViewById(R.id.usn_head);
        Adc1 = findViewById(R.id.test1);
        Adc2 = findViewById(R.id.test2);
        Adc3 = findViewById(R.id.test3);
        Adattendance = findViewById(R.id.attendance);
        AddMarksButton = findViewById(R.id.update);

        //retrieving teacher session
        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        //checking conditions to display teacher subject code in header
        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                teacherNameDisplay(TeacherSubKey, TeacherPasswordKey);
            }
        }

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        AddMarksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usn = Adusn.getText().toString();
                String c1 = Adc1.getText().toString();
                String c2 = Adc2.getText().toString();
                String c3 = Adc3.getText().toString();
                String avg = String.valueOf(Math.round((Float.parseFloat(c1) + Float.parseFloat(c2) + Float.parseFloat(c3))/3)); //calculate average
                String attendance = Adattendance.getText().toString();

                //Check if marks entered are less than or equal to 50
                if(Float.parseFloat(c1) <= 50 && Float.parseFloat(c2) <= 50 && Float.parseFloat(c3) <= 50 && Float.parseFloat(avg) <= 50) {
                    UpdateMarks(attendance, usn, c1, c2, c3, avg);
                }
                else {
                    //A toast is a view containing a quick little message for the user.
                    Toast.makeText(AddMarksActivity.this, "Entered marks value cannot be greater than 50", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    //Function to store marks and attendance in firebase
    private void UpdateMarks(final String attendance, final String usn, final String c1, final String c2, final String c3, final String avg) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference().child("Marks"); //Gets a DatabaseReference for the database specified child node.
        SubCode = Paper.book().read("subcode");

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> marksdataMap = new HashMap<>();  //Hashmap to store the data into marks child node in firebase
                marksdataMap.put("usn", usn);
                marksdataMap.put("c1", c1);
                marksdataMap.put("c2", c2);
                marksdataMap.put("c3", c3);
                marksdataMap.put("avg", avg);
                marksdataMap.put("attendance", attendance);
                marksdataMap.put("SubCode", SubCode);

                //To get values from studentsModel class
                UsersModel studentData = dataSnapshot.child("students").child(usn).getValue(UsersModel.class);
                UsersModel teacherData = dataSnapshot.child("teachers").child(SubCode).getValue(UsersModel.class);

                    RootRef.child(usn).child(SubCode).updateChildren(marksdataMap) //Creating/Updating child node
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    //checking if updating task is successful or not
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AddMarksActivity.this, "Details of USN " + usn + " added successfully", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AddMarksActivity.this, TeacherDashboardActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AddMarksActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                        Toast.makeText(AddMarksActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Function to display teacher subject code
    private void teacherNameDisplay(final String subcode, final String t_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(subcode).exists()) {
                    UsersModel teachersData = dataSnapshot.child("teachers").child(subcode).getValue(UsersModel.class);

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