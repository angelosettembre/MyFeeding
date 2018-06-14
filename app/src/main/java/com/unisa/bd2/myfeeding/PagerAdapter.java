package com.unisa.bd2.myfeeding;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Prodotto prodotto;
    Bundle bundle;

    public PagerAdapter(FragmentManager fm, int NumOfTabs,Prodotto prodotto) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.prodotto = prodotto;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                bundle = new Bundle();
                bundle.putParcelable("prodotto",prodotto);
                SommarioFragment tab1 = new SommarioFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                bundle = new Bundle();
                bundle.putParcelable("prodotto",prodotto);
                IngredientiFragment tab2 = new IngredientiFragment();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
                bundle = new Bundle();
                bundle.putParcelable("prodotto",prodotto);
                TabellaFragment tab3 = new TabellaFragment();
                tab3.setArguments(bundle);
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
