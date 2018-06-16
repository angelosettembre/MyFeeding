package com.unisa.bd2.myfeeding;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_WORLD_READABLE;

/**
 * Class support for allergens preference persistence
 */
public class AllergensPersistence {
    private static Set<String> tempSet;
    private static final String PREF_SEARCH_QUERY = "allergerns_preferences";
    private static Context context;

    public static void initContext(Context myContext) {
        context = myContext;
    }

    public static Set<String> loadPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_SEARCH_QUERY, null);
    }

    private static void savePreferences(Set<String> query) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear();
        PreferenceManager.getDefaultSharedPreferences(context).edit().putStringSet(PREF_SEARCH_QUERY, query).commit();
    }

    public static boolean removeElement(String s) {
        tempSet = loadPreferences();
        if (tempSet.contains(s)) {
            tempSet.remove(s);
            savePreferences(tempSet);
            return true;
        } else {
            return false;
        }
    }

    public static boolean insertString(String s) {
        tempSet = loadPreferences();
        if (tempSet == null) {
            tempSet = new HashSet<>();
        }
        if (tempSet.contains(s)) {
            return false;
        } else {
            tempSet.add(s);
            savePreferences(tempSet);
            return true;
        }

    }

    public static int getSize() {
        tempSet = loadPreferences();
        if (tempSet != null) {
            return tempSet.size();
        } else {
            return 0;
        }
    }
}
