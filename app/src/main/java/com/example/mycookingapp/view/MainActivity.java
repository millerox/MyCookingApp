package com.example.mycookingapp.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.mycookingapp.BasicActivity;
import com.example.mycookingapp.R;
import com.example.mycookingapp.frgAllRecipes;
import com.example.mycookingapp.frgMyRecipes;
import com.example.mycookingapp.frgSearch;
import com.example.mycookingapp.view.ui.main.SectionsPagerAdapter;

public class MainActivity extends BasicActivity {

    private final String TAG = "Main Activity";
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new frgAllRecipes(),"All Recipes");
        adapter.addFragment(new frgMyRecipes(),"My Recipes");
        adapter.addFragment(new frgSearch(),"Search");
        viewPager.setAdapter(adapter);
    }

}