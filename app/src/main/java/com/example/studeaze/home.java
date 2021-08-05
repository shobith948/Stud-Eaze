package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class home extends AppCompatActivity {  //class for the home screen
    //user interface elements
    private CardView student;
    private  CardView teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_home);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        student =(CardView) findViewById(R.id.std_log);
        teacher =(CardView) findViewById(R.id.teacher);
        Paper.init(this);  //used to initialise session of user

        //retrieving student/teacher session
        String UserUsnKey = Paper.book().read("usn");
        String UserPasswordKey = Paper.book().read("password");
        String TeacherSubKey = Paper.book().read("subcode");
        String TeacherPasswordKey = Paper.book().read("t_password");

        //Checking conditions to allow access for the student or admin
        if (UserUsnKey != "" && UserPasswordKey != "")
        {
            if(!TextUtils.isEmpty(UserUsnKey) && !TextUtils.isEmpty(UserPasswordKey))
            {
                if(UserUsnKey.equals("admin") && UserPasswordKey.equals("admin123")) {
                    AdminAllowAccess();
                }
                else {
                    AllowAccess(UserUsnKey, UserPasswordKey);
                }

            }
        }
        //Checking conditions to allow access for the teacher or admin
        if (TeacherSubKey != "" && TeacherPasswordKey != "")
        {
            if(!TextUtils.isEmpty(TeacherSubKey) && !TextUtils.isEmpty(TeacherPasswordKey))
            {
                if(TeacherSubKey.equals("admin") && TeacherPasswordKey.equals("admin123")){
                    AdminAllowAccess();
                }
                else {
                    TeacherAllowAccess(TeacherSubKey, TeacherPasswordKey);
                }

            }
        }

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teach_login();
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    //Function that redirects to teacher login
    public  void teach_login(){
        Intent intent = new Intent(this,teach_login.class);
        startActivity(intent);
    }
    //Function that redirects to student login
    public  void login(){
        Intent intent = new Intent(this,login.class);
        startActivity(intent);
    }

    //Function to allow access to student
    private void AllowAccess(final String usn, final String password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("students").child(usn).exists()) {
                    students studentsData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if (studentsData.getUsn().equals(usn)) {
                        if (studentsData.getPassword().equals(password)) {

                            Intent intent = new Intent(home.this, stud_dash.class);
                            startActivity(intent);
                        }
                    } else {
                        //A toast is a view containing a quick little message for the user.
                        Toast.makeText(home.this, "Retry Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(home.this, "Account with " + usn + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    //Function to allow access to admin
    private void AdminAllowAccess() {
        Intent intent = new Intent(home.this, admin.class);
        startActivity(intent);
    }

    //Function to allow access to teacher
    private void TeacherAllowAccess(final String s_code, final String T_pass) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("teachers").child(s_code).exists()) {
                    students teachersData = dataSnapshot.child("teachers").child(s_code).getValue(students.class);

                    if (teachersData.getsubcode().equals(s_code)) {
                        if (teachersData.getPassword().equals(T_pass)) {

                            Intent intent = new Intent(home.this, teach_dash.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(home.this, "Retry Login", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(home.this, "Account with " + s_code + " does not exist", Toast.LENGTH_SHORT).show();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
