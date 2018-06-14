package com.unisa.bd2.myfeeding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

import org.bson.Document;

import java.net.URL;

import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;

public class Querier {
    public static Prodotto ProdottoFound;

    public static boolean queryTheDB(String productBarcode, Context currentContext, final Resources currentResources) {
        final MongoCollection<Document> collection = ConnectionDB.getConnection();


        String prod;
        prod = productBarcode;

//         ProgressDialog ringProgressDialog = ProgressDialog.show(currentActivity, "",
//                "Caricamento. Attendere...", true);

        BasicDBObject regexQuery = new BasicDBObject();
        regexQuery.put("product_name", new BasicDBObject("$regex", prod).append("$options", "i"));

        final FindIterable<Document> result = collection.find(regexQuery)
                .projection(fields(include("code", "product_name", "generic_name", "image_url", "nutrion_grade_fr", "quantity",
                        "brands", "ingredients_text", "image_ingredients_url", "allergens", "image_nutrion_url", "allergens",
                        "image_nutrion_url","energy_100g","fat_100g")));
        long count = collection.count(regexQuery);
        /*
        if (count == 0) {
            Toast.makeText(currentContext.getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_SHORT).show();
//            ringProgressDialog.dismiss();
        } else {
//            ringProgressDialog.setCancelable(false);
            Thread th = new Thread(new Runnable() {


                @Override
                public void run() {
                    Drawable icon;
                    Prodotto prodottoResult;
                    Document d = result.first();
                    try {
                        prodottoResult = new Prodotto(d.getLong("code"), d.getString("product_name")
                                , d.getString("image_url"), d.getString("generic_name"), downloadImage(d.getString("image_url")), d.getString("quantity"),
                                d.getString("brand"), d.getString("ingredients"), d.getString("ingredientsImageUrl"), d.getString("allergens"),
                                d.getString("imageNutritionUrl"), d.getString("energy100"), d.getString("fat100"), d.getString("carbohydrates100"),
                                d.getString("sugars100"), d.getString("fiber100"), d.getString("proteins100"), d.getString("salt100"),
                                d.getString("sodium"), d.getString("nutrionScore"));
                        System.out.println("DOCUMENTOOO " + d.getLong("code") + " " + d.getString("product_name")
                                + " " + d.getString("image_url") + " " + d.getString("generic_name"));
                        System.out.println("DOCUMENTOOO " + d.toJson());
                        System.out.println(" \n");
                        Querier.returnResult(prodottoResult);
                    } catch (Exception e) {
                        icon = currentResources.getDrawable(R.drawable.image_not_found);
                        prodottoResult = new Prodotto(d.getLong("code"), d.getString("product_name")
                                , d.getString("image_url"), d.getString("generic_name"), ((BitmapDrawable) icon).getBitmap(), d.getString("quantity"),
                                d.getString("brand"), d.getString("ingredients"), d.getString("ingredientsImageUrl"), d.getString("allergens"),
                                d.getString("imageNutritionUrl"), d.getString("energy100"), d.getString("fat100"), d.getString("carbohydrates100"),
                                d.getString("sugars100"), d.getString("fiber100"), d.getString("proteins100"), d.getString("salt100"),
                                d.getString("sodium"), d.getString("nutrionScore"));
                        Querier.returnResult(prodottoResult);
                        e.printStackTrace();
                    }
                }
                //after he logic is done, close the progress dialog

            });
            th.start();

        }
        */
        return true;
    }

    public static synchronized void returnResult(Prodotto result) {
        Querier.ProdottoFound = result;
    }

    public static Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }

}

