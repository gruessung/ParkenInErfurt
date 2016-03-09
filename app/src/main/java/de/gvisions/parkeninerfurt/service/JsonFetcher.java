package de.gvisions.parkeninerfurt.service;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;

import de.gvisions.parkeninerfurt.objects.Parkhaus;

/**
 * Created by alex on 09.03.16.
 */
public class JsonFetcher extends AsyncTask<String, String, ArrayList<Parkhaus>> {

    final String TAG = "JsonFetcher";

    // set your json string url here
    String yourJsonStringUrl = "http://api.gruessung.eu/parken/json.php?appToken=parkenerfurt2016";

    ArrayList<Parkhaus> aResult = new ArrayList<Parkhaus>();



    // contacts JSONArray
    JSONArray dataJsonArr = null;

    @Override
    protected void onPreExecute() {}

    @Override
    protected ArrayList<Parkhaus> doInBackground(String... arg0) {

        try {

            // instantiate our json parser
            JsonParser jParser = new JsonParser();

            // get json string from url
            JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);

            // get the array of users
            dataJsonArr = json.getJSONArray("Daten");

            Parkhaus geo = new Parkhaus();
            geo.sName ="Geoportal Erfurt";
            geo.iId = 9999;
            aResult.add(geo);


            // loop through all users
            for (int i = 0; i < dataJsonArr.length(); i++) {

                JSONObject c = dataJsonArr.getJSONObject(i);

                // Storing each json item in variable
                Parkhaus item = new Parkhaus();
                item.sName = c.getString("name");
                item.iGesamt = c.getInt("gesamt");
                item.iBelegt = c.getInt("belegt");
                item.sOeffnungsstand = c.getString("oeffnungszustand");
                item.iId = c.getInt("id");

                aResult.add(item);
            }

            return aResult;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




    }





