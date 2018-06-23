package com.unisa.bd2.myfeeding;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class AllergensController {
    public static String[] lattosio = {"lattosio", "latte", "Latte", "LATTE", "burro"};
    public static String[] soia = {"soia"};
    public static String[] glutine = {"glutine", "frumento", "farro", "grano", "orzo", "avena", "segale"};
    public static String[] arachidi = {"nocciole", "nocciola", "arachidi"};
    static String[] lattosioCheck = {"milk", "lactose", "butter", "beurre", "lait", "lactose", "Laktose ", "Milch", "Butter", "lactosérum", "Magermilchpulver", "Süßmolkenpulver", "Yaourt", "cream", "latte", "burro"};
    static String[] soiaCheck = {"soje", "soia", "soja"};
    static String[] glutineCheck = {"Weizenmalz", "Gerstenmalz", "cebada", "glúten", "trigo", "orge","glutine","frumento", "farro", "grano", "orzo", "avena", "segale"};
    static String[] arachidiCheck = {"Haselnüsse", "nocciole", "nocciola", "arachidi", "noisettes"};

    static private Set<String> allergens;
    static private Set<String> problemFound;
    static View view;
    static Context context;

    public static void checkProduct(Context contextPassed, String allergensOfProduct) {
        allergens = AllergensPersistence.loadPreferences();

        context = contextPassed;
        if (allergens != null && allergensOfProduct != null) {
            problemFound = new HashSet<>();
            for (String a : allergens) {
                if (a.equals("Lattosio")) {
                    for (String item : lattosioCheck) {
                        if (StringUtils.containsIgnoreCase(allergensOfProduct, item)) {
                            problemFound.add("Lattosio");
                        }
                    }
                } else if (a.equals("Soia")) {
                    for (String item : soiaCheck) {
                        if (StringUtils.containsIgnoreCase(allergensOfProduct, item)) {
                            problemFound.add("Soia");
                        }
                    }
                } else if (a.equals("Glutine")) {
                    for (String item : glutineCheck) {
                        if (StringUtils.containsIgnoreCase(allergensOfProduct, item)) {
                            problemFound.add("Glutine");
                        }
                    }
                } else if (a.equals("Arachidi")) {
                    for (String item : arachidiCheck) {
                        if (StringUtils.containsIgnoreCase(allergensOfProduct, item)) {
                            problemFound.add("Arachidi");
                        }
                    }
                } else {
                }
            }

            if (problemFound.isEmpty() == false) {
                launchAlert(problemFound);
            }
        }
    }

    private static void launchAlert(Set<String> allergen) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("ATTENZIONE")
                .setMessage("CONTIENE DEGLI INGREDIENTI CHE NON PUOI MANGIARE " + allergen.toString()).setNeutralButton("HO CAPITO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

}
