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

public class AddTeacherActivity extends AppCompatActivity {
    //user interface elements
    private Button Addt;
    private EditText InputTname,InputSubcode, InputTemail, InputTphonenumber, InputTpassword, inputCTpassword;
    long semester;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_add_teacher);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        InputTname = findViewById(R.id.name);
        InputSubcode = findViewById(R.id.subcode);
        InputTphonenumber = findViewById(R.id.phone);
        InputTemail = findViewById(R.id.temail);
        InputTpassword = findViewById(R.id.tpaswword);
        inputCTpassword = findViewById(R.id.tcpassword);
        Addt = findViewById(R.id.reg_t_btn);
        Spinner myspinner = (Spinner) findViewById(R.id.spinner2);

        //Designing Teacher Add text
        String s= "Teacher ADD";
        SpannableString ss1=  new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.5f), 0,8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView at= (TextView) findViewById(R.id.addt_head);
        at.setText(ss1);

        //List to hold spinner items
        List<String> item_list = new ArrayList<String>();
        item_list.add(0, "Sem 1");
        item_list.add(1 , "Sem 2");
        item_list.add(2 , "Sem 3");
        item_list.add(3 , "Sem 4");
        item_list.add(4 , "Sem 5");
        item_list.add(5 , "Sem 6");
        item_list.add(6 , "Sem 7");
        item_list.add(7 , "Sem 8");

        //Adapter for spinner items
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item , item_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(arrayAdapter);

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
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
    //Function to create teacher account
    private void CreateTaccount()
    {
        String name = InputTname.getText().toString();
        String subcode =InputSubcode.getText().toString();
        String phone = InputTphonenumber.getText().toString();
        String email = InputTemail.getText().toString();
        String password = InputTpassword.getText().toString();
        String Cpassword =inputCTpassword.getText().toString();
        long sem = semester +1;

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(AddTeacherActivity.this, "Please Enter your name....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(subcode))
        {
            Toast.makeText(AddTeacherActivity.this, "Please Enter Subject code....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(AddTeacherActivity.this, "Please Enter your phone number....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(AddTeacherActivity.this, "Please Enter your  Email....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(AddTeacherActivity.this, "Please Enter your password....", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(phone) < 9)
        {
            Toast.makeText(AddTeacherActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
        }
        else
        if(!(Cpassword.equals(password)))
        {
            Toast.makeText(AddTeacherActivity.this, "Confirm Password does not match", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.getTrimmedLength(password) < 6)
        {
            Toast.makeText(AddTeacherActivity.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
        }
        else
        if(TextUtils.isDigitsOnly(password))
        {
            Toast.makeText(AddTeacherActivity.this, "Password must contain alphabets", Toast.LENGTH_SHORT).show();
        }
        else
        {

            ValidateSubcode(name, phone, password , subcode , email , sem);
        }
    }
    //Function to validate and update teacher data in firebase
    private void ValidateSubcode(final String name,final String phone, final String password, final String subcode, final String email, long sem)
    {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node.

        RootRef.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(!(dataSnapshot.child("teachers").child(subcode).exists())) {
                    if (!(dataSnapshot.child("teachers").child(subcode).child(phone).exists())) {
                        HashMap<String, Object> teachersdataMap = new HashMap<>();  //Hashmap to store the data into teachers child node in firebase
                        teachersdataMap.put("phone", phone);
                        teachersdataMap.put("password", password);
                        teachersdataMap.put("name", name);
                        teachersdataMap.put("subcode", subcode);
                        teachersdataMap.put("email", email);
                        teachersdataMap.put("semester", sem);

                        RootRef.child("teachers").child(subcode).updateChildren(teachersdataMap)  //Creating/Updating child node
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        //checking if updating task is successful or not
                                        if (task.isSuccessful()) {
                                            //A toast is a view containing a quick little message for the user.
                                            Toast.makeText(AddTeacherActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(AddTeacherActivity.this, AdminActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(AddTeacherActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                            Toast.makeText(AddTeacherActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                    else{
                        Toast.makeText(AddTeacherActivity.this, "This "+phone+" already exists", Toast.LENGTH_SHORT).show();
                        Toast.makeText(AddTeacherActivity.this, "Failed to add teacher", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(AddTeacherActivity.this , AdminActivity.class);
                        startActivity(intent);
                    }
                }
                else
                if((dataSnapshot.child("teachers").child(subcode).exists()))
                {
                    Toast.makeText(AddTeacherActivity.this, "This "+subcode+" already exists", Toast.LENGTH_SHORT).show();
                    Toast.makeText(AddTeacherActivity.this, "Failed to add teacher", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(AddTeacherActivity.this , AdminActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}