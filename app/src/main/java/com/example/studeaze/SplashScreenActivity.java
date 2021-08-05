package com.example.studeaze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity { //class for splash_screen

    private static int SPLASH_SCREEN = 5000; //Variable to store 5000 value

    //variables/user interface elements
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView app_name, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  //called when activity is started, to perform initialisation
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        //Finds a view that was identified by the android:id XML attribute that was processed in onCreate.
        image = findViewById(R.id.splash_image_view);
        app_name = findViewById(R.id.splash_text_view);
        description = findViewById(R.id.splash_text_view2);
        //setting animation to interface elements
        image.setAnimation(topAnim);
        app_name.setAnimation(bottomAnim);
        description.setAnimation(bottomAnim);
        //Handler to load animation
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}