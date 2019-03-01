package com.example.dangn.gps.Model;

import android.content.Context;

import com.example.dangn.gps.Model.CityWeather;
import com.example.dangn.gps.Model.IOFile;
import com.example.dangn.gps.VNCharacterUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CityWeatherList {
    private ArrayList<CityWeather> list;
    private IOFile file;
    private Context context;
    private String fileName = "VietNam.txt";
    public CityWeatherList(Context context) {
        this.context = context;
        file = new IOFile(context);
        list = new ArrayList<>();
        suli(file.read(fileName));

    }

    public ArrayList<CityWeather> getList() {
        return list;
    }
    public void suli(String s){
        try {
            JSONObject jsonObjectCity =null;
            JSONObject jsonObjectCoord =null;
            JSONArray jsonArray = new JSONArray(s);
            for (int i=0;i<jsonArray.length();i++){
                jsonObjectCity = jsonArray.getJSONObject(i);
                jsonObjectCoord = jsonObjectCity.getJSONObject("coord");
                list.add(new CityWeather(jsonObjectCity.getInt("id"),jsonObjectCity.getString("name"),jsonObjectCoord.getDouble("lon"),jsonObjectCoord.getDouble("lat")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<CityWeather> seachCity(String city){
        ArrayList<CityWeather> arr = new ArrayList<>();
        for(CityWeather cityWeather: list){
            if(VNCharacterUtils.removeAccent(cityWeather.getName()).toLowerCase().contains(VNCharacterUtils.removeAccent(city).toLowerCase())){
                arr.add(cityWeather);
            }
        }
        return arr;
    }
}
