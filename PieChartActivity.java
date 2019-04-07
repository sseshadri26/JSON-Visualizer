package com.example.sudarshanseshadri.bloombergjson;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PieChartActivity extends AppCompatActivity {

    private static final double PERCENT_LIMIT = 3;
    PieChart graph;

    String xAxis;
    String yAxis;

    TextView titleView;
    String filter;



    ArrayList <String> xLabelsArrayList;

    double[] yAxisVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);


        titleView = findViewById(R.id.id_textView_title);
        Intent i = getIntent();
        xAxis = i.getStringExtra("x-axis");
        yAxis = i.getStringExtra("y-axis");

        filter = i.getStringExtra("filter");
        if (filter!=null) {
            titleView.setText(yAxis + " vs " + xAxis + "\n Filtered by " + filter);
        }
        else
        {
            titleView.setText(yAxis + " vs " + xAxis);
        }


        xLabelsArrayList = i.getStringArrayListExtra("x-axis labels");
        yAxisVals = i.getDoubleArrayExtra("y-axis values");


        graph = findViewById(R.id.id_pie_chart);

        setUpPieChart();
    }


    public void setUpPieChart()
    {

        graph.setBackgroundColor(Color.WHITE);
        graph.setDrawHoleEnabled(false);
        graph.getDescription().setEnabled(false);

        ArrayList<PieEntry> values = new ArrayList<>();

        double totalValue=0;
        for (int i=0;i<yAxisVals.length;i++)
        {
            totalValue+=yAxisVals[i];
        }


        for (int i=0;i<yAxisVals.length;i++)
        {
            if (yAxisVals[i]/totalValue*100<PERCENT_LIMIT)
            {
                values.add(new PieEntry((float)yAxisVals[i], ""));
            }
            else
            {
                values.add(new PieEntry((float)yAxisVals[i], xLabelsArrayList.get(i)));
            }

        }




        PieDataSet dataSet = new PieDataSet(values, "Randoms");
//        dataSet.setSelectionShift(5f);
//        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        dataSet.setValueLinePart1OffsetPercentage(80.f);
//        dataSet.setValueLinePart1Length(0.2f);
//        dataSet.setValueLinePart2Length(0.4f);


        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data=new PieData(dataSet);

        data.setValueFormatter(new MyValueFormatter());

        graph.setUsePercentValues(true);

        graph.getLegend().setEnabled(false);
        graph.setData(data);

        graph.invalidate();

        graph.animateY(1000);

    }

    public class MyValueFormatter implements IValueFormatter{ //to hide all percent below 5%

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,###,##0.0");
        }


        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            if(value < PERCENT_LIMIT) return "";
            else return mFormat.format(value) + " %";
        }
    }
}
