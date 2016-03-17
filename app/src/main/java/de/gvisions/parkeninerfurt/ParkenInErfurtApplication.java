package de.gvisions.parkeninerfurt;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;

import java.net.MalformedURLException;

import eu.inloop.easygcm.GcmListener;

/**
 * Created by alexa on 28.02.2016.
 */
public class ParkenInErfurtApplication extends Application implements GcmListener {
    private static final String TAG = "GCM";
    private Tracker mPiwikTracker;

    public synchronized Tracker getTracker() {
        if (mPiwikTracker != null) {
            return mPiwikTracker;
        }

        try {
            mPiwikTracker = Piwik.getInstance(this).newTracker("http://stat.gruessung-online.de/piwik.php", 17);
        } catch (MalformedURLException e) {
            Log.w("PIWIK LOCKER TAG", "url is malformed", e);
            return null;
        }

        return mPiwikTracker;
    }

    @Override
    public void onMessage(String from, Bundle data) {
        Log.v(TAG, "### message from: " + from);
        Log.v(TAG, "### data: " + from);
        for (String key : data.keySet()) {
            Log.v(TAG, "> " + key + ": " + data.get(key));
        }
        NotificationManager mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher,
                data.getString("message"), System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this
        // notification
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(getApplicationContext(), "Parken in Erfurt", data.getString("message"),
                contentIntent);

        // Send the notification.
        mNM.notify("Parken in Erfurt", 0, notification);

    }

    @Override
    public void sendRegistrationIdToBackend(String registrationId) {
        Log.v(TAG, "### registration id: " + registrationId);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://api.gruessung.eu/parken/gcmRegistration.php?id="+registrationId;
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.v(TAG, "### gcmRegistration Response: " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v(TAG, "### Fehler");
            }
        });
        queue.add(stringRequest);

    }
}
