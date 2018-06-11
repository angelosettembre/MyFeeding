package com.unisa.bd2.myfeeding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class ProductAdapter extends ArrayAdapter<Prodotto> {
    private LayoutInflater inflater;
    private int resource;

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
        ImageView image;

        productName = v.findViewById(R.id.nameProduct);
        barCode = v.findViewById(R.id.barCode);
        genericName = v.findViewById(R.id.genericName);
        image = v.findViewById(R.id.imageProduct);

        productName.setText(p.getProductName());
        barCode.setText((int) p.getBarcode());
        genericName.setText(p.getGenericName());

        new DownLoadImageTask(image).execute(p.getImageUrl());

        return v;
    }

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}
