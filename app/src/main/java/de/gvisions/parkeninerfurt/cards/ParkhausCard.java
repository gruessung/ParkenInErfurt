package de.gvisions.parkeninerfurt.cards;

import android.app.Application;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.gvisions.parkeninerfurt.ParkenInErfurtApplication;
import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.objects.Parkhaus;

/**
 * Created by alex on 26.02.16.
 */
public class ParkhausCard extends RecyclerView.Adapter<ParkhausCard.ItemViewHolder> {

    private List<Parkhaus> itemList;
    private Context ctx;
    private Application appl;

    public ParkhausCard(List<Parkhaus> list, Context ctx, Application appl)
    {
        this.itemList = list;
        this.ctx = ctx;
        this.appl = appl;
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
        itemViewHolder.cardTitle.setText(item.sName);
        itemViewHolder.cardDescription.setText(item.sBemerkungen);



        itemViewHolder.cardView.setTag(item.sName);




    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View viewItem = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_main_item, viewGroup, false);

        final String sName = viewItem.getTag().toString();

        ImageView btnNav = (ImageView) viewItem.findViewById(R.id.btnNav);
        btnNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ParkenInErfurtApplication) appl).getTracker().trackEvent("Parkhaus", "Navigation", sName, 1000);
                Toast.makeText(view.getContext(), "geklickt auf Navigation " + sName, Toast.LENGTH_SHORT).show();
            }
        });


        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ParkenInErfurtApplication) appl).getTracker().trackEvent("Parkhaus", "Klick auf Karte", v.getTag().toString(), 1000);



                Toast.makeText(v.getContext(), "geklickt auf Karte " + v.getTag(), Toast.LENGTH_SHORT).show();

            }
        });

        return new ItemViewHolder(viewItem);
    }






    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView cardImage;
        protected TextView cardTitle;
        protected TextView cardDescription;
        protected CardView cardView;

        public ItemViewHolder(View v)
        {
            super(v);
            cardTitle = (TextView) v.findViewById(R.id.title);
            cardDescription = (TextView) v.findViewById(R.id.description);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }


    }




}
