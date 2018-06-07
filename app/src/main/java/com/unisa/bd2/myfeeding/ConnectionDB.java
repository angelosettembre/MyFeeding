package com.unisa.bd2.myfeeding;

import android.os.StrictMode;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class ConnectionDB {


    public static MongoCollection<Document> getConnection(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        MongoClient mongoClient = new MongoClient("34.207.149.93",27017);

        System.out.println("COLLEGATO ");

        MongoDatabase db = mongoClient.getDatabase("Food");

        MongoCollection<Document> collection = db.getCollection("ItalianFood");

        return collection;

    }

}
