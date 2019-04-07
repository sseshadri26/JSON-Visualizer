package com.example.sudarshanseshadri.bloombergjson;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {


    final String TAG = "TAG_MainActivity";
    final int CHOOSE_FILE_REQUEST_CODE = 1;


    Button chooseButton;
    ImageView chooseImage;

    JSONArray jsonArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        chooseButton=findViewById(R.id.id_button_choose_file);

        chooseImage=findViewById(R.id.id_imageView);


        //both the image and the button, when clicked on, will pomt the user to select a json file from their file manager

        chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("*/*");      //all files
                intent.setType("application/json");   //allow user to select only json files--some other types may also be selectable though.
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), CHOOSE_FILE_REQUEST_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // If the device doesn't have a file viewer
                    Toast.makeText(MainActivity.this, "Please install a File Manager.", Toast.LENGTH_LONG).show();
                }
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("*/*");      //all files
                intent.setType("application/json");   //allow user to select only json files--some other types may also be selectable though.
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), CHOOSE_FILE_REQUEST_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // If the device doesn't have a file viewer
                    Toast.makeText(MainActivity.this, "Please install a File Manager.", Toast.LENGTH_LONG).show();
                }
            }
        });




    }




    //to receive the JSON file that the user has selected and retrieve the text. If valid, go to the next activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        switch(requestCode)
        {
            case CHOOSE_FILE_REQUEST_CODE:
            {
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String mimeType = getContentResolver().getType(uri);
                    //find the file's MIME type -- to find, again, if this really is a json file.
                    if (mimeType.equals("application/json"))
                    {
                        try {
                            InputStream inputStream = getContentResolver().openInputStream(uri);

                            //scanner iterate over tokens in the stream, so the beggining token of \A is the only one it goes over.
                            Scanner s = new Scanner(inputStream).useDelimiter("\\A");

                            String JSONFileText="";
                            if (s.hasNext())
                            {
                                JSONFileText=s.next();
                            }
                            s.close();
                            checkForValidJSON(JSONFileText);


                            Intent i = new Intent(this, GraphSettingsActivity.class);
                            i.putExtra("JSONFile", JSONFileText);
                            startActivity(i);


                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            //just in case
                            Toast.makeText(MainActivity.this,"Please choose a valid JSON array", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this,"Please choose a valid JSON array", Toast.LENGTH_SHORT).show();
                        }


                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,"Please choose a valid JSON array", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        }
    }

    public void checkForValidJSON(String JSONFileText) throws JSONException
    {

        // Checking if it can be a valid JSON array.

        jsonArray=new JSONArray(JSONFileText);




    }


}
