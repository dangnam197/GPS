package com.example.dangn.gps.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangn.gps.Model.CityWeather;
import com.example.dangn.gps.R;

import java.util.ArrayList;

public class AdapterCityWeather extends RecyclerView.Adapter<AdapterCityWeather.ViewHoder>{
    private ArrayList<CityWeather> list;
    private LayoutInflater layoutInflater;
    private onClickListener onClickListener;
    public AdapterCityWeather(Context context,ArrayList<CityWeather> listWeatherCity) {
        this.list = listWeatherCity;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterCityWeather.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_cityweather, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCityWeather.ViewHoder holder, int position) {
        holder.tvName.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvName;
        public ViewHoder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_cityweather);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(onClickListener!=null){
                onClickListener.itemClick(v,getAdapterPosition(),list.get(getAdapterPosition()));
            }
        }
    }
    public void setOnclickListener(onClickListener onclickListener){
        this.onClickListener = onclickListener;
    }
    public interface onClickListener{
        public void itemClick(View v,int poisition,CityWeather cityWeather);
    }
}
