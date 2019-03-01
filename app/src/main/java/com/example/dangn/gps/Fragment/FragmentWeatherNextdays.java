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
        import com.example.dangn.gps.Adapter.AdapterWeatherNextdays;
        import com.example.dangn.gps.Model.Weather;
        import com.example.dangn.gps.R;

        import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class FragmentWeatherNextdays extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Weather> list;
    public FragmentWeatherNextdays(ArrayList<Weather> listWearther) {
        this.list = listWearther;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_nextdays,container,false);
        init(view);
        return view;
    }

    private void init(View view){
        recyclerView = view.findViewById(R.id.rv_weather_nextdays);
        AdapterWeatherNextdays adapterWeatherNextdays = new AdapterWeatherNextdays(getContext(),list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapterWeatherNextdays);
    }


}
