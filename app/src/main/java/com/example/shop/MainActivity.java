package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.tomer.fadingtextview.FadingTextView;

public class MainActivity extends AppCompatActivity {
    Button shopkeeper;
    Button buyer;
    ImageView img;
    Animation animation1,animation2;
    FadingTextView ftv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shopkeeper=findViewById(R.id.shopkeeper2);
        shopkeeper.setOnClickListener(v -> shopkeeper());
        buyer=findViewById(R.id.buyer);
        buyer.setOnClickListener(v -> shopsOpen());
        img = findViewById(R.id.main_img);
        ftv = findViewById(R.id.fading_tv);


        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        img.startAnimation(animation1);


    }
    public void shopkeeper(){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 25);
        myAnim.setInterpolator(interpolator);

        shopkeeper.startAnimation(myAnim);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, Registration_page.class);
            startActivity(i);
        }, 500);

    }
    public void shopsOpen(){
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 25);
        myAnim.setInterpolator(interpolator);

        buyer.startAnimation(myAnim);
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, shopsOpen.class);
            startActivity(i);
        }, 500);
    }
}

