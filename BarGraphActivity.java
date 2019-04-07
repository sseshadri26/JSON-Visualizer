package com.example.sudarshanseshadri.bloombergjson;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.series.BarGraphSeries;

import java.util.ArrayList;

public class BarGraphActivity extends AppCompatActivity {

    private static final String TAG = "BarGraphActivity";
    String xAxis;
    String yAxis;
    String filter;

    BarChart graph;


    ArrayList <String> xLabelsArrayList;

    double[] yAxisVals;

    TextView xAxisView, titleView;

    VerticalLabelView yAxisView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        Intent i = getIntent();
        xAxis = i.getStringExtra("x-axis");
        yAxis = i.getStringExtra("y-axis");

        xLabelsArrayList = i.getStringArrayListExtra("x-axis labels");
        yAxisVals = i.getDoubleArrayExtra("y-axis values");


        xAxisView = findViewById(R.id.id_textView_x_axis);
        yAxisView = findViewById(R.id.id_textView_y_axis);
        titleView = findViewById(R.id.id_textView_title);
        xAxis = i.getStringExtra("x-axis");
        yAxis = i.getStringExtra("y-axis");
        xAxisView.setText(xAxis);

        yAxisView.setText(yAxis);
        yAxisView.setTextColor(xAxisView.getCurrentTextColor());

        filter = i.getStringExtra("filter");
        if (filter!=null) {
            titleView.setText(yAxis + " vs " + xAxis + "\n Filtered by " + filter);
        }
        else
        {
            titleView.setText(yAxis + " vs " + xAxis);
        }

        graph = findViewById(R.id.id_barChart);
        setUpBarGraph();
        graph.setFitBars(true);

    }

    public void setUpBarGraph()
    {

        String[] xLabels = new String[xLabelsArrayList.size()];
        xLabels = xLabelsArrayList.toArray(xLabels);


        Log.v(TAG, xLabelsArrayList.toString());

        graph.setBackgroundColor(Color.WHITE);
        graph.getDescription().setEnabled(false);

        ArrayList<BarEntry> yVals = new ArrayList<>();
        for (int i = 0; i<yAxisVals.length;i++)
        {
            yVals.add(new BarEntry(i, (float) yAxisVals[i]));
        }








        BarDataSet set=new BarDataSet(yVals, "Randoms");

        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        graph.getLegend().setEnabled(false);

        YAxis rightAxis = graph.getAxisRight();
        rightAxis.setDrawLabels(false);

        graph.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xLabels));

        XAxis xAxis = graph.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        graph.setData(data);

        graph.invalidate();

        graph.animateY(1000);

    }
}
