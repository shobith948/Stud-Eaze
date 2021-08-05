package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class stud_dash extends AppCompatActivity implements View.OnClickListener{ //class for student dashboard
    //user interface elements
    private TextView student_name;
    private CardView view_attendance, marks, notice, timetable;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_stud_dash);
        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        student_name = findViewById(R.id.S_text_name);
        view_attendance = (CardView) findViewById(R.id.view_attendance);
        marks = (CardView) findViewById(R.id.S_marks);
        notice = (CardView) findViewById(R.id.S_notice);
        timetable = (CardView) findViewById(R.id.S_view_class);
        logout =  findViewById(R.id.S_log_out);

        view_attendance.setOnClickListener(this);
        marks.setOnClickListener(this);
        notice.setOnClickListener(this);
        timetable.setOnClickListener(this);

        Paper.init(this);  //used to initialise session of user

        //retrieving student session
        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");

        //checking conditions to display student name in dashboard
        if(UserUsnKey != "" && UserPasswordKey != ""){
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey)){
                studentNameDisplay(UserUsnKey, UserPasswordKey);
            }
        }

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(stud_dash.this, home.class);
                startActivity(intent);
            }
        });
    }

    //Function to display student name
    private void studentNameDisplay(String usn, String s_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("students").child(usn).exists()){
                    students studentsData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if(studentsData.getUsn().equals(usn)){
                        if(studentsData.getPassword().equals(s_pass)){
                            String s_name = studentsData.getName();
                            student_name.setText(s_name); //setting text for the textView
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //onClick event for card view in student dashboard
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.view_attendance:
                i = new Intent(this, attendance_recycler.class);
                startActivity(i);
                break;

            case R.id.S_marks:
                i = new Intent(this, marks_recycler.class);
                startActivity(i);
                break;

            case R.id.S_notice:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.S_view_class:
                i = new Intent(this, TimeTableDisplay.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(stud_dash.this,home.class);
        startActivity(intent);
    }
}