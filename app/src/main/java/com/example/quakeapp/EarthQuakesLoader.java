package com.example.quakeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakesLoader extends AsyncTaskLoader<List<Earthquake>> {

    String url = null;


    public EarthQuakesLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }


    public List<Earthquake> loadInBackground() {
        List<Earthquake> earthquakesList = new ArrayList<>();

        earthquakesList = QueryUtils.extractEarthquakes(url);
        Log.d("BACKGROUND","COMPLETE");
        return earthquakesList;

    }


}
