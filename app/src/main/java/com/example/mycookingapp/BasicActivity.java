package com.example.mycookingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class BasicActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optional,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_recipes:
                intent = new Intent(BasicActivity.this, SingleRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_search:
                intent = new Intent(BasicActivity.this, SingleRecipeActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_add:
                intent = new Intent(BasicActivity.this, AddRecipe.class);
                startActivity(intent);
                break;
            case R.id.menu_login:
                intent = new Intent(BasicActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_signup:
                intent = new Intent(BasicActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_resetPsw:
                intent = new Intent(BasicActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
