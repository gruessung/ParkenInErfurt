package de.gvisions.parkeninerfurt;

import android.app.Application;
import android.util.Log;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.Tracker;

import java.net.MalformedURLException;

/**
 * Created by alexa on 28.02.2016.
 */
public class ParkenInErfurtApplication extends Application {

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
}
