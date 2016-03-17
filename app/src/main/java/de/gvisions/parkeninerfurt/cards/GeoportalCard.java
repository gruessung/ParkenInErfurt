package de.gvisions.parkeninerfurt.cards;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.fragments.MainFragment;

/**
 * Created by alex on 09.03.16.
 */
public class GeoportalCard extends RecyclerView.Adapter<GeoportalCard.ItemViewHolder> {

    private Context ctx;
    private Application appl;
    private MainFragment main;

    public GeoportalCard(Context ctx, Application appl, MainFragment main)
    {
        this.ctx = ctx;
        this.appl = appl;
        this.main = main;
    }



    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i)
    {

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View viewItem = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_main_geoportal, viewGroup, false);

        return new ItemViewHolder(viewItem);
    }






    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        protected ImageView refreshBtn;


        public ItemViewHolder(View v)
        {
            super(v);

        }


    }

}
