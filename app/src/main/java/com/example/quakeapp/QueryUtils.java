package com.example.quakeapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getSimpleName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<Earthquake> extractEarthquakes( String url) {
        ArrayList<Earthquake> listOfEarthquake = null;
        URL responseURL = createUrl( url);
        try{
            String theJsonResponse = makeHttpRequest(responseURL);
            listOfEarthquake = extractFeatureFromJson(theJsonResponse);

        }catch (IOException e){

        }

        if( listOfEarthquake != null)
            return listOfEarthquake;
        else
            return null;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
            Log.d(LOG_TAG,"URL CREATED SUCCESSFULLY");
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if the url is null return an empty jsonResponse
        if( url == null){

            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();

            Log.d(LOG_TAG,"URLCONNECTION OPENED");

            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            //if the status code is 200 read the stream
            if( urlConnection.getResponseCode() == 200){

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

                Log.d(LOG_TAG,jsonResponse);

            }else{

                Log.d(LOG_TAG,"Error Occurred When Connecting , Status :" + urlConnection.getResponseCode());

            }
        } catch (IOException e) {
            // TODO: Handle the exception
            Log.e(LOG_TAG,"IOException :",e);
        } finally {


            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link Earthquake} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    public static ArrayList<Earthquake> extractFeatureFromJson(String earthquakeJSON) {
        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();


        if(TextUtils.isEmpty(earthquakeJSON)){

            return null;
        }


        try {

            JSONObject root = new JSONObject(earthquakeJSON);
            JSONArray features = root.optJSONArray("features");

            for(int i = 0 ; i < features.length() ; i++){
                JSONObject earthQuake = features.getJSONObject(i);
                JSONObject properties = earthQuake.getJSONObject("properties");

                Double magnitude = properties.getDouble("mag");
                String locationAndDistance = properties.getString("place");
                Long timeUnix = properties.getLong("time");

                Date time = new Date(timeUnix);
                SimpleDateFormat dateFormat = new SimpleDateFormat("DD MMM yyyy");
                String dateToDisplay = dateFormat.format(time);

                String distance;
                String location;
                String splitString = "of";

                if( locationAndDistance.contains(splitString)){
                    String [] arraySplits = locationAndDistance.split(splitString);
                    distance = arraySplits[0] + " of";
                    location = arraySplits[1].trim();

                }else{

                    distance = "Near the";
                    location = locationAndDistance;
                }
                earthquakes.add( new Earthquake( magnitude.toString(),
                        distance, location,
                        dateToDisplay));

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return null;
    }

}

