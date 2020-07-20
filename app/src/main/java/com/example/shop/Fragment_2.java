package com.example.shop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class Fragment_2 extends Fragment {
    private FirebaseAuth firebaseAuth;
    private EditText etName, etEmail, etPassword, etCPassword, etMobile, etLocation;
    private TextInputLayout til_email;
    private TextInputLayout til_mobile;
    private TextInputLayout til_password;
    private TextInputLayout til_con_pass;

    public Fragment_2() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_2, container, false);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            // User is logged in
            Intent intent = new Intent(getActivity(), shopkeeper_space.class);
            startActivity(intent);
        }


        SharedPreferences sp = Objects.requireNonNull(this.getActivity()).getSharedPreferences("pref", Context.MODE_PRIVATE);

        //TextInputLayout til_name = rootView.findViewById(R.id.f2_til_name);
        til_email =rootView.findViewById(R.id.f2_til_email);
        til_mobile =rootView.findViewById(R.id.f2_til_mobile);
        //TextInputLayout til_address = rootView.findViewById(R.id.f2_til_address);
        til_password =rootView.findViewById(R.id.f2_til_password);
        til_con_pass =rootView.findViewById(R.id.f2_til_con_pass);


        etName = rootView.findViewById(R.id.Fragment_2_name);
        etEmail = rootView.findViewById(R.id.Fragment_2_email);
        etPassword = rootView.findViewById(R.id.Fragment_2_password);
        etCPassword = rootView.findViewById(R.id.Fragment_2_con_password);
        etMobile = rootView.findViewById(R.id.Fragment_2_mobile);
        etLocation = rootView.findViewById(R.id.Fragment_2_address);

        Button next = rootView.findViewById(R.id.Fragment_2_next_button);
        next.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String mobile = etMobile.getText().toString();
            String location = etLocation.getText().toString();
            final String userEmail = etEmail.getText().toString().trim();
            final String userPassword = etPassword.getText().toString().trim();
            String con_password = etCPassword.getText().toString().trim();


            if (TextUtils.isEmpty(userEmail)) {
                //showToast("Enter email address!");
                til_email.setError("Email can't be empty");
                return;
            }
            /*if (!con_password.equals(userPassword)) {
                //showToast("password mismatched");
                til_con_pass.setError("password mismatched");
                return;
            }

            if(TextUtils.isEmpty(userPassword)){
                //showToast("Enter Password!");
                til_password.setError("password can't be empty");
                return;
            }

            if(userPassword.length() < 6){
                //showToast("Password too short, enter minimum 6 characters");
                til_password.setError("Password too short, enter minimum 6 characters");
                return;
            }
            if(mobile.length() != 10){
                til_mobile.setError("Enter Valid Mobile Number");
            }

             */

            SharedPreferences.Editor editor = sp.edit();
            editor.putString("name",name);
            editor.putString("mobile",mobile);
            editor.putString("location",location);
            editor.putString("userEmail",userEmail);
            editor.putString("userPassword",userPassword);

            editor.apply();

            Registration_page var = (Registration_page)getActivity();
            var.viewPager.setCurrentItem(1);
        });

        Button sign_in = rootView.findViewById(R.id.Fragment_2_sign_in_button);
        sign_in.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Log_in.class);
            startActivity(intent);
        });



        return rootView;
    }
    //public void showToast(String toastText) {
    //    Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    //}

}