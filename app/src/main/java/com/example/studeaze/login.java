package com.example.studeaze;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login extends AppCompatActivity {

    TextView reg;
    private EditText Susn, Spassword;
    Button loginbtn;
    private static int LOADING_DIALOG = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);

        setContentView(R.layout.activity_stud_login);
        TextView textView = (TextView) findViewById(R.id.textView7);
        loginbtn = findViewById(R.id.s_login);
        Susn = findViewById(R.id.usn);
        Spassword = findViewById(R.id.pass);
        final loading_dialog loading_dialog = new loading_dialog(login.this);


        String s= "Student login";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.home_btn), 0, 7, 0);// set color
        TextView tv= (TextView) findViewById(R.id.textView7);
        tv.setText(ss1);

        reg = findViewById(R.id.reg);

        reg.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

                                       Intent intent = new Intent(login.this, reg_student.class);
                                       startActivity(intent);
                                   }
                               });
                loginbtn.setOnClickListener(v -> {
                    if (Susn.getText().toString().equals("admin") && Spassword.getText().toString().equals("admin123")) {
                        Paper.book().write("usn", Susn.getText().toString());
                        Paper.book().write("password", Spassword.getText().toString());
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
                        }, LOADING_DIALOG);
                    } else {
                        LoginStudent();
                    }
                });
    }

    public  void regStudent(){
        Intent intent = new Intent(this,reg_student.class);
        startActivity(intent);
    }
    private void LoginStudent()
    {
        String usn = Susn.getText().toString();
        String password= Spassword.getText().toString();


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
            Toast.makeText(login.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AllowAccess(usn,password);
        }
    }

    private void AllowAccess(final String usn, final String password)
    {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("students").child(usn).exists())
                {
                    students userData = dataSnapshot.child("students").child(usn).getValue(students.class);

                    if (userData.getUsn().equals(usn))
                    {

                        if (userData.getPassword().equals(password)) {
                            Paper.book().write("usn", usn);
                            Paper.book().write("password", password);
                            loading_dialog.startLoadingDialog();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
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
