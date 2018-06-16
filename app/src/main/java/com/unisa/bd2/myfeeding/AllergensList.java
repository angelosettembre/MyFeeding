package com.unisa.bd2.myfeeding;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AllergensList extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> bah;
    private FloatingActionButton addAllergen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.allerges_fragment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        addAllergen = view.findViewById(R.id.fab);

        addAllergen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Aggiungi Allergene");
                final Set<String> choices = new HashSet<>();
                choices.addAll(Arrays.asList("Lattosio", "Glutine", "Soia", "Arachidi"));
                if (AllergensPersistence.getSize() != 0) {
                    choices.removeAll(AllergensPersistence.loadPreferences());
                }
                final String[] types = choices.toArray(new String[choices.size()]);
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        bah.add(types[which]);
                        AllergensPersistence.insertString(types[which]);
                        mAdapter.notifyDataSetChanged();

                    }

                });

                b.show();
            }
        });
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        bah = new ArrayList<>();
        // specify an adapter (see also next example)
        Set<String> preferencesStored = AllergensPersistence.loadPreferences();
        if (preferencesStored != null) {
            bah.addAll(preferencesStored);
        }
        mAdapter = new Allergens_Adapter(bah, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

}
