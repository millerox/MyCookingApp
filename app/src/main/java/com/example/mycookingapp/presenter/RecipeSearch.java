package com.example.mycookingapp.presenter;

import com.example.mycookingapp.model.Recipe;
import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearch implements iRecipeSearch {

    private FirebaseSingleton firebase = FirebaseSingleton.getInstance();

    public List<Recipe> getAllRecipes(DataSnapshot snapshotUserID){
        List<Recipe> recipeList = new ArrayList<>();
        for (DataSnapshot recipeID : snapshotUserID.getChildren()){
            Recipe recipe = new Recipe();
            recipe.setRecipe_userId(snapshotUserID.getKey());
            recipe.setRecipe_name(recipeID.getKey());
            for (final DataSnapshot attributes : recipeID.getChildren()){
                recipe.setRecipe_id(attributes.getKey());
                recipe.setRecipe_imageURL(attributes.child("imageURL").getValue().toString());
                recipe.setRecipe_steps(attributes.child("steps").getValue().toString());
                recipe.setRecipe_category(attributes.child("category").getValue().toString());
                recipe.setRecipe_ingredients(attributes.child("ingredients").getValue().toString());
            }
            recipeList.add(recipe);
        }
        return recipeList;
    }

    @Override
    public void searchRecipeByCategory(DataSnapshot snapshot, boolean category) {

    }

    @Override
    public void searchRecipeByUser(String userID) {

    }
}
