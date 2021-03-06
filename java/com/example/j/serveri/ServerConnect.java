package com.example.j.serveri;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by J on 14.2.2018.
 */

public class ServerConnect extends AsyncTask<String, String, String> {
    private String URL_SIGHTINGS = "http://192.168.0.25:8081/sightings";
    private String URL_SPECIES = "http://192.168.0.25:8081/species";

    /*
    * Retrieving the JSON data from the server as a string.
    * Two methods at the getResultti and getSpecies
    * provided in order to easily access the String format of the result
    * */

    @Override
    public String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
            }

            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try{
                if (reader != null) {
                    reader.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public String getResultti() throws ExecutionException, InterruptedException {
        return new ServerConnect().execute(URL_SIGHTINGS).get();
    }

    public String getSpecies() throws ExecutionException, InterruptedException {
        return new ServerConnect().execute(URL_SPECIES).get();
    }
}
