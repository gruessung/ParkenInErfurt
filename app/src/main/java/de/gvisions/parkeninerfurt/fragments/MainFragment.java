package de.gvisions.parkeninerfurt.fragments;

import android.app.Application;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.cards.ParkhausCard;
import de.gvisions.parkeninerfurt.objects.Parkhaus;

/**
 * Created by alex on 26.02.16.
 */
public class MainFragment extends Fragment {


    RecyclerView oList;
    Application appl;
    MainFragment main;
    SwipeRefreshLayout swipeRefreshLayout;
   // ProgressBar spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getView();

        appl = getActivity().getApplication();
        main = this;





      //  spinner = (ProgressBar) v.findViewById(R.id.progressBar1);
      //  spinner.setVisibility(View.GONE);


        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i("Refresh", "onRefresh called from SwipeRefreshLayout");

                        createList(main, false);
                    }
                }
        );

        oList = (RecyclerView) v.findViewById(R.id.cardList);
        oList.setHasFixedSize(true);
      //  oList.setVisibility(View.VISIBLE);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oList.setLayoutManager(llm);
        createList(this, true);




    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (oList != null) {
            oList.removeAllViews();
            oList.setAdapter(new ParkhausCard(createList(this, true), getView().getContext(), getActivity().getApplication(), this));
        }
    }

    public List<Parkhaus> createList(final MainFragment t, Boolean bIndicator)
    {
        if (bIndicator) {
            swipeRefreshLayout.setRefreshing(true);
        }

        List<Parkhaus> oReturn = new ArrayList<Parkhaus>();

        RequestQueue rq = Volley.newRequestQueue(getContext());
        JsonArrayRequest jReq = new JsonArrayRequest("http://api.gruessung.eu/parken/json.php",
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        List<Parkhaus> result = new ArrayList<Parkhaus>();

                        Parkhaus geo = new Parkhaus();
                        geo.sName ="Geoportal Erfurt";
                        geo.iId = 9999;
                        result.add(geo);
                        int z = 0;

                        for (int i = 0; i < response.length(); i++) {
                            z++;
                            try {
                                result.add(convertParkhaus(response
                                        .getJSONObject(i)));
                            } catch (JSONException e) {
                            }
                        }

                        if (z == 0) {
                            AlertDialog.Builder localBuilder = new AlertDialog.Builder(main.getContext());
                            localBuilder.setTitle("Oops...");
                            localBuilder.setMessage("Hast du eine aktive Internetverbindung?\n\nLeider konnten keine Daten geladen werden.").setPositiveButton("Nochmal probieren", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    createList(main, true);
                                }
                            });
                            localBuilder.setNegativeButton("Abbruch", null);
                            localBuilder.create().show();
                        }

                        ParkhausCard oParkhausAdapter = new ParkhausCard(result, t.getContext(), appl, t);
                        oList.setAdapter(oParkhausAdapter);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(main.getContext());
                localBuilder.setTitle("Oops...");
                localBuilder.setMessage("Hast du eine aktive Internetverbindung?\n\nLeider konnten keine Daten geladen werden.").setPositiveButton("Nochmal probieren", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createList(main, true);
                    }
                });
                localBuilder.setNegativeButton("Abbruch", null);
                localBuilder.create().show();

            }
        });

        rq.add(jReq);



            swipeRefreshLayout.setRefreshing(false);


        return oReturn;

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
            item.sTendenz = "nix";
            item.sTendenz = c.getString("tendenz");
            Log.d("TEST", item.sTendenz);
        } catch (JSONException e) {
        }
        return item;
    }



}