package com.unisa.bd2.myfeeding;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchProductActivity extends Fragment {

    EditText product;
    Button searchBtn,categoryBtn;
    FloatingActionButton advancedbtn;
    ArrayList<Prodotto> listResult;
    Drawable icon;
    String prod;
    FindIterable<Document> result;
    MongoCollection<Document> collection;
    String[] items = {"Latte", "Glutine", "Soia", "Arachidi"};
    List<Integer> selectedItems = new ArrayList<>();
    String[] listCategories;
    String selectedCategories;


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
        advancedbtn = view.findViewById(R.id.fab_advanced_search);
        categoryBtn = view.findViewById(R.id.categorybtn);
        listCategories = getResources().getStringArray(R.array.category_item);

        product.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    doSearch(false,false);
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch(false,false);
            }
        });

        advancedbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean[] checkedItems = {false, false, false, false};
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                        if (isChecked) {
                            selectedItems.add(indexSelected);
                        } else if (selectedItems.contains(indexSelected)) {
                            selectedItems.remove(Integer.valueOf(indexSelected));
                        }
                    }
                }).setTitle("Ricerca per allergene").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        doSearch(true,false);
                    }
                }).show();
            }
        });

        categoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setSingleChoiceItems(listCategories, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        selectedCategories = listCategories[i];
                        System.out.println("CATEOGRIAAAA SCELTA "+selectedCategories);

                    }
                }).setTitle("Seleziona una categoria").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        doSearch(false,true);
                    }
                }).show();
            }
        });

    }

    public void doSearch(boolean advanced,boolean categorySearch) {
        long count;
        listResult = new ArrayList<>();
        prod = product.getText().toString();
        //|| prod.matches("[0-9]+")
        if (prod.matches("[a-zA-Z0-9_ ]+") || advanced || categorySearch) {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "",
                    "Caricamento. Attendere...", true);

            BasicDBObject regexQuery = new BasicDBObject();
            if (!advanced && !categorySearch) {
                regexQuery.put("product_name", new BasicDBObject("$regex", prod).append("$options", "i"));
            } else if(advanced) {
                regexQuery.put("allergens", new BasicDBObject("$regex", generateSearchString()).append("$options", "i"));
            }else if(categorySearch){
                regexQuery.put("categories", new BasicDBObject("$regex", selectedCategories).append("$options", "i"));

            }

            result = collection.find(regexQuery);
            count = collection.count(regexQuery);

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
                                listResult.add(new Prodotto(d.getLong("code"),
                                        d.getString("product_name"),
                                        d.getString("image_url"),
                                        d.getString("generic_name"),
                                        downloadImage(d.getString("image_url")),
                                        d.getString("quantity"),
                                        d.getString("brands"),
                                        d.getString("ingredients_text"),
                                        d.getString("image_ingredients_url"),
                                        d.getString("allergens"),
                                        d.getString("image_nutrition_url"),
                                        d.getString("energy_100g"),
                                        d.getString("fat_100g"),
                                        d.getString("carbohydrates_100g"),
                                        d.getString("sugars_100g"),
                                        d.getString("fiber_100g"),
                                        d.getString("proteins_100g"),
                                        d.getString("salt_100g"),
                                        d.getString("sodium_100g"),
                                        d.getString("nutrition_grade_fr"),
                                        d.getString("additives_en"),
                                        d.getString("ingredients_from_palm_oil_tags"),
                                        d.getString("traces"),
                                        d.getString("serving_size"),
                                        d.getString("saturated-fat_100g"),
                                        d.getString("cocoa_100g")));

                                System.out.println("DOCUMENTOOO " + d.getLong("code") + " " + d.getString("product_name")
                                        + " " + d.getString("image_url") + " " + d.getString("generic_name"));
                                System.out.println("DOCUMENTOOO " + d.toJson());
                                System.out.println(" \n");
                            } catch (Exception e) {
                                icon = getResources().getDrawable(R.drawable.image_not_found);
                                Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
                                listResult.add(new Prodotto(d.getLong("code"),
                                        d.getString("product_name"),
                                        d.getString("image_url"),
                                        d.getString("generic_name"),
                                        ((BitmapDrawable) icon).getBitmap(),
                                        d.getString("quantity"),
                                        d.getString("brands"),
                                        d.getString("ingredients_text"),
                                        d.getString("image_ingredients_url"),
                                        d.getString("allergens"),
                                        d.getString("image_nutrition_url"),
                                        d.getString("energy_100g"),
                                        d.getString("fat_100g"),
                                        d.getString("carbohydrates_100g"),
                                        d.getString("sugars_100g"),
                                        d.getString("fiber_100g"),
                                        d.getString("proteins_100g"),
                                        d.getString("salt_100g"),
                                        d.getString("sodium_100g"),
                                        d.getString("nutrition_grade_fr"),
                                        d.getString("additives_en"),
                                        d.getString("ingredients_from_palm_oil_tags"),
                                        d.getString("traces"),
                                        d.getString("serving_size"),
                                        d.getString("saturated-fat_100g"),
                                        d.getString("cocoa_100g")));
                                e.printStackTrace();
                            }
                        }


                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("lista", listResult);

                        Fragment fragment = new ResultSearchActivity();
                        fragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().add(R.id.content_frame, fragment).addToBackStack("search").commit();


                        //after he logic is done, close the progress dialog
                        ringProgressDialog.dismiss();
                    }
                });
                th.start();
            }
        }
    }

    private String generateSearchString() {
        String searchString = "";
        for (Integer i : selectedItems) {
            if (items[i].equals("Glutine")) {
                searchString += Arrays.toString(AllergensController.glutine).replace("[", "")
                        .replace("]", "")
                        .replace(",", "|") + "|";
            } else if (items[i].equals("Latte")) {
                searchString += Arrays.toString(AllergensController.lattosio).replace("[", "")
                        .replace("]", "")
                        .replace(",", "|") + "|";
            } else if (items[i].equals("Arachidi")) {
                searchString += Arrays.toString(AllergensController.arachidi).replace("[", "")
                        .replace("]", "")
                        .replace(",", "|") + "|";
            } else if (items[i].equals("Soia")) {
                searchString += Arrays.toString(AllergensController.soia).replace("[", "")
                        .replace("]", "")
                        .replace(",", "|") + "|";
            } else {
            }

        }
        searchString = searchString.substring(0, searchString.length() - 1).replace(" ", "");
        System.out.println("SEARCH_STRING " + searchString);
        return searchString;
    }

    public Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
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
