package com.example.shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Change_Image extends AppCompatActivity {
    StorageReference storageReference ;
    DatabaseReference mDatabaseRef ;

    public static Uri filePath;
    private ImageView imageView;
    EditText about ;
    private final int PICK_IMAGE_REQUEST = 234;
    ProgressBar p_b_filter ;
    ImageView filtered_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__image);

        p_b_filter = findViewById(R.id.p_b_filter);
        p_b_filter.setVisibility(View.GONE);
        filtered_image = findViewById(R.id.imgView_bw);
        filtered_image.setVisibility(View.GONE);



        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("users");

        Button btnSelect = findViewById(R.id.btnChoose);
        Button btnUpload = findViewById(R.id.btnUpload);
        Button btnChange = findViewById(R.id.btnchange);
        about = findViewById(R.id.About_shop);

        imageView = findViewById(R.id.imgView);

        btnSelect.setOnClickListener(v -> SelectImage());
        btnUpload.setOnClickListener(v -> uploadImage());
        btnChange.setOnClickListener(v -> {
            /*filtered_image.setVisibility(View.GONE);
            p_b_filter.setVisibility(View.VISIBLE);

            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 25);
            myAnim.setInterpolator(interpolator);
            btnChange.startAnimation(myAnim);

            Bitmap selected_image = null;
            try {
                selected_image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();

            }
            AtomicReference<Bitmap> b_w_image = new AtomicReference<>();
            assert selected_image != null;
            Bitmap finalSelected_image = selected_image;
            new Handler().postDelayed(() -> {
                b_w_image.set(test(finalSelected_image, p_b_filter));
                filtered_image.setVisibility(View.VISIBLE);
                filtered_image.setImageBitmap(b_w_image.get());
            }, 1000);

             */
            Intent intent = new Intent(this, ApplyFilter.class);
            startActivity(intent);

        });
    }
    public static Bitmap test(Bitmap src,ProgressBar pb){
        int width = src.getWidth();
        int height = src.getHeight();



        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        double value = 50;
        double contrast = Math.pow((100 + value) / 100, 2);

        int pixel;
        for (int x = 0; x < width; ++x) {

            for (int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);

                // set new pixel color to output bitmap
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }

        }
        pb.setVisibility(View.GONE);

        return bmOut;
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
            //Bitmap bitmap = BitmapFactory.decodeFile(filePath.toString());
            imageView.setImageURI(filePath);


        }
    }


    private void uploadImage()
    {
        String email = shopkeeper_space.finalEmail;
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


}