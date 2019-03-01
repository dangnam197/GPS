package com.example.dangn.gps.Fragment;

        import android.annotation.SuppressLint;
        import android.graphics.Color;
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
        import com.github.mikephil.charting.charts.CombinedChart;
        import com.github.mikephil.charting.components.AxisBase;
        import com.github.mikephil.charting.components.XAxis;
        import com.github.mikephil.charting.components.YAxis;
        import com.github.mikephil.charting.data.CombinedData;
        import com.github.mikephil.charting.data.DataSet;
        import com.github.mikephil.charting.data.Entry;
        import com.github.mikephil.charting.data.LineData;
        import com.github.mikephil.charting.data.LineDataSet;
        import com.github.mikephil.charting.formatter.IAxisValueFormatter;
        import com.github.mikephil.charting.highlight.Highlight;
        import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
        import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.ArrayList;
        import java.util.List;

@SuppressLint("ValidFragment")
public class FragmentWeatherNextdays extends Fragment implements OnChartValueSelectedListener {
    private RecyclerView recyclerView;
    private ArrayList<Weather> list;
    private CombinedChart mChart;
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
        ve(view);
    }
    private void ve(View view){
        mChart = (CombinedChart) view.findViewById(R.id.combinedChart);
        mChart.getDescription().setEnabled(false);
        mChart.setBackgroundColor(Color.argb(57,255,255,255));
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);
        mChart.setHighlightFullBarEnabled(false);
        mChart.setOnChartValueSelectedListener(this);

        mChart.getAxisLeft().setTextColor(Color.WHITE); // left y-axis
        mChart.getXAxis().setTextColor(Color.WHITE);
        mChart.getLegend().setTextColor(Color.WHITE);
        mChart.getAxisRight().setTextColor(Color.WHITE);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f);

        final List<String> xLabel = new ArrayList<>();
        String pattern;
        DateFormat dateFormat = new SimpleDateFormat("dd/MM");
        for (int i=0;i<list.size();i++){
            xLabel.add(dateFormat.format(list.get(i).getDate()));
        }
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int) value % xLabel.size());
            }
        });

        CombinedData data = new CombinedData();
        LineData lineDatas = new LineData();
        lineDatas.addDataSet((ILineDataSet) dataChart(list));

        data.setData(lineDatas);

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);

        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
    private static DataSet dataChart(ArrayList<Weather> list) {

        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for(int i=0;i<list.size();i++){
            entries.add(new Entry(i, (int)list.get(i).getMainTemp()));
        }
        LineDataSet set = new LineDataSet(entries, "");
        set.setColor(Color.WHITE);
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.argb(150,255,255,255));
        set.setCircleRadius(5f);
        set.setFillColor(Color.WHITE);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setDrawValues(true);
        set.setValueTextSize(12f);
        set.setValueTextColor(Color.WHITE);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return set;
    }
}
