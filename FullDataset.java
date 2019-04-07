package com.example.sudarshanseshadri.bloombergjson;



import android.util.Log;

import java.util.ArrayList;

public class FullDataset {
    ArrayList<NumericalData> numericalDataArrayList;
    ArrayList<NonNumericalData> nonNumericalDataArrayList;

    public FullDataset(ArrayList<NumericalData> numericalData, ArrayList<NonNumericalData> nonNumericalData) {
        this.numericalDataArrayList = numericalData;
        this.nonNumericalDataArrayList = nonNumericalData;
    }

    public FullDataset() {
        numericalDataArrayList=new ArrayList<>();
        nonNumericalDataArrayList=new ArrayList<>();
    }





    public ArrayList<NumericalData> getNumericalData() {
        return numericalDataArrayList;
    }
    public ArrayList<NonNumericalData> getNonNumericalData() {
        return nonNumericalDataArrayList;
    }

    public void setNumericalData(ArrayList<NumericalData> numericalData) {
        this.numericalDataArrayList = numericalData;
    }

    public void setNonNumericalData(ArrayList<NonNumericalData> nonNumericalData) {
        this.nonNumericalDataArrayList = nonNumericalData;
    }

    public void addNumericalData(NumericalData numericalData) {
        this.numericalDataArrayList.add(numericalData);
    }

    public void addNonNumericalData(NonNumericalData nonNumericalData) {
        this.nonNumericalDataArrayList.add(nonNumericalData);
    }


    //adds a numerical value to the correct NumericalData object in the ArrayList. if a number of the provided key has already been added, they will be grouped in the same NumericalData object. If not, a new NumericalData will be added.
    public void addNumericalData(String key, double data) {

        boolean keyExists=false;
        int index=0;
        for (NumericalData numericalData : numericalDataArrayList)
        {
            if (numericalData.getKey().equals(key))
            {
                keyExists=true;
                index=numericalDataArrayList.indexOf(numericalData);
            }
        }

        if (keyExists)
        {
            NumericalData currentNumericalData = numericalDataArrayList.get(index);
            currentNumericalData.addData(data);

            numericalDataArrayList.set(index, currentNumericalData);
        }
        else
        {
            NumericalData currentNumericalData = new NumericalData(key, data);
            numericalDataArrayList.add(currentNumericalData);
        }
    }


    //a non-numerical version of the last method.
    //adds a non-numerical value to the correct NonNumericalData object in the ArrayList. if a number of the provided key has already been added, they will be grouped in the same NonNumericalData object. If not, a new NonNumericalData will be added.
    public void addNonNumericalData(String key, String data) {
        boolean keyExists=false;
        int index=0;
        for (NonNumericalData nonNumericalData : nonNumericalDataArrayList)
        {
            if (nonNumericalData.getKey().equals(key))
            {
                keyExists=true;
                index=nonNumericalDataArrayList.indexOf(nonNumericalData);
            }
        }

        if (keyExists)
        {
            NonNumericalData currentNonNumericalData = nonNumericalDataArrayList.get(index);
            currentNonNumericalData.addData(data);

            nonNumericalDataArrayList.set(index, currentNonNumericalData);
        }
        else
        {
            NonNumericalData currentNonNumericalData = new NonNumericalData(key, data);
            nonNumericalDataArrayList.add(currentNonNumericalData);
        }
    }




    //returns the labels of the numerical and non-numerical keys for debugging purposes.
    @Override
    public String toString() {
        String returnString = "Numerical Data: ";
        for (NumericalData numericalData: numericalDataArrayList)
        {
            returnString+=numericalData.getKey() + ", ";
        }

        returnString += "..........Non-Numerical Data: ";
        for (NonNumericalData nonNumericalData: nonNumericalDataArrayList)
        {
            returnString+=nonNumericalData.getKey() + ", ";
        }
        return returnString;
    }

    //returns each unique value in a given key (for non-numerical values). This is not used solely for bar graphs, but it is the main use.
    public ArrayList<String> getBarGraphXLabels(String key) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
        for (NonNumericalData n : nonNumericalDataArrayList)
        {
             if(n.getKey().equals(key))
             {
                 data=n.getData();
             }
        }

        for (String s : data)
        {
            if (result.indexOf(s)==-1)
            {
                String sNew = s.equals("")? "\" \"" :s ;

                result.add(sNew);
            }
        }

        return result;

    }

    //gets y values for each bar in the bar graph based on what the user wants to graph. This method does not use the filter
    public ArrayList<Double> getBarGraphYValues(String firstSpinner, String secondSpinner) {
        ArrayList<Double> result = new ArrayList<>();
        ArrayList<String> resultStrings = new ArrayList<>();

        String arr[] = secondSpinner.split(" ", 2);

        String firstWord = arr[0];
        String theRest = arr[1];     //not including the first space

        switch(firstWord)
        {
            case "Count":
            {

                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (String s : data)
                {
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        result.add(1.0);
                    }
                    else
                    {
                        result.set(resultStrings.indexOf(s), result.get(resultStrings.indexOf(s))+1);
                    }
                }

            }//case count
            break;

            case "Total":
            {

                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                ArrayList<Double> numericalData = numericalDataArrayList.get(0).getData();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (NumericalData n : numericalDataArrayList)
                {
                    if(n.getKey().equals(theRest))
                    {
                        numericalData=n.getData();
                    }
                }

                for (int i=0;i<data.size();i++)
                {
                    String s=data.get(i);
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        Double val = (double) numericalData.get(i);
                        result.add(val);
                    }
                    else
                    {
                        Double val = (double) numericalData.get(i);
                        result.set(resultStrings.indexOf(s), result.get(resultStrings.indexOf(s))+val);
                    }
                }

            }//case total
            break;


            case "Average":
            {

                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                ArrayList<Double> numericalData = numericalDataArrayList.get(0).getData();
                ArrayList<Double> count = new ArrayList<>();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (NumericalData n : numericalDataArrayList)
                {
                    if(n.getKey().equals(theRest))
                    {
                        numericalData=n.getData();
                    }
                }

                for (int i=0;i<data.size();i++)
                {
                    String s=data.get(i);
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        Double val = (double) numericalData.get(i);
                        count.add(1.0);

                        result.add(val);
                    }
                    else
                    {
                        Double val = (double) numericalData.get(i);
                        count.set(resultStrings.indexOf(s), count.get(resultStrings.indexOf(s))+1);
                        result.set(resultStrings.indexOf(s), result.get(resultStrings.indexOf(s))+val);
                    }
                }

                for (int i=0; i < result.size();i++) //average the total values
                {
                    double average = result.get(i)/count.get(i);
                    result.set(i, average);
                }

            }//case count


            break;
        }







        return result;

    }

    //gets y values for each bar in the bar graph based on what the user wants to graph. This method uses the filter
    public ArrayList<Double> getBarGraphYValues(String firstSpinner, String secondSpinner, String firstAdvanced, String secondAdvanced) {

        ArrayList<Double> result = new ArrayList<>();
        ArrayList<String> resultStrings = new ArrayList<>();

        String arr[] = secondSpinner.split(" ", 2);

        String firstWord = arr[0];
        String theRest = arr[1];     //not including the first space

        ArrayList<String> filterData = nonNumericalDataArrayList.get(0).getData();
        for (NonNumericalData n : nonNumericalDataArrayList)
        {
            if(n.getKey().equals(firstAdvanced))
            {
                filterData=n.getData();
            }
        }

        switch(firstWord)
        {
            case "Count":
            {
                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (int i = 0 ; i < data.size() ; i++)
                {
                    String s = data.get(i);
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        if(filterData.get(i).equals(secondAdvanced)) {
                            result.add(1.0);
                        }
                        else
                        {
                            result.add(0.0);
                        }

                    }
                    else
                    {
                        if(filterData.get(i).equals(secondAdvanced)) {
                            double addedOne = result.get(resultStrings.indexOf(s))+1;
                            result.set(resultStrings.indexOf(s), addedOne);
                        }


                    }
                }

            }//case count
            break;

            case "Total":
            {

                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                ArrayList<Double> numericalData = numericalDataArrayList.get(0).getData();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (NumericalData n : numericalDataArrayList)
                {
                    if(n.getKey().equals(theRest))
                    {
                        numericalData=n.getData();
                    }
                }

                for (int i=0;i<data.size();i++)
                {
                    String s=data.get(i);
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        Double val = (double) numericalData.get(i);
                        if(filterData.get(i).equals(secondAdvanced)) {
                            result.add(val);
                        }
                        else
                        {
                            result.add(0.0);
                        }

                    }
                    else
                    {
                        Double val = (double) numericalData.get(i);
                        if(filterData.get(i).equals(secondAdvanced)) {
                            result.set(resultStrings.indexOf(s), result.get(resultStrings.indexOf(s))+val);
                        }
                        else
                        {

                        }

                    }
                }

            }//case total
            break;




            case "Average":
            {

                ArrayList<String> data = nonNumericalDataArrayList.get(0).getData();
                ArrayList<Double> numericalData = numericalDataArrayList.get(0).getData();
                ArrayList<Double> count = new ArrayList<>();
                for (NonNumericalData n : nonNumericalDataArrayList)
                {
                    if(n.getKey().equals(firstSpinner))
                    {
                        data=n.getData();
                    }
                }

                for (NumericalData n : numericalDataArrayList)
                {
                    if(n.getKey().equals(theRest))
                    {
                        numericalData=n.getData();
                    }
                }

                for (int i=0;i<data.size();i++)
                {
                    String s=data.get(i);
                    if (resultStrings.indexOf(s)==-1)
                    {
                        resultStrings.add(s);
                        Double val=0.0;
                        if(filterData.get(i).equals(secondAdvanced)) {
                            val = (double) numericalData.get(i);
                            count.add(1.0);
                        }
                        else
                        {
                            val=0.0;
                            count.add(1.0);
                        }


                        result.add(val);
                    }
                    else
                    {
                        Double val=0.0;
                        if(filterData.get(i).equals(secondAdvanced)) {
                            val = (double) numericalData.get(i);
                            count.set(resultStrings.indexOf(s), count.get(resultStrings.indexOf(s))+1);
                            result.set(resultStrings.indexOf(s), result.get(resultStrings.indexOf(s))+val);
                        }




                    }
                }

                for (int i=0; i < result.size();i++) //average the total values
                {
                    double countNew=1;
                    if (count.get(i)!=0)
                    {
                        countNew = count.get(i);
                    }
                    double average = result.get(i)/countNew;
                    result.set(i, average);
                }

            }//case average





            break;
        }







        return result;

    }

    //used to return values for both the x and y axes for a scatter plot. This method is for when there is no filter.
    public double[] getNumericalGraphYValues(String firstSpinner, String secondSpinner, String xOrY) {

        ArrayList<Double> xAxis = new ArrayList<>();
        ArrayList<Double> yAxis = new ArrayList<>();

        ArrayList<String> resultStrings = new ArrayList<>();

        NumericalData correctKeyData=numericalDataArrayList.get(0);
        for (NumericalData n: numericalDataArrayList)
        {
            if(n.getKey().equals(firstSpinner))
            {
                correctKeyData=n;
            }
        }

        for (double d : correctKeyData.getData())
        {
            xAxis.add(d);
        }

        correctKeyData=numericalDataArrayList.get(0); //y axis now
        for (NumericalData n: numericalDataArrayList)
        {
            if(n.getKey().equals(secondSpinner))
            {
                correctKeyData=n;
            }
        }

        for (double d : correctKeyData.getData())
        {
            yAxis.add(d);
        }


        if(xOrY.equals("x"))
        {
            double[] result = new double[xAxis.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = xAxis.get(i);
            }
            return result;
        }
        else
        {
            double[] result = new double[yAxis.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = yAxis.get(i);
            }
            return result;
        }




    }


    //used to return values for both the x and y axes for a scatter plot. This method is for when there is a filter. It accepts two more parameters.
    public double[] getNumericalGraphYValues(String firstSpinner, String secondSpinner, String firstAdvanced, String secondAdvanced, String xOrY) {

        ArrayList<String> filterData = nonNumericalDataArrayList.get(0).getData();
        for (NonNumericalData n : nonNumericalDataArrayList) {
            if (n.getKey().equals(firstAdvanced)) {
                filterData = n.getData();
            }
        }


        ArrayList<Double> xAxis = new ArrayList<>();
        ArrayList<Double> yAxis = new ArrayList<>();

        ArrayList<String> resultStrings = new ArrayList<>();

        NumericalData correctKeyData = numericalDataArrayList.get(0);
        for (NumericalData n : numericalDataArrayList) {
            if (n.getKey().equals(firstSpinner)) {
                correctKeyData = n;
            }
        }

        for (int i = 0; i < correctKeyData.getData().size(); i++) {
            double d = correctKeyData.getData().get(i);
            if (filterData.get(i).equals(secondAdvanced)) {
                xAxis.add(d);
            } else {
                xAxis.add(0.0);
            }


        }

        correctKeyData = numericalDataArrayList.get(0); //y axis now
        for (NumericalData n : numericalDataArrayList) {
            if (n.getKey().equals(secondSpinner)) {
                correctKeyData = n;
            }
        }

        for (int i = 0; i < correctKeyData.getData().size(); i++) {
            double d = correctKeyData.getData().get(i);

            if (filterData.get(i).equals(secondAdvanced)) {
                yAxis.add(d);
            } else {
                yAxis.add(0.0);
            }


        }

        if(xOrY.equals("x"))
        {
            double[] result = new double[xAxis.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = xAxis.get(i);
            }
            return result;
        }
        else
        {
            double[] result = new double[yAxis.size()];
            for (int i = 0; i < result.length; i++) {
                result[i] = yAxis.get(i);
            }
            return result;
        }

    }


}
