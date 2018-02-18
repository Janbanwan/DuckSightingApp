package com.example.j.serveri;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by J on 14.2.2018.
 */

public class ParseJson {

    ServerConnect myServ = new ServerConnect();
    ArrayList<Sighting> arrList = new ArrayList<>();


    public ArrayList<Sighting> parseJson() throws ExecutionException, InterruptedException, JSONException {
        String idukka = "id";
        String lajike = "species";
        String kuvaus = "description";
        String aika = "dateTime";
        String maara = "count";

        String jsonString = myServ.getResultti().trim();
        try{
            JSONArray jAr = new JSONArray(jsonString);
            JSONObject jo = null;

            for(int i = 0; i < jAr.length(); i++){
                jo = jAr.getJSONObject(i);

                String id = jo.getString(idukka);
                String species = jo.getString(lajike);
                String description = jo.getString(kuvaus);
                String dateTime = jo.getString(aika);
                int count = Integer.parseInt(jo.getString(maara));

                Sighting sighting = new Sighting(id,species,description,dateTime,count);

                arrList.add(sighting);

            }

        } catch (JSONException e) {
        e.printStackTrace();
        }

        return arrList;
    }

    public ArrayList<String> parseSpecies() throws ExecutionException, InterruptedException {
        String jsonSpecies = myServ.getSpecies().trim();
        ArrayList<String> lajikkeet = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(jsonSpecies);

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String spe1 = jsonObject.getString("name");
                lajikkeet.add(spe1);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lajikkeet;
    }
}
