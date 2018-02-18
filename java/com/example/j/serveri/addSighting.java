package com.example.j.serveri;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by J on 17.2.2018.
 */

public class addSighting extends Activity {
    Button btnCancel;
    Button btnAdd;
    TextView tvTitle;
    EditText etSpecies;
    EditText etDescription;
    EditText etCount;
    ParseJson pj = new ParseJson();
    ArrayList<String> lajikkeet = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_sighting);

        btnCancel = (Button) findViewById(R.id.btnCancel);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        etSpecies = (EditText) findViewById(R.id.etSpecies);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etCount = (EditText) findViewById(R.id.etCount);

        /**
         * Creating a popup activity for adding new Sightings
         */


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.8));

        /*
        * Parse the species into an arraylist in order to compare for valid entries
        * */
        try {
            lajikkeet = pj.parseSpecies();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        /*
        * Close the activity incase used changes their mind
        * */

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                * If the species matches the species provided by the server, a new Sighting object is created and passed to the MainActivity,
                * otherwise the user is provided with valid option for species.
                 *
                 * Would have probably been better just to implement a spinner with the valid species.
                * */

                String id = "0";
                String lajike = etSpecies.getText().toString();
                if(lajikkeet.contains(lajike.toLowerCase())){
                    String kuvaus = etDescription.getText().toString();
                    String aika = getTime();
                    int maara = Integer.parseInt(etCount.getText().toString());

                    Sighting sight = new Sighting(id,lajike,kuvaus,aika,maara);
                    Log.d("tag",sight.toString());

                    Intent i = new Intent(addSighting.this, MainActivity.class);
                    Bundle bun = new Bundle();
                    bun.putSerializable("jou",(Serializable)sight);
                    i.putExtra("Bun",bun);
                    startActivity(i);
                }else{
                    Toast.makeText(addSighting.this, "Please provide an acceptable species: \nMallard, Redhead, Gadwall, Canvasback or Lesser scaup", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private String getTime(){
        Calendar aikaNyt = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String aikaString = dateFormat.format(aikaNyt.getTime());
        return aikaString;
    }
}
