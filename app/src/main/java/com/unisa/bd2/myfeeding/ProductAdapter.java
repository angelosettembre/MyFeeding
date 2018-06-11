package com.unisa.bd2.myfeeding;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ProductAdapter extends ArrayAdapter<Prodotto> {
    private LayoutInflater inflater;
    private int resource;
    boolean isImageFitToScreen;

    public ProductAdapter(Context context, int resourceId) {
        super(context, resourceId);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            Log.d("DEBUG", "Inflating view");
            v = inflater.inflate(R.layout.app_detail_listelement, null);
        }

        Prodotto p = getItem(position);
        TextView productName;
        TextView barCode;
        TextView genericName;
        final ImageView image;

        productName = v.findViewById(R.id.nameProduct);
        barCode = v.findViewById(R.id.barCode);
        genericName = v.findViewById(R.id.genericName);
        image = v.findViewById(R.id.imageProduct);

        productName.setText(p.getProductName());
        barCode.setText(String.valueOf(p.getBarcode()));
        genericName.setText(p.getGenericName());
        image.setImageBitmap(p.getImageProduct());

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(150, 150);
                    image.setLayoutParams(params);
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                    image.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                    params.setMargins(0, 100, 0, 0);
                    image.setLayoutParams(params);
                    image.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

        return v;
    }
}
