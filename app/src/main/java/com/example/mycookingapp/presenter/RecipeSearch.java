package com.example.mycookingapp.presenter;

import com.example.mycookingapp.model.Recipe;
import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearch implements iRecipeSearchPresenter {

    private FirebaseSingleton firebase = FirebaseSingleton.getInstance();
    private DatabaseReference databaseReference;

    @Override
    public List<Recipe> getAllRecipes(DataSnapshot allRecipes){
        List<Recipe> recipeList = new ArrayList<>();
        for(DataSnapshot recipesOfUser: allRecipes.getChildren()) {
            for (DataSnapshot recipeID : recipesOfUser.getChildren()) {
                for (final DataSnapshot recipeSnapshot : recipeID.getChildren()) {
                    Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                    recipeList.add(recipe);
                }
           }
        }
        return recipeList;
    }

    @Override
    public List<Recipe> filterRecipeByCategory(List<Recipe> recipeList, boolean category) {
        List<Recipe> filteredList = new ArrayList<>();
        for(Recipe recipe: recipeList){
            if(recipe.getCategory() == category){
                filteredList.add(recipe);
            }
        }
        return filteredList;
    }

    @Override
    public List<Recipe> getUserRecipes(DataSnapshot userRecipes) {
        List<Recipe> recipeList = new ArrayList<>();
        for (DataSnapshot recipeID : userRecipes.getChildren()) {
            for (final DataSnapshot recipeSnapshot : recipeID.getChildren()) {
                Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                recipeList.add(recipe);
            }
        }
        return recipeList;
    }

}
