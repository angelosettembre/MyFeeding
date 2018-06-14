package com.unisa.bd2.myfeeding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Prodotto prodotto;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,Prodotto prodotto) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.prodotto = prodotto;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putParcelable("prodotto",prodotto);
                SommarioFragment tab1 = new SommarioFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                IngredientiFragment tab2 = new IngredientiFragment();
                return tab2;
            case 2:
                TabellaFragment tab3 = new TabellaFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

}
