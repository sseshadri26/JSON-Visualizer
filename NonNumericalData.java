package com.example.sudarshanseshadri.bloombergjson;

import java.util.ArrayList;

public class NonNumericalData {

    String key;

    ArrayList<String> data;

    public NonNumericalData(String key, ArrayList<String> data) {
        this.key = key;
        this.data = data;
    }

    public NonNumericalData(String key) {
        this.key = key;
        data = new ArrayList<>();
    }

    public NonNumericalData(String key, String data) {
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

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public void addData(String data) {
        this.data.add(data);
    }
}
