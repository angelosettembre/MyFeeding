package com.unisa.bd2.myfeeding;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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
    private Animator mCurrentAnimatorEffect;
    private int mShortAnimationDurationEffect;
    Bitmap image;

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
            image = downloadImage(prod.getImageNutritionUrl());
            tableIngredientImage.setImageBitmap(image);
        } catch (Exception e) {
            icon = getResources().getDrawable(R.drawable.image_not_found);
            Bitmap bitmap = ((BitmapDrawable) icon).getBitmap();
            image = ((BitmapDrawable) icon).getBitmap();
            tableIngredientImage.setImageBitmap(image);
            e.printStackTrace();
        }

        final View thumbImageView = view.findViewById(R.id.nutritionImage);
        thumbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zoomImageFromThumb(thumbImageView, image);
            }
        });

        mShortAnimationDurationEffect = getResources().getInteger(android.R.integer.config_shortAnimTime);


        portionText.setText(prod.getServing());

        if (prod.getServing() != null) {
            /*ENERGIA*/
            if (prod.getEnergy100() != null) {
                eneryCalculation = (int) calculateKcal(Integer.parseInt(prod.getEnergy100()));
                String energyKCAL = eneryCalculation + " kcal";

                energyValue.setText(energyKCAL);

                if (prod.getServing().length() < 7) {
                    if (prod.getServing().contains(".")) {
                        energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), eneryCalculation)) + " kcal");
                    } else if (prod.getServing().contains(" g")) {
                        energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(" g", "")), eneryCalculation)) + " kcal");
                    } else if (prod.getServing().contains(" ml")) {
                        energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(" ml", "")), eneryCalculation)) + " kcal");
                    } else if (prod.getServing().contains(" cl")) {
                        energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(prod.getServing().replace(" cl", "")), eneryCalculation)) + " kcal");
                    } else {
                        String s = prod.getServing().replace("g", " g");
                        energyPortion.setText(String.valueOf(calculatePortionEnergy(Integer.parseInt(s.replace(" g", "")), eneryCalculation)) + " kcal");
                    }
                }
            }
            /*GRASSI*/
            if (prod.getFat100() != null) {
                if(prod.getFat100().length() > 4){
                    grassValue.setText(prod.getFat100().substring(0, prod.getFat100().length() - 1) + " g");
                }
                if(prod.getFat100().length() == 4){
                    if(prod.getFat100().contains(".")){
                        grassValue.setText(prod.getFat100()+ " g");
                    } else {
                        grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(3))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(3))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(3))) + " ml"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(3))) + " cl"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(3))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getFat100().length() == 3){
                    if(prod.getFat100().contains(".")){
                        grassValue.setText(prod.getFat100() + " g");
                    } else {
                        grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(2))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(2))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(2))) + " ml"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(2))) + " cl"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getFat100().contains(".")){
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(2))) + " g"));
                            } else {
                                grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 3) + "." + prod.getFat100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getFat100().length() <= 2) {
                        if (prod.getFat100().length() == 1) {
                            grassValue.setText(prod.getFat100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    grassPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100())) + " g"));
                                }
                            }
                        } else {
                            grassValue.setText(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1) + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    grassPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }
            /*GRASSI SATURI*/
            if (prod.getSaturated_fat() != null) {
                if(prod.getSaturated_fat().length() > 4){
                    grassSaturiValue.setText(prod.getSaturated_fat().substring(0, prod.getSaturated_fat().length() - 1)+ " g");
                }
                if(prod.getSaturated_fat().length() == 4){
                    if(prod.getSaturated_fat().contains(".")){
                        grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                    } else {
                        grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(3))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(3))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(3))) + " ml"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(3))) + " cl"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(3))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getSaturated_fat().length() == 3) {
                    if(prod.getSaturated_fat().contains(".")){
                        grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                    } else {
                        grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 2) + "." + prod.getSaturated_fat().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(2))) + " ml"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(2))) + " cl"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSaturated_fat().contains(".")){
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            } else {
                                grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 3) + "." + prod.getSaturated_fat().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getSaturated_fat().length() <= 2) {
                        if (prod.getSaturated_fat().length() == 1) {
                            grassSaturiValue.setText("0." + prod.getSaturated_fat() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    grassSaturiPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat())) + " g"));
                                }
                            }
                        } else {
                            System.out.println("GRASSI SATURIIIII: " + prod.getSaturated_fat());
                            grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().substring(1) + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    grassSaturiPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSaturated_fat().substring(0, 1) + "." + prod.getSaturated_fat().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*CARBOIDRATI*/
            if (prod.getCarbohydrates100() != null) {
                if(prod.getCarbohydrates100().length() > 4){
                    carboidratoValue.setText(prod.getCarbohydrates100().substring(0, prod.getCarbohydrates100().length() - 1)+ " g");
                }
                if(prod.getCarbohydrates100().length() == 4){
                    if(prod.getCarbohydrates100().contains(".")){
                        carboidratoValue.setText(prod.getCarbohydrates100()+ " g");
                    } else {
                        carboidratoValue.setText(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(3))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(3))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(3))) + " ml"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(3))) + " cl"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(3))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getCarbohydrates100().length() == 3) {
                    if(prod.getCarbohydrates100().contains(".")){
                        carboidratoValue.setText(prod.getCarbohydrates100() + " g");
                    } else {
                        carboidratoValue.setText(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(2) + " g");
                    }
                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(2))) + " ml"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(2))) + " cl"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getCarbohydrates100().contains(".")){
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            } else {
                                carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 3) + "." + prod.getCarbohydrates100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getCarbohydrates100().length() <= 2) {
                        if (prod.getCarbohydrates100().length() == 1) {
                            carboidratoValue.setText("0." + prod.getCarbohydrates100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    carboidratoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100())) + " g"));
                                }
                            }
                        } else {
                            carboidratoValue.setText(prod.getCarbohydrates100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    carboidratoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCarbohydrates100().substring(0, 1) + "." + prod.getCarbohydrates100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*ZUCCHERI*/
            if (prod.getSugars100() != null) {
                if(prod.getSugars100().length() > 4){
                    zuccheriValue.setText(prod.getSugars100().substring(0, prod.getSugars100().length() - 1)+ " g");
                }
                if (prod.getSugars100().length() == 4) {
                    if(prod.getSugars100().contains(".")){
                        zuccheriValue.setText(prod.getSugars100() + " g");
                    } else {
                        zuccheriValue.setText(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(3))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(3))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2))) + " ml"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(3))) + " cl"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(3))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getSugars100().length() == 3) {
                    if(prod.getSugars100().contains(".")){
                        zuccheriValue.setText(prod.getSugars100() + " g");
                    } else {
                        zuccheriValue.setText(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(2))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(2))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(2))) + " ml"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(2))) + " cl"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSugars100().contains(".")){
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(2))) + " g"));
                            } else {
                                zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 3) + "." + prod.getSugars100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getSugars100().length() <= 2) {
                        if (prod.getSugars100().length() == 1) {
                            zuccheriValue.setText("0." + prod.getSugars100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    zuccheriPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100())) + " g"));
                                }
                            }
                        } else {
                            zuccheriValue.setText(prod.getSugars100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    zuccheriPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSugars100().substring(0, 1) + "." + prod.getSugars100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*PROTEINE*/
            if (prod.getProteins100() != null) {
                if(prod.getProteins100().length() > 4){
                    proteineValue.setText(prod.getProteins100().substring(0, prod.getProteins100().length() - 1)+ " g");
                }
                if (prod.getProteins100().length() == 4) {
                    if(prod.getProteins100().contains(".")){
                        proteineValue.setText(prod.getProteins100() + " g");
                    } else {
                        proteineValue.setText(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(3))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(3))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(3))) + " ml"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(3))) + " cl"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getProteins100().contains(".")) {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(3))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getProteins100().length() == 3) {
                    if(prod.getProteins100().contains(".")){
                        proteineValue.setText(prod.getProteins100() + " g");
                    } else {
                        proteineValue.setText(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(2))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(2))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(2))) + " ml"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getProteins100().contains(".")){
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(2))) + " cl"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getProteins100().contains(".")) {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(2))) + " g"));
                            } else {
                                proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 3) + "." + prod.getProteins100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getProteins100().length() <= 2) {
                        if (prod.getProteins100().length() == 1) {
                            proteineValue.setText("0." + prod.getProteins100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    proteinePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100())) + " g"));
                                }
                            }
                        } else {
                            proteineValue.setText(prod.getProteins100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    proteinePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getProteins100().substring(0, 1) + "." + prod.getProteins100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*SODIO*/
            if (prod.getSodium100() != null) {
                if(prod.getSodium100().length() > 4){
                    sodioValue.setText(prod.getSodium100().substring(0, prod.getSodium100().length() - 1)+ " g");
                }
                if (prod.getSodium100().length() == 4) {
                    if(prod.getSodium100().contains(".")) {
                        sodioValue.setText(prod.getSodium100() + " g");
                    } else {
                        sodioValue.setText(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(3))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(3))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(3))) + " ml"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(3))) + " cl"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(3))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getSodium100().length() == 3) {
                    if(prod.getSodium100().contains(".")) {
                        sodioValue.setText(prod.getSodium100() + " g");
                    } else {
                        sodioValue.setText(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(2))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(2))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(2))) + " ml"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(2))) + " cl"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSodium100().contains(".")) {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(2))) + " g"));
                            } else {
                                sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 3) + "." + prod.getSodium100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getSodium100().length() <= 2) {
                        if (prod.getSodium100().length() == 1) {
                            sodioValue.setText("0." + prod.getSodium100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    sodioPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100())) + " g"));
                                }
                            }
                        } else {
                            sodioValue.setText(prod.getSodium100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    sodioPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSodium100().substring(0, 1) + "." + prod.getSodium100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*SALE*/
            if (prod.getSalt100() != null) {
                if(prod.getSalt100().length() > 4){
                    saleValue.setText(prod.getSalt100().substring(0, prod.getSalt100().length() - 1)+ " g");
                }
                if (prod.getSalt100().length() == 4) {
                    if(prod.getSalt100().contains(".")) {
                        saleValue.setText(prod.getSalt100() + " g");
                    } else {
                        saleValue.setText(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(3))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(3))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(3))) + " ml"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(3))) + " cl"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(3))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getSalt100().length() == 3) {
                    if(prod.getSalt100().contains(".")) {
                        saleValue.setText(prod.getSalt100() + " g");
                    } else {
                        saleValue.setText(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(2))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(2))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(2))) + " ml"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(2))) + " cl"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if(prod.getSalt100().contains(".")) {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(2))) + " g"));
                            } else {
                                salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 3) + "." + prod.getSalt100().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getSalt100().length() <= 2) {
                        if (prod.getSalt100().length() == 1) {
                            saleValue.setText("0." + prod.getSalt100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    salePortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100())) + " g"));
                                }
                            }
                        } else {
                            saleValue.setText(prod.getSalt100() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    salePortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getSalt100().substring(0, 1) + "." + prod.getSalt100().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }

            /*CACAO*/
            if (prod.getCacao() != null) {
                if(prod.getCacao().length() > 4){
                    cacaoValue.setText(prod.getCacao().substring(0, prod.getCacao().length() - 1)+ " g");
                }
                if (prod.getCacao().length() == 4) {
                    if (prod.getCacao().contains(".")) {
                        cacaoValue.setText(prod.getCacao() + " g");
                    } else {
                        cacaoValue.setText(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(3))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(3))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(3))) + " ml"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(3))) + " cl"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(3))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        }
                    }
                } else if (prod.getCacao().length() == 3) {
                    if (prod.getCacao().contains(".")) {
                        cacaoValue.setText(prod.getCacao() + " g");
                    } else {
                        cacaoValue.setText(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(2) + " g");
                    }

                    if (prod.getServing().length() < 7) {
                        if (prod.getServing().contains(".")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(2))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" g")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(2))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        } else if (prod.getServing().contains(" ml")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(2))) + " ml"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " ml"));
                            }
                        } else if (prod.getServing().contains(" cl")) {
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(2))) + " cl"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " cl"));
                            }
                        } else {
                            String s = prod.getServing().replace("g", " g");
                            if (prod.getCacao().contains(".")) {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(2))) + " g"));
                            } else {
                                cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 3) + "." + prod.getCacao().charAt(2))) + " g"));
                            }
                        }
                    }
                } else {
                    if (prod.getCacao().length() <= 2) {
                        if (prod.getCacao().length() == 1) {
                            cacaoValue.setText("0." + prod.getCacao() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao())) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao())) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao())) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao())) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    cacaoPortion.setText(String.valueOf(calculatePortionForTwo(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao())) + " g"));
                                }
                            }
                        } else {
                            cacaoValue.setText(prod.getCacao() + " g");

                            if (prod.getServing().length() < 7) {
                                if (prod.getServing().contains(".")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(".", "").substring(0, 2)), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" g")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1))) + " g"));
                                } else if (prod.getServing().contains(" ml")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" ml", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1))) + " ml"));
                                } else if (prod.getServing().contains(" cl")) {
                                    cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(prod.getServing().replace(" cl", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1))) + " cl"));
                                } else {
                                    String s = prod.getServing().replace("g", " g");
                                    cacaoPortion.setText(String.valueOf(calculatePortion(Integer.parseInt(s.replace(" g", "")), Double.parseDouble(prod.getCacao().substring(0, 1) + "." + prod.getCacao().charAt(1))) + " g"));
                                }
                            }
                        }
                    }
                }
            }
        } else {
            /*ENERGIA*/
            if (prod.getEnergy100() != null) {
                eneryCalculation = (int) calculateKcal(Integer.parseInt(prod.getEnergy100()));
                String energyKCAL = eneryCalculation + " kcal";

                energyValue.setText(energyKCAL);
            }
            /*GRASSI*/
            if (prod.getFat100() != null) {
                if(prod.getFat100().length() > 4){
                    grassValue.setText(prod.getFat100().substring(0, prod.getFat100().length() - 1)+ " g");
                }
                if(prod.getFat100().length() == 4){
                    if (prod.getFat100().contains(".")) {
                        grassValue.setText(prod.getFat100() + " g");
                    }
                } else if (prod.getFat100().length() == 3) {
                    if (prod.getFat100().contains(".")) {
                        grassValue.setText(prod.getFat100() + " g");
                    } else {
                        grassValue.setText(prod.getFat100().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getFat100().length() <= 2) {
                        if (prod.getFat100().length() == 1) {
                            grassValue.setText(prod.getFat100() + " g");
                        } else {
                            grassValue.setText(prod.getFat100().substring(0, 1) + "." + prod.getFat100().charAt(1) + " g");
                        }
                    }
                }
            }
            /*GRASSI SATURI*/
            if (prod.getSaturated_fat() != null) {
                if(prod.getSaturated_fat().length() > 4){
                    grassSaturiValue.setText(prod.getSaturated_fat().substring(0, prod.getSaturated_fat().length() - 1)+ " g");
                }
                if(prod.getSaturated_fat().length() == 4){
                    if (prod.getSaturated_fat().contains(".")) {
                        grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                    }
                } else if (prod.getSaturated_fat().length() == 3) {
                    if (prod.getFat100().contains(".")) {
                        grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                    } else {
                        grassSaturiValue.setText(prod.getSaturated_fat().substring(0, 2) + "." + prod.getFat100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getSaturated_fat().length() <= 2) {
                        if (prod.getSaturated_fat().length() == 1) {
                            grassSaturiValue.setText("0." + prod.getSaturated_fat() + " g");
                        } else {
                            grassSaturiValue.setText(prod.getSaturated_fat() + " g");
                        }
                    }
                }
            }
            /*CARBOIDRATI*/
            if (prod.getCarbohydrates100() != null) {
                if(prod.getCarbohydrates100().length() > 4){
                    carboidratoValue.setText(prod.getCarbohydrates100().substring(0, prod.getCarbohydrates100().length() - 1)+ " g");
                }
                if(prod.getCarbohydrates100().length() == 4){
                    if (prod.getCarbohydrates100().contains(".")) {
                        carboidratoValue.setText(prod.getCarbohydrates100() + " g");
                    }
                } else if (prod.getCarbohydrates100().length() == 3) {
                    if (prod.getCarbohydrates100().contains(".")) {
                        carboidratoValue.setText(prod.getCarbohydrates100() + " g");
                    } else {
                        carboidratoValue.setText(prod.getCarbohydrates100().substring(0, 2) + "." + prod.getCarbohydrates100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getCarbohydrates100().length() <= 2) {
                        if (prod.getCarbohydrates100().length() == 1) {
                            carboidratoValue.setText("0." + prod.getCarbohydrates100() + " g");
                        } else {
                            carboidratoValue.setText(prod.getCarbohydrates100() + " g");
                        }
                    }
                }
            }
            /*ZUCCHERI*/
            if (prod.getSugars100() != null) {
                if(prod.getSugars100().length() > 4){
                    zuccheriValue.setText(prod.getSugars100().substring(0, prod.getSugars100().length() - 1)+ " g");
                }
                if(prod.getSugars100().length() == 4){
                    if (prod.getSugars100().contains(".")) {
                        zuccheriValue.setText(prod.getSugars100() + " g");
                    }
                } else if (prod.getSugars100().length() == 3) {
                    if (prod.getSugars100().contains(".")) {
                        zuccheriValue.setText(prod.getSugars100() + " g");
                    } else {
                        zuccheriValue.setText(prod.getSugars100().substring(0, 2) + "." + prod.getSugars100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getSugars100().length() <= 2) {
                        if (prod.getSugars100().length() == 1) {
                            zuccheriValue.setText("0." + prod.getSugars100() + " g");
                        } else {
                            zuccheriValue.setText(prod.getSugars100() + " g");
                        }
                    }
                }
            }
            /*PROTEINE*/
            if (prod.getProteins100() != null) {
                if(prod.getProteins100().length() > 4){
                    proteineValue.setText(prod.getProteins100().substring(0, prod.getProteins100().length() - 1)+ " g");
                }
                if(prod.getProteins100().length() == 4){
                    if (prod.getProteins100().contains(".")) {
                        proteineValue.setText(prod.getProteins100() + " g");
                    }
                } else if (prod.getProteins100().length() == 3) {
                    if (prod.getProteins100().contains(".")) {
                        proteineValue.setText(prod.getProteins100() + " g");
                    } else {
                        proteineValue.setText(prod.getProteins100().substring(0, 2) + "." + prod.getProteins100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getProteins100().length() <= 2) {
                        if (prod.getProteins100().length() == 1) {
                            proteineValue.setText("0." + prod.getProteins100() + " g");
                        } else {
                            proteineValue.setText(prod.getProteins100() + " g");
                        }
                    }
                }
            }
            /*SODIO*/
            if (prod.getSodium100() != null) {
                if(prod.getSodium100().length() > 4){
                    sodioValue.setText(prod.getSodium100().substring(0, prod.getSodium100().length() - 1)+ " g");
                }
                if(prod.getSodium100().length() == 4){
                    if (prod.getSodium100().contains(".")) {
                        sodioValue.setText(prod.getSodium100() + " g");
                    }
                } else if (prod.getSodium100().length() == 3) {
                    if (prod.getSodium100().contains(".")) {
                        sodioValue.setText(prod.getSodium100() + " g");
                    } else {
                        sodioValue.setText(prod.getSodium100().substring(0, 2) + "." + prod.getSodium100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getSodium100().length() <= 2) {
                        if (prod.getSodium100().length() == 1) {
                            sodioValue.setText("0." + prod.getSodium100() + " g");
                        } else {
                            sodioValue.setText(prod.getSodium100() + " g");
                        }
                    }
                }
            }
            /*SALE*/
            if (prod.getSalt100() != null) {
                if(prod.getSalt100().length() > 4){
                    saleValue.setText(prod.getSalt100().substring(0, prod.getSalt100().length() - 1)+ " g");
                }
                if(prod.getSalt100().length() == 4){
                    if (prod.getSalt100().contains(".")) {
                        saleValue.setText(prod.getSalt100() + " g");
                    }
                } else if (prod.getSalt100().length() == 3) {
                    if (prod.getSalt100().contains(".")) {
                        saleValue.setText(prod.getSalt100() + " g");
                    } else {
                        saleValue.setText(prod.getSalt100().substring(0, 2) + "." + prod.getSalt100().charAt(2) + " g");
                    }
                } else {
                    if (prod.getSalt100().length() <= 2) {
                        if (prod.getSalt100().length() == 1) {
                            saleValue.setText("0." + prod.getSalt100() + " g");
                        } else {
                            saleValue.setText(prod.getSalt100() + " g");
                        }
                    }
                }
            }
            /*CACAO*/
            if (prod.getCacao() != null) {
                if(prod.getCacao().length() > 4){
                    cacaoValue.setText(prod.getCacao().substring(0, prod.getCacao().length() - 1)+ " g");
                }
                if(prod.getCacao().length() == 4){
                    if (prod.getCacao().contains(".")) {
                        cacaoValue.setText(prod.getCacao() + " g");
                    }
                } else if (prod.getCacao().length() == 3) {
                    if (prod.getCacao().contains(".")) {
                        cacaoValue.setText(prod.getCacao() + " g");
                    } else {
                        cacaoValue.setText(prod.getCacao().substring(0, 2) + "." + prod.getCacao().charAt(2) + " g");
                    }
                } else {
                    if (prod.getCacao().length() <= 2) {
                        if (prod.getCacao().length() == 1) {
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

    public int calculatePortionEnergy(int portion, int nutritionalValue) {
        double rapp = portionG / portion;
        return (int) (nutritionalValue / rapp);
    }

    public double calculatePortion(int portion, double nutritionalValue) {
        System.out.println("PORTION: " + portion);
        System.out.println("NUTRITIONAL VALUE: " + nutritionalValue);

        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        double rapp = portionG / portion;
        double nut = (nutritionalValue / Double.parseDouble(df.format(rapp)));
        System.out.println("RAPP: " + nut);

        double result = (Double.parseDouble(df.format((nut))) * 0.100);

        System.out.println("RESULT: " + result);

        return Double.parseDouble(df.format(result));
    }

    public double calculatePortionForTwo(int portion, double nutritionalValue) {
        DecimalFormat df = new DecimalFormat("#0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();

        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        double rapp = portionG / portion;
        double nut = (nutritionalValue / Double.parseDouble(df.format(rapp)));
        System.out.println("RAPP: " + nut);

        double result = (Double.parseDouble(df.format((nut))) * 1.0);

        System.out.println("RESULT: " + result);

        return Double.parseDouble(df.format(result));
    }

    private void zoomImageFromThumb(final View thumbView, Bitmap imageResId) {
        if (mCurrentAnimatorEffect != null) {
            mCurrentAnimatorEffect.cancel();
        }

        final ImageView expandedImageView = (ImageView) view.findViewById(
                R.id.expanded_image);
        expandedImageView.setImageBitmap(imageResId);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        view.findViewById(R.id.frame_layout).getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDurationEffect);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimatorEffect = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimatorEffect = null;
            }
        });
        set.start();
        mCurrentAnimatorEffect = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimatorEffect != null) {
                    mCurrentAnimatorEffect.cancel();
                }

                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDurationEffect);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimatorEffect = null;
                    }
                });
                set.start();
                mCurrentAnimatorEffect = set;
            }
        });
    }
}
