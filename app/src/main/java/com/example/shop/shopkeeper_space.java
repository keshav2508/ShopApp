package com.example.shop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class shopkeeper_space extends AppCompatActivity {


    DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthListener;
    TextView profile_name;
    TextView profile_mobile;
    TextView profile_address;
    TextView status_text1;
    TextView status_text2;
    public static String finalEmail;
    public static CircleImageView profile_img;
    Switch shop_status;
    TextView change_img ;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopkeeper_space);

        shop_status = findViewById(R.id.shop_on_off);
        change_img = findViewById(R.id.change_image);


        status_text1 = findViewById(R.id.status_text1);
        status_text1.setText("Shop Closed");
        status_text2 = findViewById(R.id.status_text2);
        status_text2.setText("Shop Opened");
        profile_name=findViewById(R.id.profile_name);
        profile_mobile=findViewById(R.id.profile_mobile);
        profile_address=findViewById(R.id.profile_address);

        status_text1.setVisibility(View.GONE);
        status_text2.setVisibility(View.GONE);


        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = null;
        if(user!= null) {
            email = user.getEmail();
            update_profile(email);
        }else {
            shopkeeper_space.this.startActivity(new Intent(shopkeeper_space.this, Log_in.class));
            shopkeeper_space.this.finish();
        }

        fireAuthListener = firebaseAuth -> {
            FirebaseUser user1 = firebaseAuth.getCurrentUser();

            if (user1 == null) {
                //user not login
                shopkeeper_space.this.startActivity(new Intent(shopkeeper_space.this, Log_in.class));
                shopkeeper_space.this.finish();
            }
        };

        finalEmail = email;

        profile_img = findViewById(R.id.profile_image);
        profile_img.setOnClickListener(v -> {
            Intent i = new Intent(shopkeeper_space.this, Change_Image.class);
            startActivity(i);
        });


        shop_status.setOnClickListener(v -> {
            if (shop_status.isChecked()) {
                status_text1.setVisibility(View.GONE);
                status_text2.setVisibility(View.VISIBLE);
                update_status("shop Opened", finalEmail);

            } else {
                status_text2.setVisibility(View.GONE);
                status_text1.setVisibility(View.VISIBLE);
                update_status("shop Closed", finalEmail);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fireAuthListener != null) {
            firebaseAuth.removeAuthStateListener(fireAuthListener);
        }
    }

    public void update_status(String status, String email) {
        String str = email;
        str = str.replaceAll("[^a-zA-Z0-9]", " ");

        myRef= FirebaseDatabase.getInstance().getReference("users").child(str).child("shopStatus");
        myRef.setValue(status);

    }


    @SuppressLint("ShowToast")
    public void update_profile(String Email) {
        StorageReference ref_extra = FirebaseStorage.getInstance().getReference().child("images/").child(Email);

        ref_extra.getDownloadUrl().addOnSuccessListener(uri -> {
            String imageURL = uri.toString();
            Glide.with(getApplicationContext()).load(imageURL).fitCenter().into(profile_img);
        }).addOnFailureListener(exception -> Toast.makeText(this,"profile" + exception,Toast.LENGTH_LONG).show());


        String str = Email;
        str = str.replaceAll("[^a-zA-Z0-9]", " ");

        myRef=FirebaseDatabase.getInstance().getReference().child("users").child(str);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsw:snapshot.getChildren())
                {
                    if(Objects.requireNonNull(dsw.getKey()).contentEquals("name"))
                    {
                        String profile_name1= Objects.requireNonNull(dsw.getValue()).toString();
                        profile_name.setText(profile_name1);

                    }
                    if(Objects.requireNonNull(dsw.getKey()).contentEquals("mobile"))
                    {
                        String profile_mobile1= Objects.requireNonNull(dsw.getValue()).toString();
                        profile_mobile.setText(profile_mobile1);

                    }
                    if(Objects.requireNonNull(dsw.getKey()).contentEquals("location"))
                    {
                        String profile_address1= Objects.requireNonNull(dsw.getValue()).toString();
                        profile_address.setText(profile_address1);

                    }
                    if(Objects.requireNonNull(dsw.getKey()).contentEquals("shopStatus"))
                    {
                        if(Objects.requireNonNull(dsw.getValue()).toString().contentEquals("shop Opened")){
                            status_text1.setVisibility(View.GONE);
                            status_text2.setVisibility(View.VISIBLE);
                            shop_status.setChecked(true);

                        }else {
                            status_text1.setVisibility(View.VISIBLE);
                            status_text2.setVisibility(View.GONE);
                            shop_status.setChecked(false);
                        }

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Error - " + error,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_space,menu);
        //MenuItem menuItem = menu.findItem(R.id.log_out);
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.log_out){
            firebaseAuth.signOut();
        }

        return super.onOptionsItemSelected(item);

    }
}




