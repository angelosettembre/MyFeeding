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

import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;

public class TabellaFragment extends Fragment {

    final double kCalValue = 4.184;
    final double portionG = 100.0;
    int eneryCalculation;

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

        if(prod.getServing() != null){
            /*ENERGIA*/

            if(prod.getEnergy100() != null){
                eneryCalculation = (int) calculateKcal(Integer.parseInt(prod.getEnergy100()));
                String energyKCAL = eneryCalculation + " kcal";

                energyValue.setText(energyKCAL);

                if(prod.getServing().contains(".")){
                    energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)), eneryCalculation))+" kcal");
                } else if(prod.getServing().contains(" g")){
                    energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(" g","")), eneryCalculation))+" kcal");
                } else {
                    String s = prod.getServing().replace("g"," g");
                    energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(s.replace(" g","")), eneryCalculation))+" kcal");
                }
            }
            /*GRASSI*/
            if (prod.getFat100().length() == 3) {
                grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");

                if(prod.getServing().contains(".")){
                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2)))+ " g"));
                } else if(prod.getServing().contains(" g")){
                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2)))+ " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2)))+ " g"));
                }
            } else {
                if (prod.getFat100().length() <= 2) {
                    if(prod.getFat100().length() == 1){
                        grassValue.setText(prod.getFat100() + " g");

                        if(prod.getServing().contains(".")){
                            grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getFat100()))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getFat100()))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getFat100()))+ " g"));
                        }
                    } else {
                        grassValue.setText(prod.getFat100().substring(0,1) + "." + prod.getFat100().charAt(1) + " g");

                        if(prod.getServing().contains(".")){
                            grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*GRASSI SATURI*/
            if(prod.getSaturated_fat().length() == 3) {
                grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                }
            } else {
                if (prod.getSaturated_fat().length() <= 2) {
                    if(prod.getSaturated_fat().length() == 1) {
                        grassSaturiValue.setText("0." + prod.getSaturated_fat() + " g");

                        if (prod.getServing().contains(".")) {
                            grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                        }
                    } else {
                        System.out.println("GRASSI SATURIIIII: "+prod.getSaturated_fat());
                        grassSaturiValue.setText(prod.getSaturated_fat().substring(0,1) + "." + prod.getSaturated_fat().substring(1) + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*CARBOIDRATI*/
            if(prod.getCarbohydrates100().length() == 3){
                carboidratoValue.setText(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                }
            } else {
                if (prod.getCarbohydrates100().length() <= 2) {
                    if(prod.getCarbohydrates100().length() == 1){
                        carboidratoValue.setText("0." + prod.getCarbohydrates100() + " g");

                        if (prod.getServing().contains(".")) {
                            carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                        }
                    } else {
                        carboidratoValue.setText(prod.getCarbohydrates100() + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*ZUCCHERI*/
            if(prod.getSugars100().length() == 3){
                zuccheriValue.setText(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                }
            } else {
                if (prod.getSugars100().length() <= 2) {
                    if(prod.getSugars100().length() == 1){
                        zuccheriValue.setText("0." + prod.getSugars100() + " g");

                        if (prod.getServing().contains(".")) {
                            zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSugars100())) + " g"));
                        }
                    } else {
                        zuccheriValue.setText(prod.getSugars100() + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*PROTEINE*/
            if(prod.getProteins100().length() == 3){
                proteineValue.setText(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                }
            } else {
                if (prod.getProteins100().length() <= 2) {
                    if(prod.getProteins100().length() == 1){
                        proteineValue.setText("0." + prod.getProteins100() + " g");

                        if (prod.getServing().contains(".")) {
                            proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getProteins100())) + " g"));
                        }
                    } else {
                        proteineValue.setText(prod.getProteins100() + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*SODIO*/
            if(prod.getSodium100().length() == 3){
                sodioValue.setText(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                }
            } else {
                if (prod.getSodium100().length() <= 2) {
                    if(prod.getSodium100().length() == 1){
                        sodioValue.setText("0." + prod.getSodium100() + " g");

                        if (prod.getServing().contains(".")) {
                            sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSodium100())) + " g"));
                        }
                    } else {
                        sodioValue.setText(prod.getSodium100() + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*SALE*/
            if(prod.getSalt100().length() == 3){
                saleValue.setText(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(2) + " g");

                if (prod.getServing().contains(".")) {
                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                } else if(prod.getServing().contains(" g")){
                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                } else {
                    String s = prod.getServing().replace("g"," g");
                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                }
            } else {
                if (prod.getSalt100().length() <= 2) {
                    if(prod.getSalt100().length() == 1){
                        saleValue.setText("0." + prod.getSalt100() + " g");

                        if (prod.getServing().contains(".")) {
                            salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100())) + " g"));
                        } else if(prod.getServing().contains(" g")){
                            salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100())) + " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getSalt100())) + " g"));
                        }
                    } else {
                        saleValue.setText(prod.getSalt100() + " g");

                        if(prod.getServing().contains(".")){
                            System.out.println("SATURROOOOOO 2222");
                            salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1)))+ " g"));
                        } else if(prod.getServing().contains(" g")){
                            System.out.println("SATURROOOOOO 3333");
                            salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1)))+ " g"));
                        } else {
                            String s = prod.getServing().replace("g"," g");
                            System.out.println("SATURROOOOOO");
                            salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1)))+ " g"));
                        }
                    }
                }
            }
            /*CACAO*/
            if(prod.getCacao() != null){
                if(prod.getCacao().length() == 3){
                    cacaoValue.setText(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(2) + " g");

                    if (prod.getServing().contains(".")) {
                        cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                    } else if(prod.getServing().contains(" g")){
                        cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                    } else {
                        String s = prod.getServing().replace("g"," g");
                        cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                    }
                } else {
                    if (prod.getCacao().length() <= 2) {
                        if(prod.getCacao().length() == 1){
                            cacaoValue.setText("0." + prod.getCacao() + " g");

                            if (prod.getServing().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao())) + " g"));
                            } else if(prod.getServing().contains(" g")){
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao())) + " g"));
                            } else {
                                String s = prod.getServing().replace("g"," g");
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g","")), Double.parseDouble(prod.getCacao())) + " g"));
                            }
                        } else {
                            cacaoValue.setText(prod.getCacao() + " g");

                            if(prod.getServing().contains(".")){
                                System.out.println("SATURROOOOOO 2222");
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".","").substring(0,2)),  Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1)))+ " g"));
                            } else if(prod.getServing().contains(" g")){
                                System.out.println("SATURROOOOOO 3333");
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g","")),  Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1)))+ " g"));
                            } else {
                                String s = prod.getServing().replace("g"," g");
                                System.out.println("SATURROOOOOO");
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g","")),  Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1)))+ " g"));
                            }
                        }
                    }
                }
            }
        } else {
            /*GRASSI*/
            if(prod.getFat100() != null){
                if (prod.getFat100().length() == 3) {
                    grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
                } else {
                    if (prod.getFat100().length() <= 2) {
                        if(prod.getFat100().length() == 1){
                            grassValue.setText(prod.getFat100() + " g");
                        } else {
                            grassValue.setText(prod.getFat100().substring(0,1) + "." + prod.getFat100().charAt(1) + " g");
                        }
                    }
                }
            }
            /*GRASSI SATURI*/
            if(prod.getSaturated_fat() != null){
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
            }
            /*CARBOIDRATI*/
            if(prod.getCarbohydrates100() != null){
                if(prod.getCarbohydrates100().length() == 3){
                    carboidratoValue.setText(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(2) + " g");
                } else {
                    if (prod.getCarbohydrates100().length() <= 2) {
                        if(prod.getCarbohydrates100().length() == 1){
                            carboidratoValue.setText("0." + prod.getCarbohydrates100() + " g");
                        } else {
                            carboidratoValue.setText(prod.getCarbohydrates100() + " g");
                        }
                    }
                }
            }
            /*ZUCCHERI*/
            if(prod.getSugars100() != null){
                if(prod.getSugars100().length() == 3){
                    zuccheriValue.setText(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2) + " g");
                } else {
                    if (prod.getSugars100().length() <= 2) {
                        if(prod.getSugars100().length() == 1){
                            zuccheriValue.setText("0." + prod.getSugars100() + " g");
                        } else {
                            zuccheriValue.setText(prod.getSugars100() + " g");
                        }
                    }
                }
            }
            /*PROTEINE*/
            if(prod.getProteins100() != null){
                if(prod.getProteins100().length() == 3){
                    proteineValue.setText(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(2) + " g");
                } else {
                    if (prod.getProteins100().length() <= 2) {
                        if(prod.getProteins100().length() == 1){
                            proteineValue.setText("0." + prod.getProteins100() + " g");
                        } else {
                            proteineValue.setText(prod.getProteins100() + " g");
                        }
                    }
                }
            }
            /*SODIO*/
            if(prod.getSodium100() != null){
                if(prod.getSodium100().length() == 3){
                    sodioValue.setText(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(2) + " g");
                } else {
                    if (prod.getSodium100().length() <= 2) {
                        if(prod.getSodium100().length() == 1){
                            sodioValue.setText("0." + prod.getSodium100() + " g");
                        } else {
                            sodioValue.setText(prod.getSodium100() + " g");
                        }
                    }
                }
            }
            /*SALE*/
            if(prod.getSalt100() != null){
                if(prod.getSalt100().length() == 3){
                    saleValue.setText(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(2) + " g");
                } else {
                    if (prod.getSalt100().length() <= 2) {
                        if(prod.getSalt100().length() == 1){
                            saleValue.setText("0." + prod.getSalt100() + " g");
                        } else {
                            saleValue.setText(prod.getSalt100() + " g");
                        }
                    }
                }
            }
            /*CACAO*/
            if(prod.getCacao() != null){
                if(prod.getCacao().length() == 3){
                    cacaoValue.setText(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(2) + " g");
                } else {
                    if (prod.getCacao().length() <= 2) {
                        if(prod.getCacao().length() == 1){
                            cacaoValue.setText("0." + prod.getCacao() + " g");
                        } else {
                            cacaoValue.setText(prod.getCacao() + " g");
                        }
                    }
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

    public int calculatePortionEnergy(int portion, int nutritionalValue){
        double rapp = portionG / portion;
        return (int) (nutritionalValue / rapp);
    }

    public double calculatePortion(int portion, double nutritionalValue){
        System.out.println("PORTION: "+portion);
        System.out.println("NUTRITIONAL VALUE: "+nutritionalValue);

        DecimalFormat df = new DecimalFormat("#.#");
        df.setRoundingMode(RoundingMode.FLOOR);

        double rapp = portionG / portion;
        double nut = nutritionalValue / new Double(df.format(rapp));
        System.out.println("RAPP: "+nut);

        DecimalFormat df2 = new DecimalFormat("#.##");
        df2.setRoundingMode(RoundingMode.FLOOR);


        double result = new Double(df2.format(nut)) * 0.100;
        System.out.println("RESULT: "+result);

        return result;
    }

    public double calculatePortionForTwo(int portion, double nutritionalValue){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.FLOOR);

        double rapp = portionG / portion;
        double nut = nutritionalValue / new Double(df.format(rapp));
        System.out.println("RAPP: "+nut);

        DecimalFormat df2 = new DecimalFormat("#.#");
        df2.setRoundingMode(RoundingMode.FLOOR);


        double result = new Double(df2.format(nut)) * 1.0;
        System.out.println("RESULT: "+result);

        return result;
    }
}
