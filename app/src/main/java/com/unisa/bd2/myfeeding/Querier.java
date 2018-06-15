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
import com.mongodb.client.MongoCursor;

import org.bson.Document;

import java.net.URL;

import static com.mongodb.client.model.Filters.eq;

public class Querier {
    static MongoCollection<Document> collection = ConnectionDB.getConnection();
    public static Prodotto prodottoFound = null;

    public static Prodotto queryTheDB(long productBarcode, Context currentContext, final Resources currentResources) {

        int count=0;

        BasicDBObject regexQuery = new BasicDBObject();
        //regexQuery.put("code", new BasicDBObject("$regex", productBarcode).append("$options", "i"));

        MongoCursor<Document> cursor = collection.find(eq("code",productBarcode)).iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println("QUERYYYYYYYYYYYYYYYY"+cursor.next().toJson());
                count++;
            }
        } finally {
            cursor.close();
        }


        FindIterable<Document> result = collection.find(eq("code", productBarcode));

        System.out.println("COUNT " + count);
        if (count == 0) {
            prodottoFound = new Prodotto();
            prodottoFound.setProductName("");
            Toast.makeText(currentContext.getApplicationContext(), "PRODOTTO NON TROVATO", Toast.LENGTH_SHORT).show();
            System.out.println("Count " + count);
        } else {
            Toast.makeText(currentContext.getApplicationContext(), "PRODOTTO TROVATO", Toast.LENGTH_SHORT).show();
            Drawable icon;
            for (Document d : result) {
                System.out.println("First Document " + d.toJson());
                try {
                    prodottoFound = new Prodotto(d.getLong("code"),
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
                            d.getString("cocoa_100g"));
                } catch (Exception e) {
                    icon = currentResources.getDrawable(R.drawable.image_not_found);
                    prodottoFound = new Prodotto(d.getLong("code"),
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
                            d.getString("cocoa_100g"));
                    e.printStackTrace();
                }
            }

        }
        return prodottoFound;
    }

    public static Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }

}

