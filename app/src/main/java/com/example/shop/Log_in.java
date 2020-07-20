package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class Log_in extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        firebaseAuth = FirebaseAuth.getInstance();

        EditText email=(EditText) findViewById(R.id.email_login);
        EditText password=(EditText)findViewById(R.id.password_login);
        Button loginButton=(Button) findViewById(R.id.login_button);
        Button resetButton=(Button) findViewById(R.id.reset_button);
        Button btnSignup=(Button) findViewById(R.id.btn_signup);
        ProgressBar progressBar=(ProgressBar) findViewById(R.id.progressBar);

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(Log_in.this, shopkeeper_space.class));
            finish();
        }

        btnSignup.setOnClickListener(v -> Log_in.this.startActivity(new Intent(Log_in.this, Registration_page.class)));

        resetButton.setOnClickListener(v -> {
            //Log_in.this.startActivity(new Intent(Log_in.this, ResetActivity.class));
            Intent intent = new Intent(this, ResetActivity.class);
            startActivity(intent);
        });



        loginButton.setOnClickListener(v -> {
            String user_email = email.getText().toString();
            final String user_password = password.getText().toString();

            if (TextUtils.isEmpty(user_email)) {
                Toast.makeText(Log_in.this.getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(user_password)) {
                Toast.makeText(Log_in.this.getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            //login user
            firebaseAuth.signInWithEmailAndPassword(user_email,user_password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.GONE);

                        if (!task.isSuccessful()) {
                           showToast("login unsuccessful");
                        } else {
                            Log_in.this.startActivity(new Intent(Log_in.this, shopkeeper_space.class));
                            Log_in.this.finish();
                        }
                    });

        });



    }
    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }


}