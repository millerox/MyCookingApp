package com.example.mycookingapp.view;

import android.app.Activity;
import android.widget.ListView;

import com.example.mycookingapp.model.Recipe;

import java.util.List;

public interface iRecipeView {
    void updateRecipeList(final Activity activity);
    void displayRecipes(List<Recipe> recipeList, ListView listViewRecipes);
}
