package com.example.mycookingapp.presenter;

import com.example.mycookingapp.model.Recipe;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public interface iRecipeSearch {
    List<Recipe> getAllRecipes(DataSnapshot snapshot);
    List<Recipe> getUserRecipes(DataSnapshot snapshot);
    List<Recipe> filterRecipeByCategory(List<Recipe> recipeList, boolean category);

}
