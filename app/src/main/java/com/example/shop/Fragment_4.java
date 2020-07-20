package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Fragment_4 extends AppCompatActivity {
    StorageReference storageReference ;
    DatabaseReference mDatabaseRef ;

    public Uri filePath;
    private ImageView imageView;
    EditText about ;
    private final int PICK_IMAGE_REQUEST = 234;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_4);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            showToast("User not Registered");
        }else{
            email = firebaseAuth.getCurrentUser().getEmail();
        }





        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        Button btnSelect = findViewById(R.id.btnChoose_f4);
        Button next_btn = findViewById(R.id.sk_space_btn);
        Button skip_btn = findViewById(R.id.skip_btn);
        about = findViewById(R.id.About_shop);

        imageView = findViewById(R.id.imgView_f4);

        btnSelect.setOnClickListener(v -> SelectImage());
        next_btn.setOnClickListener(v -> uploadImage());
        skip_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, shopkeeper_space.class);
            startActivity(intent);
        });





    }



    private void SelectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            imageView.setImageURI(filePath);
        }
    }


    private void uploadImage()
    {
        //String email = shopkeeper_space.finalEmail;
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + email);

            ref.putFile(filePath)
                    .addOnSuccessListener(taskSnapshot -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();

                        String str = email;
                        str = str.replaceAll("[^a-zA-Z0-9]", " ");
                        String finalStr = str;
                        ref.getDownloadUrl().addOnSuccessListener(uri -> mDatabaseRef.child(finalStr).child("ImagesURL").setValue(uri.toString()));

                        mDatabaseRef.child(str).child("About").setValue(about.getText().toString().trim());


                        shopkeeper_space.profile_img.setImageURI(filePath);
                        Intent intent = new Intent(this, shopkeeper_space.class);
                        startActivity(intent);
                    })

                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int)progress + "%");
                    });
        }
    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }
}