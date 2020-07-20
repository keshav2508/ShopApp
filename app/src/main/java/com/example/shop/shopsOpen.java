package com.example.shop;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class shopsOpen extends AppCompatActivity {
    private  StorageReference ref_extra;
    FirebaseDatabase db;
    DatabaseReference myRef;
    public static Shops shop;
    Shops[] ListElements = new Shops[] {
    };
    ListView list_view;
    CustomAdaptor adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shops_open);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ProgressBar pb= findViewById(R.id.pb_id);

        final ArrayList<Shops> ListElementsArrayList = new ArrayList<>(Arrays.asList(ListElements));
        adapter = new CustomAdaptor(this, ListElementsArrayList);
        list_view = findViewById(R.id.listView);





        db= FirebaseDatabase.getInstance();
        myRef=db.getReference("users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren())
                {

                    String name ="";
                    String status = "";
                    String id=" ";
                    String mobile=" ";
                    String address=" ";
                    String url = "https://firebasestorage.googleapis.com/v0/b/shop-59bbe.appspot.com/o/ic_logo.png?alt=media&token=a23688c2-df83-418e-8eec-c590a5a70147";


                    for(DataSnapshot dsw:ds.getChildren())
                    {
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("name")) {
                            name = Objects.requireNonNull(dsw.getValue()).toString();
                        }
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("shopStatus")) {
                            status = Objects.requireNonNull(dsw.getValue()).toString();
                        }
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("mobile")) {
                            mobile = Objects.requireNonNull(dsw.getValue()).toString();

                        }
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("location")) {
                            address = Objects.requireNonNull(dsw.getValue()).toString();

                        }
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("userEmail")) {
                            id = Objects.requireNonNull(dsw.getValue()).toString();

                        }
                        if(Objects.requireNonNull(dsw.getKey()).contentEquals("ImagesURL")) {
                            url = Objects.requireNonNull(dsw.getValue()).toString();
                        }
                    }


                    ListElementsArrayList.add(new Shops(name,status,id,mobile,address,url));

                    adapter.notifyDataSetChanged();
                }
                //showToast("Done");

                pb.setVisibility(View.GONE);
                list_view.setAdapter(adapter);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast("cancelled");
            }
        });
        list_view.setOnItemClickListener((parent, view, position, id) -> {
            shop = (Shops) list_view.getItemAtPosition(position);
            String s = shop.getMshopid();
            showToast("item at - " + s) ;
            Intent intent = new Intent(this, ShopInfo.class);
            startActivity(intent);
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.search_view:
                return true;
            case R.id.option1:
                Toast.makeText(this, "Option 2 selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void showToast(String toastText) {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show();
    }


}


