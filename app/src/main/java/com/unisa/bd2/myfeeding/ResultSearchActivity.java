package com.unisa.bd2.myfeeding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultSearchActivity extends Fragment {

    ArrayList<Prodotto>listProduct;
    ProductAdapter adapter;
    ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.app_list_product, container, false);
        listProduct = getArguments().getParcelableArrayList("lista");
        adapter = new ProductAdapter(getContext(),R.layout.app_detail_listelement);
        listView.setAdapter(adapter);

        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        for(Prodotto p : listProduct){
            adapter.add(p);
        }

    }
}
