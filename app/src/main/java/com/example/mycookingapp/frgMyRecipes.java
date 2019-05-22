package com.example.mycookingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.mycookingapp.model.Recipe;
import com.example.mycookingapp.model.RecipeAdapter;
import com.example.mycookingapp.presenter.RecipeSearch;
import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class frgMyRecipes extends frgAllRecipes {

    // The Fragment can display two views depending on the scenario:
    //  1. First Scenario: the current user does have recipes ( display frg_recipes view)
    //  2. Second Scenario: a user is null, or the current user DOESN'T have recipes yet (display frg_my_recipes_empty view)
    private FirebaseSingleton firebase;
    private FirebaseAuth firebaseAuth;
    private boolean isUserRegistered;
    private boolean hasRecipes;
    private View view;
    // 1. Set of variables for the First Scenario
    private TextView tv_msg;
    private Button btn_login_or_add;
    // 2. Set of variables for the Second Scenario
    private ListView listViewRecipes;
    private RadioGroup radioGroupCategory;
    private RecipeSearch recipeSearch;
    private List<Recipe> myRecipeList;
    private String userID;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_my_recipes_empty, container, false);
        //Initialite variables
        firebase = FirebaseSingleton.getInstance();
        firebaseAuth = firebase.authentication;
        if (firebaseAuth.getCurrentUser() == null) {
            isUserRegistered = false;
        } else {
            isUserRegistered = true;
        }
        //Initialize views for first scenario
        tv_msg = view.findViewById(R.id.tv_myRecipes_msg);
        btn_login_or_add = view.findViewById(R.id.btn_myRecipes_redirect);
        //Set onclick listener for button
        btn_login_or_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUserRegistered) {
                    Intent intent = new Intent(getActivity(), AddRecipeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        //Update UI according to is user registered or not, has recipes or not
        if (!isUserRegistered) {
            tv_msg.setText("Only registered users can add recipes");
            btn_login_or_add.setText("LOGIN");
        } else {
            //If user has recipes, display the recipes, otherwise set onClick Listener for button
            userID = firebaseAuth.getCurrentUser().getUid();
            hasRecipes = true;

            if(hasRecipes){
                view = inflater.inflate(R.layout.frg_recipes, container, false);
                //Initialize views & variables for the second scenario
                databaseReference = firebase.database.getReference("recipes");
                myRecipeList = new ArrayList<>();
                recipeSearch = new RecipeSearch();
                radioGroupCategory = view.findViewById(R.id.radioGroup_recipes_category);
                listViewRecipes = view.findViewById(R.id.listView_recipes_result);
                //Set Radio Group onChecked Listener:
                radioGroupCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radioButton_recipes_all:
                                displayRecipes(myRecipeList,listViewRecipes);
                                break;
                            case R.id.radioButton_recipes_food:
                                displayRecipes(recipeSearch.filterRecipeByCategory(myRecipeList,true),listViewRecipes);
                                break;
                            case R.id.radioButton_recipes_drink:
                                displayRecipes(recipeSearch.filterRecipeByCategory(myRecipeList,false),listViewRecipes);
                                break;
                        }
                    }
                });
                updateRecipeList(this.getActivity());
            }
        }
        return view;
    }

    @Override
    public void updateRecipeList(final Activity activity){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot recipes) {
                if(recipes.hasChild(userID)) {
                    myRecipeList.addAll(recipeSearch.getUserRecipes(recipes.child(userID)));
                    RecipeAdapter adapter = new RecipeAdapter(activity, myRecipeList);
                    listViewRecipes.setAdapter(adapter);
                }else{

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getDetails());
            }
        });
    }
}


