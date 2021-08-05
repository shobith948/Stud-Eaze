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

public class StudentRegisterActivity extends AppCompatActivity {
    //user interface elements
    private Button CreateAccountButton;
    private EditText InputName,Inputusn,Inputemail, InputPhonenumber, InputPassword, CInputPassword;
    long semester;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        setContentView(R.layout.activity_student_register);

        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        InputName = findViewById(R.id.name);
        Inputusn = findViewById(R.id.usn);
        InputPhonenumber = findViewById(R.id.phone);
        Inputemail = findViewById(R.id.email);
        InputPassword = findViewById(R.id.password);
        CInputPassword = findViewById(R.id.cpassword);
        CreateAccountButton = findViewById(R.id.reg_s_btn);
        Spinner myspinner = (Spinner) findViewById(R.id.spinner1);

        //List to hold semester selected through spinner
        List<String> item_list = new ArrayList<String>();
        item_list.add(0, "Sem 1");
        item_list.add(1 , "Sem 2");
        item_list.add(2 , "Sem 3");
        item_list.add(3 , "Sem 4");
        item_list.add(4 , "Sem 5");
        item_list.add(5 , "Sem 6");
        item_list.add(6 , "Sem 7");
        item_list.add(7 , "Sem 8");

        //Adapter for spinner
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item , item_list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(arrayAdapter);

        //Item selection listener for spinner
        myspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    semester = parent.getItemIdAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //Designing Student Register text
        String s = "Student Register";
        SpannableString ss1 = new SpannableString(s);
        ss1.setSpan(new RelativeSizeSpan(1.4f), 0, 8, 0); // set size
        ss1.setSpan(new ForegroundColorSpan(R.drawable.card_view), 0, 7, 0);// set color
        TextView tv = (TextView) findViewById(R.id.reg_head);
        tv.setText(ss1);

        //Register a callback to be invoked when this view is clicked. If this view is not clickable, it becomes clickable.
        CreateAccountButton.setOnClickListener(v -> {
            CreateAccount();
        });
    }

    //Function to create student account
    private void CreateAccount()
            {
                String name =InputName.getText().toString();
                String usn =Inputusn.getText().toString();
                String phone =InputPhonenumber.getText().toString();
                String email =Inputemail.getText().toString();
                String password =InputPassword.getText().toString();
                String Cpassword =CInputPassword.getText().toString();
                long sem = semester+1;

                if(TextUtils.isEmpty(name))
                {
                    //A toast is a view containing a quick little message for the user.
                    Toast.makeText(StudentRegisterActivity.this, "Please Enter your name....", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.isEmpty(usn))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Please Enter your Usn....", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.isEmpty(phone))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Please Enter your phone number....", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Please Enter your  Email....", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Please Enter your password....", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.getTrimmedLength(phone) < 9)
                {
                    Toast.makeText(StudentRegisterActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else
                if(!(Cpassword.equals(password)))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Confirm Password does not match", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.getTrimmedLength(password) < 6)
                {
                    Toast.makeText(StudentRegisterActivity.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                }
                else
                if(TextUtils.isDigitsOnly(password))
                {
                    Toast.makeText(StudentRegisterActivity.this, "Password must contain alphabets", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    ValidateUsn(name, phone, password , usn , email , sem);
                }
            }

            //Function to validate and add details into firebase database
            private void ValidateUsn(final String name,final String phone, final String password, final String usn, final String email, long sem)
            {
                final DatabaseReference RootRef;
                RootRef= FirebaseDatabase.getInstance().getReference(); //Gets a DatabaseReference for the database root node.

                RootRef.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if(!(dataSnapshot.child("students").child(usn).exists())) {
                            if (!(dataSnapshot.child("students").child(usn).child(phone).exists())) {
                                HashMap<String, Object> studentsdataMap = new HashMap<>(); //Hashmap to store data into students child node in firebase
                                studentsdataMap.put("phone", phone);
                                studentsdataMap.put("password", password);
                                studentsdataMap.put("name", name);
                                studentsdataMap.put("usn", usn);
                                studentsdataMap.put("email", email);
                                studentsdataMap.put("semester", sem);

                                RootRef.child("students").child(usn).updateChildren(studentsdataMap)  //Creating/Updating child node
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //checking if updating task is successful or not
                                                if (task.isSuccessful()) {

                                                    Toast.makeText(StudentRegisterActivity.this, "Congratulations your account is created successfully", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(StudentRegisterActivity.this, StudentLoginActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(StudentRegisterActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(StudentRegisterActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                            else{
                                Toast.makeText(StudentRegisterActivity.this, "This "+phone+" already exists", Toast.LENGTH_SHORT).show();
                                Toast.makeText(StudentRegisterActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        if((dataSnapshot.child("students").child(usn).exists()))
                        {
                            Toast.makeText(StudentRegisterActivity.this, "This "+usn+" already exists", Toast.LENGTH_SHORT).show();
                            Toast.makeText(StudentRegisterActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(StudentRegisterActivity.this , StudentLoginActivity.class);
                            startActivity(intent);
                        }

                        else
                        if((dataSnapshot.child("students").child(phone).exists()))
                        {
                            Toast.makeText(StudentRegisterActivity.this, "This "+phone+" already exists", Toast.LENGTH_SHORT).show();
                            Toast.makeText(StudentRegisterActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(StudentRegisterActivity.this , StudentLoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
}
