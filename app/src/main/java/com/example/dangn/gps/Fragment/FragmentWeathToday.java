package com.example.dangn.gps.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dangn.gps.Adapter.AdapterWeatherCurrent;
import com.example.dangn.gps.Model.Weather;
import com.example.dangn.gps.R;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentWeathToday extends Fragment {
    private TextView tvTemp,tvTempMaxMin,tvMain;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ArrayList<String> listName;
    private ArrayList<String> listValue;
    private Weather weather;
    public FragmentWeathToday(Weather weather) {
        this.weather = weather;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weathercurrent,container,false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.rv_weathercurren);
        tvTemp = view.findViewById(R.id.tv_weathercurren_temp);
        tvTempMaxMin = (TextView) view.findViewById(R.id.tv_weathercurren_tempmaxmin);
        tvMain = (TextView) view.findViewById(R.id.tv_weathercurren_main);
        imageView = (ImageView) view.findViewById(R.id.iv_weathercurren_icon) ;
        listName = new ArrayList<>();
        listValue = new ArrayList<>();
        setData(listName,listValue);
        setDataView();
        AdapterWeatherCurrent adapterWeatherCurrent = new AdapterWeatherCurrent(getContext(),listName,listValue);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapterWeatherCurrent);
    }
    private void setDataView(){
        tvTemp.setText((int)weather.getMainTemp()+"");
        tvMain.setText(weather.getWeatherMain());
        tvTempMaxMin.setText("↑ "+(int)weather.getMainTempMax()+"° ↓ "+(int)weather.getMainTempMin()+"°");
        Glide.with(getContext()).load("http://openweathermap.org/img/w/"+weather.getWeatherIcon()+".png").into(imageView);
    }
    private void setData(ArrayList<String> listName,ArrayList<String> listValue){
        listName.add("Tốc độ gió");
        listValue.add(weather.getWindSpeed()+"m/s");

        listName.add("Độ ẩm");
        listValue.add(weather.getMainHumidity()+"%");

        listName.add("Áp xuất");
        listValue.add(weather.getMainPressure()+"mb");
    }
}
