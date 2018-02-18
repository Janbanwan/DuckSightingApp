package com.example.j.serveri;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by J on 14.2.2018.
 */

public class Sighting implements Serializable, Comparable<Sighting> {

    private String id;
    private String duckSpecies;
    private String description;
    private String dateTime;
    private int count;

    public Sighting(){
        this.id = "1";
        this.duckSpecies = "mallard";
        this.description = "A duck, probably";
        this.dateTime = getTime();
        this.count = 1;
    }

    public Sighting(String duckSpecies, String description, int count){
        this.id = "1";
        this.duckSpecies = duckSpecies;
        this.description = description;
        this.dateTime = getTime();
        this.count = count;
    }

    public Sighting(String id, String duckSpecies, String description, String dateTime, int count){
        this.id = id;
        this.duckSpecies = duckSpecies;
        this.description = description;
        this.dateTime = dateTime;
        this.count = count;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDuckSpecies() {
        return duckSpecies;
    }

    public void setDuckSpecies(String duckspecies) {
        this.duckSpecies = duckSpecies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private String getTime(){
        Calendar aikaNyt = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String aikaString = dateFormat.format(aikaNyt.getTime());
        return aikaString;
    }

    @Override
    public int hashCode() {
        return getId().hashCode() * 31 + getDuckSpecies().hashCode() * 31 + getDescription().hashCode() * 31 + getDateTime().hashCode() * 31 + getCount() * 31 ;
    }

    @Override
    public String toString(){
        return "\nSpecies: " + getDuckSpecies() + " \nDescription: " + getDescription() + " \nDate Time: " + getDateTime() + " \nCount: " + getCount();
    }

    @Override
    public int compareTo(@NonNull Sighting sighting) {
        return 0;
    }
}
