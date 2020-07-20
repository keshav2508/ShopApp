package com.example.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Fragment_3 extends Fragment {
    String final_email;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;
    public Fragment_3() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_fragment_3, container, false);

        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences("pref",Context.MODE_PRIVATE);
        String name = sharedPref.getString("name", "not_found");
        String mobile = sharedPref.getString("mobile", "not_found");
        String location = sharedPref.getString("location", "not_found");
        String email = sharedPref.getString("userEmail", "not_found");
        String password = sharedPref.getString("userPassword", "not_found");
        final_email= email;

        Button sb = rootView.findViewById(R.id.f3_button);
        sb.setOnClickListener(v -> registerUser(name,mobile,location,email,password));

        return rootView;
    }

    private void registerUser(String name,String mobile,String location, String userEmail,String userPassword) {

        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
                .addOnCompleteListener((Activity) Objects.requireNonNull(getContext()), task -> {

                    if (!task.isSuccessful()) {
                        showToast("Authentication failed. " + task.getException());
                    } else {
                        User user =new User(name,mobile,location,userEmail,userPassword,"shop Closed" );
                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        String str = userEmail;
                        str = str.replaceAll("[^a-zA-Z0-9]", " ");
                        mDatabase.child("users").child(str).setValue(user);

                        showToast("Account Registered");
                        Intent intent = new Intent(getActivity(), Fragment_4.class);
                        startActivity(intent);
                    }
                });


    }
    public void showToast(String toastText) {
        Toast.makeText(getActivity(), toastText, Toast.LENGTH_SHORT).show();
    }
}