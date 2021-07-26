package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class admin extends AppCompatActivity {
Button rm_std, add_teach, rm_teach, add_notice, logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        rm_std = findViewById(R.id.remove_std);
        add_teach = findViewById(R.id.add_teacher);
        rm_teach = findViewById(R.id.remove_teacher);
        add_notice = findViewById(R.id.notice);
        logout = findViewById(R.id.log_out);


        String s= "Admin Dashboard";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(2f), 0,6, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 5, 0);// set color
        TextView tv= (TextView) findViewById(R.id.admin_htext);
        tv.setText(ss1);

        rm_std.setOnClickListener(View ->{
            remStudent();
        });

        add_teach.setOnClickListener(View ->{
            regTeacher();
        });

        rm_teach.setOnClickListener(View ->{
            remTeacher();
        });

        add_notice.setOnClickListener(View ->{
            add_Notice();
        });

        Paper.init(this);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().destroy();
                Intent intent = new Intent(admin.this, home.class);
                startActivity(intent);
            }
        });
    }

    public  void regTeacher(){
        Intent intent = new Intent(this,add_teacher.class);
        startActivity(intent);
    }

    public  void remStudent(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(admin.this);
        View mView = getLayoutInflater().inflate(R.layout.del_student,null);
        final EditText rem_S_txt = (EditText)mView.findViewById(R.id.rem_S_txt);
        Button btn_cancel = (Button)mView.findViewById(R.id.S_btn_cancel);
        Button btn_delete = (Button)mView.findViewById(R.id.S_btn_delete);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usn = rem_S_txt.getText().toString();
                if(TextUtils.isEmpty(usn))
                {
                    Toast.makeText(admin.this, "Please Enter Usn....", Toast.LENGTH_SHORT).show();
                }
                else {
                    final DatabaseReference RootRef;
                    RootRef= FirebaseDatabase.getInstance().getReference();

                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("students").child(usn).exists()){
                                RootRef.child("students").child(usn).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(admin.this, "Student with USN "+usn+" is removed Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(admin.this, "Network Error", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(admin.this,"Try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(admin.this, "Provided USN "+usn+" is not registered", Toast.LENGTH_SHORT).show();
                                Toast.makeText(admin.this, "Retry removal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public  void remTeacher(){
        final AlertDialog.Builder alert = new AlertDialog.Builder(admin.this);
        View mView = getLayoutInflater().inflate(R.layout.del_teacher,null);
        final EditText rem_T_txt = (EditText)mView.findViewById(R.id.rem_T_txt);
        Button btn_cancel = (Button)mView.findViewById(R.id.T_btn_cancel);
        Button btn_delete = (Button)mView.findViewById(R.id.T_btn_delete);
        alert.setView(mView);
        final AlertDialog alertDialog = alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sub_code = rem_T_txt.getText().toString();
                if(TextUtils.isEmpty(sub_code))
                {
                    Toast.makeText(admin.this, "Please Enter Subject Code....", Toast.LENGTH_SHORT).show();
                }
                else {
                    final DatabaseReference RootRef;
                    RootRef= FirebaseDatabase.getInstance().getReference();

                    RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(dataSnapshot.child("teachers").child(sub_code).exists()){
                                RootRef.child("teachers").child(sub_code).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(admin.this, "Teacher with Subject Code "+sub_code+" is removed Successfully", Toast.LENGTH_SHORT).show();
                                                }
                                                else {
                                                    Toast.makeText(admin.this, "Network Error", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(admin.this,"Try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else {
                                Toast.makeText(admin.this, "Provided Subject Code "+sub_code+" is not registered", Toast.LENGTH_SHORT).show();
                                Toast.makeText(admin.this, "Retry removal", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public  void add_Notice(){
        Intent intent = new Intent(this,add_notice.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(admin.this,home.class);
        startActivity(intent);
    }
}