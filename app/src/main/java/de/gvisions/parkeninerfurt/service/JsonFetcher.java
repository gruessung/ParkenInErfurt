package de.gvisions.parkeninerfurt.service;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import de.gvisions.parkeninerfurt.objects.Parkhaus;

/**
 * Created by alex on 09.03.16.
 */


public class JsonFetcher {

}
/*
    String json = "";
    JSONObject jObj;
    static String TAG = "JSON";
    JSONArray dataJsonArr = null;
    ArrayList<Parkhaus> aResult = new ArrayList<Parkhaus>();


    public ArrayList<Parkhaus> fetch(Context ctx) {


        RequestQueue rq = Volley.newRequestQueue(ctx);
        JsonArrayRequest jReq = new JsonArrayRequest("http://api.gruessung.eu/parken/json.php",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Parkhaus> result = new ArrayList<Parkhaus>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                result.add(convertParkhaus(response
                                        .getJSONObject(i)));
                            } catch (JSONException e) {
                            }
                        }
                        adpt.setItemList(result);
                        adpt.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error

            }
        });

        rq.add(jReq);



    }

    public Parkhaus convertParkhaus(JSONObject c) {
        // Storing each json item in variable
        Parkhaus item = new Parkhaus();
        try {
            item.sName = c.getString("name");
            item.iGesamt = c.getInt("gesamt");
            item.iBelegt = c.getInt("belegt");
            item.sOeffnungsstand = c.getString("oeffnungszustand");
            item.iId = c.getInt("id");
            Log.d("TEST", item.sName);
        } catch (JSONException e) {
        }
        return item;
    }
}


/*
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(ctx);

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://api.gruessung.eu/parken/json.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {



                            // Display the first 500 characters of the response string.
                            json = response;
                            Log.d("JSON-RESULT", response);
                            // get json string from url
                            try {
                            JSONObject json = new JSONObject(response);
                            // get the array of users
                            dataJsonArr = json.getJSONArray("Daten");
                            Parkhaus geo = new Parkhaus();
                            geo.sName ="Geoportal Erfurt";
                            geo.iId = 9999;
                            aResult.add(geo);

                            // loop through all users
                            for (int i = 0; i < dataJsonArr.length(); i++) {

                            }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("JSON-FEHLER", e.getMessage());
                                Log.d("JSON-FEHLER", e.getLocalizedMessage());
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.d("FEHLER", e.getMessage());
                                Log.d("FEHLER", e.getLocalizedMessage());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("JSON", "Fehler beim Holen der Datei");
                }
            });
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        Log.d("aResult", String.valueOf(aResult.size()));
        return aResult;
    }


    public interface CallBack {
        void onSuccess(ArrayList<Parkhaus> aReturn);

        void onFail(String msg);
    }

}





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

*/



