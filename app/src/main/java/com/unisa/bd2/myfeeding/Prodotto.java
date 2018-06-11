package com.unisa.bd2.myfeeding;

import android.os.Parcel;
import android.os.Parcelable;

public class Prodotto implements Parcelable {

    long barcode;
    String productName;
    String imageUrl;
    String genericName;

    public Prodotto() {
    }

    public Prodotto(long barcode, String productName, String imageUrl, String genericName) {
        this.barcode = barcode;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.genericName = genericName;
    }


    protected Prodotto(Parcel in) {
        barcode = in.readLong();
        productName = in.readString();
        imageUrl = in.readString();
        genericName = in.readString();
    }

    public static final Creator<Prodotto> CREATOR = new Creator<Prodotto>() {
        @Override
        public Prodotto createFromParcel(Parcel in) {
            return new Prodotto(in);
        }

        @Override
        public Prodotto[] newArray(int size) {
            return new Prodotto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(barcode);
        dest.writeString(productName);
        dest.writeString(imageUrl);
        dest.writeString(genericName);
    }

    public long getBarcode() {
        return barcode;
    }

    public void setBarcode(long barcode) {
        this.barcode = barcode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGenericName() {
        return genericName;
    }

    public void setGenericName(String genericName) {
        this.genericName = genericName;
    }




}
