package com.example.mycookingapp;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.mycookingapp.model.Recipe;
import com.example.mycookingapp.presenter.RecipeAdapter;
import com.example.mycookingapp.presenter.RecipeSearch;
import com.example.mycookingapp.singleton.FirebaseSingleton;
import com.example.mycookingapp.view.iRecipeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends BasicActivity implements iRecipeView {

    private static String smPICTURE_NOT_AVAILABLE;
    private RadioButton rb_allRecipes;
    private RadioButton rb_food;
    private RadioButton rb_drinks;
    private ListView listViewRecipes;
    private RecipeSearch recipeSearch;

    private FirebaseSingleton firebase = FirebaseSingleton.getInstance();
    List<Recipe> recipeList;
    private ArrayList<ImageView> mImageViews;
    private ArrayList<TextView> mTextViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        //Initializing Views
        rb_allRecipes = findViewById(R.id.radioButton_recipes_all);
        rb_drinks = findViewById(R.id.radioButton_recipes_drink);
        rb_food = findViewById(R.id.radioButton_recipes_food);
        listViewRecipes = findViewById(R.id.listView_recipes_result);
        // Initialize variables
        smPICTURE_NOT_AVAILABLE = "https://www.themezzaninegroup.com/wp-content/uploads/2017/12/photo-not-available.jpg";
        recipeList = new ArrayList<>();
        mImageViews = new ArrayList<>();
        mTextViews = new ArrayList<>();
        recipeSearch = new RecipeSearch();
        firebase.databaseReference = firebase.database.getReference("recipes");
        displayRecipes();
    }

    @Override
    public void displayRecipes() {
        firebase.databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot recipeSnaphot: dataSnapshot.getChildren()){
                    recipeList.addAll(recipeSearch.getAllRecipes(recipeSnaphot));
                    //TODO you have to add elements from recipeList to the primary ArrayList
                }
                RecipeAdapter adapter = new RecipeAdapter(RecipesActivity.this,recipeList);
                listViewRecipes.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("DatabaseError", databaseError.getDetails());
            }
        });
    }
}
