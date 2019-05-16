package com.example.mycookingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.mycookingapp.model.Recipe;
import com.example.mycookingapp.model.RecipeAdapter;
import com.example.mycookingapp.presenter.RecipeSearch;
import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.example.mycookingapp.view.iRecipeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends BasicActivity implements iRecipeView {

    private ListView listViewRecipes;
    private RecipeSearch recipeSearch;
    private List<Recipe> recipeList;

    private FirebaseSingleton firebase = FirebaseSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        //Initializing View
        listViewRecipes = findViewById(R.id.listView_recipes_result);
        // Initialize variables
        recipeList = new ArrayList<>();
        recipeSearch = new RecipeSearch();
        firebase.databaseReference = firebase.database.getReference("recipes");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Display all recipes on the first load
        updateRecipeList();

    }

    public void onCheckedChanged(View view) {
        switch (view.getId()){
            case R.id.radioButton_recipes_all:
                displayRecipes(recipeList);
                break;
            case R.id.radioButton_recipes_food:
                displayRecipes(recipeSearch.filterRecipeByCategory(recipeList,true));
                break;
            case R.id.radioButton_recipes_drink:
                displayRecipes(recipeSearch.filterRecipeByCategory(recipeList,false));
                break;
        }
    }

    public void updateRecipeList(){
        firebase.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot recipes) {
                recipeList.addAll(recipeSearch.getAllRecipes(recipes));
                RecipeAdapter adapter = new RecipeAdapter(RecipesActivity.this,recipeList);
                listViewRecipes.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getDetails());
            }
        });
    }

    @Override
    public void displayRecipes(List<Recipe> recipeList) {
        RecipeAdapter adapter = new RecipeAdapter(RecipesActivity.this,recipeList);
        listViewRecipes.setAdapter(adapter);
    }
}
