package com.unisa.bd2.myfeeding;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MongoClient mongoClient = new MongoClient("34.207.149.93",27017);

        System.out.println("COLLEGATO ");

        MongoDatabase db = mongoClient.getDatabase("Food");

        MongoCollection<Document> collection = db.getCollection("ItalianFood");

        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println("RECORD "+cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }


    }
}
