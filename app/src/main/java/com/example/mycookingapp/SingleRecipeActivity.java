package com.example.mycookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SingleRecipeActivity extends BasicActivity {

    ImageView img_photo;
    TextView tv_recipeName;
    TextView tv_ingredients;
    TextView tv_instructions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_recipe);

        //Initialize views
        img_photo = findViewById(R.id.img_singleRecipe_photo);
        tv_recipeName = findViewById(R.id.tv_singleRecipe_name);
        tv_ingredients = findViewById(R.id.tv_singleRecipe_ingredients);
        tv_instructions = findViewById(R.id.tv_singleRecipe_steps);

        //Getting parameters from Recipe Adapter
        Intent intent = getIntent();
        final String strRecipeName = intent.getStringExtra("name");
        final ArrayList<String> strIngredients = intent.getStringArrayListExtra("ingredients");
        final String strSteps = intent.getStringExtra("steps");
        final String strImageURL = intent.getStringExtra("imageURL");

        displayRecipe(strRecipeName, strImageURL, strIngredients, strSteps);
    }

    private void displayRecipe(String recipeName, String imgURL, ArrayList<String> ingredients, String steps){
        Picasso.get().load(imgURL).into(img_photo);
        tv_recipeName.setText(recipeName);
        tv_instructions.setText(steps);
        tv_ingredients.setText(castToString(ingredients));
    }

    private String castToString(ArrayList<String> arrayList){
        String ingredients = "";
        for(int i=0; i < arrayList.size(); i++){
            ingredients += arrayList.get(i);
            ingredients += "\n";
        }
        return ingredients;
    }
}
