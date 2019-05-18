package com.example.mycookingapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

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

public class frgAllRecipes extends Fragment implements iRecipeView {

    private ListView listViewRecipes;
    private RadioGroup radioGroupCategory;
    private RecipeSearch recipeSearch;
    private List<Recipe> recipeList;

    private FirebaseSingleton firebase = FirebaseSingleton.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_recipes, container, false);
        //Initializing Views
        radioGroupCategory = view.findViewById(R.id.radioGroup_recipes_category);
        listViewRecipes = view.findViewById(R.id.listView_recipes_result);
        // Initialize variables
        recipeList = new ArrayList<>();
        recipeSearch = new RecipeSearch();
        firebase.databaseReference = firebase.database.getReference("recipes");
        //Set Radio Group onChecked Listener:
        radioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
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
        });
        return view;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.frg_recipes);
//        //Initializing Views
//        radioGroupCategory = findViewById(R.id.radioGroup_recipes_category);
//        listViewRecipes = findViewById(R.id.listView_recipes_result);
//        // Initialize variables
//        recipeList = new ArrayList<>();
//        recipeSearch = new RecipeSearch();
//        firebase.databaseReference = firebase.database.getReference("recipes");
//        //Set Radio Group onChecked Listener:
//        radioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId){
//                    case R.id.radioButton_recipes_all:
//                        displayRecipes(recipeList);
//                        break;
//                    case R.id.radioButton_recipes_food:
//                        displayRecipes(recipeSearch.filterRecipeByCategory(recipeList,true));
//                        break;
//                    case R.id.radioButton_recipes_drink:
//                        displayRecipes(recipeSearch.filterRecipeByCategory(recipeList,false));
//                        break;
//                }
//            }
//        });
//    }

    @Override
    public void onStart() {
        super.onStart();
        //Display all recipes on the first load
        updateRecipeList(this.getActivity());

    }

    public void updateRecipeList(final Activity activity){
        firebase.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot recipes) {
                recipeList.addAll(recipeSearch.getAllRecipes(recipes));
                RecipeAdapter adapter = new RecipeAdapter(activity,recipeList);
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
        RecipeAdapter adapter = new RecipeAdapter(this.getActivity(),recipeList);
        listViewRecipes.setAdapter(adapter);
    }
}
