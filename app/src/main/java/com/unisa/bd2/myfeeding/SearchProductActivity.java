package com.unisa.bd2.myfeeding;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchProductActivity extends Fragment {

    EditText product;
    Button searchBtn;
    ArrayList<Prodotto>listResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_search_product, container, false);
        return viewGroup;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        product = view.findViewById(R.id.search_bar);
        searchBtn = view.findViewById(R.id.search_btn);
        final MongoCollection<Document> collection = ConnectionDB.getConnection();



        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listResult = new ArrayList<>();
                String prod = product.getText().toString();
                Toast.makeText(getContext().getApplicationContext(), "RICERCA PRODOTTO..."+prod, Toast.LENGTH_SHORT).show();

                /*Block<Document> printBlock = new Block<Document>() {
                    @Override
                    public void apply(final Document document) {
                        System.out.println("DOCUMENTOOO "+document.toJson());
                    }
                };*/

                BasicDBObject regexQuery = new BasicDBObject();
                regexQuery.put("product_name", new BasicDBObject("$regex", prod).append("$options", "i"));


                FindIterable<Document>result = collection.find(regexQuery)
                        .projection(fields(include("code", "product_name", "generic_name","image_url")));
                long count = collection.count(regexQuery);

                if(count == 0){
                    Toast.makeText(getContext().getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (Document d : result) {
                        listResult.add(new Prodotto(d.getLong("code"),d.getString("product_name")
                                ,d.getString("image_url"),d.getString("generic_name")));
                        System.out.println("DOCUMENTOOO " + d.getLong("code") + " " + d.getString("product_name")
                                +" "+d.getString("image_url")+" "+d.getString("generic_name"));
                        System.out.println("DOCUMENTOOO " + d.toJson());
                        System.out.println(" \n");
                    }
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lista",listResult);

                    Fragment fragment = new ResultSearchActivity();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.content_frame,fragment).commit();

                }



                // collection.find(eq("product_name" ,text(prod))).forEach(printBlock);
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
