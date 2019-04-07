package com.example.sudarshanseshadri.bloombergjson;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ScatterPlotActivity extends AppCompatActivity {


    private static final double PERCENT_LIMIT = 3;
    ScatterChart graph;

    String xAxis;
    String yAxis;

    String filter;


    double[] xAxisVals;

    double[] yAxisVals;

    TextView xAxisView, titleView;

    VerticalLabelView yAxisView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scatter_plot);

        Intent i = getIntent();
        xAxis = i.getStringExtra("x-axis");
        yAxis = i.getStringExtra("y-axis");

        xAxisVals = i.getDoubleArrayExtra("x-axis values");
        yAxisVals = i.getDoubleArrayExtra("y-axis values");

        xAxisView = findViewById(R.id.id_textView_x_axis);
        yAxisView = findViewById(R.id.id_textView_y_axis);
        titleView = findViewById(R.id.id_textView_title);

        xAxis = i.getStringExtra("x-axis");
        yAxis = i.getStringExtra("y-axis");
        xAxisView.setText(xAxis);

        yAxisView.setText(yAxis);
        yAxisView.setTextColor(xAxisView.getCurrentTextColor());

        yAxis = i.getStringExtra("y-axis");
        filter = i.getStringExtra("filter");
        if (filter!=null) {
            titleView.setText(yAxis + " vs " + xAxis + "\n Filtered by " + filter);
        }
        else
        {
            titleView.setText(yAxis + " vs " + xAxis);
        }



        graph = findViewById(R.id.id_scatter_plot);

        setUpChart();
    }


    public void setUpChart() {

        graph.setTouchEnabled(true);
        graph.setDragEnabled(true);
        graph.setScaleEnabled(true);

        graph.setBackgroundColor(Color.WHITE);

        ArrayList<Entry> values = new ArrayList<>();


        for (int i = 0; i < yAxisVals.length; i++) {
            values.add(new Entry((float) yAxisVals[i], (float) xAxisVals[i]));

        }


        ScatterDataSet dataSet = new ScatterDataSet(values, "Randoms");
        dataSet.setDrawValues(false);

        dataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        dataSet.setScatterShapeSize(8f);
        dataSet.setColor(Color.BLACK);

        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);


        ScatterData data = new ScatterData(dataSet);

        CustomMarkerView mv = new CustomMarkerView(this, R.layout.custom_marker_view_layout);

        graph.getDescription().setEnabled(false);

        graph.setMarker(mv);



        graph.getLegend().setEnabled(false);
        graph.setData(data);

        graph.invalidate();

        graph.animateY(100);

    }


    public class CustomMarkerView extends MarkerView {

        private TextView tvContent;
        public CustomMarkerView (Context context, int layoutResource) {
            super(context, layoutResource);
            // this markerview only displays a textview
            tvContent = (TextView) findViewById(R.id.tvContent);
        }

        // callbacks everytime the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            tvContent.setText(e.getX()+ ", " + e.getY());

            // this will perform necessary layouting
            super.refreshContent(e, highlight);
        }

        private MPPointF mOffset;

        @Override
        public MPPointF getOffset() {

            if(mOffset == null) {
                // center the marker horizontally and vertically
                mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
            }

            return mOffset;
        }
    }


}


