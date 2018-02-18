package com.example.j.serveri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    Button btnHit;
    Button display;
    ArrayList<Sighting> sightings = new ArrayList<>();
    ParseJson pj = new ParseJson();
    JSONObject joob = new JSONObject();
    TextView tvIntro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHit = (Button) findViewById(R.id.btnHit);
        display = (Button) findViewById(R.id.buttonDisplay);
        tvIntro = (TextView) findViewById(R.id.tvIntro);

        changeActivities();
        addSighting();

        /*
        * Calling the parseJson method to populate the arraylist Sightings with entries from the server
        * */

        try {
            sightings = pj.parseJson();
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        /*
        * Receive the Sighting object created by the addSighting activity, then add the object to sightings array list acting as our repository.
        * After that we create a JSON Object from the Sighting object and call the sendPost method to post the JSON to the server
        * */

        Intent i = getIntent();
        try{
            Bundle bun = i.getBundleExtra("Bun");
            Sighting sight = (Sighting) bun.getSerializable("jou");
            sightings.add(sight);

            joob = createJson(sight.getId(),sight.getDuckSpecies(),sight.getDescription(),sight.getDateTime(),sight.getCount());
            sendPost();

        }catch (NullPointerException | JSONException e){
            Log.d("tag","tyhja");
        }


    }



    private void changeActivities(){

        /*
        * Send the sighting Arraylist to DisplayActivity
        * */

        display.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, DisplayActivity.class);
                Bundle bun = new Bundle();
                bun.putSerializable("jou",(Serializable)sightings);
                i.putExtra("Bun",bun);
                startActivity(i);
            }
        });
    }

    /*
    * Open up the addSighting popup activity
    * */
    private void addSighting(){
        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(MainActivity.this, addSighting.class);
                startActivity(ii);
            }
        });
    }

    /*
    * Creating JSONObjects from normal Sightings objects
    * */

    private JSONObject createJson(String id,String lajik,String kuvau,String aik,int maar) throws JSONException {
        JSONObject jO = new JSONObject();

        jO.put("id", id);
        jO.put("species", lajik);
        jO.put("description", kuvau);
        jO.put("dateTime", aik);
        jO.put("count", maar);

        return jO;

    }

    /*
    * sendPost method creates a new thread and attempts a Post request to the server
    * Could not get the post request working using a Async task implementation, as was used in the ServerConnect class, so that's why this method is
    * sort of misplaced in the MainActivity
    * */

    private void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.0.25:8081/sightings");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(joob.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
                    Log.i("MSG" , conn.getResponseMessage());

                    conn.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}





