package com.example.dangn.gps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ArrayList<Fragment> listFragment;
    private ArrayList<String> listTitle;
    private  CityWeatherList weatherList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }
    private void init(){
//        weatherList = new CityWeatherList(getApplicationContext());
//
//        ApiWeather apiWeather = new ApiWeather(21.43498,105.293137,getApplicationContext());
//        Weather weather = apiWeather.getWeatherCurrent();
       // Log.d("test",weather.getWeatherMain()+"");
        listFragment = new ArrayList<>();
        listTitle = new ArrayList<>();
//        listFragment.add(new FragmentWeathToday());
//        listFragment.add(new FragmentWeathToday());
        listTitle.add("tab1");
        listTitle.add("tab2");
        viewPager = findViewById(R.id.vp_test);
        tabLayout = findViewById(R.id.tl_test);
        FragmentManager fragmentManager = getSupportFragmentManager();
        PagerAdapter pagerAdapter = new PagerAdapter(fragmentManager,listFragment,listTitle);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
