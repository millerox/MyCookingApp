package com.example.mycookingapp.model;

public class Recipe implements iRecipe {
    private String recipe_name;
    private String recipe_userId;
    private String recipe_id;
    private String recipe_imageURL;
    private String recipe_steps;
    private String recipe_category;
    private String recipe_ingredients;


    public Recipe(){

    }

    public Recipe(String userID, String recipeID, String name, String imageURL,
                  String steps, String ingredients, String category ){
        this.recipe_userId = userID;
        this.recipe_id = recipeID;
        this.recipe_name = name;
        this.recipe_imageURL = imageURL;
        this.recipe_steps = steps;
        this.recipe_ingredients = ingredients;
        this.recipe_category = category;
    }

    public String getRecipe_name() {
        return recipe_name;
    }

    public void setRecipe_name(String recipe_name) {
        this.recipe_name = recipe_name;
    }

    public String getRecipe_userId() {
        return recipe_userId;
    }

    public void setRecipe_userId(String recipe_userId) {
        this.recipe_userId = recipe_userId;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getRecipe_imageURL() {
        return recipe_imageURL;
    }

    public void setRecipe_imageURL(String recipe_imageURL) {
        this.recipe_imageURL = recipe_imageURL;
    }

    public String getRecipe_steps() {
        return recipe_steps;
    }

    public void setRecipe_steps(String recipe_steps) {
        this.recipe_steps = recipe_steps;
    }

    public String getRecipe_category() {
        return recipe_category;
    }

    public void setRecipe_category(String recipe_category) {
        this.recipe_category = recipe_category;
    }

    public String getRecipe_ingredients() {
        return recipe_ingredients;
    }

    public void setRecipe_ingredients(String recipe_ingredients) {
        this.recipe_ingredients = recipe_ingredients;
    }
}
