package com.example.shop;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ShopInfo extends AppCompatActivity {
    TextView email,location,status,name;
    ImageView profile_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        email = findViewById(R.id.info_email);
        location = findViewById(R.id.info_address);
        status = findViewById(R.id.info_status);
        name = findViewById(R.id.info_name);
        profile_image = findViewById(R.id.info_image);

        Shops info_shop = shopsOpen.shop;
        email.setText(info_shop.getMshopid());
        location.setText(info_shop.getMshopaddress());
        status.setText(info_shop.getMshopStatus());
        name.setText(info_shop.getMshopname());

        Glide.with(this).load(info_shop.getImageResourceId()).centerCrop().fitCenter().into(profile_image);


    }
}