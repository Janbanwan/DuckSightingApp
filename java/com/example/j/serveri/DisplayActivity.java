package com.example.j.serveri;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by J on 16.2.2018.
 */

public class DisplayActivity  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private ListView lista;
    private Spinner spinnerSort;
    private TextView tvTitle;
    private ArrayList<Sighting> sightings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        lista = (ListView) findViewById(R.id.lista);
        spinnerSort = (Spinner) findViewById(R.id.spinnerSort);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        Intent i = getIntent();
        Bundle bun = i.getBundleExtra("Bun");
        sightings = (ArrayList<Sighting>) bun.getSerializable("jou");

        ArrayAdapter<CharSequence> aa = ArrayAdapter.createFromResource(this, R.array.valinnat, android.R.layout.simple_spinner_dropdown_item);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(aa);

        spinnerSort.setOnItemSelectedListener(this);

    }

    /*
    * Listening for the spinner choice in order to correctly sort the arraylist containing the Sightings
    * */

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        String asc = "Ascending";
        String desc = "Descending";
        String item = parent.getItemAtPosition(pos).toString();
        if(item.equals("Ascending")){
            sortListAsc();

            ListAdapter janneAdapteri = new ArrayAdapter<Sighting>(this, android.R.layout.simple_list_item_1, sightings);
            lista.setAdapter(janneAdapteri);

        }else{
            sortListDesc();

            ListAdapter janneAdapteri = new ArrayAdapter<Sighting>(this, android.R.layout.simple_list_item_1, sightings);
            lista.setAdapter(janneAdapteri);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        Log.d("tag","jj");
    }


    public void sortListAsc(){
        Comparator dateComparator = new Comparator<Sighting>(){
            @Override
            public int compare(Sighting o1, Sighting o2){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date o1Date = null;
                Date o2Date = null;
                try{
                    o1Date = sf.parse(o1.getDateTime());
                    o2Date = sf.parse(o2.getDateTime());
                }catch (ParseException e){
                    e.printStackTrace();
                }

                return o1Date.compareTo(o2Date);
            }
        };

        Collections.sort(sightings,dateComparator);
    }

    public void sortListDesc(){
        Comparator dateComparator = new Comparator<Sighting>(){
            @Override
            public int compare(Sighting o1, Sighting o2){
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date o1Date = null;
                Date o2Date = null;
                try{
                    o1Date = sf.parse(o1.getDateTime());
                    o2Date = sf.parse(o2.getDateTime());
                }catch (ParseException e){
                    e.printStackTrace();
                }

                return o2Date.compareTo(o1Date);
            }
        };

        Collections.sort(sightings,dateComparator);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
