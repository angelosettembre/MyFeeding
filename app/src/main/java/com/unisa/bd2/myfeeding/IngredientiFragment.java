package com.unisa.bd2.myfeeding;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.URL;

public class IngredientiFragment extends Fragment {

    View view;
    ImageView ingredientsImage;
    TextView ingredientsList;
    TextView sostanzeList;
    TextView additiviList;
    TextView olioList;
    TextView tracesList;

    LinearLayout fisrtLinear;
    LinearLayout secondLinear;
    LinearLayout thirdLinear;
    LinearLayout fourLinear;
    LinearLayout fiveLinear;


    Prodotto prod;
    Drawable icon;


    public IngredientiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ingredienti_fragment, container, false);

        ingredientsImage = view.findViewById(R.id.ingredientsImage);
        ingredientsList = view.findViewById(R.id.list_Ingredients);
        sostanzeList = view.findViewById(R.id.list_Sostanze);
        additiviList = view.findViewById(R.id.list_Additivi);
        olioList = view.findViewById(R.id.list_Olio);
        tracesList = view.findViewById(R.id.traces_List);

        fisrtLinear = view.findViewById(R.id.firsLinear);
        secondLinear = view.findViewById(R.id.secondLinear);
        thirdLinear = view.findViewById(R.id.thirdLinear);
        fourLinear = view.findViewById(R.id.fourLinear);
        fiveLinear = view.findViewById(R.id.fiveLinear);


        prod = getArguments().getParcelable("prodotto");

        System.out.println("INGREDIENTI FRAGMENTTT prod "+prod.toString());

        try {
            ingredientsImage.setImageBitmap(downloadImage(prod.getIngredientsImageUrl()));
        } catch (Exception e) {
            icon = getResources().getDrawable(R.drawable.image_not_found);
            Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
            ingredientsImage.setImageBitmap(((BitmapDrawable) icon).getBitmap());
            e.printStackTrace();
        }

        if(prod.getIngredients() == null){
            fisrtLinear.setVisibility(LinearLayout.GONE);
        } else {
            ingredientsList.setText(prod.getIngredients());
        }
        if(prod.getAllergens() == null){
            secondLinear.setVisibility(LinearLayout.GONE);
        } else {
            sostanzeList.setText(prod.getAllergens());
        }
        if(prod.getAdditives() == null){
            thirdLinear.setVisibility(LinearLayout.GONE);
        } else {
            additiviList.setText(prod.getAdditives());
        }
        if(prod.getIngredientPalmOil() == null){
            fourLinear.setVisibility(LinearLayout.GONE);
        } else {
            olioList.setText(prod.getIngredientPalmOil());
        }
        if(prod.getTraces() == null){
            fiveLinear.setVisibility(LinearLayout.GONE);
        } else {
            tracesList.setText(prod.getTraces());
        }

        return view;
    }

    public Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }
}
