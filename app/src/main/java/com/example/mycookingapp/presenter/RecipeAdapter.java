package com.example.mycookingapp.presenter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycookingapp.R;
import com.example.mycookingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends ArrayAdapter<Recipe> {

    private Activity context;
    private List<Recipe>recipeList;

    public RecipeAdapter(Activity context, List<Recipe>recipeList){
        super(context, R.layout.custom_view,recipeList);
        this.context = context;
        this.recipeList = recipeList;
    }


    @Nullable
    @Override
    public View getView(int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View customView = inflater.inflate(R.layout.custom_view,null,true);

        TextView recipeName = (TextView)customView.findViewById(R.id.tv_recipes_description);
        ImageView recipePhoto = (ImageView)customView.findViewById(R.id.img_recipes_photo);

        Recipe recipe = recipeList.get(position);
        recipeName.setText(recipe.getRecipe_name());
        Picasso.get().load(recipe.getRecipe_imageURL()).into(recipePhoto);

        return customView;
    }
}
