package com.example.shop;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ApplyFilter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_filter);

        Button B_W = findViewById(R.id.changeToBW);
        CheckBox checkBox = findViewById(R.id.checkBox);

        ImageView filtered_image = findViewById(R.id.filteredImage_applyFilter);
        filtered_image.setImageURI(Change_Image.filePath);
        ImageView original_image = findViewById(R.id.originalImage_applyFilter);
        ProgressBar p_b_filter = findViewById(R.id.p_b_applyFilter);
        original_image.setImageURI(Change_Image.filePath);

        checkBox.setOnClickListener(v -> {
            if(checkBox.isChecked()){
                filtered_image.setVisibility(View.GONE);
                p_b_filter.setVisibility(View.GONE);
            }
            if(!checkBox.isChecked()){
                filtered_image.setVisibility(View.VISIBLE);
            }
        });


        B_W.setOnClickListener(v -> {
            filtered_image.setVisibility(View.GONE);
            p_b_filter.setVisibility(View.VISIBLE);

            final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

            MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 25);
            myAnim.setInterpolator(interpolator);
            B_W.startAnimation(myAnim);

            Bitmap selected_image = null;
            try {
                selected_image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Change_Image.filePath);
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

        });
        SeekBar sbBrightness = findViewById(R.id.sb_bright);
        sbBrightness.setMax(200);
        sbBrightness.setProgress(100);
        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    filtered_image.setColorFilter(brightness(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                new Handler().postDelayed(() -> {
                    //sbBrightness.setVisibility(View.GONE);
                },1000);

            }
        });
    }
    private ColorMatrixColorFilter brightness(float value) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, value,
                0, 1, 0, 0, value,
                0, 0, 1, 0, value,
                0, 0, 0, 1, 0});
        return new ColorMatrixColorFilter(cmB);
    }
    public static Bitmap test(Bitmap src, ProgressBar pb){
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

}
