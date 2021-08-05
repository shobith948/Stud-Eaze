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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class TeacherLoginActivity extends AppCompatActivity { //class for teacher login
    //user interface elements
    private EditText subcode, Tpassword;
    Button Tloginbtn;
    private static int LOADING_DIALOG = 5000; //variable to store 5000 value

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_teacher_login);
        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        Tloginbtn = findViewById(R.id.tlogin_btn);
        subcode = findViewById(R.id.subcode);
        Tpassword = findViewById(R.id.tpass);
        final LoadingDialogAcitivity loading_dialog = new LoadingDialogAcitivity(TeacherLoginActivity.this);

        Paper.init(this);  //used to initialise session of user

        //Designing Teacher login string
        String s= "Teacher login";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.tlogin);
        tv.setText(ss1);

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        Tloginbtn.setOnClickListener(v -> {
            //admin login
            if(subcode.getText().toString().equals("admin") && Tpassword.getText().toString().equals("admin123")){
                Paper.book().write("subcode", subcode.getText().toString());
                Paper.book().write("t_password", Tpassword.getText().toString());
                loading_dialog.startLoadingDialog();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TeacherLoginActivity.this, "You are now an Admin", Toast.LENGTH_SHORT).show();
                        loading_dialog.dismissDialog();
                        Intent intent = new Intent(TeacherLoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }
                },LOADING_DIALOG);

            } else {
                //teacher login
                LoginTeacher();
            }
        });
    }
    //Function for teacher login
    private void LoginTeacher()
    {
        String scode = subcode.getText().toString();
        String Tpass= Tpassword.getText().toString();


        if(TextUtils.isEmpty(scode))
        {
            //A toast is a view containing a quick little message for the user.
            Toast.makeText(TeacherLoginActivity.this, "Please Enter subject code....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(Tpass))
        {
            Toast.makeText(this, "Please Enter provided password....", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AllowAccess(scode,Tpass);
        }
    }

    //Allow access to teacher on success
    private void AllowAccess(final String scode, final String Tpass)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("teachers").child(scode).exists())
                {
                    UsersModel userData = dataSnapshot.child("teachers").child(scode).getValue(UsersModel.class); //Gets a DatabaseReference for the database specified child node.

                    if (userData.getsubcode().equals(scode))
                    {

                        if (userData.getPassword().equals(Tpass)) {
                            //Storing teacher session
                            Paper.book().write("subcode", scode);
                            Paper.book().write("t_password", Tpass);
                            //loading dialog
                            LoadingDialogAcitivity.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(TeacherLoginActivity.this, "Logged In successfully", Toast.LENGTH_SHORT).show();
                                    LoadingDialogAcitivity.dismissDialog();
                                    Intent intent = new Intent(TeacherLoginActivity.this, TeacherDashboardActivity.class);
                                    startActivity(intent);
                                }
                            },LOADING_DIALOG);

                        } else {
                            Toast.makeText(TeacherLoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(TeacherLoginActivity.this, "Error in get method", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(TeacherLoginActivity.this, "Account with " + scode + " does not exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TeacherLoginActivity.this, "Server Error", Toast.LENGTH_SHORT).show();

            }

        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TeacherLoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}