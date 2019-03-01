package com.example.dangn.gps.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dangn.gps.Adapter.AdapterWeatherCurrent;
import com.example.dangn.gps.Model.CityWeather;
import com.example.dangn.gps.Model.CityWeatherList;
import com.example.dangn.gps.R;

import java.util.ArrayList;

public class SeachActivity extends AppCompatActivity {
    private CityWeatherList cityWeatherList;
    private EditText edtSeach;
    private Button btnSeach;
    private RecyclerView rvCity;
    private AdapterWeatherCurrent adapterWeatherCurrent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach);
        cityWeatherList = new CityWeatherList(getApplicationContext());
        init();
    }
    private void init(){
        edtSeach = findViewById(R.id.edt_seachcity);
        btnSeach = findViewById(R.id.btn_seachcity);
        rvCity = findViewById(R.id.rv_seachcicy);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvCity.setLayoutManager(layoutManager);
        btnSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CityWeather> arr = cityWeatherList.seachCity(edtSeach.getText().toString());
                ArrayList<String> name = new ArrayList<>();
                ArrayList<String> value = new ArrayList<>();
                Log.d("seach", arr.size()+" ");
                for(CityWeather cityWeather:arr){
                    name.add(cityWeather.getName());
                    value.add("");
                }
                adapterWeatherCurrent = new AdapterWeatherCurrent(getApplicationContext(),name,value);
                rvCity.setAdapter(adapterWeatherCurrent);
            }
        });
    }
}
