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

    String quantity;
    String brand;
    String nutritionScore;

    /*Ingredients*/
    String ingredients;
    String ingredientsImageUrl;
    String allergens;


    /*Nutrition*/
    String imageNutritionUrl;
    String energy100, fat100, carbohydrates100, sugars100, fiber100, proteins100, salt100, sodium100;

    /*Additives*/
    String additives;

    /*Ingredient from palm oil*/
    String ingredientPalmOil;

    /*Traces*/
    String traces;

    public Prodotto() {
    }

    public Prodotto(long barcode, String productName, String imageUrl, String genericName, Bitmap imageProduct, String quantity, String brand, String ingredients, String ingredientsImageUrl, String allergens, String imageNutritionUrl, String energy100, String fat100, String carbohydrates100, String sugars100, String fiber100, String proteins100, String salt100, String sodium100, String nutritionScore, String additives, String ingredientPalmOil, String traces) {
        this.barcode = barcode;
        this.productName = productName;
        this.imageUrl = imageUrl;
        this.genericName = genericName;
        this.imageProduct = imageProduct;
        this.quantity = quantity;
        this.brand = brand;
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
        this.nutritionScore = nutritionScore;
        this.additives = additives;
        this.ingredientPalmOil = ingredientPalmOil;
        this.traces = traces;
    }


    protected Prodotto(Parcel in) {
        barcode = in.readLong();
        productName = in.readString();
        imageUrl = in.readString();
        genericName = in.readString();
        imageProduct = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
        quantity = in.readString();
        brand = in.readString();
        ingredients = in.readString();
        ingredientsImageUrl = in.readString();
        allergens = in.readString();
        imageNutritionUrl = in.readString();
        energy100 = in.readString();
        fat100 = in.readString();
        carbohydrates100 = in.readString();
        sugars100 = in.readString();
        fiber100 = in.readString();
        proteins100 = in.readString();
        salt100 = in.readString();
        sodium100 = in.readString();
        nutritionScore = in.readString();
        additives = in.readString();
        ingredientPalmOil = in.readString();
        traces = in.readString();
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
        dest.writeString(quantity);
        dest.writeString(brand);
        dest.writeString(ingredients);
        dest.writeString(ingredientsImageUrl);
        dest.writeString(allergens);
        dest.writeString(imageNutritionUrl);
        dest.writeString(energy100);
        dest.writeString(fat100);
        dest.writeString(carbohydrates100);
        dest.writeString(fiber100);
        dest.writeString(sugars100);
        dest.writeString(proteins100);
        dest.writeString(salt100);
        dest.writeString(sodium100);
        dest.writeString(nutritionScore);
        dest.writeString(additives);
        dest.writeString(ingredientPalmOil);
        dest.writeString(traces);
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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

    public String getEnergy100() {
        return energy100;
    }

    public void setEnergy100(String energy100) {
        this.energy100 = energy100;
    }

    public String getFat100() {
        return fat100;
    }

    public void setFat100(String fat100) {
        this.fat100 = fat100;
    }

    public String getCarbohydrates100() {
        return carbohydrates100;
    }

    public void setCarbohydrates100(String carbohydrates100) {
        this.carbohydrates100 = carbohydrates100;
    }

    public String getSugars100() {
        return sugars100;
    }

    public void setSugars100(String sugars100) {
        this.sugars100 = sugars100;
    }

    public String getFiber100() {
        return fiber100;
    }

    public void setFiber100(String fiber100) {
        this.fiber100 = fiber100;
    }

    public String getProteins100() {
        return proteins100;
    }

    public void setProteins100(String proteins100) {
        this.proteins100 = proteins100;
    }

    public String getSalt100() {
        return salt100;
    }

    public void setSalt100(String salt100) {
        this.salt100 = salt100;
    }

    public String getSodium100() {
        return sodium100;
    }

    public void setSodium100(String sodium100) {
        this.sodium100 = sodium100;
    }

    public String getNutritionScore() {
        return nutritionScore;
    }

    public void setNutritionScore(String nutritionScore) {
        this.nutritionScore = nutritionScore;
    }

    public String getAdditives() {
        return additives;
    }

    public void setAdditives(String additives) {
        this.additives = additives;
    }

    public String getIngredientPalmOil() {
        return ingredientPalmOil;
    }

    public void setIngredientPalmOil(String ingredientPalmOil) {
        this.ingredientPalmOil = ingredientPalmOil;
    }

    public String getTraces() {
        return traces;
    }

    public void setTraces(String traces) {
        this.traces = traces;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "barcode=" + barcode +
                ", productName='" + productName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", genericName='" + genericName + '\'' +
                ", imageProduct=" + imageProduct +
                ", quantity='" + quantity + '\'' +
                ", brand='" + brand + '\'' +
                ", nutritionScore='" + nutritionScore + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", ingredientsImageUrl='" + ingredientsImageUrl + '\'' +
                ", allergens='" + allergens + '\'' +
                ", imageNutritionUrl='" + imageNutritionUrl + '\'' +
                ", energy100='" + energy100 + '\'' +
                ", fat100='" + fat100 + '\'' +
                ", carbohydrates100='" + carbohydrates100 + '\'' +
                ", sugars100='" + sugars100 + '\'' +
                ", fiber100='" + fiber100 + '\'' +
                ", proteins100='" + proteins100 + '\'' +
                ", salt100='" + salt100 + '\'' +
                ", sodium100='" + sodium100 + '\'' +
                ", additives='" + additives + '\'' +
                ", ingredientPalmOil='" + ingredientPalmOil + '\'' +
                ", traces='" + traces + '\'' +
                '}';
    }
}
