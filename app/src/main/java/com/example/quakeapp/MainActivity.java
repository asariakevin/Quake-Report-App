package com.example.quakeapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    ListView earthquakeListView;

    ArrayList<Earthquake> earthquakes ;
    EarthquakeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new EarthquakeTask().execute(USGS_REQUEST_URL);

    }

    private class EarthquakeTask extends AsyncTask<String,Void, List<Earthquake>>{

        @Override
        protected List<Earthquake> doInBackground(String... strings) {
                earthquakes = QueryUtils.extractEarthquakes(strings[0]);
                Log.d("BACKGROUND","COMPLETE");
                return earthquakes;
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakesList) {
            // Find a reference to the {@link ListView} in the layout
            earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            adapter = new EarthquakeAdapter(getApplicationContext(),earthquakesList);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
        }
    }
}
