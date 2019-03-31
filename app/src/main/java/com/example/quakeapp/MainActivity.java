package com.example.quakeapp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks< List<Earthquake>> {

    private final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    ListView earthquakeListView;

    ArrayList<Earthquake> earthquakes ;
    EarthquakeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        earthquakeListView = (ListView) findViewById(R.id.list);

        getSupportLoaderManager().initLoader(1,null,  this).forceLoad();





    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new EarthQuakesLoader(MainActivity.this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        adapter = new EarthquakeAdapter(getApplicationContext(),earthquakes);
        earthquakeListView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Earthquake>> loader) {

    }
}
