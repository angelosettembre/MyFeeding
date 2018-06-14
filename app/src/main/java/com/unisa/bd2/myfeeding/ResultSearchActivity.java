package com.unisa.bd2.myfeeding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultSearchActivity extends Fragment {

    ArrayList<Prodotto>listProduct;
    ProductAdapter adapter;
    ListView listView;
    View view;
    TextView numResult;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.app_list_product, container,false);
        }
        else {
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }

        adapter = new ProductAdapter(getActivity(),R.layout.app_detail_listelement);

        listView = (ListView) view.findViewById(R.id.listProduct);
        numResult = view.findViewById(R.id.numResult);

        listProduct = getArguments().getParcelableArrayList("lista");
        numResult.setText(String.valueOf(listProduct.size()));


        addElement();
        listView.setAdapter(adapter);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Prodotto p = adapter.getItem(position);
                System.out.println("PRODOTTO RICEVUTO "+ p.toString());

                Bundle bundle = new Bundle();
                bundle.putParcelable("prodotto",p);

                Fragment fragment = new ProductDetails();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().add(R.id.content_frame, fragment).addToBackStack("result").commit();
            }
        });
    }

    public void addElement(){
        for(Prodotto p : listProduct){
            adapter.add(p);
        }
    }
}