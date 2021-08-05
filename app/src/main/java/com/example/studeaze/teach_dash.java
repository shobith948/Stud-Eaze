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

public class teach_dash extends AppCompatActivity implements View.OnClickListener{ //class for teacher dashboard
    //user interface elements
    private TextView name, disSubcode;
    private CardView take_attendance, marks, notice, timetable;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_teach_dash);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        name = findViewById(R.id.T_text_name);
        disSubcode = findViewById(R.id.disSube);
        take_attendance = (CardView) findViewById(R.id.take_attendance);
        marks = (CardView) findViewById(R.id.T_marks);
        notice = (CardView) findViewById(R.id.T_notice);
        timetable = (CardView) findViewById(R.id.T_view_class);
        logout = (Button) findViewById(R.id.T_log_out);

        take_attendance.setOnClickListener(this);
        marks.setOnClickListener(this);
        notice.setOnClickListener(this);
        timetable.setOnClickListener(this);

        Paper.init(this); //used to initialise session of user

        //retrieving teacher session
        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        //checking conditions to display teacher name in dashboard
        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                    teacherNameDisplay(TeacherSubKey, TeacherPasswordKey);
            }
        }

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy(); //destroying session
                Intent intent = new Intent(teach_dash.this, home.class);
                startActivity(intent);
            }
        });
    }

    //Function to display teacher name in dashboard
    private void teacherNameDisplay(final String s_code, final String t_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(s_code).exists()) {
                    students teachersData = dataSnapshot.child("teachers").child(s_code).getValue(students.class); //Gets a DatabaseReference for the database specified child node

                    if (teachersData.getsubcode().equals(s_code)) {
                        if (teachersData.getPassword().equals(t_pass)) {
                            String t_name = teachersData.getName();
                            String t_subcode = teachersData.getsubcode();
                            name.setText(t_name);
                            disSubcode.setText(t_subcode); //setting subject code into textView
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //onClick for cardView in dashboard
    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()) {
            case R.id.take_attendance:

            case R.id.T_marks:
                i = new Intent(this, add_marks.class); //Redirect to the specifies class
                startActivity(i);
                break;

            case R.id.T_notice:
                i = new Intent(this, ts_notice.class);
                startActivity(i);
                break;

            case R.id.T_view_class:
                i = new Intent(this, TimeTableDisplay.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(teach_dash.this,home.class);
        startActivity(intent);
    }
}