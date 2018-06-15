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
import android.widget.TextView;

import java.net.URL;

public class TabellaFragment extends Fragment {

    final double kCalValue = 4.184;

    View view;
    ImageView tableIngredientImage;
    TextView portionText;

    TextView energyValue;
    TextView energyPortion;
    TextView grassValue;
    TextView grassPortion;
    TextView grassSaturiValue;
    TextView grassSaturiPortion;
    TextView carboidratoValue;
    TextView carboidratoPortion;
    TextView zuccheriValue;
    TextView zuccheriPortion;
    TextView proteineValue;
    TextView proteinePortion;
    TextView sodioValue;
    TextView sodioPortion;
    TextView saleValue;
    TextView salePortion;
    TextView mineraliValue;
    TextView mineraliPortion;
    TextView cacaoValue;
    TextView cacaoPortion;

    Prodotto prod;
    Drawable icon;


    public TabellaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tabella_fragment, container, false);

        tableIngredientImage = view.findViewById(R.id.nutritionImage);
        portionText = view.findViewById(R.id.portionText);

        energyValue = view.findViewById(R.id.energyVal);
        energyPortion = view.findViewById(R.id.energyPortion);
        grassValue = view.findViewById(R.id.grassValue);
        grassPortion = view.findViewById(R.id.grassPortion);
        grassSaturiValue = view.findViewById(R.id.grassSaturiValue);
        grassSaturiPortion = view.findViewById(R.id.grassSaturiPortion);
        carboidratoValue = view.findViewById(R.id.carboidratoValue);
        carboidratoPortion = view.findViewById(R.id.carboidratoPortion);
        zuccheriValue = view.findViewById(R.id.zuccheriValue);
        zuccheriPortion = view.findViewById(R.id.zuccheriPortion);
        proteineValue = view.findViewById(R.id.proteineValue);
        proteinePortion = view.findViewById(R.id.proteinePortion);
        sodioValue = view.findViewById(R.id.sodioValue);
        sodioPortion = view.findViewById(R.id.sodioPortion);
        saleValue = view.findViewById(R.id.saleValue);
        salePortion = view.findViewById(R.id.salePortion);
        mineraliValue = view.findViewById(R.id.mineraliValue);
        mineraliPortion = view.findViewById(R.id.mineraliPortion);
        cacaoValue = view.findViewById(R.id.cacaoValue);
        cacaoPortion = view.findViewById(R.id.cacaoPortion);

        prod = getArguments().getParcelable("prodotto");

        System.out.println("TABELLA FRAGMENTTT prod " + prod.toString());

        try {
            tableIngredientImage.setImageBitmap(downloadImage(prod.getImageNutritionUrl()));
        } catch (Exception e) {
            icon = getResources().getDrawable(R.drawable.image_not_found);
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            tableIngredientImage.setImageBitmap(((BitmapDrawable) icon).getBitmap());
            e.printStackTrace();
        }
        portionText.setText(prod.getServing());

        int eneryCalculation = (int) calculateKcal(Integer.parseInt(prod.getEnergy100()));
        String energyKCAL = eneryCalculation + " kcal";

        energyValue.setText(energyKCAL);
        //energyPortion.setText(prod.);

        if (prod.getFat100().length() == 3) {
            grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
        } else {
            if (prod.getFat100().length() <= 2) {
                grassValue.setText(prod.getFat100() + " g");
            }
        }

        //grassPortion.setText();

        if(prod.getSaturated_fat().length() == 3){
            grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
        } else {
            if (prod.getSaturated_fat().length() <= 2) {
                if(prod.getSaturated_fat().length() == 1){
                    grassSaturiValue.setText("0." + prod.getSaturated_fat() + " g");
                } else {
                    grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                }
            }
        }

        return view;
    }

    public Bitmap downloadImage(String imageURL) throws Exception {
        URL newurl = new URL(imageURL);
        Bitmap logo = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        return logo;
    }

    public double calculateKcal(int kj) {
        return (kj / kCalValue);
    }
}
