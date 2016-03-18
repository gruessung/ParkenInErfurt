package de.gvisions.parkeninerfurt;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import de.gvisions.parkeninerfurt.fragments.AboutFragment;
import de.gvisions.parkeninerfurt.fragments.MainFragment;
import de.madcyph3r.materialnavigationdrawer.MaterialNavigationDrawer;
import de.madcyph3r.materialnavigationdrawer.activity.MaterialNavHeadItemActivity;
import de.madcyph3r.materialnavigationdrawer.head.MaterialHeadItem;
import de.madcyph3r.materialnavigationdrawer.listener.MaterialSectionChangeListener;
import de.madcyph3r.materialnavigationdrawer.menu.MaterialMenu;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSection;
import de.madcyph3r.materialnavigationdrawer.menu.item.section.MaterialItemSectionFragment;
import de.madcyph3r.materialnavigationdrawer.menu.item.style.MaterialItemDevisor;
import de.madcyph3r.materialnavigationdrawer.tools.RoundedCornersDrawable;
import eu.inloop.easygcm.EasyGcm;

/**
 * Created by alex on 26.02.16.
 */
public class MainActivity extends MaterialNavHeadItemActivity {


    private MaterialNavigationDrawer drawer = null;
    private String newTitle = "Parken in Erfurt";



    @Override
    public void onResume() {
        super.onResume();

    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //Toast.makeText(this, "Sorry, Bildschirm drehen geht no", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected boolean finishActivityOnNewIntent() {
        return false;
    }

    @Override
    protected int getNewIntentRequestCode(Class clazz) {
        return 0;
    }

    @Override
    public void init(final Bundle savedInstanceState) {

        EasyGcm.setLoggingLevel(EasyGcm.Logger.LEVEL_WARNING);
        EasyGcm.init(this);

        drawer = this;

        SharedPreferences localSharedPreferences = getSharedPreferences("de.gvisions.parkeninerfurt", 0);

        Object localObject = "";
        try
        {
            String str = String.valueOf(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
            localObject = str;
            if (!Boolean.valueOf(localSharedPreferences.getBoolean("version_" + (String)localObject, false)).booleanValue())
            {

                WebView wv = new WebView(this);
                wv.loadUrl("http://api.gruessung.eu/parken/whatsNew.php");
                wv.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);

                        return true;
                    }
                });

                AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setTitle("Neu in Version " + (String)localObject);
                localBuilder.setView(wv);
                localBuilder.setPositiveButton("Woohoo!", null);
                localBuilder.setIcon(R.drawable.ic_action_info_outline);
                localBuilder.create().show();
                localSharedPreferences.edit().putBoolean("version_" + (String)localObject, true).commit();

            }

            localObject = str;
            if (!Boolean.valueOf(localSharedPreferences.getBoolean("abfrageErfurt", false)).booleanValue())
            {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setTitle("Erlaube mir eine kurze Info :)");
                localBuilder.setMessage("Die App sammelt anonym Daten, welche Parkhäuser wie oft angeklickt werden.\nEs werden von dir keine Daten übertragen oder gespeichert.\n\nEine kurze Frage zum Schluss, kommst du aus Erfurt oder von außerorts").setPositiveButton("Ich komme aus Erfurt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        String url = "http://api.gruessung.eu/parken/statistik.php?id=erfurtJa";
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("STATISTIK", "### Fehler");
                            }
                        });
                        queue.add(stringRequest);
                    }
                });
                localBuilder.setNegativeButton("Ich komme von außerorts", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                        String url ="http://api.gruessung.eu/parken/statistik.php?id=erfurtNein";
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.v("STATISTIK", "### Fehler");
                            }
                        });
                        queue.add(stringRequest);
                    }
                });
                localBuilder.create().show();
                localSharedPreferences.edit().putBoolean("abfrageErfurt", true).commit();

            }


        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
            while (true)
                localNameNotFoundException.printStackTrace();
        }

        // create menu
        MaterialMenu menu = new MaterialMenu();
        menu.add(new MaterialItemSectionFragment(this, "Parkhäuser", new MainFragment(), "Parkhäuser"));
        menu.add(new MaterialItemDevisor());
        menu.add(new MaterialItemSectionFragment(this, "Über", new AboutFragment(), "Über"));

        drawer.setSectionChangeListener(new MaterialSectionChangeListener() {
            @Override
            public void onBeforeChangeSection(MaterialItemSection newSection) {

            }

            @Override
            public void onAfterChangeSection(MaterialItemSection newSection) {
                newTitle = newSection.getTitle();
                afterInit(savedInstanceState);
            }
        });

        // create Head Item
        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_maps_directions);
        final RoundedCornersDrawable drawableAppIcon = new RoundedCornersDrawable(getResources(), bitmap);
        MaterialHeadItem headItem = new MaterialHeadItem(this, "Parken in Erfurt", "Hallo!", null, R.drawable.erfurt, menu);
        this.addHeadItem(headItem);

        this.setActionBarOverlay(false);

        // load menu
        this.loadMenu(getCurrentHeadItem().getMenu());




        // load the first MaterialItemSectionFragment from the menu
        this.loadStartFragmentFromMenu(getCurrentHeadItem().getMenu());

        Log.d("INIT", "INIT OK");

    }

    @Override
    public void afterInit(Bundle savedInstanceState) {
        android.support.v7.app.ActionBar actionBar = this.getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF33B5E5")));
        Log.d("AFTERINIT", "AFTERINIT OK");
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        afterInit(savedInstanceState);
    }




}
