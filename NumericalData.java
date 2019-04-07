package com.example.sudarshanseshadri.bloombergjson;

import java.util.ArrayList;

public class NumericalData {
    String key;

    ArrayList<Double> data;

    public NumericalData(String key, ArrayList<Double> data) {
        this.key = key;
        this.data = data;
    }

    public NumericalData(String key) {
        this.key = key;
        data = new ArrayList<>();
    }

    public NumericalData(String key, double data) {
        this.key = key;
        this.data = new ArrayList<>();
        this.data.add(data);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public void addData(double data) {
        this.data.add(data);
    }

}
