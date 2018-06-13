package com.unisa.bd2.myfeeding;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Prodotto implements Parcelable {
    /*Summary*/
    long barcode;
    String productName;
    String imageUrl;
    String genericName;
    Bitmap imageProduct;

    int quantity;
    String brand;
    String link;

    /*Ingredients*/
    String ingredients;
    String ingredientsImageUrl;
    String allergens;


    /*Nutrition*/
    String imageNutritionUrl;
    int energy100, fat100, carbohydrates100, sugars100, fiber100, proteins100, salt100, sodium100;

    public Prodotto() {
    }

    public Prodotto(long barcode, String productName, String imageUrl, String genericName, Bitmap imageProduct, int quantity, String brand, String link, String ingredients, String ingredientsImageUrl, String allergens, String imageNutritionUrl, int energy100, int fat100, int carbohydrates100, int sugars100, int fiber100, int proteins100, int salt100, int sodium100) {
        this.barcode = barcode;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.genericName = genericName;
        this.imageProduct = imageProduct;
        this.quantity = quantity;
        this.brand = brand;
        this.link = link;
        this.ingredients = ingredients;
        this.ingredientsImageUrl = ingredientsImageUrl;
        this.allergens = allergens;
        this.imageNutritionUrl = imageNutritionUrl;
        this.energy100 = energy100;
        this.fat100 = fat100;
        this.carbohydrates100 = carbohydrates100;
        this.sugars100 = sugars100;
        this.fiber100 = fiber100;
        this.proteins100 = proteins100;
        this.salt100 = salt100;
        this.sodium100 = sodium100;
    }


    protected Prodotto(Parcel in) {
        barcode = in.readLong();
        productName = in.readString();
        imageUrl = in.readString();
        genericName = in.readString();
        imageProduct = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        quantity = in.readInt();
        brand = in.readString();
        link = in.readString();
        ingredients = in.readString();
        ingredientsImageUrl = in.readString();
        allergens = in.readString();
        imageNutritionUrl = in.readString();
        energy100 = in.readInt();
        fat100 = in.readInt();
        carbohydrates100 = in.readInt();
        sugars100 = in.readInt();
        fiber100 = in.readInt();
        proteins100 = in.readInt();
        salt100 = in.readInt();
        sodium100 = in.readInt();
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
        dest.writeValue(imageProduct);
        dest.writeInt(quantity);
        dest.writeString(brand);
        dest.writeString(link);
        dest.writeString(ingredients);
        dest.writeString(ingredientsImageUrl);
        dest.writeString(allergens);
        dest.writeString(imageNutritionUrl);
        dest.writeInt(energy100);
        dest.writeInt(fat100);
        dest.writeInt(carbohydrates100);
        dest.writeInt(fiber100);
        dest.writeInt(proteins100);
        dest.writeInt(salt100);
        dest.writeInt(sodium100);
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getIngredientsImageUrl() {
        return ingredientsImageUrl;
    }

    public void setIngredientsImageUrl(String ingredientsImageUrl) {
        this.ingredientsImageUrl = ingredientsImageUrl;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public String getImageNutritionUrl() {
        return imageNutritionUrl;
    }

    public void setImageNutritionUrl(String imageNutritionUrl) {
        this.imageNutritionUrl = imageNutritionUrl;
    }

    public int getEnergy100() {
        return energy100;
    }

    public void setEnergy100(int energy100) {
        this.energy100 = energy100;
    }

    public int getFat100() {
        return fat100;
    }

    public void setFat100(int fat100) {
        this.fat100 = fat100;
    }

    public int getCarbohydrates100() {
        return carbohydrates100;
    }

    public void setCarbohydrates100(int carbohydrates100) {
        this.carbohydrates100 = carbohydrates100;
    }

    public int getSugars100() {
        return sugars100;
    }

    public void setSugars100(int sugars100) {
        this.sugars100 = sugars100;
    }

    public int getFiber100() {
        return fiber100;
    }

    public void setFiber100(int fiber100) {
        this.fiber100 = fiber100;
    }

    public int getProteins100() {
        return proteins100;
    }

    public void setProteins100(int proteins100) {
        this.proteins100 = proteins100;
    }

    public int getSalt100() {
        return salt100;
    }

    public void setSalt100(int salt100) {
        this.salt100 = salt100;
    }

    public int getSodium100() {
        return sodium100;
    }

    public void setSodium100(int sodium100) {
        this.sodium100 = sodium100;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "barcode=" + barcode +
                ", productName='" + productName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", genericName='" + genericName + '\'' +
                ", imageProduct=" + imageProduct +
                ", quantity=" + quantity +
                ", brand='" + brand + '\'' +
                ", link='" + link + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", ingredientsImageUrl='" + ingredientsImageUrl + '\'' +
                ", allergens='" + allergens + '\'' +
                ", imageNutritionUrl='" + imageNutritionUrl + '\'' +
                ", energy100=" + energy100 +
                ", fat100=" + fat100 +
                ", carbohydrates100=" + carbohydrates100 +
                ", sugars100=" + sugars100 +
                ", fiber100=" + fiber100 +
                ", proteins100=" + proteins100 +
                ", salt100=" + salt100 +
                ", sodium100=" + sodium100 +
                '}';
    }

}
