package com.unisa.bd2.myfeeding;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SommarioFragment extends Fragment {


    ImageView imageProductView;
    ImageView nutriScoreView;
    TextView nameprductView;
    TextView genericNameView;
    TextView barcodeView;
    TextView quantityView;
    TextView brandsView;

    View view;
    Prodotto prod;

    public SommarioFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.sommario_fragment, container, false);

        imageProductView = view.findViewById(R.id.imgProduct);
        nutriScoreView = view.findViewById(R.id.nutriScore);
        nameprductView = view.findViewById(R.id.titleProduct);
        genericNameView = view.findViewById(R.id.genericNameProduct);
        barcodeView = view.findViewById(R.id.valueBarcode);
        quantityView = view.findViewById(R.id.valueQuantity);
        brandsView = view.findViewById(R.id.valueBrands);

        prod = getArguments().getParcelable("prodotto");

        System.out.println("SOMMARIO FRAGMENTTT prod "+prod.toString());


        imageProductView.setImageBitmap(prod.getImageProduct());

        System.out.println("NUTRITIONSCORE "+prod.getNutritionScore());

        String value = prod.getNutritionScore();

        if(prod.getNutritionScore()==null){
            nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.image_not_found));
        }

        else {
            if(value.equals("a")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_a));
            }
            else if(value.equals("b")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_b));
            }
            else if(value.equals("c")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_c));
            }
            else if(value.equals("d")){
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_d));
            }
            else if(value.equals("e")) {
                nutriScoreView.setImageDrawable(getResources().getDrawable(R.drawable.nutriscore_e));
            }
        }

        nameprductView.setText(prod.getProductName());
        genericNameView.setText(prod.getGenericName());
        barcodeView.setText(String.valueOf(prod.getBarcode()));
        quantityView.setText(prod.getQuantity());
        brandsView.setText(prod.getBrand());


        return view;

    }




}
