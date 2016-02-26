package de.gvisions.parkeninerfurt.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getView();






        oList = (RecyclerView) v.findViewById(R.id.cardList);
        oList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(v.getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        oList.setLayoutManager(llm);

        ParkhausCard oParkhausAdapter = new ParkhausCard(createList(), v.getContext());
        oList.setAdapter(oParkhausAdapter);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (oList != null) {
            oList.removeAllViews();
            oList.setAdapter(new ParkhausCard(createList(), getView().getContext()));
        }
    }

    private List<Parkhaus> createList()
    {
        List<Parkhaus> oReturn = new ArrayList<Parkhaus>();




            Parkhaus item = new Parkhaus();
            item.sName = "Domplatz";
            item.sBemerkungen = "Plätze: 100/200\nÖffnunungsstand: offen\nKosten: 1,50€ je Stunde";
            oReturn.add(item);

            Parkhaus item2 = new Parkhaus();
            item2.sName = "Forum 1";
            item2.sBemerkungen = "Plätze: 100/200\nÖffnunungsstand: offen\nKosten: 1,50€ je Stunde";
            oReturn.add(item);




        return oReturn;

    }




}