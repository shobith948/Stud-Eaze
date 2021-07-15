package com.example.studeaze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class add_teacher extends AppCompatActivity {
    private Button Addt;
    private EditText InputTname,InputSubcode, InputTemail, InputTphonenumber, InputTpassword, inputCTpassword;
    long semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        InputTname = findViewById(R.id.name);
        InputSubcode = findViewById(R.id.subcode);
        InputTphonenumber = findViewById(R.id.phone);
        InputTemail = findViewById(R.id.temail);
        InputTpassword = findViewById(R.id.tpaswword);
        inputCTpassword = findViewById(R.id.tcpassword);

        Addt = findViewById(R.id.reg_t_btn);
        Spinner myspinner = (Spinner) findViewById(R.id.spinner2);

        TextView textView = (TextView) findViewById(R.id.addt_head);
        String s= "Teacher ADD";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView at= (TextView) findViewById(R.id.addt_head);
        at.setText(ss1);

        List<String> item_list = new ArrayList<String>();
        item_list.add(0, "Sem 1");
        item_list.add(1 , "Sem 2");
        item_list.add(2 , "Sem 3");
        item_list.add(3 , "Sem 4");
        item_list.add(4 , "Sem 5");
        item_list.add(5 , "Sem 6");
        item_list.add(6 , "Sem 7");
        item_list.add(7 , "Sem 8");

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item , item_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(arrayAdapter);

        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                semester = parent.getItemIdAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        Addt.setOnClickListener(v -> {
            CreateTaccount();
        });

    }
    private void CreateTaccount()
    {
        String name = InputTname.getText().toString();
        String subcode =InputSubcode.getText().toString();
        String phone = InputTphonenumber.getText().toString();
        String email = InputTemail.getText().toString();
        String password = InputTpassword.getText().toString();
        String Cpassword =inputCTpassword.getText().toString();
        long sem = this.semester +1;

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(add_teacher.this, "Please Enter your name....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(subcode))
        {
            Toast.makeText(add_teacher.this, "Please Enter Subject code....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(add_teacher.this, "Please Enter your phone number....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(add_teacher.this, "Please Enter your  Email....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(add_teacher.this, "Please Enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(phone) < 9)
        {
            Toast.makeText(add_teacher.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        if(!(Cpassword.equals(password)))
        {
            Toast.makeText(add_teacher.this, "Confirm Password does not match", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(phone) < 6)
        {
            Toast.makeText(add_teacher.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isDigitsOnly(password))
        {
            Toast.makeText(add_teacher.this, "Password must contain alphabets", Toast.LENGTH_SHORT).show();
        }
        else
        {

            ValidateSubcode(name, phone, password , subcode , email , semester);
        }
    }
    private void ValidateSubcode(final String name,final String phone, final String password, final String subcode, final String email, long sem)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("teachers").child(subcode).exists())) {
                    if (!(dataSnapshot.child("teachers").child(subcode).child(phone).exists())) {
                        HashMap<String, Object> studentsdataMap = new HashMap<>();
                        studentsdataMap.put("phone", phone);
                        studentsdataMap.put("password", password);
                        studentsdataMap.put("name", name);
                        studentsdataMap.put("subcode", subcode);
                        studentsdataMap.put("email", email);
                        studentsdataMap.put("semester", sem);

                        RootRef.child("teachers").child(subcode).updateChildren(studentsdataMap)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(add_teacher.this, "Added successfull", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(add_teacher.this, admin.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(add_teacher.this, "Network Error", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(add_teacher.this, "Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(add_teacher.this, "This "+phone+" already exists", Toast.LENGTH_SHORT).show();
                        Toast.makeText(add_teacher.this, "Please Login", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                if((dataSnapshot.child("students").child(subcode).exists()))
                {
                    Toast.makeText(add_teacher.this, "This "+subcode+" already exists", Toast.LENGTH_SHORT).show();
                    Toast.makeText(add_teacher.this, "Please Login", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(add_teacher.this , admin.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}