package com.unisa.bd2.myfeeding;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class SearchProductActivity extends Fragment {

    EditText product;
    Button searchBtn;
    ArrayList<Prodotto> listResult;
    Drawable icon;
    String prod;
    FindIterable<Document> result;
    MongoCollection<Document> collection;

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
        collection = ConnectionDB.getConnection();


        product.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    doSearch();
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
                /*Block<Document> printBlock = new Block<Document>() {
                    @Override
                    public void apply(final Document document) {
                        System.out.println("DOCUMENTOOO "+document.toJson());
                    }
                };*/
            }
        });
    }

    public void doSearch(){
        listResult = new ArrayList<>();
        prod = product.getText().toString();

        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "",
                "Caricamento. Attendere...", true);

        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("product_name", new BasicDBObject("$regex", prod).append("$options", "i"));

        result = collection.find(regexQuery)
                .projection(fields(include("code", "product_name", "generic_name", "image_url")));
        long count = collection.count(regexQuery);

        if (count == 0) {
            Toast.makeText(getContext().getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_SHORT).show();
            ringProgressDialog.dismiss();
        } else {
            ringProgressDialog.setCancelable(false);
            Thread th = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (Document d : result) {
                        try {
                            listResult.add(new Prodotto(d.getLong("code"), d.getString("product_name")
                                    , d.getString("image_url"), d.getString("generic_name"), compressBitmap(downloadImage(d.getString("image_url")))));
                            System.out.println("DOCUMENTOOO " + d.getLong("code") + " " + d.getString("product_name")
                                    + " " + d.getString("image_url") + " " + d.getString("generic_name"));
                            System.out.println("DOCUMENTOOO " + d.toJson());
                            System.out.println(" \n");
                        } catch (Exception e) {
                            icon = getResources().getDrawable(R.drawable.image_not_found);
                            Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
                            listResult.add(new Prodotto(d.getLong("code"), d.getString("product_name")
                                    , d.getString("image_url"), d.getString("generic_name"), compressBitmap(bitmap)));
                            e.printStackTrace();
                        }
                    }
                    /*
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("lista", listResult);

                    Fragment fragment = new ResultSearchActivity();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction().add(R.id.content_frame, fragment).commit();
                    */

                    /*for(Prodotto p: listResult){
                        System.out.println("LISTA"+p.toString());

                    }*/

                    Intent i = new Intent(getContext(), ResultSearchActivity.class);
                    i.putParcelableArrayListExtra("lista", listResult);
                    startActivity(i);
                    //after he logic is done, close the progress dialog
                    ringProgressDialog.dismiss();
                }
            });
            th.start();
        }
    }

    public Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }


    public byte[] compressBitmap(Bitmap b){
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100,bStream);
        byte[] byteArray = bStream.toByteArray();
        return  byteArray;
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
