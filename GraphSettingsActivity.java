package com.example.sudarshanseshadri.bloombergjson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphSettingsActivity extends AppCompatActivity {

    private static final String TAG = "GraphSettingsActivity";
    Spinner graphTypeSpinner; //specify the graph type: bar, scatter, pie, etc. It will only show choices that work for this data set.
    Spinner firstInputSpinner;
    Spinner secondInputSpinner;
    TextView firstInputText;
    TextView secondInputText;

    TextView advancedSecondText;

    Spinner advancedFirstSpinner, advancedSecondSpinner;

    Button createGraph;
    ArrayList<String> fieldNames;
    ArrayList<Boolean> areFieldsNumerical;

    FullDataset fullDataset = new FullDataset();

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_settings);

        graphTypeSpinner = findViewById(R.id.id_spinner_graph_type);
        firstInputSpinner = findViewById(R.id.id_spinner_first_variable);
        secondInputSpinner = findViewById(R.id.id_spinner_second_variable);

        firstInputText = findViewById(R.id.id_textView_first_variable);
        secondInputText = findViewById(R.id.id_textView_second_variable);

        advancedFirstSpinner = findViewById(R.id.id_spinner_filter_first);
        advancedSecondSpinner = findViewById(R.id.id_spinner_filter_second);
        advancedSecondText = findViewById(R.id.id_textView_select_keyName);



        createGraph = findViewById(R.id.id_button_create_graph);


        createGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (graphTypeSpinner.getSelectedItem().toString())
                {
                    case "Bar Graph":
                    {
                        Intent i = new Intent(GraphSettingsActivity.this, BarGraphActivity.class);
                        i.putExtra("x-axis", firstInputSpinner.getSelectedItem().toString());
                        i.putExtra("y-axis", secondInputSpinner.getSelectedItem().toString());
                        i.putStringArrayListExtra("x-axis labels", fullDataset.getBarGraphXLabels( firstInputSpinner.getSelectedItem().toString()   ));  //that one in the middle is the x axis name
                        ArrayList<Double> yValArrayList = fullDataset.getBarGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString());
                        if (!advancedFirstSpinner.getSelectedItem().equals("None"))
                        {
                            yValArrayList = fullDataset.getBarGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), advancedFirstSpinner.getSelectedItem().toString(), advancedSecondSpinner.getSelectedItem().toString());
                            i.putExtra("filter", advancedSecondSpinner.getSelectedItem().toString());
                        }
                        //put in a double array - first convert the arraylist to array
                        double[] tempArray = new double[yValArrayList.size()];
                        for (int j = 0; j < tempArray.length; j++) {

                            tempArray[j] = yValArrayList.get(j);
                        }
                        i.putExtra("y-axis values", tempArray);  //that one in the middle is the y axis name


                        startActivity(i);
                    }
                    break;

                    case "Pie Chart":
                    {
                        //the words x-axis and y-axis here are just for convenience--not actual axes.

                        Intent i = new Intent(GraphSettingsActivity.this, PieChartActivity.class);
                        i.putExtra("x-axis", firstInputSpinner.getSelectedItem().toString());
                        i.putExtra("y-axis", secondInputSpinner.getSelectedItem().toString());
                        i.putStringArrayListExtra("x-axis labels", fullDataset.getBarGraphXLabels( firstInputSpinner.getSelectedItem().toString()   ));  //that one in the middle is the x axis name

                        //put in a double array - first convert the arraylist to array
                        ArrayList<Double> yValArrayList=fullDataset.getBarGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString());;
                        if (advancedFirstSpinner.getSelectedItem().toString().equals("None")) {
                            yValArrayList = fullDataset.getBarGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString());
                        }
                        else
                        {
                            //send in the inputs of all the strings and let it figure out values
                            yValArrayList = fullDataset.getBarGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), advancedFirstSpinner.getSelectedItem().toString(), advancedSecondSpinner.getSelectedItem().toString());
                            i.putExtra("filter", advancedSecondSpinner.getSelectedItem().toString());
                        }
                        double[] tempArray = new double[yValArrayList.size()];
                        for (int j = 0; j < tempArray.length; j++) {

                            tempArray[j] = yValArrayList.get(j);
                        }
                        i.putExtra("y-axis values", tempArray);  //that one in the middle is the y axis name


                        startActivity(i);

                    }
                    break;
                    case "Scatter Plot":
                    {

                        Intent i = new Intent(GraphSettingsActivity.this, ScatterPlotActivity.class);
                        i.putExtra("x-axis", firstInputSpinner.getSelectedItem().toString());
                        i.putExtra("y-axis", secondInputSpinner.getSelectedItem().toString());

                        i.putExtra("x-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), "x"));  //that one in the middle is the x axis name

                        i.putExtra("y-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), "y"));  //that one in the middle is the x axis name

                        if (advancedFirstSpinner.getSelectedItem().toString().equals("None")) {
                            i.putExtra("x-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), "x"));  //that one in the middle is the x axis name

                            i.putExtra("y-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), "y"));  //that one in the middle is the x axis name

                        }
                        else
                        {
                            //send in the inputs of all the strings and let it figure out values
                            i.putExtra("x-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), advancedFirstSpinner.getSelectedItem().toString(), advancedSecondSpinner.getSelectedItem().toString(), "x"));  //that one in the middle is the x axis name

                            i.putExtra("y-axis values", fullDataset.getNumericalGraphYValues(firstInputSpinner.getSelectedItem().toString(), secondInputSpinner.getSelectedItem().toString(), advancedFirstSpinner.getSelectedItem().toString(), advancedSecondSpinner.getSelectedItem().toString(), "y"));  //that one in the middle is the x axis name
                            i.putExtra("filter", advancedSecondSpinner.getSelectedItem().toString());
                        }
                        startActivity(i);
                    }
                    break;
                }
            }
        });


        Intent i = getIntent();
        String JSONFileText = i.getStringExtra("JSONFile");

        try {
            //this has already been checked in the previous activity
            jsonArray = new JSONArray(JSONFileText);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            parseJSONArray(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setUpGraphTypeSpinner();
        setUpOptionSpinner();
        setUpAdvancedSpinners();
    }

    private void parseJSONArray(JSONArray jsonArray) throws JSONException {
        fieldNames=new ArrayList<>();
        areFieldsNumerical=new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.v(TAG, "We're on number " + i);


            for(int j = 0; j<jsonObject.names().length(); j++){
                //some really dumb debugging stuff i decided to keep
//                Log.v(TAG, "Names arraylist:" + jsonObject.names().toString());
//                Log.v(TAG, "what j?:" + j);
//                Log.v(TAG, "what key?:" + jsonObject.names().getString(j));
                String key = jsonObject.names().getString(j);
                String val = jsonObject.get(jsonObject.names().getString(j)).toString();

                //find if each is numeric or not;
                if (fieldNames.indexOf(key)==-1)
                {
                    fieldNames.add(key);

                    if (isNumeric(val))
                    {
                        areFieldsNumerical.add(true);
                        //Log.i(TAG, " is numerical");
                    }
                    else
                    {
                        areFieldsNumerical.add(false);
                        //Log.i(TAG, val + " is not numerical");
                    }
                }
                else
                {
                    if (!isNumeric(val))
                    {
                        //Log.i(TAG, val + " is not numerical");
                        areFieldsNumerical.set(fieldNames.indexOf((key)), false);
                    }

                    else
                    {
                        //Log.i(TAG, val + " is numerical");
                    }

                }



                Log.v(TAG, "key = " + key + " value = " + val);
            }

        }
        //at the end of this, fieldNames and areFieldsNumerical should be filled correctly

        Log.v(TAG, "fieldNames:" + fieldNames.toString());
        Log.v(TAG, "areFieldsNumerical:" + areFieldsNumerical.toString());

        //Now, creating the objects.
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Log.v(TAG, "We're on number " + i);

            for (int j = 0; j < fieldNames.size(); j++) {


                String key = fieldNames.get(j);


                if (areFieldsNumerical.get(j)) //if the field associated with the key is numerical...
                {
                    Double value = 0.0;

                    try
                    {
                        value = jsonObject.getDouble(key);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }


                    fullDataset.addNumericalData(key, value);
                }

                else{ //non numerical
                    String value = "";
                    try
                    {
                        value = jsonObject.getString(key);
                    }
                    catch (Exception e)
                    {

                    }
                    fullDataset.addNonNumericalData(key, value);
                }

            }
        }
        //now, fullDataSet should be filled and ready to go!


        //
    }

    public static boolean isNumeric(String str)
    {
        if (str.equals(""))
        {
            return true;
        }
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public void setUpAdvancedSpinners()
    {



        ArrayList<String> sortByOptions = getInputVariableOptions(graphTypeSpinner.getSelectedItem().toString())[0];
        sortByOptions.add(0, "None");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortByOptions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        advancedFirstSpinner.setAdapter(dataAdapter);

        advancedFirstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (advancedFirstSpinner.getSelectedItem().toString().equals("None"))
                {
                    advancedSecondText.setText("");
                    setUpSecondAdvancedSpinner("None");
                }
                else
                {
                    advancedSecondText.setText("Select " + advancedFirstSpinner.getSelectedItem().toString());
                    setUpSecondAdvancedSpinner(advancedFirstSpinner.getSelectedItem().toString());

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setUpSecondAdvancedSpinner(String s) {
        ArrayList<String> sortByOptions = new ArrayList<>();
        sortByOptions.add("");
        if(!s.equals("None"))
        {
            sortByOptions = fullDataset.getBarGraphXLabels(s);
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortByOptions);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        advancedSecondSpinner.setAdapter(dataAdapter);
    }


    public void setUpGraphTypeSpinner()
    {



        ArrayList<String> categories = getGraphTypeOptions();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        graphTypeSpinner.setAdapter(dataAdapter);

        graphTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUpOptionSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void setUpOptionSpinner() {


        ArrayList<String>[] categories = getInputVariableOptions(graphTypeSpinner.getSelectedItem().toString());

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories[0]);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        firstInputSpinner.setAdapter(dataAdapter);

        firstInputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String>[] categories = getInputVariableOptions(graphTypeSpinner.getSelectedItem().toString());
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(GraphSettingsActivity.this, android.R.layout.simple_spinner_item, categories[1]);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                secondInputSpinner.setAdapter(dataAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Creating adapter for spinner
        dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories[1]);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        secondInputSpinner.setAdapter(dataAdapter);

        secondInputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    public ArrayList<String> getGraphTypeOptions() {

        ArrayList<String> options = new ArrayList<>();

        int nonnumericalNumber = fullDataset.getNonNumericalData().size();
        int numericalNumber = fullDataset.getNumericalData().size();

        if (numericalNumber > 0 && nonnumericalNumber > 0)
        {
            options.add("Bar Graph");
            options.add("Pie Chart");

            if (numericalNumber > 1)
            {
                options.add("Scatter Plot");
            }
        }
        else if(numericalNumber==0&&nonnumericalNumber==0)
        {
            finish();
            Toast.makeText(this, "Select a JSON Array with numerical fields.", Toast.LENGTH_LONG);
        }
        return options;
    }

    public ArrayList<String>[] getInputVariableOptions(String graphType) {

        ArrayList<String>[] options = new ArrayList[2];
        options[0]=new ArrayList<>();
        options[1]=new ArrayList<>();

        int nonnumericalNumber = fullDataset.getNonNumericalData().size();
        int numericalNumber = fullDataset.getNumericalData().size();

        if (graphType.equals("Bar Graph")) {
            firstInputText.setText("x-Axis");
            secondInputText.setText("y-Axis");
            for (NonNumericalData n : fullDataset.getNonNumericalData()) //all non numerical options for the x axis
            {
                options[0].add(n.getKey());
            }
            //all numerical options for y axis (this and the 2 for loops)

            try
            {
                options[1].add("Count of " + firstInputSpinner.getSelectedItem().toString());
            }
            catch (Exception e)
            {
                options[1].add("Count of " + options[0].get(0));
            }


            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[1].add("Total " + n.getKey());
            }

            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[1].add("Average " + n.getKey());
            }

        }




        if (graphType.equals("Pie Chart")) {
            firstInputText.setText("Labels");
            secondInputText.setText("Slices");
            for (NonNumericalData n : fullDataset.getNonNumericalData()) //all non numerical options for the x axis
            {
                options[0].add(n.getKey());
            }

            try
            {
                options[1].add("Count of " + firstInputSpinner.getSelectedItem().toString());
            }
            catch (Exception e)
            {
                options[1].add("Count of " + options[0].get(0));
            }

            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[1].add("Total " + n.getKey());
            }

            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[1].add("Average " + n.getKey());
            }

        }

        if (graphType.equals("Scatter Plot")) {
            firstInputText.setText("x-Axis");
            secondInputText.setText("y-Axis");

            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[0].add(n.getKey());
            }




            for (NumericalData n : fullDataset.getNumericalData())
            {
                options[1].add(n.getKey());
            }



        }

        return options;
    }

}
