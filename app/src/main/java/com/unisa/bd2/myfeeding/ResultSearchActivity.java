package com.unisa.bd2.myfeeding;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class ResultSearchActivity extends Activity{

    ArrayList<Prodotto>listProduct;
    ProductAdapter adapter;
    ListView listView;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_product);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        actionBar.setTitle("MY_FEEDING");

        adapter = new ProductAdapter(this,R.layout.app_detail_listelement);

        listView = (ListView) findViewById(R.id.listProduct);

        Intent i = getIntent();

        listProduct = i.getParcelableArrayListExtra("lista");

        for(Prodotto p: listProduct){
            System.out.println("LISTA"+p.toString());
            p.setImageProduct(BitmapFactory.decodeByteArray(p.getImageByte(),0, p.getImageByte().length));
        }

        addElement();
        listView.setAdapter(adapter);
    }

    public void addElement(){
        for(Prodotto p : listProduct){
            adapter.add(p);
        }
    }
}

/*
public class ResultSearchActivity extends Fragment {

    ArrayList<Prodotto>listProduct;
    ProductAdapter adapter;
    ListView listView;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null)
        {
            view = inflater.inflate(R.layout.app_list_product, container,false);
        }
        else
        {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        adapter = new ProductAdapter(getActivity(),R.layout.app_detail_listelement);

        listView = (ListView) view.findViewById(R.id.listProduct);

        listProduct = getArguments().getParcelableArrayList("lista");
        addElement();
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    public void addElement(){
        for(Prodotto p : listProduct){
            adapter.add(p);
        }
    }
}
*/
