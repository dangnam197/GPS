package com.example.dangn.gps.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dangn.gps.Model.Weather;
import com.example.dangn.gps.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AdapterWeatherNextdays  extends RecyclerView.Adapter<AdapterWeatherNextdays.ViewHoder>{
    private ArrayList<Weather> listWeather;
    private LayoutInflater layoutInflater;
    private Context context;
    private DateFormat dateFormat;
    public AdapterWeatherNextdays(Context context, ArrayList<Weather> listWeather) {
        this.listWeather = listWeather;
        layoutInflater = layoutInflater.from(context);
        this.context = context;
        dateFormat = new SimpleDateFormat("dd/MM");
    }

    @NonNull
    @Override
    public AdapterWeatherNextdays.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_weather_nextdays, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWeatherNextdays.ViewHoder holder, int position) {
        holder.tvMain.setText(listWeather.get(position).getWeatherMain());
        holder.tvTemp.setText((int)listWeather.get(position).getMainTemp()+"°");
        holder.tvDate.setText(dateFormat.format(listWeather.get(position).getDate()));
        Glide.with(context).load("http://openweathermap.org/img/w/"+listWeather.get(position).getWeatherIcon()+".png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return listWeather.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvDate,tvMain,tvTemp;
        ImageView imageView;
        public ViewHoder(View itemView) {
            super(itemView);
            tvMain = (TextView) itemView.findViewById(R.id.tv_ndays_weather_main);
            tvTemp = (TextView) itemView.findViewById(R.id.tv_ndays_temp);
            tvDate = (TextView) itemView.findViewById(R.id.tv_ndays_date);
            imageView = (ImageView) itemView.findViewById(R.id.img_ndays_icon) ;
        }
    }

}