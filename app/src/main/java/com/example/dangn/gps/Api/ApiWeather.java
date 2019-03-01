package com.example.dangn.gps.Api;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.dangn.gps.Model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ApiWeather {
    private String lat;
    private String lon;
    private String country;
    private Context context;
    private UpdatateView updatateView;
    public ApiWeather(double lat, double lon,Context context,UpdatateView updatateView) {
        this.lat = Double.toString(lat);
        this.lon = Double.toString(lon);
        this.context = context;
        this.updatateView = updatateView;
    }

    public ApiWeather(String country) {
        this.country = country;
    }

    public ApiWeather(double lat, double lon, String country) {
        this.lat = Double.toString(lat);
        this.lon = Double.toString(lon);
        this.country = country;
    }

    public Weather getWeatherCurrent() {
        final Weather weather  = new Weather();
        Log.d("api", "Latitude : " + lat + "\nLongitude : " + lon);
        AndroidNetworking.get("http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&APPID=e2481ae3890b1600d08fa8bce7b99ee3").addPathParameter("lon", lon).addPathParameter("lat", lat).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.LOW)
                .build().getAsJSONObject(new JSONObjectRequestListener() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String s = response.getString("name");
//                    Toast.makeText(context,s,Toast.LENGTH_LONG).show();
                   // Toast.makeText(context,response.getString("cod")+"",Toast.LENGTH_LONG).show();
                    JSONObject mainObject = response.getJSONObject("main");
                    JSONObject windObject = response.getJSONObject("wind");
                    JSONArray weatherArray = response.getJSONArray("weather");

                    weather.setMainHumidity(mainObject.getDouble("humidity"));
                    weather.setMainPressure(mainObject.getDouble("pressure"));
                    weather.setMainTemp(mainObject.getDouble("temp"));
                    weather.setMainTempMax(mainObject.getDouble("temp_max"));
                    weather.setMainTempMin(mainObject.getDouble("temp_min"));
                    weather.setWeatherMain(weatherArray.getJSONObject(0).getString("main"));
                    weather.setWeatherIcon(weatherArray.getJSONObject(0).getString("icon"));
                   // weather.setWindDeg(windObject.getDouble("deg"));
                    weather.setWindSpeed(windObject.getDouble("speed"));
                    Log.d("api",weather.getWindSpeed()+"");
                    updatateView.update(weather);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
        Log.d("test","Đặng Phương Nam");
        return weather;
    }
    public ArrayList<Weather> getWeatherNextDays(){
        final ArrayList<Weather>list   = new ArrayList<>();
        Log.d("apiNext",list.size()+"");
        Log.d("api", "Latitude : " + lat + "\nLongitude : " + lon);
        AndroidNetworking.get("http://api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&APPID=e2481ae3890b1600d08fa8bce7b99ee3").addPathParameter("lon", lon).addPathParameter("lat", lat).addQueryParameter("limit", "3").addHeaders("token", "1234").setTag("test")
                .setPriority(Priority.LOW)
                .build().getAsJSONObject(new JSONObjectRequestListener() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    Weather weather ;
                    Log.d("apiNext",list.size()+"da chay");
                    JSONArray array = response.getJSONArray("list");
                    DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
                    for (int i=0;i<array.length();i++){
                        if(i%8==4){
                            weather = new Weather();
                            JSONObject object = array.getJSONObject(i);
                            JSONObject mainObject = object.getJSONObject("main");
                            JSONObject windObject = object.getJSONObject("wind");
                            JSONArray weatherArray = object.getJSONArray("weather");
                            weather.setMainHumidity(mainObject.getDouble("humidity"));
                            weather.setMainPressure(mainObject.getDouble("pressure"));
                            weather.setMainTemp(mainObject.getDouble("temp"));
                            weather.setMainTempMax(mainObject.getDouble("temp_max"));
                            weather.setMainTempMin(mainObject.getDouble("temp_min"));
                            weather.setWeatherMain(weatherArray.getJSONObject(0).getString("main"));
                            weather.setWeatherIcon(weatherArray.getJSONObject(0).getString("icon"));
                            // weather.setWindDeg(windObject.getDouble("deg"));
                            weather.setWindSpeed(windObject.getDouble("speed"));
                            String string;
                            weather.setDate(dateFormat.parse(object.getString("dt_txt")));
                            Log.d("text", i+" "+object.getString("dt_txt"));
                            list.add(weather);
                        }

                    }

                    Log.d("apiNext",list.size()+"");
                    updatateView.updateNextDays(list);

                } catch (JSONException e) {
                    Log.d("apiNext","bi loi roi");
                    e.printStackTrace();
                } catch (ParseException e) {
                    Log.d("err", "date err ");
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });
        return list;
    }
    public interface UpdatateView{
        public void update(Weather weather);
        public void updateNextDays(ArrayList<Weather> listNextDays);
    }

}