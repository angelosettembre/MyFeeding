package com.unisa.bd2.myfeeding;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Arrays;

public class Prodotto implements Parcelable {

    long barcode;
    String productName;
    String imageUrl;
    String genericName;
    Bitmap imageProduct;
    byte[] imageByte;

    public Prodotto() {
    }

    public Prodotto(long barcode, String productName, String imageUrl, String genericName, byte[] imageByte) {
        this.barcode = barcode;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.genericName = genericName;
        this.imageByte = imageByte;
    }

    public byte[] getImageByte() {
        return imageByte;
    }

    public void setImageByte(byte[] imageByte) {
        this.imageByte = imageByte;
    }

    protected Prodotto(Parcel in) {
        barcode = in.readLong();
        productName = in.readString();
        imageUrl = in.readString();
        genericName = in.readString();
        imageByte = new byte[in.readInt()];
        in.readByteArray(imageByte);
        //imageProduct = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
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
        dest.writeInt(imageByte.length);
        dest.writeByteArray(imageByte);
        //dest.writeValue(imageProduct);
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

    public Bitmap getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(Bitmap imageProduct) {
        this.imageProduct = imageProduct;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "barcode=" + barcode +
                ", productName='" + productName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", genericName='" + genericName + '\'' +
                ", imageProduct=" + imageProduct +
                ", imageByte=" + Arrays.toString(imageByte) +
                '}';
    }
}
