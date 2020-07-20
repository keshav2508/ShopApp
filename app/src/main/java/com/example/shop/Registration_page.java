package com.example.shop;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;


public class Registration_page extends AppCompatActivity {

    private int[] imageResId = {
            R.drawable.one, R.drawable.two , R.drawable.three};
    private String[] tabTitles = new String[] { "Shop Details", "Set Location" , "Register"};
    public ViewPager viewPager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);




        viewPager = findViewById(R.id.pager);
        TabsPagerAdaptor mAdapter = new TabsPagerAdaptor(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(mAdapter);
        TabLayout tabLayout = findViewById(R.id.view_pager_tab);
        tabLayout.setupWithViewPager(viewPager);



        for (int i = 0; i < tabTitles.length; i++) {
            Objects.requireNonNull(tabLayout.getTabAt(i)).setText(tabTitles[i]).setIcon(imageResId[i]);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });


    }
}