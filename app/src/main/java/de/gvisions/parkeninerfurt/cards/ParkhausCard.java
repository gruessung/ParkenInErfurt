package de.gvisions.parkeninerfurt.cards;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import de.gvisions.parkeninerfurt.ParkenInErfurtApplication;
import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.fragments.MainFragment;
import de.gvisions.parkeninerfurt.objects.Parkhaus;

/**
 * Created by alex on 26.02.16.
 */
public class ParkhausCard extends RecyclerView.Adapter<ParkhausCard.ItemViewHolder> {

    private List<Parkhaus> itemList;
    private Context ctx;
    private Application appl;
    private MainFragment main;

    public ParkhausCard(List<Parkhaus> list, Context ctx, Application appl, MainFragment main)
    {
        this.itemList = list;
        this.ctx = ctx;
        this.appl = appl;
        this.main = main;
    }

    @Override
    public int getItemCount()
    {
        return itemList.size();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i)
    {
        Parkhaus item = itemList.get(i);


        //Farbe errechnen
        int iFrei = item.iGesamt - item.iBelegt;
        if (iFrei > (item.iGesamt / 3)) {
            itemViewHolder.cardColor.setBackgroundColor(Color.GREEN);
        } else if (iFrei > (item.iGesamt / 4)) {
            itemViewHolder.cardColor.setBackgroundColor(Color.YELLOW);
        } else {
            itemViewHolder.cardColor.setBackgroundColor(Color.RED);
        }
        Log.d("TENDENZ2", item.sTendenz);
        if (item.sTendenz.contains("gleich")) {
            itemViewHolder.cardTrend.setImageResource(R.drawable.ic_action_trending_neutral);
            Log.d("TENDENZ", "gleichbleibend");
        } else if (item.sTendenz.contains("fall")) {
            itemViewHolder.cardTrend.setImageResource(R.drawable.ic_action_trending_down);
            Log.d("TENDENZ", "falled");
        } else {
            itemViewHolder.cardTrend.setImageResource(R.drawable.ic_action_trending_up);
            Log.d("TENDENZ", "steigend");
        }

        if (item.iId == 9999) {
            itemViewHolder.cardColor.setVisibility(View.GONE);
            itemViewHolder.cardTrend.setVisibility(View.GONE);
            itemViewHolder.cardTitle.setTextSize(15);
            itemViewHolder.cardTitle.setText("Datenquelle: Geoportal Erfurt");
            itemViewHolder.cardView.setTag(9999);


        } else {
            itemViewHolder.cardTitle.setText(item.sName);
            itemViewHolder.cardView.setTag(i);
        }






    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View viewItem = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_main_item, viewGroup, false);




        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {





                if (Integer.parseInt(v.getTag().toString()) == 9999) {
                    String url = "http://geoportal.erfurt.de";
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    ctx.startActivity(i);
                } else {
                    Parkhaus item = itemList.get(Integer.parseInt(v.getTag().toString()));
                    ((ParkenInErfurtApplication) appl).getTracker().trackEvent("Parkhaus", "Klick auf Karte", item.sName, 0);

                    RequestQueue queue = Volley.newRequestQueue(ctx);
                    String url ="http://api.gruessung.eu/parken/statistik.php?id="+item.iId;
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


                    final String name = item.sName;
                    int iFrei = item.iGesamt - item.iBelegt;
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(ctx);
                    localBuilder.setTitle(item.sName);
                    localBuilder.setMessage("Belegt: " + item.iBelegt + "/" + item.iGesamt + "\nFrei: " + iFrei + "\nStatus: " + item.sOeffnungsstand+"\nTendenz: "+item.sTendenz);
                    localBuilder.setPositiveButton("Okay", null);

                    /*
                    localBuilder.setPositiveButton("Navigation", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "geklickt auf Navigation", Toast.LENGTH_SHORT).show();
                            ((ParkenInErfurtApplication) appl).getTracker().trackEvent("Parkhaus", "Navigation", name, 0);
                        }
                    }).*/
                    localBuilder.setIcon(R.drawable.ic_action_info_outline);
                    localBuilder.create().show();


                    //Toast.makeText(v.getContext(), "geklickt auf Karte " + item.sName, Toast.LENGTH_SHORT).show();
                }



            }
        });

        return new ItemViewHolder(viewItem);
    }






    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView cardImage;
        protected TextView cardTitle;
        protected CardView cardView;
        protected ImageView cardTrend;
        protected LinearLayout cardColor;

        public ItemViewHolder(View v)
        {
            super(v);
            cardTitle = (TextView) v.findViewById(R.id.title);
            cardTrend = (ImageView) v.findViewById(R.id.btnTrend);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardColor = (LinearLayout) v.findViewById(R.id.cardColor);
        }


    }




}
