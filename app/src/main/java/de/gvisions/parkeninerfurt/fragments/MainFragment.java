package de.gvisions.parkeninerfurt.fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.cards.ParkhausCard;
import de.gvisions.parkeninerfurt.objects.Parkhaus;
import de.gvisions.parkeninerfurt.service.JsonFetcher;

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
        createList();




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
            oList.setAdapter(new ParkhausCard(createList(), getView().getContext(), getActivity().getApplication(), this));
        }
    }

    public List<Parkhaus> createList()
    {
        List<Parkhaus> oReturn = new ArrayList<Parkhaus>();



        try {
            oReturn = new JsonFetcher().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        ParkhausCard oParkhausAdapter = new ParkhausCard(oReturn, getView().getContext(), getActivity().getApplication(), this);
        oList.setAdapter(oParkhausAdapter);
        return oReturn;

    }





}