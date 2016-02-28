package de.gvisions.parkeninerfurt.fragments;

import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.gvisions.parkeninerfurt.BuildConfig;
import de.gvisions.parkeninerfurt.R;
import de.gvisions.parkeninerfurt.cards.ParkhausCard;

/**
 * Created by alexa on 28.02.2016.
 */
public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View v = getView();

        TextView versionText = (TextView) v.findViewById(R.id.textView2);
        versionText.setText("Version: "+BuildConfig.VERSION_NAME);
    }

}
