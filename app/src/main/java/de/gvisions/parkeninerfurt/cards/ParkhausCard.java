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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        if (iFrei > (item.iGesamt / 2)) {
            itemViewHolder.cardColor.setBackgroundColor(Color.GREEN);
        } else if (iFrei > (item.iGesamt / 3)) {
            itemViewHolder.cardColor.setBackgroundColor(Color.YELLOW);
        } else {
            itemViewHolder.cardColor.setBackgroundColor(Color.RED);
        }

        if (item.iId == 9999) {
            itemViewHolder.cardColor.setVisibility(View.GONE);
            itemViewHolder.cardBelegung.setText("");
            itemViewHolder.cardBelegung.setVisibility(View.GONE);
            itemViewHolder.cardTrend.setVisibility(View.GONE);
            itemViewHolder.cardTitle.setTextSize(15);
            itemViewHolder.cardTitle.setText("Datenquelle: Geoportal Erfurt");
            itemViewHolder.cardView.setTag(9999);


        } else {
            itemViewHolder.cardTitle.setText(item.sName);
            itemViewHolder.cardBelegung.setText("Belegt: "+item.iBelegt+"/"+item.iGesamt+"\nFrei: "+iFrei+"\nStatus: "+item.sOeffnungsstand);
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
                    final String name = item.sName;
                    AlertDialog.Builder localBuilder = new AlertDialog.Builder(ctx);
                    localBuilder.setTitle(item.sName);
                    localBuilder.setMessage("Hier sollen dann die restlichen Parkhausinfos stehen");
                    localBuilder.setPositiveButton("Navigation", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(v.getContext(), "geklickt auf Navigation", Toast.LENGTH_SHORT).show();
                            ((ParkenInErfurtApplication) appl).getTracker().trackEvent("Parkhaus", "Navigation", name, 0);
                        }
                    }).setIcon(R.drawable.ic_action_info_outline);
                    localBuilder.create().show();


                    Toast.makeText(v.getContext(), "geklickt auf Karte " + item.sName, Toast.LENGTH_SHORT).show();
                }



            }
        });

        return new ItemViewHolder(viewItem);
    }






    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView cardImage;
        protected TextView cardTitle;
        protected TextView cardBelegung;
        protected CardView cardView;
        protected ImageView cardTrend;
        protected LinearLayout cardColor;

        public ItemViewHolder(View v)
        {
            super(v);
            cardTitle = (TextView) v.findViewById(R.id.title);
            cardTrend = (ImageView) v.findViewById(R.id.btnTrend);
            cardBelegung = (TextView) v.findViewById(R.id.belegung);
            cardView = (CardView) v.findViewById(R.id.card_view);
            cardColor = (LinearLayout) v.findViewById(R.id.cardColor);
        }


    }




}
