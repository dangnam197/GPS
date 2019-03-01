package com.example.dangn.gps.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangn.gps.R;

import java.util.ArrayList;

public class AdapterWeatherCurrent extends RecyclerView.Adapter<AdapterWeatherCurrent.ViewHoder>{
    private ArrayList<String> listName;
    private ArrayList<String> listValue;
    private LayoutInflater layoutInflater;
    public AdapterWeatherCurrent(Context context,ArrayList<String> listName, ArrayList<String> listValue) {
        this.listName = listName;
        this.listValue = listValue;
        layoutInflater = layoutInflater.from(context);
    }

    @NonNull
    @Override
    public AdapterWeatherCurrent.ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_weather, parent, false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterWeatherCurrent.ViewHoder holder, int position) {
        if(listName.size()>position){
            holder.tvName.setText(listName.get(position));
        }
        if(listValue.size()>position) {
            holder.tvValue.setText(listValue.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listName.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView tvName,tvValue;
        public ViewHoder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.item_weather_name);
            tvValue = (TextView) itemView.findViewById(R.id.item_weather_value);
        }
    }

}
