package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import io.paperdb.Paper;

public class login extends AppCompatActivity { //class for student login
    //user interface elements
    private TextView reg;
    private EditText S_usn , S_password;
    private Button login_btn;
    private static int LOADING_DIALOG = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation

        Paper.init(this); //used to initialise student session

        setContentView(R.layout.activity_stud_login);
        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        login_btn = findViewById(R.id.s_login);
        S_usn = findViewById(R.id.usn);
        S_password = findViewById(R.id.pass);
        reg = findViewById(R.id.reg);
        final loading_dialog loading_dialog = new loading_dialog(login.this);

        //Design Student Login text
        String s= "Student login";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.home_btn), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.textView7);
        tv.setText(ss1);

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        login_btn.setOnClickListener(v -> {
            if(S_usn.getText().toString().equals("admin") && S_password.getText().toString().equals("admin123")){
                Paper.book().write("usn", S_usn.getText().toString());
                Paper.book().write("password", S_password.getText().toString());
                loading_dialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(login.this, "You are now an Admin", Toast.LENGTH_SHORT).show();
                        loading_dialog.dismissDialog();
                        Intent intent = new Intent(login.this, admin.class);
                        startActivity(intent);
                        finish();
                    }
                },LOADING_DIALOG);
            }
            else {
                LoginStudent();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, reg_student.class);
                startActivity(intent);
            }
        });
    }

    //Function to validate and login student
    private void LoginStudent()
    {
        String usn = S_usn.getText().toString();
        String password= S_password.getText().toString();

        if(TextUtils.isEmpty(usn))
        {
            Toast.makeText(login.this, "Please Enter your usn number....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please Enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(usn) < 9)
        {
            Toast.makeText(login.this, "Invalid USN", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AllowAccess(usn,password);
        }
    }

    //Function to allow access to student
    private void AllowAccess(final String usn, final String password)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node.

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("students").child(usn).exists())
                {
                    students userData = dataSnapshot.child("students").child(usn).getValue(students.class); //Gets a DatabaseReference for the database specified child node.

                    if (userData.getUsn().equals(usn))
                    {
                        if (userData.getPassword().equals(password)) {
                            Paper.book().write("usn", usn);
                            Paper.book().write("password", password);
                            loading_dialog.startLoadingDialog();
                            Handler handler = new Handler(); //Handler for loading dialog
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //A toast is a view containing a quick little message for the user.
                                    Toast.makeText(login.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                                    loading_dialog.dismissDialog();
                                    Intent intent = new Intent(login.this, stud_dash.class);
                                    startActivity(intent);
                                }
                            },LOADING_DIALOG);

                        } else {
                            Toast.makeText(login.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(login.this, "Error in get method", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(login.this, "Account with " + usn + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(login.this, "Server Error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(login.this,home.class);
        startActivity(intent);
    }
}
