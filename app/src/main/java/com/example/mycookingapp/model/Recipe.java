package com.example.mycookingapp.model;

import java.util.ArrayList;

public class Recipe implements iRecipe {
    private String name;
    private String imageURL;
    private String steps;
    private boolean category;
    private ArrayList<String> ingredients;


    public Recipe(){

    }

    public Recipe(String name, String imageURL, String steps, ArrayList<String> ingredients, boolean category ){
        this.name = name;
        this.imageURL = imageURL;
        this.steps = steps;
        this.ingredients = ingredients;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public boolean getCategory() {
        return category;
    }

    public void setCategory(boolean category) {
        this.category = category;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}
