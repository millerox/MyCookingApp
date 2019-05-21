package com.example.mycookingapp.model;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mycookingapp.R;
import com.example.mycookingapp.SingleRecipeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Activity context;
    private List<Recipe>recipeList;

    public RecipeAdapter(Activity context, List<Recipe>recipeList){
        super(context, R.layout.custom_view_recipes,recipeList);
        this.context = context;
        this.recipeList = recipeList;
    }

    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_view_recipes,null,true);

        TextView recipeName = (TextView)customView.findViewById(R.id.tv_recipes_description);
        ImageView recipePhoto = (ImageView)customView.findViewById(R.id.img_recipes_photo);

        final Recipe recipe = recipeList.get(position);
        recipeName.setText(recipe.getName());
        Picasso.get().load(recipe.getImageURL()).into(recipePhoto);

        //Click on recipe -> Go to Single Recipe Activity
        customView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleRecipeActivity.class);
                intent.putExtra("name", recipe.getName());
                intent.putExtra("imageURL", recipe.getImageURL());
                intent.putExtra("ingredients", recipe.getIngredients());
                intent.putExtra("steps", recipe.getSteps());
                context.startActivity(intent);
            }
        });

        return customView;
    }
}
